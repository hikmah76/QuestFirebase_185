package com.example.cloud_firestore.repository

import com.example.cloud_firestore.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkMahasiswaRepository (
    private val firestore: FirebaseFirestore // Firestore instance untuk akses database cloud
): MahasiswaRepository {
    // Fungsi untuk mendapatkan semua data mahasiswa secara real-time
    override suspend fun getAllMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow { // callback yg mendukung real time
        val mhsCollection = firestore.collection("mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener {
                    value, error ->

                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    trySend(mhsList)  // Mengirim data mahasiswa ke flow
                }
            }
        awaitClose { mhsCollection.remove()
        }
    }
    // Fungsi untuk menambahkan data mahasiswa ke Firestore
    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e:Exception){
            throw Exception("Gagal menambahkan data mahasiswa: ${e.message}")
        }
    }
    // Fungsi untuk mengupdate data mahasiswa berdasarkan NIM
    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e:Exception){
            throw Exception("Gagal Mengupdate data mahasiswa: ${e.message}")
        }
    }

    override suspend fun deleteMahasiswa(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e:Exception){
            throw Exception("Gagal menghapus data mahasiswa: ${e.message}")
        }
    }



    override suspend fun getMahasiswa(nim: String): Flow<Mahasiswa> = callbackFlow{
        val mhsDocument = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener{ value, error ->
                if (value != null){
                    val mhs = value.toObject(Mahasiswa::class.java)!!
                    trySend(mhs)
                }
            }
        awaitClose{
            mhsDocument.remove()
        }

    }
}