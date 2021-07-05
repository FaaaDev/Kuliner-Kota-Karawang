<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_menu = $_POST['id_menu'];
    $nama_produk = $_POST['nama_produk'];
    $harga = $_POST['harga'];
    $nohp = $_POST['nohp'];
    $desk = $_POST['desk'];
    $operasional = $_POST['operasional'];
    $alamat = $_POST['alamat'];
    $gambar = $_POST['gambar'];

    require_once 'koneksi.php';

    $sql = "UPDATE menu SET nama_produk='$nama_produk', harga='$harga', desk='$desk', operasional='$operasional', gambar='$gambar' WHERE id_menu='$id_menu' ";

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