package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailRiwayatActivity extends AppCompatActivity {

    // VARIABEL MODAL UNTUK TIM BACK-END MENERIMA DATA INTENT:
    private ImageButton btnBack;
    private TextView txtToolbarTitle, txtDetailJudul, txtDetailKategori, txtDetailStatus, txtDetailTanggal;
    private TextView txtDetailKronologis, txtDetailTanggapanAdmin, txtDetailDitanggapi;
    private ImageView imgDetailStatusIcon, imgDetailBukti;
    private VideoView videoDetailBukti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        // ======================================================================
        // TUGAS BACK-END TIM (CATATAN PANDUAN LOGIKA):
        //
        // TODO 1: Lakukan casting menggunakan findViewById() untuk menghubungkan
        //         semua variabel global di atas dengan ID komponen dari XML.
        //
        // TODO 2: Ambil data objek/ID laporan yang dikirim lewat Intent dari
        //         halaman RecyclerView sebelumnya (RiwayatActivity).
        //
        // TODO 3: Lakukan pemanggilan data ke API/Database berdasarkan ID laporan
        //         tersebut, lalu suntikkan datanya ke masing-masing komponen teks
        //         (txtDetailJudul, txtDetailKronologis, dll) menggunakan .setText().
        //
        // TODO 4: Buat logika percabangan untuk ikon dan warna status. Sesuaikan
        //         tampilan txtDetailStatus dan imgDetailStatusIcon berdasarkan
        //         apakah laporan berstatus "Menunggu", "Diproses", atau "Selesai".
        //
        // TODO 5: Buat pengondisian media bukti. Jika bukti berupa video, sembunyikan
        //         imgDetailBukti (GONE), tampilkan videoDetailBukti (VISIBLE),
        //         lalu jalankan fungsi MediaController untuk memutar videonya.
        //
        // TODO 6: Pasang fungsi onClickListener pada 'btnBack' untuk mengeksekusi
        //         finish() agar user bisa kembali ke halaman list riwayat.
        // ======================================================================
    }
}