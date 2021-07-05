<?php


if($_SERVER['REQUEST_METHOD']=='POST') {

    $nama = $_POST['nama'];
    $nohp = $_POST['nohp'];
    $alamat = $_POST['alamat'];
    $wilayah = $_POST['wilayah'];
    $pass = $_POST['pass'];
    $gambar = $_POST['gambar'];


    

    $sql = "INSERT INTO user (nama, nohp, alamat, wilayah, pass, gambar) VALUES ('$nama', '$nohp', '$alamat', '$wilayah', '$pass', '$gambar')";

    require_once 'koneksi.php';

    if(mysqli_query($conn, $sql)) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    }else{
        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>