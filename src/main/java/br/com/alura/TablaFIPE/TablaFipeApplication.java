package br.com.alura.TablaFIPE;

import br.com.alura.TablaFIPE.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TablaFipeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TablaFipeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();

    }
}
