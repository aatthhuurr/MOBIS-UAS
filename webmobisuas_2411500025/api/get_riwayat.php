<?php
include '../koneksi.php'; // Pastikan variabelnya $koneksi ya!

// Wajib pakai $_GET karena di Android kita pakai @GET
$id_user = $_GET['id_user']; 

// Query SQL JOIN untuk mengambil nama_kategori agar pas dengan model Android
$query = "SELECT laporan.*, kategori.nama_kategori 
          FROM laporan 
          JOIN kategori ON laporan.id_kategori = kategori.id_kategori 
          WHERE laporan.id_user = '$id_user'";

$result = mysqli_query($koneksi, $query);
$data_laporan = array();

if ($result) {
    while ($row = mysqli_fetch_assoc($result)) {
        $data_laporan[] = $row;
    }
    
    echo json_encode(array(
        "status" => true,
        "message" => "Berhasil memuat riwayat",
        "data" => $data_laporan
    ));
} else {
    echo json_encode(array(
        "status" => false,
        "message" => "Gagal memuat data: " . mysqli_error($koneksi),
        "data" => []
    ));
}
?>