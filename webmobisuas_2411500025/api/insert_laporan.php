<?php
header("Content-Type: application/json");
include '../koneksi.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // 1. Tangkap data teks Multipart menggunakan $_POST
    $id_user             = $_POST['id_user'] ?? '';
    $id_kategori         = $_POST['id_kategori'] ?? '';
    $judul_laporan       = $_POST['judul_laporan'] ?? '';
    $lokasi_kejadian     = $_POST['lokasi_kejadian'] ?? '';
    $kronologis_kejadian = $_POST['kronologis_kejadian'] ?? '';
    
    // 2. Proses upload file gambar menggunakan $_FILES
    $nama_file_baru = null; // Default jika user tidak upload foto
    
    if (isset($_FILES['bukti_foto']) && $_FILES['bukti_foto']['error'] === UPLOAD_ERR_OK) {
        $file_tmp  = $_FILES['bukti_foto']['tmp_name'];
        $file_name = $_FILES['bukti_foto']['name'];
        
        // Ambil ekstensi file (jpg, png, dll)
        $file_ext  = pathinfo($file_name, PATHINFO_EXTENSION);
        
        // Buat nama file unik baru agar tidak bentrok di folder uploads
        $nama_file_baru = "laporan_" . time() . "_" . rand(1000, 9999) . "." . $file_ext;
        
        // Tentukan jalur tujuan folder uploads (naik satu folder lalu masuk ke folder uploads)
        $target_dir = "../uploads/" . $nama_file_baru;
        
        // Pindahkan file dari memori sementara ke folder tujuan fisik di Ubuntu
        if (!move_uploaded_file($file_tmp, $target_dir)) {
            $response["status"] = false;
            $response["message"] = "Gagal mengunggah file gambar ke folder server.";
            echo json_encode($response);
            exit;
        }
    }

    // Validasi data teks wajib diisi
    if (!empty($id_user) && !empty($id_kategori) && !empty($judul_laporan) && !empty($lokasi_kejadian) && !empty($kronologis_kejadian)) {
        
        // Simpan nama_file_baru ke dalam kolom bukti_foto di MySQL
        $query = "INSERT INTO laporan (id_user, id_kategori, judul_laporan, lokasi_kejadian, kronologis_kejadian, bukti_foto, status_laporan) 
                  VALUES ('$id_user', '$id_kategori', '$judul_laporan', '$lokasi_kejadian', '$kronologis_kejadian', '$nama_file_baru', 'Menunggu Tanggapan')";
        
        if (mysqli_query($koneksi, $query)) {
            $response["status"] = true;
            $response["message"] = "Laporan beserta foto berhasil dikirim!";
        } else {
            $response["status"] = false;
            $response["message"] = "Gagal menyimpan laporan: " . mysqli_error($koneksi);
        }
    } else {
        $response["status"] = false;
        $response["message"] = "Semua kolom laporan wajib diisi!";
    }
} else {
    $response["status"] = false;
    $response["message"] = "Metode request salah";
}

echo json_encode($response);
mysqli_close($koneksi);
?>