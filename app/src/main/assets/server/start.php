<?php

// Menentukan direktori kerja server
define("XP_SERVER_DIR", __DIR_
echo "Memulai XPocketMP Server...\n";

// Menjalankan proses server
$phpBinary = "../php/php"; // Pastikan PHP sudah terpasang pada perangkat
$serverScript = XP_SERVER_DIR . '/PocketMine-MP.phar'; // Skrip server utama

// Perintah untuk menjalankan server
$command = escapeshellcmd("$phpBinary $serverScript");

// Menjalankan server
echo "Menjalankan perintah: $command\n";
$output = shell_exec($command);

// Menampilkan output server
echo "Output server:\n$output\n";

// Log error atau masalah lainnya
if ($output === null) {
    echo "Gagal menjalankan server. Pastikan PHP dan ekstensi yang diperlukan telah terpasang dengan benar.\n";
}