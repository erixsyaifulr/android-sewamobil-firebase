package com.ptkasuari.sewamobil;

public class PaketUser {
    String nama;
    String nohp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String email;
    String password;

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    String waktu;


    public  PaketUser(){

    }
    public  PaketUser(String nama,String nohp,String email,String password,String status,String waktu){
        this.nama=nama;
        this.nohp=nohp;
        this.email=email;
        this.password=password;
        this.status=status;
        this.waktu=waktu;
    }

}
