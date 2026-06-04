package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import komponen biar dak error
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    // Komponen Input & Tombol Utama
    private TextInputEditText edtNoHp, edtPassword;
    private MaterialButton btnLogin;

    // MODAL DEKLARASI BIOMETRIK BUAT TIM BACK-END:
    private ImageButton btnFingerprint, btnFaceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Kode default bawaan Android Studio Quail untuk padding layar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ======================================================================
        // TUGAS BACK-END TIM:
        //lanjutkan casting findViewById dan logika onClick API di bawah nih ok ges!
        // ======================================================================

    }
}