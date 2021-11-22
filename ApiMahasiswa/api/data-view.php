<?php
require '../configs/koneksi.php';


$query = "SELECT * FROM mahasiswa";
$execute = mysqli_query($conn, $query);
$test = mysqli_affected_rows($conn);

if ($test > 0) {
    $answer["kode"] = 1;
    $answer["pesan"] = "Data Tersedia";
    $answer["data"] = array();

    while ($view = mysqli_fetch_object($execute)) {
        $F['NIM'] = $view->NIM;
        $F['Nama'] = $view->Nama;
        $F['Semester'] = $view->Semester;
        $F['Jurusan'] = $view->Jurusan;
        $F['Prodi'] = $view->Prodi;

        array_push($answer["data"], $F);
    }
} else {
    $answer["kode"] = 0;
    $asnwer["pesan"] = "Data Tidak Tersedia";
}

echo json_encode($answer);
mysqli_close($conn);
