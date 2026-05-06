package com.example.cheezuberkahjaya;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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

        // Ambil data dari arguments
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

        // Tombol Edit
        btnEdit.setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onEdit(barang);
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Yakin ingin menghapus " + barang.getNamaBarang() + "?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(DatabaseHelper.TABLE_BARANG, "id = ?",
                                new String[]{String.valueOf(barang.getId())});
                        dismiss();
                        if (listener != null) listener.onDelete();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        return view;
    }
}