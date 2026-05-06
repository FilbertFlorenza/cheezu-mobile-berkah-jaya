package com.example.cheezuberkahjaya;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Barang implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("tanggal")
    private String tanggal;

    public Barang() {}

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