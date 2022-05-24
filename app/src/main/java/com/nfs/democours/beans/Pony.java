package com.nfs.democours.beans;

import com.nfs.democours.mapping.PonyMapping;

import java.io.Serializable;
import java.util.Objects;

public class Pony implements Serializable, PonyMapping {
    private int id;
    private String name;
    private String couleur;
    private int age;

    private Pony() {

    }

    public Pony(int id, String name, String couleur, int age) {
        this.id = id;
        this.name = name;
        this.couleur = couleur;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() { return name; }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, couleur, age);
    }

}
