package com.example.app;

import java.util.List;

public class RecomendadorNeo4j {
    private Neo4jConnection neo4jConnection;

    public RecomendadorNeo4j(String uri, String user, String password) {
        neo4jConnection = new Neo4jConnection(uri, user, password);
    }

    public void close() {
        neo4jConnection.close();
    }

    public void agregarPreferencia(String codigoUsuario, String tituloPelicula, boolean favorita) {
        neo4jConnection.crearPreferencia(codigoUsuario, tituloPelicula, favorita);
    }

    public List<String> recomendar(String codigoUsuario) {
        return neo4jConnection.recomendarPeliculas(codigoUsuario);
    }
}
