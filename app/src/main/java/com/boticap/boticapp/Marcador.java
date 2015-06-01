package com.boticap.boticapp;

public class Marcador {
    private long id;
    private String titulo;
    private String fragmento;
    private String coordenadas;

    public Marcador(){
    }

    public Marcador(long id, String titulo, String fragmento, String coordenadas){
        this.setId(id);
        this.setTitulo(titulo);
        this.setFragmento(fragmento);
        this.setCoordenadas(coordenadas);
    }

    public Marcador(String titulo, String fragmento, String coordenadas){
        this.setTitulo(titulo);
        this.setFragmento(fragmento);
        this.setCoordenadas(coordenadas);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFragmento() {
        return fragmento;
    }

    public void setFragmento(String fragmento) {
        this.fragmento = fragmento;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}