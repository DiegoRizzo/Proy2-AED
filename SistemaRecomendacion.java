import  java.util.*;

public class SistemaRecomendacion {
    public static void main(String[] args) {
        // Crear películas
        Pelicula p1 = new Pelicula("La La Land", "Musical", 2016);
        Pelicula p2 = new Pelicula("Interstellar", "Ciencia Ficción", 2014);
        Pelicula p3 = new Pelicula("Hable con Ella", "Drama", 2002);

        // Crear usuarios
        Usuario ana = new Usuario("Ana", 21);
        Usuario juan = new Usuario("Juan", 19);
        Usuario maria = new Usuario("Maria", 33);

        // Marcar preferencias
        ana.marcarComoFavorita(p1);
        ana.marcarComoNoGustada(p2);

        juan.marcarComoFavorita(p2);
        juan.marcarComoFavorita(p3);

        maria.marcarComoFavorita(p1);
        maria.marcarComoFavorita(p3);

        // Sistema de recomendación
        Recomendador recomendador = new Recomendador();
        recomendador.agregarUsuario(ana);
        recomendador.agregarUsuario(juan);
        recomendador.agregarUsuario(maria);

        // Recomendaciones para Ana
        List<Pelicula> recomendacionesAna = recomendador.recomendar(ana);
        System.out.println("Recomendaciones para Ana:");
        for (Pelicula p : recomendacionesAna) {
            System.out.println("- " + p);
        }
    }
}