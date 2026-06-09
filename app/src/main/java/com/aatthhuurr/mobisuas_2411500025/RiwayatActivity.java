package com.aatthhuurr.mobisuas_2411500025;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextInputEditText edtSearch;
    private MaterialButton btnFilter;
    private RecyclerView rvRiwayat;
    private FloatingActionButton fabTambahLaporan;

    private RiwayatAdapter adapter;
    private List<LaporanModel> listAsli = new ArrayList<>();
    private List<LaporanModel> listFilter = new ArrayList<>();

    // 1. Deklarasi variabel ID User dipindah ke sini (sebagai variabel global class)
    private String idUserLogedIn = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        btnFilter = findViewById(R.id.btnFilter);
        rvRiwayat = findViewById(R.id.rvRiwayat);
        fabTambahLaporan = findViewById(R.id.fabTambahLaporan);

        // 2. Logika menangkap Intent SEKARANG SUDAH MASUK ke dalam onCreate() agar tidak error
        if (getIntent().hasExtra("ID_USER")) {
            idUserLogedIn = getIntent().getStringExtra("ID_USER");
        }

        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data dari API berdasarkan ID User yang aktif
        loadLaporanDariServer();

        fabTambahLaporan.setOnClickListener(v -> {
            startActivity(new Intent(RiwayatActivity.this, PelaporanActivity.class));
        });

        // Logika Pencarian Real-Time judul laporan
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cariJudulLaporan(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadLaporanDariServer() {
        ApiClient.getClient().getRiwayat(idUserLogedIn).enqueue(new Callback<List<LaporanModel>>() {
            @Override
            public void onResponse(Call<List<LaporanModel>> call, Response<List<LaporanModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listAsli.clear();
                    listAsli.addAll(response.body());
                    listFilter.clear();
                    listFilter.addAll(listAsli);

                    adapter = new RiwayatAdapter(RiwayatActivity.this, listFilter);
                    rvRiwayat.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<LaporanModel>> call, Throwable t) {
                Toast.makeText(RiwayatActivity.this, "Gagal memuat riwayat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cariJudulLaporan(String query) {
        listFilter.clear();
        for (LaporanModel item : listAsli) {
            if (item.getJudul().toLowerCase().contains(query.toLowerCase())) {
                listFilter.add(item);
            }
        }
        if (adapter != null) adapter.notifyDataSetChanged();
    }
}