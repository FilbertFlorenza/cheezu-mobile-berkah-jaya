package com.example.cheezuberkahjaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private List<Barang> barangList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Barang barang);
    }

    public BarangAdapter(Context context, List<Barang> barangList, OnItemClickListener listener) {
        this.context = context;
        this.barangList = barangList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barang barang = barangList.get(position);

        holder.tvNamaBarang.setText(barang.getNamaBarang());
        holder.tvTanggal.setText(barang.getTanggal());

        boolean isMasuk = barang.getJenis().equals("masuk");

        // Icon & warna sesuai jenis
        if (isMasuk) {
            holder.tvIcon.setText("↓");
            holder.tvIcon.setTextColor(context.getResources().getColor(R.color.green_main));
            holder.iconWrapper.setBackgroundColor(context.getResources().getColor(R.color.green_light));
            holder.tvJumlah.setText("+" + barang.getJumlah());
            holder.tvJumlah.setTextColor(context.getResources().getColor(R.color.green_main));
            holder.tvBadge.setText("Masuk");
            holder.tvBadge.setTextColor(context.getResources().getColor(R.color.green_dark));
            holder.tvBadge.setBackgroundColor(context.getResources().getColor(R.color.green_light));
        } else {
            holder.tvIcon.setText("↑");
            holder.tvIcon.setTextColor(context.getResources().getColor(R.color.coral_main));
            holder.iconWrapper.setBackgroundColor(context.getResources().getColor(R.color.coral_light));
            holder.tvJumlah.setText("-" + barang.getJumlah());
            holder.tvJumlah.setTextColor(context.getResources().getColor(R.color.coral_main));
            holder.tvBadge.setText("Keluar");
            holder.tvBadge.setTextColor(context.getResources().getColor(R.color.coral_dark));
            holder.tvBadge.setBackgroundColor(context.getResources().getColor(R.color.coral_light));
        }

        // Klik item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(barang));
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvTanggal, tvJumlah, tvBadge, tvIcon;
        FrameLayout iconWrapper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            iconWrapper = itemView.findViewById(R.id.iconWrapper);
        }
    }
}