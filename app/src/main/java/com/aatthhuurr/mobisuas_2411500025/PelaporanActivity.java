package com.aatthhuurr.mobisuas_2411500025;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PelaporanActivity extends AppCompatActivity {

    private ImageButton btnBack, btnAddPhoto;
    private TextInputEditText edtJudul, edtSearchMaps, edtKronologis;
    private AutoCompleteTextView dropKategori;
    private MaterialButton btnGetLocation, btnLaporkan;
    private ImageView imgBuktiPreview;
    private View cardMediaPreview; // Menambahkan variabel layout preview data

    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int LOCATION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelaporan);

        // TODO 1: Casting ID XML
        btnBack = findViewById(R.id.btnBack);
        btnAddPhoto = findViewById(R.id.btnUploadMedia);
        edtJudul = findViewById(R.id.edtJudul);
        edtSearchMaps = findViewById(R.id.edtSearchMaps);
        edtKronologis = findViewById(R.id.edtKronologis);
        dropKategori = findViewById(R.id.dropKategori);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnLaporkan = findViewById(R.id.btnLaporkan);
        imgBuktiPreview = findViewById(R.id.imgBuktiPreview);
        cardMediaPreview = findViewById(R.id.cardMediaPreview); // Pastikan ID ini ada di XML kalian gess

        // TODO 2: Membuat daftar Kategori Pengaduan di Dropdown
        String[] daftarKategori = {"Tambang Ilegal", "Polusi Lingkungan", "Kebakaran Hutan", "Fasilitas Rusak", "Lainnya"};
        ArrayAdapter<String> adapterKategori = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, daftarKategori);
        dropKategori.setAdapter(adapterKategori);

        // TODO 3: Fungsikan Ambil Foto dari Kamera
        btnAddPhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            } else {
                bukaKamera();
            }
        });

        // TODO 4: Ambil Koordinat GPS Terkini
        btnGetLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ambilLokasiGps();
            }
        });

        // TODO 5: Tombol Kirim Laporan ke API
        btnLaporkan.setOnClickListener(v -> {
            String judul = edtJudul.getText().toString().trim();
            String kategori = dropKategori.getText().toString().trim();
            String kronologis = edtKronologis.getText().toString().trim();

            if (judul.isEmpty() || kategori.isEmpty() || kronologis.isEmpty()) {
                Toast.makeText(this, "Form laporan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                // Di sini nanti proses tembak data ke server backend kalian
                Toast.makeText(this, "Laporan berhasil terkirim ke server!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // TODO 6: Tombol Kembali
        btnBack.setOnClickListener(v -> finish());
    }

    private void bukaKamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void ambilLokasiGps() {
        // Menggunakan LocationManager bawaan Android agar tidak perlu library tambahan luar gess
        android.location.LocationManager locationManager = (android.location.LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    double latitude = lastKnownLocation.getLatitude();
                    double longitude = lastKnownLocation.getLongitude();
                    // Set teks lokasi berupa koordinat ke kolom maps
                    edtSearchMaps.setText(latitude + ", " + longitude);
                    Toast.makeText(this, "Lokasi GPS berhasil didapatkan!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nyalakan GPS HP kamu terlebih dahulu gess!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Menangkap hasil foto setelah kamera ditutup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (cardMediaPreview != null) {
                cardMediaPreview.setVisibility(View.VISIBLE); // Munculkan wadah frame preview
            }
            imgBuktiPreview.setImageBitmap(imageBitmap); // Pasang fotonya
        }
    }
}