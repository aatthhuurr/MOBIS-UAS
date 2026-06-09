package com.aatthhuurr.mobisuas_2411500025;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtNoHp, edtPassword;
    private MaterialButton btnLogin;
    private MaterialCheckBox chkRememberMe;
    private MaterialButton btnGoogleLogin;
    private ImageButton btnFingerprint, btnFaceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Casting ID berdasarkan XML kalian
        edtNoHp = findViewById(R.id.edtNoHp);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnFingerprint = findViewById(R.id.btnFingerprint);
        btnFaceId = findViewById(R.id.btnFaceId);

        btnLogin.setOnClickListener(v -> {
            String noHp = edtNoHp.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (noHp.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Panggil API Login sesuai skema gambar kalian
                ApiClient.getClient().login(noHp, password).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse res = response.body();

                            if (res.getStatus().equalsIgnoreCase("success")) {
                                Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                                // Lempar ID User ke RiwayatActivity agar query SQL di PHP tepat sasaran
                                Intent intent = new Intent(LoginActivity.this, RiwayatActivity.class);
                                intent.putExtra("ID_USER", res.getIdUser());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Gagal koneksi server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}