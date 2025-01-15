package com.example.cloud_firestore.repository

import com.example.cloud_firestore.model.Kampus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
//repo
interface MahasiswaRepository {
    suspend fun getAllMahasiswa(): Flow<List<Kampus>>

    suspend fun insertMahasiswa(kampus: Kampus)

    suspend fun updateMahasiswa(nim: String, kampus: Kampus)

    suspend fun deleteMahasiswa(kampus: Kampus)

    suspend fun getMahasiswa(nim: String): Flow<Kampus>
}