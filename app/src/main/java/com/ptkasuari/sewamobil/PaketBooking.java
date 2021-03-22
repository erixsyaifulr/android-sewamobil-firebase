package com.ptkasuari.sewamobil;

public class PaketBooking {
    String email;
    String kode;
    String nama;
    String asal;

    public PaketBooking(){

    }

    public PaketBooking(String email,String kode,String nama,String asal,String tujuan,String tanggal_berangkat,String harga,String waktu_booking,String status){
        this.email=email;
        this.kode=kode;
        this.nama=nama;
        this.asal=asal;
        this.tujuan=tujuan;
        this.tanggal_berangkat=tanggal_berangkat;
        this.harga=harga;
        this.waktu_booking=waktu_booking;
        this.status=status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal_berangkat() {
        return tanggal_berangkat;
    }

    public void setTanggal_berangkat(String tanggal_berangkat) {
        this.tanggal_berangkat = tanggal_berangkat;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getWaktu_booking() {
        return waktu_booking;
    }

    public void setWaktu_booking(String waktu_booking) {
        this.waktu_booking = waktu_booking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String tujuan;
    String tanggal_berangkat;
    String harga;
    String waktu_booking;
    String status;

}
