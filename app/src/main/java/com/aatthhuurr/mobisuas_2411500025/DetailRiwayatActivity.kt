package com.aatthhuurr.mobisuas_2411500025

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aatthhuurr.mobisuas_2411500025.api.RetrofitClient
import com.aatthhuurr.mobisuas_2411500025.model.LaporanResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRiwayatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat)

        // 1. Inisialisasi Seluruh Komponen UI dari XML
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val txtDetailJudul = findViewById<TextView>(R.id.txtDetailJudul)
        val txtDetailKategori = findViewById<TextView>(R.id.txtDetailKategori)
        val txtDetailStatus = findViewById<TextView>(R.id.txtDetailStatus)
        val imgDetailStatusIcon = findViewById<ImageView>(R.id.imgDetailStatusIcon)
        val txtDetailTanggal = findViewById<TextView>(R.id.txtDetailTanggal)
        val txtDetailKronologis = findViewById<TextView>(R.id.txtDetailKronologis)

        // Inisialisasi ImageView untuk bukti gambar besar yang ada di XML kamu
        val imgDetailBukti = findViewById<ImageView>(R.id.imgDetailBukti)

        val txtDetailTanggapanAdmin = findViewById<TextView>(R.id.txtDetailTanggapanAdmin)
        val txtDetailDitanggapi = findViewById<TextView>(R.id.txtDetailDitanggapi)

        // 2. Tombol Kembali Kembali ke Halaman Riwayat
        btnBack.setOnClickListener {
            finish()
        }

        // 3. Menangkap ID Laporan yang dikirim dari Klik Item di RiwayatActivity
        val idLaporan = intent.getIntExtra("ID_LAPORAN", -1)

        if (idLaporan != -1) {
            // Panggil fungsi Ambil Data dari MySQL Server
            ambilDetailLaporanFromServer(
                idLaporan, txtDetailJudul, txtDetailKategori, txtDetailStatus,
                imgDetailStatusIcon, txtDetailTanggal, txtDetailKronologis, imgDetailBukti,
                txtDetailTanggapanAdmin, txtDetailDitanggapi
            )
        } else {
            Toast.makeText(this, "ID Laporan tidak valid!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun ambilDetailLaporanFromServer(
        idLaporan: Int, judul: TextView, kategori: TextView, status: TextView,
        iconStatus: ImageView, tanggal: TextView, kronologis: TextView, imgBukti: ImageView,
        tanggapan: TextView, tglDitanggapi: TextView
    ) {
        RetrofitClient.instance.getDetailLaporan(idLaporan).enqueue(object : Callback<LaporanResponse> {
            override fun onResponse(call: Call<LaporanResponse>, response: Response<LaporanResponse>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.status) {
                    // Ambil item pertama dari list data data laporan
                    val data = response.body()!!.data[0]

                    // Set Data ke Komponen UI
                    judul.text = data.judul_laporan
                    kategori.text = "Kategori: ${data.nama_kategori ?: "Tanpa Kategori"}"
                    status.text = data.status_laporan.uppercase()
                    tanggal.text = "Dilapor: ${data.tgl_dilapor}"
                    kronologis.text = data.kronologis_kejadian

                    tanggapan.text = data.tanggapan_admin ?: "(Belum ada tanggapan resmi dari pihak terkait)"
                    tglDitanggapi.text = "Ditanggapi pada:\n${data.tgl_ditanggapi ?: "--"}"

                    // =========================================================================
                    // PROSES MEMUAT GAMBAR BUKTI DARI SERVER MENGGUNAKAN GLIDE
                    // =========================================================================
                    val urlGambarDetail = "http://192.168.18.55/webmobisuas_2411500025/uploads/" + data.bukti_foto

                    Glide.with(this@DetailRiwayatActivity)
                        .load(urlGambarDetail)
                        .placeholder(R.drawable.kamera) // Diperbaiki: Menggunakan file gambar asli kamera di drawable
                        .error(R.drawable.kamera)       // Diperbaiki: Menggunakan file gambar asli kamera di drawable
                        .into(imgBukti)
                    // =========================================================================

                    // Atur Warna Status secara Dinamis
                    when (data.status_laporan) {
                        "Menunggu Tanggapan" -> {
                            status.setTextColor(Color.parseColor("#856404"))
                            iconStatus.setImageResource(android.R.drawable.stat_sys_warning)
                            iconStatus.setColorFilter(Color.parseColor("#856404"))
                        }
                        "Ditolak" -> {
                            status.setTextColor(Color.parseColor("#D32F2F"))
                            iconStatus.setImageResource(android.R.drawable.ic_delete)
                            iconStatus.setColorFilter(Color.parseColor("#D32F2F"))
                        }
                        "Diproses" -> {
                            status.setTextColor(Color.parseColor("#1976D2"))
                            iconStatus.setImageResource(android.R.drawable.stat_sys_download)
                            iconStatus.setColorFilter(Color.parseColor("#1976D2"))
                        }
                        "Selesai" -> {
                            status.setTextColor(Color.parseColor("#388E3C"))
                            iconStatus.setImageResource(android.R.drawable.checkbox_on_background)
                            iconStatus.setColorFilter(Color.parseColor("#388E3C"))
                        }
                    }
                } else {
                    Toast.makeText(this@DetailRiwayatActivity, "Gagal mengambil rincian data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LaporanResponse>, t: Throwable) {
                Toast.makeText(this@DetailRiwayatActivity, "Koneksi Bermasalah: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}