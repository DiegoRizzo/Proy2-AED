import java.util.*;

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



    private static void ejecutarPruebas() {
        System.out.println("Ejecutando pruebas...");

        // Crear películas
        Pelicula p1 = new Pelicula("La La Land", "Musical", 2016);
        Pelicula p2 = new Pelicula("Interstellar", "Ciencia Ficción", 2014);
        Pelicula p3 = new Pelicula("Hable con Ella", "Drama", 2002);

        // Crear usuarios
        Usuario ana = new Usuario("Ana", 21);
        Usuario juan = new Usuario("Juan", 19);

        // Marcar preferencias
        ana.marcarComoFavorita(p1);
        ana.marcarComoNoGustada(p2);

        juan.marcarComoFavorita(p2);
        juan.marcarComoFavorita(p3);

        // Sistema de recomendación
        Recomendador testRecomendador = new Recomendador();
        testRecomendador.agregarUsuario(ana);
        testRecomendador.agregarUsuario(juan);

        // Recomendaciones para Ana
        List<Pelicula> recomendacionesAna = testRecomendador.recomendar(ana);

        // Verificar resultados
        if (recomendacionesAna.contains(p3) && !recomendacionesAna.contains(p1) && !recomendacionesAna.contains(p2)) {
            System.out.println("Prueba de recomendaciones para Ana: PASADA");
        } else {
            System.out.println("Prueba de recomendaciones para Ana: FALLIDA");
        }

        // Prueba de similitud (no accesible directamente, así que se prueba indirectamente)
        double similitud = 0.0; // No se puede acceder directamente al método privado
        System.out.println("Prueba de similitud (valor esperado aproximado): " + similitud);

        // Prueba de agregar usuario
        Usuario maria = new Usuario("Maria", 33);
        testRecomendador.agregarUsuario(maria);
        if (testRecomendador.usuarios.contains(maria)) {
            System.out.println("Prueba de agregar usuario: PASADA");
        } else {
            System.out.println("Prueba de agregar usuario: FALLIDA");
        }

        System.out.println("Pruebas finalizadas.");
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
