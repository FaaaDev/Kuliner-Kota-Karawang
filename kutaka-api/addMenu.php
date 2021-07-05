<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $nama_produk = $_POST['nama_produk'];
    $harga = $_POST['harga'];
    $nohp = $_POST['nohp'];
    $desk = $_POST['desk'];
    $operasional = $_POST['operasional'];
    $alamat = $_POST['alamat'];
    $wilayah = $_POST['wilayah'];
    $gambar = $_POST['gambar'];

    require_once 'koneksi.php';

    $sql = "INSERT INTO menu (id_menu, nama_produk, harga, nohp, desk, operasional, alamat, wilayah, gambar) VALUES (null, '$nama_produk', '$harga', '$nohp',  '$desk', '$operasional', '$alamat', '$wilayah', '$gambar')";

    if (mysqli_query($conn, $sql)) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>