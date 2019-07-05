package com.example.codigoqr;

public class asistencia {
    String id_evento;
    String id_usuario;

    public asistencia()
    {

    }

    public asistencia(String id_usuario, String id_evento)
    {
        this.id_evento=id_evento;
        this.id_usuario=id_usuario;
    }

    public String getElidevento() {
        return id_evento;
    }

    public void setElidevento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getElidusuario() {
        return id_usuario;
    }

    public void setElidusuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

}
