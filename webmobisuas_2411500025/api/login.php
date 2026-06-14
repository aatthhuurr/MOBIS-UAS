<?php
header("Content-Type: application/json");
include '../koneksi.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nomor_hp = $_POST['nomor_hp'] ?? '';
    $password = $_POST['password'] ?? '';

    if (!empty($nomor_hp) && !empty($password)) {
        $query = "SELECT * FROM users WHERE nomor_hp = '$nomor_hp' AND password = '$password'";
        $result = mysqli_query($koneksi, $query);

        if (mysqli_num_rows($result) > 0) {
            $user = mysqli_fetch_assoc($result);
            $response["status"] = true;
            $response["message"] = "Login berhasil!";
            $response["data"] = [
                "id_user" => $user["id_user"],
                "nomor_hp" => $user["nomor_hp"]
            ];
        } else {
            $response["status"] = false;
            $response["message"] = "Nomor HP atau Password salah!";
        }
    } else {
        $response["status"] = false;
        $response["message"] = "Data tidak boleh kosong";
    }
} else {
    $response["status"] = false;
    $response["message"] = "Metode request salah";
}

echo json_encode($response);
mysqli_close($koneksi);