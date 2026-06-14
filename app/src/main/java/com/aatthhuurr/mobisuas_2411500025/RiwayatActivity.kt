package com.aatthhuurr.mobisuas_2411500025

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aatthhuurr.mobisuas_2411500025.api.RetrofitClient
import com.aatthhuurr.mobisuas_2411500025.helper.SharedPrefManager
import com.aatthhuurr.mobisuas_2411500025.model.LaporanResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvRiwayat: RecyclerView
    private lateinit var prefManager: SharedPrefManager
    private var idUserTerlogin: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val edtSearch = findViewById<TextInputEditText>(R.id.edtSearch)
        val fabTambahLaporan = findViewById<FloatingActionButton>(R.id.fabTambahLaporan)

        rvRiwayat = findViewById(R.id.rvRiwayat)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        rvRiwayat.layoutManager = LinearLayoutManager(this)

        prefManager = SharedPrefManager(this)
        idUserTerlogin = prefManager.getIdUser()

        // Pemicu tarik manual (Swipe to Refresh) tetap aktif
        swipeRefresh.setOnRefreshListener {
            checkAndLoadData()
        }

        fabTambahLaporan.setOnClickListener {
            startActivity(Intent(this, PelaporanActivity::class.java))
        }

        btnBack.setOnClickListener {
            // 1. Bersihkan seluruh session login di Shared Preference
            prefManager.logout()

            Toast.makeText(this, "Berhasil keluar dari akun", Toast.LENGTH_SHORT).show()

            // 2. Arahkan kembali ke halaman Login dengan menghancurkan seluruh tumpukan activity belakang (Clear Task)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // 3. Tutup halaman riwayat secara permanen
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        checkAndLoadData() // Otomatis refresh data tanpa perlu ditarik manual
    }
    // =========================================================================

    private fun checkAndLoadData() {
        if (idUserTerlogin != -1) {
            muatDataRiwayatLaporan(idUserTerlogin, rvRiwayat)
        } else {
            swipeRefresh.isRefreshing = false
            Toast.makeText(this, "Sesi bermasalah, silakan login ulang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun muatDataRiwayatLaporan(idUser: Int, recyclerView: RecyclerView) {
        RetrofitClient.instance.getRiwayat(idUser).enqueue(object : Callback<LaporanResponse> {
            override fun onResponse(call: Call<LaporanResponse>, response: Response<LaporanResponse>) {
                swipeRefresh.isRefreshing = false

                if (response.isSuccessful && response.body() != null) {
                    val res = response.body()!!

                    if (res.status) {
                        val listData = res.data
                        val adapter = RiwayatAdapter(listData) { laporan ->
                            val intent = Intent(this@RiwayatActivity, DetailRiwayatActivity::class.java)
                            intent.putExtra("ID_LAPORAN", laporan.id_laporan)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                        Log.d("API_SUCCESS", "Jumlah data laporan: ${listData.size}")
                    } else {
                        Toast.makeText(this@RiwayatActivity, res.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RiwayatActivity, "Gagal memproses data server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LaporanResponse>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Log.e("API_ERROR", "Error: ${t.message}")
                Toast.makeText(this@RiwayatActivity, "Gagal memuat riwayat: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}