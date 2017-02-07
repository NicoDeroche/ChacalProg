package com.appli.chacalprog;

import java.io.Serializable;

/**
 * Created by nderoche on 30/01/2017.
 */

public class Produit   implements Serializable {
    String libelle;
    Double prix;


    public Produit(String libelle, Double prix) {
        this.libelle = libelle;
        this.prix = prix;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}
