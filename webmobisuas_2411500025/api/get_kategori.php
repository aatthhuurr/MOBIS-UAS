<?php
header("Content-Type: application/json");
include '../koneksi.php';

$query = "SELECT * FROM kategori";
$result = mysqli_query($koneksi, $query);
$response = array();

if (mysqli_num_rows($result) > 0) {
    $response["status"] = true;
    $response["message"] = "Berhasil memuat kategori";
    $response["data"] = array();

    while ($row = mysqli_fetch_assoc($result)) {
        array_push($response["data"], $row);
    }
} else {
    $response["status"] = false;
    $response["message"] = "Kategori kosong";
    $response["data"] = array();
}

echo json_encode($response);
mysqli_close($koneksi);