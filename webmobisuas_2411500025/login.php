

<?php
require 'koneksi.php';

if (isset($_SESSION['user'])) {
    header("Location: index.php");
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nomor_hp = mysqli_real_escape_string($koneksi, $_POST['nomor_hp']);
    $password = $_POST['password'];

    // Cek apakah nomor HP sudah terdaftar
    $query = "SELECT * FROM users WHERE nomor_hp = '$nomor_hp'";
    $result = mysqli_query($koneksi, $query);

    if (mysqli_num_rows($result) > 0) {
        $user = mysqli_fetch_assoc($result);
        // Verifikasi password (menggunakan cek string langsung sesuai kesederhanaan UAS)
        if ($password === $user['password']) {
            $_SESSION['user'] = $user;
            header("Location: index.php");
            exit;
        } else {
            $error = "Password salah!";
        }
    } else {
        // Jika belum terdaftar, otomatis daftarkan akun baru
        $insert = "INSERT INTO users (nomor_hp, password) VALUES ('$nomor_hp', '$password')";
        if (mysqli_query($koneksi, $insert)) {
            // Ambil data yang baru disimpan
            $new_id = mysqli_insert_id($koneksi);
            $_SESSION['user'] = [
                'id_user' => $new_id,
                'nomor_hp' => $nomor_hp
            ];
            header("Location: index.php");
            exit;
        } else {
            $error = "Gagal membuat akun otomatis.";
        }
    }
}
?>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>LOGIN - dbmobisuas_2411500025</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="bg-white rounded-2xl shadow-md p-8 border border-gray-200 w-full max-w-sm">
        <div class="flex flex-col items-center mb-6">
            <div class="w-24 h-24 rounded-full border-4 border-gray-300 flex items-center justify-center text-gray-400 font-bold bg-gray-50">Logo</div>
            <h2 class="text-sm font-semibold text-gray-500 mt-2 uppercase">Halaman Login</h2>
        </div>

        <?php if(isset($error)): ?>
            <div class="bg-red-100 text-red-700 p-2 text-xs rounded mb-4 text-center"><?= $error; ?></div>
        <?php endif; ?>

        <form action="" method="POST" class="space-y-4">
            <div>
                <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Nomor HP</label>
                <input type="text" name="nomor_hp" required placeholder="Nomor HP Anda" class="w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500">
            </div>
            <div>
                <label class="block text-xs font-semibold text-gray-500 uppercase mb-1">Password</label>
                <input type="password" name="password" required placeholder="••••••••" class="w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500">
            </div>
            <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-lg text-sm uppercase tracking-wider transition">Button LOGIN</button>
        </form>
    </div>
</body>
</html>

