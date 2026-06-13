package com.aatthhuurr.mobisuas_2411500025.model

// 1. Kelas Utama untuk membungkus respon JSON dari API get_riwayat.php
data class LaporanResponse(
    val status: Boolean,
    val message: String,
    val data: List<LaporanData>
)

// 2. Kelas Data Detail untuk menampung isi kolom tabel laporan milikmu
data class LaporanData(
    val id_laporan: Int,
    val id_user: Int,
    val id_kategori: Int,
    val judul_laporan: String,
    val lokasi_kejadian: String,
    val kronologis_kejadian: String,
    val bukti_foto: String?,
    val status_laporan: String,
    val tanggapan_admin: String?,
    val tgl_dilapor: String,
    val tgl_ditanggapi: String?,

    // Kolom tambahan dari hasil SQL JOIN dengan tabel kategori
    val nama_kategori: String?
)