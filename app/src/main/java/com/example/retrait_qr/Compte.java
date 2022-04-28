package com.example.retrait_qr;

import java.util.Date;

public class Compte {
    private Long code;
    private Double solde;
    private Double mnt;
    private Date dateCreation;
    private String nom;
    private String prenom;

    public Double getMnt() {
        return mnt;
    }
    public void setMnt(Double mnt) {
        this.mnt = mnt;
    }
    public Long getCode() {
        return code;
    }
    public Double getSolde() {
        return solde;
    }
    public void setCode(Long code) {
        this.code = code;
    }
    public Compte() {
    }
    public void setSolde(Double solde) {
        this.solde = solde;
    }

}
