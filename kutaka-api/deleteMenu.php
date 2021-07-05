<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_menu = $_GET['id_menu'];

    require_once('koneksi.php');

    $sql = "DELETE FROM kutaka WHERE id_menu=$id_menu;";

    if (mysqli_query($conn, $sql)) {
        echo 'Berhasil Menghapus Menu';
    } else {
        echo 'Gagal Menghapus Menu';
    }

    mysqli_close($conn);

}
?>