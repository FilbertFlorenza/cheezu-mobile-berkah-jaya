package com.example.cheezuberkahjaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cheezuberkahjaya.api.ApiService;
import com.example.cheezuberkahjaya.api.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBarang;
    FloatingActionButton fabTambah;
    TextView tvTotalMasuk, tvTotalKeluar;
    BarangAdapter adapter;
    ApiService apiService;
    List<Barang> barangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBarang = findViewById(R.id.rvBarang);
        fabTambah = findViewById(R.id.fabTambah);
        tvTotalMasuk = findViewById(R.id.tvTotalMasuk);
        tvTotalKeluar = findViewById(R.id.tvTotalKeluar);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        fabTambah.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        apiService.getAllBarang().enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    barangList.clear();
                    int totalMasuk = 0, totalKeluar = 0;

                    List<Barang> data = response.body().data;
                    if (data != null) {
                        for (Barang barang : data) {
                            barangList.add(barang);

                            if ("masuk".equals(barang.getJenis())) {
                                totalMasuk += barang.getJumlah();
                            } else {
                                totalKeluar += barang.getJumlah();
                            }
                        }
                    }

                    tvTotalMasuk.setText(String.valueOf(totalMasuk));
                    tvTotalKeluar.setText(String.valueOf(totalKeluar));

                    adapter = new BarangAdapter(MainActivity.this, barangList, barang -> {
                        BottomSheetBarang sheet = BottomSheetBarang.newInstance(barang);
                        sheet.setOnActionListener(new BottomSheetBarang.OnActionListener() {
                            @Override
                            public void onEdit(Barang barang) {
                                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                                intent.putExtra("id", barang.getId());
                                intent.putExtra("nama", barang.getNamaBarang());
                                intent.putExtra("jumlah", barang.getJumlah());
                                intent.putExtra("jenis", barang.getJenis());
                                intent.putExtra("tanggal", barang.getTanggal());
                                startActivity(intent);
                            }

                            @Override
                            public void onDelete() {
                                loadData();
                            }
                        });
                        sheet.show(getSupportFragmentManager(), "BottomSheetBarang");
                    });
                    rvBarang.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}