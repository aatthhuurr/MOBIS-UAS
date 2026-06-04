package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PelaporanActivity extends AppCompatActivity {

    // MODAL VARIABEL RESMI UNTUK TIM BACK-END:
    private ImageButton btnBack, btnAddPhoto;
    private TextInputEditText edtJudul, edtSearchMaps, edtKronologis;
    private AutoCompleteTextView dropKategori;
    private MaterialButton btnGetLocation, btnLaporkan;
    private ImageView imgBuktiPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelaporan);

        // ======================================================================
        // TUGAS BACK-END TIM:
        // 1. Lakukan casting findViewById untuk semua komponen di atas.
        // 2. Buat ArrayAdapter string berisi kategori (Tambang Ilegal, Kebakaran, dll)
        //    lalu set ke dropKategori.
        // 3. Fungsikan btnAddPhoto untuk memunculkan pilihan (Kamera vs Galeri)
        //    dan set hasilnya ke imgBuktiPreview gess!
        // 4. Integrasikan Maps SDK / FusedLocationProvider di btnGetLocation.
        // 5. Hubungkan btnLaporkan ke API kirim data pengaduan ke database!
        // ======================================================================
    }
}