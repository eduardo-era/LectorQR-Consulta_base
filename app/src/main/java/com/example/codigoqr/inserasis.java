package com.example.codigoqr;

public class inserasis {
    private int elidevento;
    private int elidusuario;


    public inserasis() {

    }
    public inserasis (int elidevento, int elidusuario)
    {
        this.elidusuario=elidusuario;
        this.elidevento=elidevento;
    }

    public int getElidevento() {
        return elidevento;
    }

    public void setElidevento(int elidevento) {
        this.elidevento = elidevento;
    }

    public int getElidusuario() {
        return elidusuario;
    }

    public void setElidusuario(int elidusuario) {
        this.elidusuario = elidusuario;
    }
}