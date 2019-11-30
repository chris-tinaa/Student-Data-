package com.example.studentcrud.model;

import java.io.Serializable;

public class Siswa implements Serializable {
    private String nama, no_hp, email, idSiswa;
    private byte[] image;

    public Siswa() {
    }

    public Siswa(String idSiswa, String nama, String no_hp, String email, byte[] image) {
        this.idSiswa = idSiswa;
        this.nama = nama;
        this.no_hp = no_hp;
        this.email = email;
        this.image = image;
    }

    public String getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(String idSiswa) {
        this.idSiswa = idSiswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}

