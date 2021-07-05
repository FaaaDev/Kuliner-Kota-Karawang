package com.example.kulinerkotakarawang.model;

public class DetailModel {

    private static String nama, harga, nohp, deskripsi, operasional, alamat, wilayah, gambar;

    public DetailModel() {

    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        DetailModel.nama = nama;
    }

    public static String getHarga() {
        return harga;
    }

    public static void setHarga(String harga) {
        DetailModel.harga = harga;
    }

    public static String getNohp() {
        return nohp;
    }

    public static void setNohp(String nohp) {
        DetailModel.nohp = nohp;
    }

    public static String getDeskripsi() {
        return deskripsi;
    }

    public static void setDeskripsi(String deskripsi) {
        DetailModel.deskripsi = deskripsi;
    }

    public static String getOperasional() {
        return operasional;
    }

    public static void setOperasional(String operasional) {
        DetailModel.operasional = operasional;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        DetailModel.alamat = alamat;
    }

    public static String getWilayah() {
        return wilayah;
    }

    public static void setWilayah(String wilayah) {
        DetailModel.wilayah = wilayah;
    }

    public static String getGambar() {
        return gambar;
    }

    public static void setGambar(String gambar) {
        DetailModel.gambar = gambar;
    }
}
