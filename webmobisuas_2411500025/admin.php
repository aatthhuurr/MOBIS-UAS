<?php
require 'koneksi.php';

// PROSES UPDATE STATUS DAN TANGGAPAN KUSTOM OLEH ADMIN
if (isset($_POST['btn_tanggapi'])) {
    $id_lpr = $_POST['id_laporan'];
    $status_baru = $_POST['status_laporan'];
    $tanggapan_kustom = mysqli_real_escape_string($koneksi, $_POST['tanggapan_admin']);

    $query_update = "UPDATE laporan SET 
                        status_laporan = '$status_baru', 
                        tanggapan_admin = '$tanggapan_kustom', 
                        tgl_ditanggapi = NOW() 
                     WHERE id_laporan = '$id_lpr'";
                     
    if (mysqli_query($koneksi, $query_update)) {
        echo "<script>alert('Laporan berhasil ditanggapi!'); window.location='admin.php';</script>";
    } else {
        echo "<script>alert('Gagal memperbarui laporan.');</script>";
    }
}

// AMBIL SEMUA LAPORAN MASUK (JOIN USERS & KATEGORI)
$query_laporan = "SELECT laporan.*, kategori.nama_kategori, users.nomor_hp 
                  FROM laporan 
                  LEFT JOIN kategori ON laporan.id_kategori = kategori.id_kategori
                  LEFT JOIN users ON laporan.id_user = users.id_user 
                  ORDER BY laporan.status_laporan = 'Menunggu Tanggapan' DESC, laporan.id_laporan DESC";
$res_laporan = mysqli_query($koneksi, $query_laporan);
?>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>PANEL ADMIN - dbmobisuas_2411500025</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gray-900 text-gray-100 min-h-screen flex flex-col">

    <header class="bg-slate-800 border-b border-slate-700 text-white shadow p-4 flex justify-between items-center">
        <div>
            <h1 class="font-bold text-lg text-amber-400"><i class="fa-solid fa-user-shield mr-2"></i>Control Panel Admin</h1>
            <p class="text-xs text-slate-400">Database: dbmobisuas_2411500025</p>
        </div>
        <div class="text-sm">
            <a href="index.php" class="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1.5 rounded text-xs font-semibold transition">
                <i class="fa-solid fa-eye mr-1"></i> Lihat Sisi User
            </a>
        </div>
    </header>

    <main class="flex-1 max-w-6xl w-full mx-auto p-4 my-4">
        
        <div class="bg-slate-800 rounded-2xl shadow-xl p-6 border border-slate-700">
            <div class="border-b border-slate-700 pb-3 mb-6 flex justify-between items-center">
                <h2 class="font-bold text-sm uppercase tracking-wider text-slate-300">
                    <i class="fa-solid fa-folder-tree text-amber-400 mr-2"></i>Kelola Laporan Masuk
                </h2>
                <span class="bg-amber-500/20 text-amber-400 text-xs px-2 py-0.5 rounded-full font-bold border border-amber-500/30">
                    Total: <?= mysqli_num_rows($res_laporan); ?> Laporan
                </span>
            </div>

            <div class="grid grid-cols-1 gap-6">
                <?php if (mysqli_num_rows($res_laporan) === 0): ?>
                    <div class="text-center py-12 text-slate-500">
                        <i class="fa-solid fa-mailbox text-4xl mb-2"></i>
                        <p>Belum ada laporan dari pengguna di database.</p>
                    </div>
                <?php endif; ?>

                <?php while ($lpr = mysqli_fetch_assoc($res_laporan)): 
                    // Menentukan warna border & badge berdasarkan status di DB
                    $status = $lpr['status_laporan'];
                    if ($status === 'Menunggu Tanggapan') {
                        $cardBorder = 'border-amber-500/50 bg-slate-800';
                        $badgeStyle = 'bg-amber-500/20 text-amber-400 border border-amber-500/30';
                    } elseif ($status === 'Diproses') {
                        $cardBorder = 'border-blue-500/50 bg-slate-800';
                        $badgeStyle = 'bg-blue-500/20 text-blue-400 border border-blue-500/30';
                    } elseif ($status === 'Selesai') {
                        $cardBorder = 'border-green-500/30 bg-slate-800/50';
                        $badgeStyle = 'bg-green-500/20 text-green-400 border border-green-500/30';
                    } else {
                        $cardBorder = 'border-red-500/30 bg-slate-800/50';
                        $badgeStyle = 'bg-red-500/20 text-red-400 border border-red-500/30';
                    }
                ?>
                    
                    <div class="border-2 rounded-xl p-4 transition shadow-sm <?= $cardBorder; ?>">
                        <div class="grid grid-cols-1 md:grid-cols-12 gap-4">
                            
                            <div class="md:col-span-7 space-y-2 border-b md:border-b-0 md:border-r border-slate-700 pb-4 md:pb-0 md:pr-4">
                                <div class="flex justify-between items-start">
                                    <h3 class="text-base font-bold text-white"><?= htmlspecialchars($lpr['judul_laporan']); ?></h3>
                                    <span class="text-[11px] px-2 py-0.5 rounded-full font-semibold uppercase tracking-wide <?= $badgeStyle; ?>">
                                        <?= $status; ?>
                                    </span>
                                </div>
                                <div class="text-xs text-slate-400 space-y-1">
                                    <p><strong class="text-slate-300">Kategori:</strong> <?= $lpr['nama_kategori'] ?? 'Umum'; ?></p>
                                    <p><strong class="text-slate-300">Pelapor (HP):</strong> <?= $lpr['nomor_hp']; ?> (ID User: <?= $lpr['id_user']; ?>)</p>
                                    <p><strong class="text-slate-300">Lokasi:</strong> <?= htmlspecialchars($lpr['lokasi_kejadian']); ?></p>
                                    <p><strong class="text-slate-300">Waktu Masuk:</strong> <?= $lpr['tgl_dilapor']; ?></p>
                                </div>
                                <div class="bg-slate-900/50 p-2.5 rounded border border-slate-700 text-xs text-slate-300">
                                    <strong class="text-slate-400 block mb-1">Kronologi Kejadian:</strong>
                                    <?= nl2br(htmlspecialchars($lpr['kronologis_kejadian'])); ?>
                                </div>
                                <?php if($lpr['tgl_ditanggapi'] && $lpr['tgl_ditanggapi'] != '-'): ?>
                                    <div class="text-[11px] text-emerald-400 bg-emerald-950/30 p-2 rounded border border-emerald-900/40">
                                        <strong>Tanggapan Sebelumnya:</strong> "<?= htmlspecialchars($lpr['tanggapan_admin']); ?>"
                                        <span class="block text-[10px] text-slate-400 mt-1">Diproses pada: <?= $lpr['tgl_ditanggapi']; ?></span>
                                    </div>
                                <?php endif; ?>
                            </div>

                            <div class="md:col-span-5 flex flex-col justify-center">
                                <span class="text-xs font-semibold uppercase tracking-wider text-amber-400 block mb-2"><i class="fa-solid fa-pen-to-square mr-1"></i> Update Tanggapan & Status</span>
                                
                                <form action="" method="POST" class="space-y-3">
                                    <input type="hidden" name="id_laporan" value="<?= $lpr['id_laporan']; ?>">
                                    
                                    <div>
                                        <label class="block text-[10px] text-slate-400 uppercase font-medium mb-1">Pilih Status Terbaru</label>
                                        <select name="status_laporan" required class="w-full bg-slate-900 border border-slate-700 rounded p-1.5 text-xs text-white focus:outline-none focus:border-amber-500">
                                            <option value="Menunggu Tanggapan" <?= $status == 'Menunggu Tanggapan' ? 'selected' : ''; ?>>Menunggu Tanggapan</option>
                                            <option value="Diproses" <?= $status == 'Diproses' ? 'selected' : ''; ?>>Diproses (Sedang Ditangani)</option>
                                            <option value="Selesai" <?= $status == 'Selesai' ? 'selected' : ''; ?>>Selesai (Clear)</option>
                                            <option value="Ditolak" <?= $status == 'Ditolak' ? 'selected' : ''; ?>>Ditolak</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label class="block text-[10px] text-slate-400 uppercase font-medium mb-1">Tulis Tanggapan Resmi</label>
                                        <textarea name="tanggapan_admin" rows="2" required placeholder="Ketik respon penanganan di sini..." class="w-full bg-slate-900 border border-slate-700 rounded p-1.5 text-xs text-white focus:outline-none focus:border-amber-500"><?= $lpr['tanggapan_admin'] != '-' ? htmlspecialchars($lpr['tanggapan_admin']) : ''; ?></textarea>
                                    </div>

                                    <button type="submit" name="btn_tanggapi" class="w-full bg-amber-500 hover:bg-amber-600 text-slate-900 font-bold py-1.5 rounded text-xs uppercase tracking-wide transition shadow-md">
                                        Simpan Perubahan Ke DB
                                    </button>
                                </form>
                            </div>

                        </div>
                    </div>
                <?php endwhile; ?>
            </div>
        </div>

    </main>

    <footer class="bg-slate-950 text-slate-500 text-center py-3 text-xs border-t border-slate-800">
        <p>Panel Kontrol Admin Basis Data Real-Time — dbmobisuas_2411500025</p>
    </footer>

</body>
</html>