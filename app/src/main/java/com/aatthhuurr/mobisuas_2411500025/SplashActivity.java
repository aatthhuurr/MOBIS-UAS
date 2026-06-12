package com.aatthhuurr.mobisuas_2411500025;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1. Inisialisasi Container yang mau dikasih animasi gess
        LinearLayout layoutSplashContainer = findViewById(R.id.layoutSplashContainer);

        // 2. Load file animasi XML yang kita buat sebelumnya
        Animation animasiKeren = AnimationUtils.loadAnimation(this, R.anim.fade_in_slide_up);

        // 3. Jalankan animasinya!
        if (layoutSplashContainer != null) {
            layoutSplashContainer.startAnimation(animasiKeren);
        }

        // Durasi Splash Screen sebelum pindah halaman (3000 ms = 3 detik)
        int durasiSplash = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah 3 detik, otomatis meluncur ke halaman Login gess
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Biar kalau di-back di HP, gak balik ke halaman loading lagi
            }
        }, durasiSplash);
    }
}