package com.example.cheezuberkahjaya;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BarangResponse {
    @SerializedName("data")
    public List<Barang> data;
}