package com.aatthhuurr.mobisuas_2411500025.model

data class KategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<KategoriModel>
)

data class KategoriModel(
    val id_kategori: Int,
    val nama_kategori: String,
    val ikon_kategori: String?
)