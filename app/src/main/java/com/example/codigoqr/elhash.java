package com.example.codigoqr;

public class elhash {
    private int id_usuario;
    private String hash1;
    private String nombre;

    public elhash() {

    }

    public elhash(int id_usuario, String hash1) {
        this.id_usuario = id_usuario;
        this.hash1 = hash1;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getHash1() {
        return hash1;
    }

    public void setHash1(String hash1) {
        this.hash1 = hash1;
    }

    @Override
    public String toString() {
        return hash1;
    }
}