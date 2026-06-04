package com.aatthhuurr.mobisuas_2411500025;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class RiwayatActivity extends AppCompatActivity {

    // Komponen Utama Layout 3
    private ImageButton btnBack;
    private TextInputEditText edtSearch;
    private ChipGroup chipGroupStatus;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        // ======================================================================
        // TUGAS TIM BACK-END (NILAI SEMPURNA - PAK LUKAS TOMMY):
        // 1. Hubungkan rvRiwayat dengan Custom Adapter menggunakan 'item_riwayat.xml'
        // 2. Buat logika visibilitas layoutExpandableArea (View.GONE / View.VISIBLE) ketika card diklik
        // 3. Fungsikan pencarian live di edtSearch & filter status di chipGroupStatus
        // 4. Implementasikan fungsionalitas swipeRefresh untuk menarik data ulang dari API
        // 5. Tambahkan library / kode untuk btnExportPdf (Cetak PDF) & btnShare (Intent Share)
        // 6. Set VideoView / ImageView berdasarkan jenis file bukti di database!
        // ======================================================================
    }
}