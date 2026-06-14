package com.aatthhuurr.mobisuas_2411500025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aatthhuurr.mobisuas_2411500025.model.LaporanData
import com.bumptech.glide.Glide

class RiwayatAdapter(
    private val listLaporan: List<LaporanData>,
    private val onItemClick: (LaporanData) -> Unit
) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtJudulPengaduan: TextView = view.findViewById(R.id.txtJudulPengaduan)
        val txtStatusLaporan: TextView = view.findViewById(R.id.txtStatusLaporan)
        val txtKategoriLaporan: TextView = view.findViewById(R.id.txtKategoriLaporan)
        val txtTanggapanAdmin: TextView = view.findViewById(R.id.txtTanggapanAdmin)
        val txtTanggalPelaporan: TextView = view.findViewById(R.id.txtTanggalPelaporan)
        val txtTanggalDitanggapi: TextView = view.findViewById(R.id.txtTanggalDitanggapi)
        val imgIkonStatus: ImageView = view.findViewById(R.id.imgIkonStatus)
        val imgBuktiLaporan: ImageView = view.findViewById(R.id.imgBuktiLaporan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat, parent, false)
        return ViewHolder(view)
    }

    // GANTI isi fungsi onBindViewHolder kamu menjadi seperti ini:
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val laporan = listLaporan[position]

        // 1. Set Data Teks Masuk Sesuai ID XML Asli Kamu
        holder.txtJudulPengaduan.text = laporan.judul_laporan
        holder.txtStatusLaporan.text = laporan.status_laporan.uppercase()
        holder.txtKategoriLaporan.text = laporan.nama_kategori ?: "Tanpa Kategori"

        holder.txtTanggapanAdmin.text = if (!laporan.tanggapan_admin.isNullOrEmpty()) {
            "Tanggapan Admin: ${laporan.tanggapan_admin}"
        } else {
            "Tanggapan Admin: (Belum ada tanggapan resmi dari pihak terkait)"
        }

        holder.txtTanggalPelaporan.text = "Dilapor: ${laporan.tgl_dilapor}"
        holder.txtTanggalDitanggapi.text = "Ditanggapi: ${laporan.tgl_ditanggapi ?: "--"}"

        // 2. Load Gambar Mini Menggunakan Glide
        val urlGambar = "http://192.168.18.55/webmobisuas_2411500025/uploads/" + laporan.bukti_foto

        Glide.with(holder.itemView.context)
            .load(urlGambar)
            .placeholder(android.R.drawable.ic_menu_camera) // Menggunakan icon kamera default Android bawaan sistem
            .error(android.R.drawable.ic_menu_camera)
            .into(holder.imgBuktiLaporan)

        // 3. Set Status Warna & Ikon Dinamis
        when (laporan.status_laporan) {
            "Menunggu Tanggapan" -> {
                holder.txtStatusLaporan.setTextColor(android.graphics.Color.parseColor("#C29302"))
                holder.imgIkonStatus.setImageResource(android.R.drawable.stat_sys_warning)
                holder.imgIkonStatus.setColorFilter(android.graphics.Color.parseColor("#C29302"))
            }
            "Ditolak" -> {
                holder.txtStatusLaporan.setTextColor(android.graphics.Color.parseColor("#D32F2F"))
                holder.imgIkonStatus.setImageResource(android.R.drawable.ic_delete)
                holder.imgIkonStatus.setColorFilter(android.graphics.Color.parseColor("#D32F2F"))
            }
            "Diproses" -> {
                holder.txtStatusLaporan.setTextColor(android.graphics.Color.parseColor("#1976D2"))
                holder.imgIkonStatus.setImageResource(android.R.drawable.stat_sys_download)
                holder.imgIkonStatus.setColorFilter(android.graphics.Color.parseColor("#1976D2"))
            }
            "Selesai" -> {
                holder.txtStatusLaporan.setTextColor(android.graphics.Color.parseColor("#388E3C"))
                holder.imgIkonStatus.setImageResource(android.R.drawable.checkbox_on_background)
                holder.imgIkonStatus.setColorFilter(android.graphics.Color.parseColor("#388E3C"))
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(laporan)
        }
    }

    override fun getItemCount(): Int {
        return listLaporan.size
    }
}