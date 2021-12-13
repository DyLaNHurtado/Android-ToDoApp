package com.example.todoapp;


public class Nota {
    String texto;
    boolean importante;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public Nota(String texto, boolean importante){
        this.texto=texto;
        this.importante=importante;
    }

    public Nota(){}
}