package com.example.cloud_firestore

import android.app.Application
import com.example.cloud_firestore.di.AppContainer
import com.example.cloud_firestore.di.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi container dengan instance dari MahasiswaContainer
        container= MahasiswaContainer()
    }
}