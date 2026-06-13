<?php
header("Content-Type: application/json");
include '../koneksi.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nomor_hp = $_POST['nomor_hp'] ?? '';
    $password = $_POST['password'] ?? ''; // Catatan: Untuk tugas kuliah MD5 atau plain text biasanya cukup, tapi di industri disarankan password_hash.

    if (!empty($nomor_hp) && !empty($password)) {
        // Cek apakah nomor HP sudah terdaftar
        $cek = mysqli_query($koneksi, "SELECT * FROM users WHERE nomor_hp = '$nomor_hp'");
        
        if (mysqli_num_rows($cek) > 0) {
            $response["status"] = false;
            $response["message"] = "Nomor HP sudah terdaftar!";
        } else {
            // Insert user baru
            $query = "INSERT INTO users (nomor_hp, password) VALUES ('$nomor_hp', '$password')";
            if (mysqli_query($koneksi, $query)) {
                $response["status"] = true;
                $response["message"] = "Registrasi berhasil!";
            } else {
                $response["status"] = false;
                $response["message"] = "Gagal registrasi: " . mysqli_error($koneksi);
            }
        }
    } else {
        $response["status"] = false;
        $response["message"] = "Nomor HP dan Password tidak boleh kosong";
    }
} else {
    $response["status"] = false;
    $response["message"] = "Metode request salah";
}

echo json_encode($response);
mysqli_close($koneksi);