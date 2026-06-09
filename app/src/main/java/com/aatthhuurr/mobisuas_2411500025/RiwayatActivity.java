package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // Di-import biar FAB kamu jalan gess
import com.google.android.material.textfield.TextInputEditText;

public class RiwayatActivity extends AppCompatActivity {

    // MODAL DASAR COMPLIANT SOAL DOSEN UNTUK BACK-END TIM:
    private ImageButton btnBack;
    private TextInputEditText edtSearch;
    private MaterialButton btnFilter;
    private RecyclerView rvRiwayat;
    private FloatingActionButton fabTambahLaporan; // Ditambahkan agar sinkron dengan XML-mu gess!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        // ======================================================================
        // TUGAS BACK-END TIM (PANDUAN PEMBAGIAN TUGAS):
        //
        // TODO 1: Lakukan casting menggunakan findViewById() untuk menghubungkan
        //         semua variabel global di atas dengan ID komponen dari XML
        //         (termasuk fabTambahLaporan).
        //
        // TODO 2: Buatlah sebuah class Java baru bernama 'RiwayatAdapter' dan 'RiwayatViewHolder'
        //         untuk merender item_riwayat.xml ke dalam 'rvRiwayat'.
        //
        // TODO 3: Pastikan di dalam Adapter, data di-set BERURUTAN sesuai soal dosen:
        //         (1. Judul, 2. Ikon, 3. Status, 4. Kategori, 5. Tanggapan Admin,
        //          6. Tanggal Lapor, 7. Tanggal Tanggapi).
        //
        // TODO 4: Atur LayoutManager untuk 'rvRiwayat' menggunakan LinearLayoutManager
        //         agar list data berderet rapi ke bawah.
        //
        // TODO 5: Buat fungsi onClickListener pada 'fabTambahLaporan' (+) menggunakan
        //         Intent untuk membuka halaman input baru (PelaporanActivity).
        //
        // TODO 6: Pasang textChangedListener / TextWatcher pada 'edtSearch' untuk
        //         membuat fitur pencarian laporan secara real-time berdasarkan judul.
        //
        // TODO 7: Fungsikan 'btnBack' dengan perintah finish() agar user bisa kembali
        //         ke halaman sebelumnya dengan aman.
        // ======================================================================
    }
}