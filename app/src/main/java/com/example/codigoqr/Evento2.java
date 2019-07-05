package com.example.codigoqr;

public class Evento2 {
    private int Id_evento;
    private String Nombre_evento;
    private String Tipo_evento;
    private String Fecha_inicio;
    private String Fecha_termino;
    private String Nac_int;
    private String Lugar;
    private String Col_org;

    public Evento2() {

    }

    public Evento2(int Id_evento, String Nombre_evento, String Tipo_evento, String Fecha_inicio, String Fecha_termino, String Nac_int, String Lugar, String Col_org)
    {
        this.Id_evento = Id_evento;
        this.Nombre_evento = Nombre_evento;
        this.Tipo_evento = Tipo_evento;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_termino = Fecha_termino;
        this.Nac_int = Nac_int;
        this.Lugar = Lugar;
        this.Col_org  = Col_org;
    }

    public int getId_evento() {
        return Id_evento;
    }

    public void setId_evento(int id_evento) {
        Id_evento = id_evento;
    }

    public String getNombre_evento() {
        return Nombre_evento;
    }

    public void setNombre_evento(String nombre_evento) {
        Nombre_evento = nombre_evento;
    }

    public String getTipo_evento() {
        return Tipo_evento;
    }

    public void setTipo_evento(String tipo_evento) {
        Tipo_evento = tipo_evento;
    }

    public String getFecha_inicio() {
        return Fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        Fecha_inicio = fecha_inicio;
    }

    public String getFecha_termino() {
        return Fecha_termino;
    }

    public void setFecha_termino(String fecha_termino) {
        Fecha_termino = fecha_termino;
    }

    public String getNac_int() {
        return Nac_int;
    }

    public void setNac_int(String nac_int) {
        Nac_int = nac_int;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getCol_org() {
        return Col_org;
    }

    public void setCol_org(String col_org) {
        Col_org = col_org;
    }

    @Override
    public String toString() { return Nombre_evento; }
}
