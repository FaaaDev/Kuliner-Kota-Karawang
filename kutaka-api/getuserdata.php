<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){

    $nohp = $_POST['nohp'];

    require_once('koneksi.php');

    $sql = "SELECT * FROM user WHERE nohp = '$nohp'";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['result'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['result'][] = "Data not Found";
    }

    print_r(json_encode($data));
}
?>