<?php
include '../koneksi.php'; // Pastikan variabelnya $koneksi

// WAJIB pakai $_POST karena di Android kita pakai @POST dan @Field
$id_laporan = $_POST['id_laporan']; 

// Query untuk mengambil detail 1 laporan saja berdasarkan id_laporan
$query = "SELECT laporan.*, kategori.nama_kategori 
          FROM laporan 
          JOIN kategori ON laporan.id_kategori = kategori.id_kategori 
          WHERE laporan.id_laporan = '$id_laporan'";

$result = mysqli_query($koneksi, $query);
$data_laporan = array();

if ($result) {
    while ($row = mysqli_fetch_assoc($result)) {
        $data_laporan[] = $row;
    }
    
    // Kirim respon balik ke Android
    echo json_encode(array(
        "status" => true,
        "message" => "Berhasil memuat rincian laporan",
        "data" => $data_laporan
    ));
} else {
    echo json_encode(array(
        "status" => false,
        "message" => "Gagal: " . mysqli_error($koneksi),
        "data" => []
    ));
}
?>