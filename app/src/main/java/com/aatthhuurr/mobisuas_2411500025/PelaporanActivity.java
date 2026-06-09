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
    private ImageButton btnBack, btnAddPhoto; // Catatan tim: 'btnAddPhoto' digunakan untuk memegang ID 'btnUploadMedia' di XML
    private TextInputEditText edtJudul, edtSearchMaps, edtKronologis;
    private AutoCompleteTextView dropKategori;
    private MaterialButton btnGetLocation, btnLaporkan;
    private ImageView imgBuktiPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelaporan);

        // ======================================================================
        // TUGAS BACK-END TIM (PANDUAN PEMBAGIAN TUGAS):
        //
        // TODO 1: Lakukan casting menggunakan findViewById() untuk menghubungkan
        //         semua variabel global di atas dengan ID komponen dari XML.
        //         (Khusus 'btnAddPhoto', hubungkan ke ID 'btnUploadMedia').
        //
        // TODO 2: Buat ArrayAdapter<String> berisi daftar kategori pengaduan
        //         (misal: Tambang Ilegal, Polusi, Kebakaran, dll), lalu pasang
        //         adapter tersebut ke komponen 'dropKategori'.
        //
        // TODO 3: Fungsikan 'btnAddPhoto' (Upload Media) dengan Intent Kamera atau
        //         Galeri. Jika berhasil mengambil gambar, munculkan wadah
        //         'cardMediaPreview' menjadi VISIBLE dan set gambarnya ke 'imgBuktiPreview'.
        //
        // TODO 4: Integrasikan Google Maps SDK / FusedLocationProviderClient pada
        //         'btnGetLocation' untuk mengambil koordinat GPS terkini dan
        //         mengarahkan pin kamera pada 'mapFragment'.
        //
        // TODO 5: Hubungkan 'btnLaporkan' ke logika API/Database untuk mengirimkan
        //         kumpulan data string form beserta file gambar dokumentasi.
        //
        // TODO 6: Fungsikan 'btnBack' dengan perintah finish() agar bisa kembali
        //         ke halaman utama/riwayat dengan mulus.
        // ======================================================================
    }
}