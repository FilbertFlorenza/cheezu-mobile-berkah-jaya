package com.example.cheezuberkahjaya.api;

import com.example.cheezuberkahjaya.Barang;
import com.example.cheezuberkahjaya.BarangResponse; // ← tambah import ini

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @GET("read.php")
    Call<BarangResponse> getAllBarang(); // ← ubah dari Map<String, Object>

    @POST("create.php")
    Call<Map<String, String>> createBarang(@Body Barang barang);

    @PUT("update.php")
    Call<Map<String, String>> updateBarang(@Body Barang barang);

    @DELETE("delete.php")
    Call<Map<String, String>> deleteBarang(@Query("id") int id);
}