package com.example.cheezuberkahjaya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cheezuberkahjaya.api.ApiService;
import com.example.cheezuberkahjaya.api.RetrofitClient;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    EditText etNama, etJumlah, etTanggal;
    RadioGroup rgJenis;
    RadioButton rbMasuk, rbKeluar;
    Button btnSimpan;
    ImageButton btnBack;
    TextView tvJudul;
    ApiService apiService;
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

        apiService = RetrofitClient.getClient().create(ApiService.class);
        btnBack.setOnClickListener(v -> finish());

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
        etTanggal.setOnClickListener(v -> showDatePicker());
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

        Barang barang = new Barang(idBarang, nama, Integer.parseInt(jumlahStr), jenis, tanggal);

        if (idBarang == -1) {
            apiService.createBarang(barang).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(FormActivity.this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(FormActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            apiService.updateBarang(barang).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(FormActivity.this, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(FormActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format jadi YYYY-MM-DD
                    String tanggal = String.format("%04d-%02d-%02d",
                            selectedYear, selectedMonth + 1, selectedDay);
                    etTanggal.setText(tanggal);
                }, year, month, day);

        dialog.show();
    }
}