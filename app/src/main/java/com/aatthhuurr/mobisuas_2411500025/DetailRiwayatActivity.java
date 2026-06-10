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
        // TUGAS BACK-END TIM:
        // 1. Ambil data pengaduan yang diklik dari Intent / Bundle RecyclerView.
        // 2. Set teks toolbar sesuai judul laporan.
        // 3. Suntikkan semua data teks dan ubah ikon status secara dinamis.
        // 4. Jika file berupa video, putar lewat videoDetailBukti!
        // ======================================================================
    }
}