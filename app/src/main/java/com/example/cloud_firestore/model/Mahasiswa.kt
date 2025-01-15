package com.example.cloud_firestore.model
//data class mahasiswa
data class Kampus(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val judulSkripsi:String,
    val dospem1:String,
    val dospem2:String,
    val angkatan: String
){
    constructor(

    ):this("","","","","","","","")
}


