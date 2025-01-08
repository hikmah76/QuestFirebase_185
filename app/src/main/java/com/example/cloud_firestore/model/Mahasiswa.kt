package com.example.cloud_firestore.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenis_Kelamin: String,
    val kelas:String,
    val angkatan: String
){
    constructor(

    ):this("","","","","","")
}


