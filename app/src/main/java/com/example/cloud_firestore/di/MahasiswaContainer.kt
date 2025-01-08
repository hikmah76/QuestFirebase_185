package com.example.cloud_firestore.di

import com.example.cloud_firestore.repository.MahasiswaRepository
import com.example.cloud_firestore.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer : AppContainer {
    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }

}