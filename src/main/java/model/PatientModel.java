package model;

import java.time.LocalDate;

public class PatientModel {
    private int id;
    private String nom;
    private LocalDate date_naissance;
    private String lieu_naissance;
    private String adresse;
    private int id_sexe;
    private String sexe;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public LocalDate getDate_naissance() {
        return date_naissance;
    }
    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }
    public String getLieu_naissance() {
        return lieu_naissance;
    }
    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public int getId_sexe() {
        return id_sexe;
    }
    public void setId_sexe(int id_sexe) {
        this.id_sexe = id_sexe;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
