package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RiwayatActivity extends AppCompatActivity {

    // MODAL DASAR COMPLIANT SOAL DOSEN UNTUK BACK-END TIM:
    private ImageButton btnBack;
    private TextInputEditText edtSearch;
    private MaterialButton btnFilter;
    private RecyclerView rvRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        // ======================================================================
        // TIM BACK-END TUGAS:
        // Silakan casting findViewById dan buat adapter berdasarkan urutan wajib
        // item_riwayat.xml gess! (Judul, Ikon, Status, Kategori, Tanggapan, Tgl Lapor, Tgl Tanggapi)
        // ======================================================================
    }
}