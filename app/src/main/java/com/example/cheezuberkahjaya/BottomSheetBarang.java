package com.example.cheezuberkahjaya;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.cheezuberkahjaya.api.ApiService;
import com.example.cheezuberkahjaya.api.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetBarang extends BottomSheetDialogFragment {

    private Barang barang;
    private OnActionListener listener;

    public interface OnActionListener {
        void onEdit(Barang barang);
        void onDelete();
    }

    public static BottomSheetBarang newInstance(Barang barang) {
        BottomSheetBarang sheet = new BottomSheetBarang();
        Bundle args = new Bundle();
        args.putInt("id", barang.getId());
        args.putString("nama", barang.getNamaBarang());
        args.putInt("jumlah", barang.getJumlah());
        args.putString("jenis", barang.getJenis());
        args.putString("tanggal", barang.getTanggal());
        sheet.setArguments(args);
        return sheet;
    }

    public void setOnActionListener(OnActionListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        Bundle args = getArguments();
        barang = new Barang(
                args.getInt("id"),
                args.getString("nama"),
                args.getInt("jumlah"),
                args.getString("jenis"),
                args.getString("tanggal")
        );

        TextView tvNama = view.findViewById(R.id.tvSheetNama);
        LinearLayout btnEdit = view.findViewById(R.id.btnSheetEdit);
        LinearLayout btnHapus = view.findViewById(R.id.btnSheetHapus);

        tvNama.setText(barang.getNamaBarang());

        btnEdit.setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onEdit(barang);
        });

        btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Yakin ingin menghapus " + barang.getNamaBarang() + "?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                        apiService.deleteBarang(barang.getId()).enqueue(new Callback<Map<String, String>>() {
                            @Override
                            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                    if (listener != null) listener.onDelete();
                                }
                            }
                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        return view;
    }
}