<?php 

    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once 'koneksi.php';

        $nohp = $_POST['nohp'];
        $pass = $_POST['pass'];
        // $id_pass = md5()

        //++++++++++++++++ username dan pass
        if(empty($nohp)){
            $ray = array(
                "status"=>"false",
                "message"=>"User Tidak Boleh Kosong");
            echo json_encode($ray);

        } elseif (empty($pass)){
            $ray = array(
                "status"=>"flase",
                "message"=>"Password Tidak Boleh Kosong");
            echo json_encode($ray);

        } else {
            //================= cek user menggunakan username
            $user_query = "SELECT * FROM user WHERE nohp = '$nohp'";
            $user_sql = mysqli_query($conn, $user_query);
            $user_ray = array();
            while ($user_row = mysqli_fetch_array($user_sql)) {
                array_push($user_ray, array(
                    "nohp" => $user_row['nohp']
                ));
            }

            if (empty($user_ray)){
                // == cek user dengan menggunakan pass
                $user_query = "SELECT * FROM user WHERE pass = '$pass'";
                $user_sql = mysqli_query($conn, $user_query);
                $user_ray = array();
                while ($user_row = mysqli_fetch_array($user_sql)) {
                    array_push($user_ray, array(
                    "pass" => $row['pass']
                ));
            }

            if (empty($user_ray)){
                $ray = array(
                    "status"=>"flase",
                    "message"=>"User Anda Tidak Terdaftar");
                echo json_encode($ray);
            } else {
                // === cek pass dengan no hp
                // == cek pass 
                //$id_pass = md5
                $query = "SELECT * FROM user WHERE nohp = '$nohp' and pass = '$pass'";
                $sql = mysqli_query($conn, $query);
                $ray = array();
                while ($row = mysqli_fetch_array($sql)){
                    $ray = array(
                        "status"=>"true",
                        "message"=>"Berhasil Login",
                        "nama" => $row['nama'],
                        "nohp" => $row['nohp'],
                        "alamat" => $row['alamat'],
                        "wilayah" => $row['wilayah'],
                        "pass" => $row['pass'],
                        "gambar"=> $row['gambar']
                    );
                }

                if(empty($ray)){
                    $ray = array(
                        "status"=>"false",
                        "message"=>"Password Anda Salah");
                    echo json_encode($ray);

                } else {
                    echo json_encode($ray);
                    mysqli_close($conn);
                }
            }

        } else {
            // ====== cek password dengan username
             // ==== cek password
             // $id_pas = md5
                $query = "SELECT * FROM user WHERE nohp = '$nohp' and pass = '$pass'";
                $sql = mysqli_query($conn, $query);
                $ray = array();
                while ($row = mysqli_fetch_array($sql)) {
                    $ray = array(
                        "status"=>"true",
                        "message"=>"Berhasil Login",
                        "nama" => $row['nama'],
                        "nohp" => $row['nohp'],
                        "alamat" => $row['alamat'],
                        "wilayah" => $row['wilayah'],
                        "pass" => $row['pass'],
                        "gambar"=> $row['gambar']
                    );
                }

                if (empty($ray)) {
                    $ray = array (
                        "status" => "false",
                        "message" => "Password Anda Salah");
                    echo json_encode($ray);

                } else {
                    echo json_encode($ray);
                    mysqli_close($conn);
                }

        }
      //=========
    }

} else {
    $ray = array(
        "status"=>"flase",
        "message"=>"Bad Request");
    echo json_encode($ray);
     
}
?>