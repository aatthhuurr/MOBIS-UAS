<?php
$host = "localhost";
$user = "root";
$pass = "root"; 
$db   = "dbmobisuas_2411500025";

$koneksi = mysqli_connect($host, $user, $pass, $db);

if (!$koneksi) {
    // Dipaksa keluar JSON agar tidak membingungkan Android Studio / Browser
    header("Content-Type: application/json");
    die(json_encode([
        "status" => false,
        "message" => "Koneksi database gagal: " . mysqli_connect_error()
    ]));
}

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
?>