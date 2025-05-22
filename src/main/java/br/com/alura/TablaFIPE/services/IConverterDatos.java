package br.com.alura.TablaFIPE.services;

import java.util.List;

public interface IConverterDatos {
    <T> T obtenerDatos(String json, Class<T> classe);

    <T> List<T> obtenerLista(String json, Class<T> classe);
}
