package br.com.alura.TablaFIPE.principal;

import br.com.alura.TablaFIPE.model.Datos;
import br.com.alura.TablaFIPE.model.Modelos;
import br.com.alura.TablaFIPE.model.Vehiculo;
import br.com.alura.TablaFIPE.services.ConsumoAPI;
import br.com.alura.TablaFIPE.services.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();


    public void exibeMenu() {

        System.out.println("***********************************************************************");
        var menu = """
                OPCIONES
                Carro
                Moto
                Camión
                
                Digite una de las opciones para consultar: 
                """;

        System.out.println(menu);
        var opcion = scanner.nextLine();

        String direccion = "";

        if (opcion.toLowerCase().contains("carr")) {
            direccion = URL_BASE + "carros/marcas";
        } else if (opcion.toLowerCase().contains("mot")) {
            direccion = URL_BASE + "motos/marcas";
        } else if (opcion.toLowerCase().contains("cami")) {
            direccion = URL_BASE + "caminhoes/marcas";
        }

        var json = consumoAPI.obtenerDatos(direccion);
        System.out.println(json);

        var marcas = convierteDatos.obtenerLista(json, Datos.class);
        marcas.stream()
                .sorted(Comparator.comparing(Datos::codigo))
                .forEach(System.out::println);
        System.out.println("***********************************************************************");
        System.out.println("\nInforme el código de la marca para consultar ");
        var codigoMarca = scanner.next();

        direccion = direccion + "/" + codigoMarca + "/modelos";
        json = consumoAPI.obtenerDatos(direccion);
        var modeloLista = convierteDatos.obtenerDatos(json, Modelos.class);
        System.out.println("\nModelos de la marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Datos::codigo))
                .forEach(System.out::println);

        System.out.println("***********************************************************************");
        System.out.println("\nDigite un trecho del nombre del carro a buscar: ");
        var nomeVehiculo = scanner.next();
        List<Datos> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVehiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);
        System.out.println("***********************************************************************");
        System.out.println("\nDigite el código del modelo para buscar los modelos de evaluación: ");
        var codigoModelo = scanner.next();
        direccion = direccion + "/" + codigoModelo + "/anos";
        json = consumoAPI.obtenerDatos(direccion);
        List<Datos> anos = convierteDatos.obtenerLista(json, Datos.class);
        List<Vehiculo> vehiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var direccionAnos = direccion + "/" + anos.get(i).codigo();
            json = consumoAPI.obtenerDatos(direccionAnos);
            Vehiculo vehiculo = convierteDatos.obtenerDatos(json, Vehiculo.class);
            vehiculos.add(vehiculo);
        }
        System.out.println("***********************************************************************");
        System.out.println("Todos los vehículos filtrados con evaluaciones por año: ");
        vehiculos.forEach(System.out::println);

    }
}
