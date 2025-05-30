import java.util.*;

class Recomendador {
    List<Usuario> usuarios;

    public Recomendador() {
        usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public List<Pelicula> recomendar(Usuario objetivo) {
        Set<Pelicula> recomendaciones = new HashSet<>();

        for (Usuario otro : usuarios) {
            if (!otro.equals(objetivo)) {
                double similitud = calcularSimilitud(objetivo, otro);
                if (similitud > 0.3) {  // umbral ajustable
                    for (Pelicula p : otro.favoritas) {
                        if (!objetivo.favoritas.contains(p) && !objetivo.noGustadas.contains(p)) {
                            recomendaciones.add(p);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(recomendaciones);
    }
public double calcularSimilitud(Usuario u1, Usuario u2) {
        Set<Pelicula> interseccion = new HashSet<>(u1.favoritas);
        interseccion.retainAll(u2.favoritas);

        Set<Pelicula> union = new HashSet<>(u1.favoritas);
        union.addAll(u2.favoritas);

        if (union.isEmpty()) return 0.0;
        return (double) interseccion.size() / union.size();  // Índice de Jaccard
    }
}