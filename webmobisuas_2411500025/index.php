<?php
require 'koneksi.php';

// Proteksi halaman, jika belum login tendang ke login.php
if (!isset($_SESSION['user'])) {
    header("Location: login.php");
    exit;
}

$user_sekarang = $_SESSION['user'];

// --- PROSES SIMPAN LAPORAN (TAMBAH LAPORAN) ---
if (isset($_POST['btn_laporkan'])) {
    $id_user = $user_sekarang['id_user'];
    $id_kategori = $_POST['id_kategori'];
    $judul_laporan = mysqli_real_escape_string($koneksi, $_POST['judul_laporan']);
    $lokasi_kejadian = mysqli_real_escape_string($koneksi, $_POST['lokasi_kejadian']);
    $kronologis_kejadian = mysqli_real_escape_string($koneksi, $_POST['kronologis_kejadian']);
    
    // Simulasi upload file gambar sederhana
    $nama_file = "default_bukti.png";
    if (isset($_FILES['bukti_foto']) && $_FILES['bukti_foto']['error'] === 0) {
        $nama_file = time() . "_" . $_FILES['bukti_foto']['name'];
        // Pastikan folder 'uploads/' sudah dibuat jika ingin memindahkan file aslinya
        move_uploaded_file($_FILES['bukti_foto']['tmp_name'], 'uploads/' . $nama_file);
    }

    $query_insert = "INSERT INTO laporan (id_user, id_kategori, judul_laporan, lokasi_kejadian, kronologis_kejadian, bukti_foto, status_laporan, tgl_dilapor) 
                     VALUES ('$id_user', '$id_kategori', '$judul_laporan', '$lokasi_kejadian', '$kronologis_kejadian', '$nama_file', 'Menunggu Tanggapan', NOW())";
    
    if(mysqli_query($koneksi, $query_insert)) {
        echo "<script>alert('Laporan berhasil disimpan ke database!'); window.location='index.php';</script>";
    }
}

// --- SIMULASI FITUR ADMIN: UPDATE STATUS & TANGGAPAN ---
if (isset($_GET['aksi']) && $_GET['aksi'] === 'update_status') {
    $id_lpr = $_GET['id'];
    $status_baru = $_GET['status'];
    
    $tanggapan = "Laporan berstatus diubah menjadi " . $status_baru;
    if($status_baru == 'Diproses') $tanggapan = "Laporan dikonfirmasi, petugas segera menuju lokasi.";
    if($status_baru == 'Ditolak') $tanggapan = "Laporan ditolak, data kurang akurat.";
    if($status_baru == 'Selesai') $tanggapan = "Masalah telah diselesaikan di lapangan. Terima kasih.";

    $query_update = "UPDATE laporan SET status_laporan = '$status_baru', tanggapan_admin = '$tanggapan', tgl_ditanggapi = NOW() WHERE id_laporan = '$id_lpr'";
    mysqli_query($koneksi, $query_update);
    header("Location: index.php");
    exit;
}

// --- AMBIL DATA KATEGORI UNTUK DROPDOWN ---
$res_kategori = mysqli_query($koneksi, "SELECT * FROM kategori");

// --- AMBIL DATA LAPORAN (JOIN DENGAN KATEGORI DAN USERS) ---
$query_laporan = "SELECT laporan.*, kategori.nama_kategori, users.nomor_hp 
                  FROM laporan 
                  LEFT JOIN kategori ON laporan.id_kategori = kategori.id_kategori
                  LEFT JOIN users ON laporan.id_user = users.id_user 
                  ORDER BY laporan.id_laporan DESC";
$res_laporan = mysqli_query($koneksi, $query_laporan);
?>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Pengaduan - dbmobisuas_2411500025</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gray-100 min-h-screen flex flex-col">

    <header class="bg-blue-600 text-white shadow p-4 flex justify-between items-center">
        <div>
            <h1 class="font-bold text-lg">Web Pengaduan Real-Database</h1>
        </div>
        <div class="text-sm font-medium">
            <i class="fa-solid fa-user-circle mr-1"></i> <span><?= $user_sekarang['nomor_hp']; ?></span>
            <a href="logout.php" class="ml-4 bg-red-500 hover:bg-red-600 px-3 py-1 rounded text-xs transition">Logout</a>
        </div>
    </header>

    <main class="flex-1 max-w-6xl w-full mx-auto p-4 grid grid-cols-1 md:grid-cols-2 gap-6 my-4">
        
        <section class="bg-white rounded-2xl shadow-md p-6 border border-gray-200 h-fit">
            <div class="flex flex-col items-center mb-4">
                <div class="w-16 h-16 rounded-full border-2 border-gray-300 flex items-center justify-center text-gray-400 font-bold text-xs bg-gray-50">Logo</div>
                <span class="text-xs text-gray-400 mt-1 uppercase tracking-wider font-semibold">Form Pelaporan</span>
            </div>

            <form action="" method="POST" enctype="multipart/form-data" class="space-y-4">
                <div>
                    <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Kategori Pelaporan</label>
                    <select name="id_kategori" required class="w-full px-3 py-2 border rounded-lg text-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <?php while($row_kat = mysqli_fetch_assoc($res_kategori)): ?>
                            <option value="<?= $row_kat['id_kategori']; ?>"><?= $row_kat['nama_kategori']; ?></option>
                        <?php endwhile; ?> </select>
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Judul Laporan</label>
                    <input type="text" name="judul_laporan" required placeholder="Judul laporan singkat" class="w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Lokasi Kejadian</label>
                    <input type="text" name="lokasi_kejadian" required placeholder="Lokasi TKP" class="w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Kronologis Kejadian</label>
                    <textarea name="kronologis_kejadian" rows="3" required placeholder="Detail kronologi..." class="w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"></textarea>
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Bukti Foto (jika ada)</label>
                    <input type="file" name="bukti_foto" accept="image/*" class="text-xs text-gray-500">
                </div>
                
                <button type="submit" name="btn_laporkan" class="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 rounded-lg text-sm uppercase tracking-wider transition shadow">Button LAPORKAN</button>
            </form>
        </section>

        <section class="bg-white rounded-2xl shadow-md p-6 border border-gray-200 flex flex-col h-[650px]">
            <div class="border-b pb-3 mb-4 flex justify-between items-center">
                <h2 class="font-bold text-gray-700 text-sm uppercase tracking-wider"><i class="fa-solid fa-list text-blue-500 mr-2"></i>Daftar Laporan (MySQL)</h2>
                <span class="bg-blue-100 text-blue-800 text-xs px-2 py-0.5 rounded-full font-bold"><?= mysqli_num_rows($res_laporan); ?></span>
            </div>
            
            <div class="space-y-4 flex-1 overflow-y-auto pr-1 text-xs">
                <?php if(mysqli_num_rows($res_laporan) === 0): ?>
                    <div class="text-center py-12 text-gray-400">
                        <i class="fa-solid fa-folder-open text-3xl mb-2"></i>
                        <p>Belum ada data laporan di database.</p>
                    </div>
                <?php endif; ?>

                <?php while($lpr = mysqli_fetch_assoc($res_laporan)): 
                    // Pewarnaan Badge Status
                    $badgeColor = "bg-gray-100 text-gray-700";
                    if($lpr['status_laporan'] === 'Diproses') $badgeColor = "bg-amber-100 text-amber-800";
                    elseif($lpr['status_laporan'] === 'Selesai') $badgeColor = "bg-green-100 text-green-800";
                    elseif($lpr['status_laporan'] === 'Ditolak') $badgeColor = "bg-red-100 text-red-800";
                ?>
                    <div class="border-2 border-gray-400 bg-white rounded-xl p-3 shadow-sm">
                        <div class="border-b pb-1 mb-2 font-bold text-sm text-gray-800 flex justify-between items-center bg-gray-50 px-2 py-0.5 rounded">
                            <span><?= htmlspecialchars($lpr['judul_laporan']); ?></span>
                            <span class="text-[10px] text-gray-400 font-mono">ID: <?= $lpr['id_laporan']; ?></span>
                        </div>

                        <div class="grid grid-cols-4 gap-2 border-b pb-2 mb-2">
                            <div class="col-span-1 flex flex-col items-center justify-center border-r pr-1 text-center">
                                <div class="w-12 h-12 rounded-full border border-red-400 flex flex-col items-center justify-center bg-red-50 text-red-600 p-1 mb-1">
                                    <i class="fa-solid fa-triangle-exclamation text-base"></i>
                                </div>
                                <span class="text-[9px] text-red-400 font-semibold leading-none">ikon status atau kategori</span>
                            </div>

                            <div class="col-span-3 space-y-1 pl-1">
                                <div class="flex justify-between">
                                    <span class="text-gray-400">Status Laporan:</span>
                                    <span class="px-1.5 py-0.5 rounded text-[10px] font-bold <?= $badgeColor; ?>"><?= $lpr['status_laporan']; ?></span>
                                </div>
                                <div class="flex justify-between">
                                    <span class="text-gray-400">Kategori Laporan:</span>
                                    <span class="font-medium text-gray-800"><?= $lpr['nama_kategori'] ?? 'Umum'; ?></span>
                                </div>
                                <div class="pt-1">
                                    <span class="text-gray-400 block mb-0.5">Tanggapan Admin:</span>
                                    <p class="italic text-gray-700 bg-gray-50 p-1 rounded border border-dashed"><?= htmlspecialchars($lpr['tanggapan_admin'] ?? '-'); ?></p>
                                </div>
                            </div>
                        </div>

                        <div class="space-y-0.5 text-[10px] text-gray-500 font-mono">
                            <div><strong>Dilapor:</strong> <?= $lpr['tgl_dilapor']; ?> (Oleh: <?= $lpr['nomor_hp']; ?>)</div>
                            <div><strong>Ditanggapi:</strong> <?= $lpr['tgl_ditanggapi'] ?? '-'; ?></div>
                        </div>
                    </div>
                <?php endwhile; ?>
            </div>
        </section>

    </main>
</body>
</html>

