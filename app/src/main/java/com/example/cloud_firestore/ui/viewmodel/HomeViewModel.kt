package com.example.cloud_firestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloud_firestore.model.Kampus
import com.example.cloud_firestore.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.Exception

sealed class HomeUiState {
    // State ketika data mahasiswa berhasil diambil, membawa list mahasiswa
    data class Success(val mahasiswa: List<Kampus>) : HomeUiState()
    data class Error(val  exception: Throwable): HomeUiState()
    object Loading : HomeUiState()
}
// ViewModel untuk mengelola data dan state UI pada layar Home
class HomeViewModel(private val mhs: MahasiswaRepository
) : ViewModel() {
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    // Fungsi untuk mengambil semua data mahasiswa dari repository
    fun getMhs() {
        viewModelScope.launch {
            mhs.getAllMahasiswa()
                .onStart{
                    mhsUIState = HomeUiState.Loading
                }
                .catch{
                    mhsUIState = HomeUiState.Error(it)
                }
                .collect{
                    mhsUIState = if (it.isEmpty()){
                        HomeUiState.Error(Exception("Belum ada daftar mahasiswa"))
                    }else{
                        HomeUiState.Success(it)
                    }
                }
        }
    }

    // Fungsi untuk menghapus mahasiswa dari repository
    fun deleteMahasiswa(kampus: Kampus){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(kampus)
            }catch (e: Exception){
                mhsUIState = HomeUiState.Error(e)
            }
        }
    }


}