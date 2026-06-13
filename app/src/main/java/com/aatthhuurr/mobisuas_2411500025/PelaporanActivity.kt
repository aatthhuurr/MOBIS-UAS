package com.aatthhuurr.mobisuas_2411500025

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.aatthhuurr.mobisuas_2411500025.api.RetrofitClient
import com.aatthhuurr.mobisuas_2411500025.helper.SharedPrefManager
import com.aatthhuurr.mobisuas_2411500025.model.LaporanResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PelaporanActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private var uriGambarTerpilih: Uri? = null
    private lateinit var imgBuktiPreview: ImageView
    private lateinit var cardMediaPreview: CardView

    // Registrasi fungsi untuk menangkap hasil pemilihan gambar dari galeri
    private val bukaGaleriLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            uriGambarTerpilih = uri
            // Tampilkan komponen preview dan masukkan gambarnya
            cardMediaPreview.visibility = View.VISIBLE
            imgBuktiPreview.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelaporan)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val edtJudul = findViewById<TextInputEditText>(R.id.edtJudul)
        val dropKategori = findViewById<AutoCompleteTextView>(R.id.dropKategori)
        val edtSearchMaps = findViewById<TextInputEditText>(R.id.edtSearchMaps)
        val btnGetLocation = findViewById<MaterialButton>(R.id.btnGetLocation)
        val edtKronologis = findViewById<TextInputEditText>(R.id.edtKronologis)
        val btnUploadMedia = findViewById<MaterialButton>(R.id.btnUploadMedia)
        val btnLaporkan = findViewById<MaterialButton>(R.id.btnLaporkan)

        // Inisialisasi komponen pratinjau foto dari XML asli kamu
        imgBuktiPreview = findViewById(R.id.imgBuktiPreview)
        cardMediaPreview = findViewById(R.id.cardMediaPreview)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val listNamaKategori = arrayOf("Tambang Ilegal", "Kebakaran", "Bencana", "Gangguan PDAM", "Penumpukan Sampah")
        val adapterKategori = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listNamaKategori)
        dropKategori.setAdapter(adapterKategori)

        btnBack.setOnClickListener { finish() }

        btnGetLocation.setOnClickListener {
            edtSearchMaps.setText("Pangkalpinang, Bangka Belitung")
            val lokasiBabel = LatLng(-2.1299, 106.1138)
            mGoogleMap?.clear()
            mGoogleMap?.addMarker(MarkerOptions().position(lokasiBabel).title("Lokasi Kejadian"))
            mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiBabel, 15f))
            Toast.makeText(this, "Lokasi berhasil disematkan", Toast.LENGTH_SHORT).show()
        }

        // AKSI TOMBOL UPLOAD FOTO: Membuka galeri sistem Android untuk memilih file gambar
        btnUploadMedia.setOnClickListener {
            bukaGaleriLauncher.launch("image/*")
        }

        btnLaporkan.setOnClickListener {
            val javaJudul = edtJudul.text.toString().trim()
            val javaLokasi = edtSearchMaps.text.toString().trim()
            val javaKronologis = edtKronologis.text.toString().trim()
            val kategoriTerpilih = dropKategori.text.toString()

            val prefManager = SharedPrefManager(this)
            val idUserTerlogin = prefManager.getIdUser()

            val idKategori = when (kategoriTerpilih) {
                "Tambang Ilegal" -> 1
                "Kebakaran" -> 2
                "Bencana" -> 3
                "Gangguan PDAM" -> 4
                "Penumpukan Sampah" -> 5
                else -> 0
            }

            if (javaJudul.isEmpty() || javaLokasi.isEmpty() || javaKronologis.isEmpty() || idKategori == 0) {
                Toast.makeText(this, "Semua data formulir pelaporan wajib diisi!", Toast.LENGTH_SHORT).show()
            } else if (idUserTerlogin == -1) {
                Toast.makeText(this, "Sesi habis, silakan lakukan login ulang", Toast.LENGTH_SHORT).show()
            } else {
                // DIPERBAIKI: Sekarang mengoper variabel uriGambarTerpilih ke fungsi pengirim
                kirimLaporanKeServer(idUserTerlogin, idKategori, javaJudul, javaLokasi, javaKronologis, uriGambarTerpilih)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val defaultBabel = LatLng(-2.1299, 106.1138)
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultBabel, 12f))
    }

    // DIPERBAIKI: Fungsi kirim laporan berbasis Multipart Form-Data untuk mengunggah file biner
    private fun kirimLaporanKeServer(idUser: Int, idKategori: Int, judul: String, lokasi: String, kronologis: String, fileUri: Uri?) {

        // 1. Konversi data teks biasa menjadi RequestBody Multipart
        val rbIdUser = RequestBody.create(MediaType.parse("text/plain"), idUser.toString())
        val rbIdKategori = RequestBody.create(MediaType.parse("text/plain"), idKategori.toString())
        val rbJudul = RequestBody.create(MediaType.parse("text/plain"), judul)
        val rbLokasi = RequestBody.create(MediaType.parse("text/plain"), lokasi)
        val rbKronologis = RequestBody.create(MediaType.parse("text/plain"), kronologis)

        // 2. Olah file gambar fisik dari URI galeri menggunakan ContentResolver
        var bodyGambar: MultipartBody.Part? = null
        if (fileUri != null) {
            try {
                val inputStream = contentResolver.openInputStream(fileUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes != null) {
                    val requestFile = RequestBody.create(MediaType.parse("image/*"), bytes)
                    // "bukti_foto" disamakan dengan input $_FILES['bukti_foto'] di PHP backend
                    bodyGambar = MultipartBody.Part.createFormData(
                        "bukti_foto",
                        "laporan_${System.currentTimeMillis()}.jpg",
                        requestFile
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 3. Eksekusi pengiriman data campuran ke server Apache via Retrofit
        RetrofitClient.instance.insertLaporan(rbIdUser, rbIdKategori, rbJudul, rbLokasi, rbKronologis, bodyGambar)
            .enqueue(object : Callback<LaporanResponse> {
                override fun onResponse(call: Call<LaporanResponse>, response: Response<LaporanResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val res = response.body()!!
                        Toast.makeText(this@PelaporanActivity, res.message, Toast.LENGTH_SHORT).show()
                        if (res.status) {
                            finish() // Berhasil menyimpan, tutup halaman form
                        }
                    } else {
                        Toast.makeText(this@PelaporanActivity, "Gagal memproses respon server", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LaporanResponse>, t: Throwable) {
                    Toast.makeText(this@PelaporanActivity, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}