package com.example.cloud_firestore.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cloud_firestore.MahasiswaApplications

object PenyediaViewModel {
    // Factory untuk membuat instance ViewModel dengan menggunakan viewModelFactory
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { InsertViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }


    }
}

fun CreationExtras. aplikasiMahasiswa (): MahasiswaApplications =
    ( this [ ViewModelProvider . AndroidViewModelFactory .APPLICATION_KEY]  as MahasiswaApplications)