package com.example.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class mainclass {
    private static Scanner scanner = new Scanner(System.in);
    private static RecomendadorNeo4j recomendadorNeo4j;
    private static Map<String, Pelicula> peliculas = new HashMap<>();

    public static void main(String[] args) {
        // Initialize Neo4j connection
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "12345678";
        recomendadorNeo4j = new RecomendadorNeo4j(uri, user, password);

        System.out.println("Bienvenido al sistema de recomendación de películas con Neo4j");
        boolean salir = false;

        while (!salir) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Agregar película");
            System.out.println("2. Marcar película como favorita");
            System.out.println("3. Marcar película como no gustada");
            System.out.println("4. Obtener recomendaciones para un usuario");
            System.out.println("5. Salir");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    agregarPelicula();
                    break;
                case "2":
                    marcarFavorita();
                    break;
                case "3":
                    marcarNoGustada();
                    break;
                case "4":
                    obtenerRecomendaciones();
                    break;
                case "5":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        // Close Neo4j connection
        recomendadorNeo4j.close();
        System.out.println("Saliendo del sistema. ¡Hasta luego!");
    }

    private static void agregarPelicula() {
        System.out.print("Ingrese el título de la película: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el género de la película: ");
        String genero = scanner.nextLine();
        System.out.print("Ingrese el año de la película: ");
        int anio = Integer.parseInt(scanner.nextLine());

        if (peliculas.containsKey(titulo)) {
            System.out.println("La película ya existe.");
            return;
        }

        Pelicula nuevaPelicula = new Pelicula(titulo, genero, anio);
        peliculas.put(titulo, nuevaPelicula);
        System.out.println("Película agregada exitosamente.");
    }

    private static void marcarFavorita() {
        System.out.print("Ingrese el código del usuario: ");
        String codigoUsuario = scanner.nextLine();
        System.out.print("Ingrese el título de la película: ");
        String tituloPelicula = scanner.nextLine();

        Pelicula pelicula = peliculas.get(tituloPelicula);

        if (pelicula == null) {
            System.out.println("Película no encontrada.");
            return;
        }

        recomendadorNeo4j.agregarPreferencia(codigoUsuario, tituloPelicula, true);
        System.out.println("Película marcada como favorita para el usuario " + codigoUsuario + ".");
    }

    private static void marcarNoGustada() {
        System.out.print("Ingrese el código del usuario: ");
        String codigoUsuario = scanner.nextLine();
        System.out.print("Ingrese el título de la película: ");
        String tituloPelicula = scanner.nextLine();

        Pelicula pelicula = peliculas.get(tituloPelicula);

        if (pelicula == null) {
            System.out.println("Película no encontrada.");
            return;
        }

        recomendadorNeo4j.agregarPreferencia(codigoUsuario, tituloPelicula, false);
        System.out.println("Película marcada como no gustada para el usuario " + codigoUsuario + ".");
    }

    private static void obtenerRecomendaciones() {
        System.out.print("Ingrese el código del usuario: ");
        String codigoUsuario = scanner.nextLine();

        List<String> recomendaciones = recomendadorNeo4j.recomendar(codigoUsuario);

        if (recomendaciones.isEmpty()) {
            System.out.println("No hay recomendaciones disponibles para este usuario.");
        } else {
            System.out.println("Recomendaciones para el usuario " + codigoUsuario + ":");
            for (String titulo : recomendaciones) {
                System.out.println("- " + titulo);
            }
        }
    }
}
