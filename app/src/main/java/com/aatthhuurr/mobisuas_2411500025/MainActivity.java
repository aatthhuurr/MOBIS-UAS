package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ======================================================================
        // CATATAN WAJIB UNTUK TIM BACK-END (PENTING OK GES!):
        //
        // 1. HALAMAN INI TIDAK DIPAKAI: Kita tidak menggunakan menu dashboard utama,
        //    jadi file 'MainActivity' dan 'activity_main.xml' ini JANGAN dikotak-katik.
        //
        // 2. TUGAS KALIAN: Buka file 'AndroidManifest.xml', lalu pindahkan/atur
        //    kunci <intent-filter> (LAUNCHER) agar aplikasi pertama kali terbuka
        //    langsung memunculkan halaman 'LoginActivity'.
        //
        // 3. ATUR INTENT LOGIN: Pastikan di 'LoginActivity', setelah user sukses
        //    masuk (baik lewat manual, google, atau biometrik), langsung lempar
        //    user ke halaman 'RiwayatActivity' menggunakan Intent dan panggil finish().
        // ======================================================================
    }
}