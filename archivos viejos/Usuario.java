import java.util.*;

class Usuario {
    String nombre;
    int edad;
    Set<Pelicula> favoritas = new HashSet<>();
    Set<Pelicula> noGustadas = new HashSet<>();

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public void marcarComoFavorita(Pelicula p) {
        favoritas.add(p);
        p.usuariosQueLaVieron.add(this);
    }

    public void marcarComoNoGustada(Pelicula p) {
        noGustadas.add(p);
        p.usuariosQueLaVieron.add(this);
    }
}