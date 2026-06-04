package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import komponen material design agar tidak error
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    // 1. Komponen Input & Tombol Utama
    private TextInputEditText edtNoHp, edtPassword;
    private MaterialButton btnLogin;

    // 2. Fitur Tambahan (Checkbox Ingat Saya)
    private MaterialCheckBox chkRememberMe;

    // 3. MODAL DEKLARASI TOMBOL SHORTCUT BUAT TIM BACK-END:
    private ImageButton btnGoogleLogin, btnFingerprint, btnFaceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Kode default bawaan Android Studio Quail untuk padding layar modern
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ======================================================================
        // TUGAS BACK-END TIM:
        // Silakan lanjutkan casting findViewById dan logika onClick API di bawah nih ok ges!
        // Semua komponen di atas (edtNoHp, edtPassword, btnLogin, chkRememberMe,
        // btnGoogleLogin, btnFingerprint, btnFaceId) lah siap di eksekusi.
        // ======================================================================

    }
}