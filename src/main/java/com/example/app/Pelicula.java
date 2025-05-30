package com.example.app;

import java.util.HashSet;
import java.util.Set;

public class Pelicula {
    String titulo;
    String genero;
    int anio;
    Set<Usuario> usuariosQueLaVieron = new HashSet<>();

    public Pelicula(String titulo, String genero, int anio) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
    }

    @Override
    public String toString() {
        return titulo + " (" + anio + ")";
    }
}
