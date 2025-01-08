package com.example.cloud_firestore.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cloud_firestore.MahasiswaApplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }

    }
}

fun CreationExtras. aplikasiMahasiswa (): MahasiswaApplications =
    ( this [ ViewModelProvider . AndroidViewModelFactory .APPLICATION_KEY]  as MahasiswaApplications)