package com.example.kulinerkotakarawang.model;

public class LapakModel {
    private static String nama, nohp, alamat, wilayah, pass, gambar;

    public LapakModel() {

    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        LapakModel.nama = nama;
    }

    public static String getNohp() {
        return nohp;
    }

    public static void setNohp(String nohp) {
        LapakModel.nohp = nohp;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        LapakModel.alamat = alamat;
    }

    public static String getWilayah() {
        return wilayah;
    }

    public static void setWilayah(String wilayah) {
        LapakModel.wilayah = wilayah;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        LapakModel.pass = pass;
    }

    public static String getGambar() {
        return gambar;
    }

    public static void setGambar(String gambar) {
        LapakModel.gambar = gambar;
    }
}
