-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 24 Jun 2020 pada 08.27
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kutaka`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `menu`
--

CREATE TABLE `menu` (
  `id_menu` int(11) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` varchar(100) NOT NULL,
  `nohp` varchar(100) NOT NULL,
  `desk` varchar(250) NOT NULL,
  `operasional` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `wilayah` varchar(100) NOT NULL,
  `gambar` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_produk`, `harga`, `nohp`, `desk`, `operasional`, `alamat`, `wilayah`, `gambar`) VALUES
(11, 'Karedok Leunca', '8000', '082261307377', 'hallo guys, ready ya untuk karedok leunca nya ..', '07:00-16:30', 'Kp. sumur bandung kidul', 'Cikampek', 'http://192.168.1.15/kutaka-api/imgUpload/59181-1592894805224.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `nama` varchar(50) NOT NULL,
  `nohp` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `wilayah` varchar(100) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `gambar` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`nama`, `nohp`, `alamat`, `wilayah`, `pass`, `gambar`) VALUES
('Lapak Malam', '00000', 'Jl. hidup manusia', 'Jatisari', '123', ''),
('Lapak Bangsa', '08123456789', 'Dawuan Timur', 'Cikampek', '1234', ''),
('Nanda Pratama', '082261307377', 'Kp. sumur bandung kidul', 'Cikampek', '1234', ''),
('Lapak Tuak', '082269212156', 'Jl. Sesat No.99, Arah Neraka Jahannam', 'Cikampek', '123', ''),
('Tehsn', '085', 'hsjsksn', 'Kosambi', '123', ''),
('Lapak Bangsa #2', '089632445128', 'Pinayungan', 'Teluk Jambe', '1234', ''),
('Ghcf', '66988', 'cvvff', 'Kosambi', '123', ''),
('Gghh', '8998', 'vvbbbfg', 'Pancawati', '123', ''),
('Ansnsn', '94949', 'bxbzb', 'Purwasari', '123', ''),
('Zjjzjsjs', '979764', 'hzhshshs', 'Pancawati', '123', ''),
('Teser', '97978784', 'hshshs', 'Jatisari', '123', ''),
('Nsnsns', '979797', 'svbssb', 'Purwasari', '123', '');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`nohp`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `menu`
--
ALTER TABLE `menu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
