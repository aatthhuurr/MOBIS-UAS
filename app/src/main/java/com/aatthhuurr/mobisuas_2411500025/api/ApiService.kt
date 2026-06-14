package com.aatthhuurr.mobisuas_2411500025.api

import com.aatthhuurr.mobisuas_2411500025.model.KategoriResponse
import com.aatthhuurr.mobisuas_2411500025.model.LaporanResponse
import com.aatthhuurr.mobisuas_2411500025.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("api/get_kategori.php")
    fun getKategori(): Call<KategoriResponse>

    @FormUrlEncoded
    @POST("api/login.php")
    fun login(
        @Field("nomor_hp") nomorHp: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // DIBERSIHKAN & DIUBAH KE MULTIPART UNTUK PROSES UPLOAD FOTO
    @Multipart
    @POST("api/insert_laporan.php")
    fun insertLaporan(
        @Part("id_user") idUser: RequestBody,
        @Part("id_kategori") idKategori: RequestBody,
        @Part("judul_laporan") judulLaporan: RequestBody,
        @Part("lokasi_kejadian") lokasiKejadian: RequestBody,
        @Part("kronologis_kejadian") kronologisKejadian: RequestBody,
        @Part buktiFoto: MultipartBody.Part? // Menampung file biner foto dari galeri HP
    ): Call<LaporanResponse>

    @GET("api/get_riwayat.php")
    fun getRiwayat(
        @Query("id_user") idUser: Int
    ): Call<LaporanResponse>

    @FormUrlEncoded
    @POST("api/get_detail_laporan.php")
    fun getDetailLaporan(
        @Field("id_laporan") idLaporan: Int
    ): Call<LaporanResponse>
}