<?php
require '../configs/koneksi.php';

$answer = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $NIM = $_POST["NIM"];

    $query = "DELETE FROM mahasiswa WHERE NIM = '$NIM'";
    $execute = mysqli_query($conn, $query);
    $test = mysqli_affected_rows($conn);

    if ($test > 0) {
        $answer["kode"] = 1;
        $answer["pesan"] = "Berhasil";
    } else {
        $answer["kode"] = 0;
        $answer["pesan"] = "Gagal";
    }
} else {
    $answer["kode"] = 0;
    $answer["pesan"] = "Tidak Ada POST Data";
}

echo json_encode($answer);
mysqli_close($conn);
