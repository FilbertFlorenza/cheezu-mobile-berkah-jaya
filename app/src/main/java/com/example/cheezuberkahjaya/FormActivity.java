package com.example.cheezuberkahjaya;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    EditText etNama, etJumlah, etTanggal;
    RadioGroup rgJenis;
    RadioButton rbMasuk, rbKeluar;
    Button btnSimpan;
    ImageButton btnBack;
    TextView tvJudul;
    DatabaseHelper dbHelper;
    int idBarang = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etNama = findViewById(R.id.etNama);
        etJumlah = findViewById(R.id.etJumlah);
        etTanggal = findViewById(R.id.etTanggal);
        rgJenis = findViewById(R.id.rgJenis);
        rbMasuk = findViewById(R.id.rbMasuk);
        rbKeluar = findViewById(R.id.rbKeluar);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);
        tvJudul = findViewById(R.id.tvJudul);
        dbHelper = new DatabaseHelper(this);

        // Tombol back
        btnBack.setOnClickListener(v -> finish());

        // Cek mode edit
        if (getIntent().hasExtra("id")) {
            idBarang = getIntent().getIntExtra("id", -1);
            tvJudul.setText("Edit Barang");

            etNama.setText(getIntent().getStringExtra("nama"));
            etJumlah.setText(String.valueOf(getIntent().getIntExtra("jumlah", 0)));
            etTanggal.setText(getIntent().getStringExtra("tanggal"));

            String jenis = getIntent().getStringExtra("jenis");
            if (jenis != null && jenis.equals("masuk")) {
                rbMasuk.setChecked(true);
            } else {
                rbKeluar.setChecked(true);
            }
        }

        btnSimpan.setOnClickListener(v -> simpanData());
    }

    private void simpanData() {
        String nama = etNama.getText().toString().trim();
        String jumlahStr = etJumlah.getText().toString().trim();
        String tanggal = etTanggal.getText().toString().trim();
        String jenis = rbMasuk.isChecked() ? "masuk" : "keluar";

        if (nama.isEmpty() || jumlahStr.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlah = Integer.parseInt(jumlahStr);

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NAMA, nama);
        values.put(DatabaseHelper.COL_JUMLAH, jumlah);
        values.put(DatabaseHelper.COL_JENIS, jenis);
        values.put(DatabaseHelper.COL_TANGGAL, tanggal);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (idBarang == -1) {
            db.insert(DatabaseHelper.TABLE_BARANG, null, values);
            Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        } else {
            db.update(DatabaseHelper.TABLE_BARANG, values,
                    "id = ?", new String[]{String.valueOf(idBarang)});
            Toast.makeText(this, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}