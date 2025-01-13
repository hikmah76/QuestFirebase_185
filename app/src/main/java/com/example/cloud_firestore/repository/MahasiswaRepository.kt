package com.example.cloud_firestore.repository

import com.example.cloud_firestore.model.Mahasiswa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
//repo
interface MahasiswaRepository {
    suspend fun getAllMahasiswa(): Flow<List<Mahasiswa>>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)

    suspend fun getMahasiswa(nim: String): Flow<Mahasiswa>
}