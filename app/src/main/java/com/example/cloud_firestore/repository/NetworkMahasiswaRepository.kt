package com.example.cloud_firestore.repository

import com.example.cloud_firestore.model.Kampus

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkMahasiswaRepository ( // repo
    private val firestore: FirebaseFirestore // Firestore instance untuk akses database cloud
): MahasiswaRepository {
    // Fungsi untuk mendapatkan semua data mahasiswa secara real-time
    override suspend fun getAllMahasiswa(): Flow<List<Kampus>> = callbackFlow { // callback yg mendukung real time
        val mhsCollection = firestore.collection("kampus")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener {
                    value, error ->

                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Kampus::class.java)!!
                    }
                    trySend(mhsList)  // Mengirim data mahasiswa ke flow
                }
            }
        awaitClose { mhsCollection.remove()
        }
    }
    // Fungsi untuk menambahkan data mahasiswa ke Firestore
    override suspend fun insertMahasiswa(kampus: Kampus) {
        try {
            firestore.collection("kampus").add(kampus).await()
        } catch (e:Exception){
            throw Exception("Gagal menambahkan data kampus: ${e.message}")
        }
    }
    // Fungsi untuk mengupdate data mahasiswa berdasarkan NIM
    override suspend fun updateMahasiswa(nim: String, kampus: Kampus) {
        try {
            firestore.collection("kampus")
                .document(kampus.nim)
                .set(kampus)
                .await()
        } catch (e:Exception){
            throw Exception("Gagal Mengupdate data kampus: ${e.message}")
        }
    }
    // Fungsi untuk menghapus data mahasiswa berdasarkan NIM
    override suspend fun deleteMahasiswa(kampus: Kampus) {
        try {
            firestore.collection("kampus")
                .document(kampus.nim)
                .delete()
                .await()
        } catch (e:Exception){
            throw Exception("Gagal menghapus data kampus: ${e.message}")
        }
    }


    // Fungsi untuk mendapatkan data mahasiswa berdasarkan NIM secara real-time
    override suspend fun getMahasiswa(nim: String): Flow<Kampus> = callbackFlow{
        val mhsDocument = firestore.collection("kampus")
            .document(nim)
            .addSnapshotListener{ value, error ->
                if (value != null){
                    val mhs = value.toObject(Kampus::class.java)!!
                    trySend(mhs)
                }
            }
        awaitClose{
            mhsDocument.remove()
        }

    }
}