package com.example.huertomatic;

import java.util.ArrayList;

public class Vegetal {

    String nombre,codigo;
    ArrayList <String> beneficiosa, perjudicial;
    byte[] imagen;

    public Vegetal() {
    }

    public Vegetal(String codigo, String nombre, ArrayList<String> beneficiosa, ArrayList<String> perjudicial, byte[] imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.beneficiosa = beneficiosa;
        this.perjudicial = perjudicial;
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getBeneficiosa() {
        return beneficiosa;
    }

    public ArrayList<String> getPerjudicial() {
        return perjudicial;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setBeneficiosa(ArrayList<String> beneficiosa) {
        this.beneficiosa = beneficiosa;
    }

    public void setPerjudicial(ArrayList<String> perjudicial) {
        this.perjudicial = perjudicial;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
