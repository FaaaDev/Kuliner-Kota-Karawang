package com.example.kulinerkotakarawang.model;

public class MenuModel {
    private String id, nama, harga, nohp, deskripsi, operasional, alamat, wilayah, gambar;

    public MenuModel() {

    }

    public MenuModel(String id, String nama, String harga, String nohp, String deskripsi, String operasional, String alamat, String wilayah, String gambar) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.nohp = nohp;
        this.deskripsi = deskripsi;
        this.operasional = operasional;
        this.alamat = alamat;
        this.wilayah = wilayah;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getOperasional() {
        return operasional;
    }

    public void setOperasional(String operasional) {
        this.operasional = operasional;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
