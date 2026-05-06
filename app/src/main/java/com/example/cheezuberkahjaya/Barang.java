package com.example.cheezuberkahjaya;

public class Barang {
    private int id;
    private String namaBarang;
    private int jumlah;
    private String jenis;
    private String tanggal;

    public Barang(int id, String namaBarang, int jumlah, String jenis, String tanggal) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.jenis = jenis;
        this.tanggal = tanggal;
    }

    public int getId() { return id; }
    public String getNamaBarang() { return namaBarang; }
    public int getJumlah() { return jumlah; }
    public String getJenis() { return jenis; }
    public String getTanggal() { return tanggal; }

    public void setId(int id) { this.id = id; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}