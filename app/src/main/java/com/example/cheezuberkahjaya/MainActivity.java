package com.example.cheezuberkahjaya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBarang;
    FloatingActionButton fabTambah;
    TextView tvTotalMasuk, tvTotalKeluar;
    DatabaseHelper dbHelper;
    List<Barang> barangList;
    BarangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBarang = findViewById(R.id.rvBarang);
        fabTambah = findViewById(R.id.fabTambah);
        tvTotalMasuk = findViewById(R.id.tvTotalMasuk);
        tvTotalKeluar = findViewById(R.id.tvTotalKeluar);
        dbHelper = new DatabaseHelper(this);

        rvBarang.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        fabTambah.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        barangList = new ArrayList<>();
        int totalMasuk = 0;
        int totalKeluar = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BARANG +
                " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Barang barang = new Barang(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                barangList.add(barang);

                if (barang.getJenis().equals("masuk")) {
                    totalMasuk += barang.getJumlah();
                } else {
                    totalKeluar += barang.getJumlah();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        tvTotalMasuk.setText(String.valueOf(totalMasuk));
        tvTotalKeluar.setText(String.valueOf(totalKeluar));

        adapter = new BarangAdapter(this, barangList, barang -> {
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

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}