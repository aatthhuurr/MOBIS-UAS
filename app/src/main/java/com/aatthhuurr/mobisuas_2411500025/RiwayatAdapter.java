package com.aatthhuurr.mobisuas_2411500025;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private Context context;
    private List<LaporanModel> laporanList;

    public RiwayatAdapter(Context context, List<LaporanModel> laporanList) {
        this.context = context;
        this.laporanList = laporanList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        LaporanModel laporan = laporanList.get(position);

        // Menghubungkan data object ke Holder komponen UI (Sesuai Urutan Soal Dosen)
        holder.txtJudul.setText(laporan.getJudul());
        holder.txtStatus.setText(laporan.getStatus());
        holder.txtKategori.setText(laporan.getKategori());
        holder.txtTanggapan.setText(laporan.getTanggapanAdmin() != null ? laporan.getTanggapanAdmin() : "Belum ada tanggapan.");
        holder.txtTanggalLapor.setText("Dilapor: " + laporan.getTanggalLapor());
        holder.txtTanggalTanggapi.setText("Ditanggapi: " + (laporan.getTanggalTanggapi() != null ? laporan.getTanggalTanggapi() : "-"));

        // Mengatur icon bawaan berdasarkan status
        if (laporan.getStatus() != null && laporan.getStatus().equalsIgnoreCase("Selesai")) {
            holder.imgIkonStatus.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            holder.imgIkonStatus.setImageResource(android.R.drawable.stat_sys_warning);
        }

        // Klik item untuk membuka halaman detail laporan gess
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRiwayatActivity.class);
            intent.putExtra("DATA_LAPORAN", laporan);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return laporanList != null ? laporanList.size() : 0;
    }

    public static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtStatus, txtKategori, txtTanggapan, txtTanggalLapor, txtTanggalTanggapi;
        ImageView imgIkonStatus;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txtJudulPengaduan);
            txtStatus = itemView.findViewById(R.id.txtStatusLaporan);
            txtKategori = itemView.findViewById(R.id.txtKategoriLaporan);
            txtTanggapan = itemView.findViewById(R.id.txtTanggapanAdmin);
            txtTanggalLapor = itemView.findViewById(R.id.txtTanggalPelaporan);
            txtTanggalTanggapi = itemView.findViewById(R.id.txtTanggalDitanggapi);
            imgIkonStatus = itemView.findViewById(R.id.imgIkonStatus);
        }
    }
}