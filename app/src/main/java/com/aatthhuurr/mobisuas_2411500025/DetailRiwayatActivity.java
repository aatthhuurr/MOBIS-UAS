package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailRiwayatActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView txtToolbarTitle, txtDetailJudul, txtDetailKategori, txtDetailStatus, txtDetailTanggal;
    private TextView txtDetailKronologis, txtDetailTanggapanAdmin, txtDetailDitanggapi;
    private ImageView imgDetailStatusIcon, imgDetailBukti;
    private VideoView videoDetailBukti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        btnBack = findViewById(R.id.btnBack);
        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtDetailJudul = findViewById(R.id.txtDetailJudul);
        txtDetailKategori = findViewById(R.id.txtDetailKategori);
        txtDetailStatus = findViewById(R.id.txtDetailStatus);
        txtDetailTanggal = findViewById(R.id.txtDetailTanggal);
        txtDetailKronologis = findViewById(R.id.txtDetailKronologis);
        txtDetailTanggapanAdmin = findViewById(R.id.txtDetailTanggapanAdmin);
        txtDetailDitanggapi = findViewById(R.id.txtDetailDitanggapi);
        imgDetailStatusIcon = findViewById(R.id.imgDetailStatusIcon);
        imgDetailBukti = findViewById(R.id.imgDetailBukti);
        videoDetailBukti = findViewById(R.id.videoDetailBukti);

        // Menerima data dari halaman list riwayat
        LaporanModel laporan = (LaporanModel) getIntent().getSerializableExtra("DATA_LAPORAN");

        if (laporan != null) {
            txtDetailJudul.setText(laporan.getJudul());
            txtDetailKategori.setText(laporan.getKategori());
            txtDetailStatus.setText(laporan.getStatus());
            txtDetailTanggal.setText("Dilapor pada:\n" + laporan.getTanggalLapor());
            txtDetailKronologis.setText(laporan.getKronologis());
            txtDetailTanggapanAdmin.setText(laporan.getTanggapanAdmin() != null ? laporan.getTanggapanAdmin() : "Belum ditanggapi.");
            txtDetailDitanggapi.setText("Ditanggapi pada:\n" + (laporan.getTanggalTanggapi() != null ? laporan.getTanggalTanggapi() : "-"));

            // Atur visibilitas media bukti
            if (laporan.getTipeBukti() != null && laporan.getTipeBukti().equalsIgnoreCase("video")) {
                imgDetailBukti.setVisibility(View.GONE);
                videoDetailBukti.setVisibility(View.VISIBLE);
            } else {
                imgDetailBukti.setVisibility(View.VISIBLE);
                videoDetailBukti.setVisibility(View.GONE);
            }
        }

        btnBack.setOnClickListener(v -> finish());
    }
}