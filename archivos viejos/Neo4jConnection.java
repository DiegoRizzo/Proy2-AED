import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import static org.neo4j.driver.Values.parameters;

public class Neo4jConnection implements AutoCloseable {
    private final Driver driver;

    public Neo4jConnection(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() {
        driver.close();
    }

    public void crearPreferencia(String codigoUsuario, String tituloPelicula, boolean favorita) {
        try (Session session = driver.session()) {
            String query = "MERGE (u:Usuario {codigo: $codigoUsuario}) " +
                           "MERGE (p:Pelicula {titulo: $tituloPelicula}) " +
                           "MERGE (u)-[r:TIENE_PREFERENCIA]->(p) " +
                           "SET r.favorita = $favorita";
            session.run(query, parameters("codigoUsuario", codigoUsuario, "tituloPelicula", tituloPelicula, "favorita", favorita));
        }
    }

    public java.util.List<String> recomendarPeliculas(String codigoUsuario) {
        try (Session session = driver.session()) {
            String query =
                "MATCH (u:Usuario {codigo: $codigoUsuario})-[:TIENE_PREFERENCIA {favorita: true}]->(p:Pelicula) " +
                "WITH u, collect(p) AS favoritasUsuario " +
                "MATCH (otros:Usuario)-[:TIENE_PREFERENCIA {favorita: true}]->(p2:Pelicula) " +
                "WHERE otros <> u " +
                "WITH u, favoritasUsuario, otros, collect(p2) AS favoritasOtros " +
                "WITH u, favoritasUsuario, otros, favoritasOtros, " +
                "     apoc.coll.intersection(favoritasUsuario, favoritasOtros) AS interseccion, " +
                "     apoc.coll.union(favoritasUsuario, favoritasOtros) AS union " +
                "WITH u, otros, interseccion, union, " +
                "     CASE WHEN size(union) = 0 THEN 0 ELSE toFloat(size(interseccion)) / size(union) END AS similitud, favoritasOtros " +
                "WHERE similitud > 0.3 " +
                "UNWIND favoritasOtros AS recomendacion " +
                "WHERE NOT recomendacion IN favoritasUsuario " +
                "RETURN DISTINCT recomendacion.titulo AS titulo";
            Result result = session.run(query, parameters("codigoUsuario", codigoUsuario));
            java.util.List<String> recomendaciones = new java.util.ArrayList<>();
            while (result.hasNext()) {
                Record record = result.next();
                recomendaciones.add(record.get("titulo").asString());
            }
            return recomendaciones;
        }
    }
}
