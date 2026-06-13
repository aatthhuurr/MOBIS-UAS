package com.aatthhuurr.mobisuas_2411500025

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aatthhuurr.mobisuas_2411500025.api.RetrofitClient
import com.aatthhuurr.mobisuas_2411500025.helper.SharedPrefManager
import com.aatthhuurr.mobisuas_2411500025.model.LoginResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. DIUBAH KE ATAS: Pasang layout XML terlebih dahulu agar findViewById tidak bernilai null saat logout
        setContentView(R.layout.activity_login)

        // 2. Pengecekan Sesi: Jika user sudah login sebelumnya, langsung lempar ke RiwayatActivity
        val prefManager = SharedPrefManager(this)
        if (prefManager.isLoggedIn()) {
            startActivity(Intent(this, RiwayatActivity::class.java))
            finish()
            return // Menghentikan eksekusi kode di bawahnya agar lebih aman
        }

        // 3. Inisialisasi komponen berdasarkan ID di activity_login.xml
        val edtNoHp = findViewById<TextInputEditText>(R.id.edtNoHp)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val nomorHp = edtNoHp.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (nomorHp.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nomor HP dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                prosesLogin(nomorHp, password)
            }
        }
    }

    private fun prosesLogin(nomorHp: String, password: String) {
        RetrofitClient.instance.login(nomorHp, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginRes = response.body()!!

                    if (loginRes.status && loginRes.data != null) {
                        // Simpan id_user dan nomor_hp ke SharedPreferences
                        val prefManager = SharedPrefManager(this@LoginActivity)
                        prefManager.saveUser(loginRes.data.id_user, loginRes.data.nomor_hp)

                        Toast.makeText(this@LoginActivity, loginRes.message, Toast.LENGTH_SHORT).show()

                        // Berpindah ke halaman list laporan utama
                        startActivity(Intent(this@LoginActivity, RiwayatActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, loginRes.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Respon server gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal terhubung ke server: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}