package com.example.cloud_firestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloud_firestore.model.Kampus

import com.example.cloud_firestore.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel(
    private val mhs: MahasiswaRepository
): ViewModel(){
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())


        private set
    var uiState: FormState by mutableStateOf(FormState.Idle)
    // Fungsi untuk memperbarui state berdasarkan event yang diterima
    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent
        )
    }
    // Fungsi untuk validasi field input
    fun validateFields(): Boolean{
        val  event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else " NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else " Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else " Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else " Alamat tidak boleh kosong",
            judulSkripsi = if (event.judulSkripsi.isNotEmpty()) null else " Nama tidak boleh kosong",
            dospem1 = if (event.dospem1.isNotEmpty()) null else " Dospem1 tidak boleh kosong",
            dospem2 = if (event.dospem2.isNotEmpty()) null else " Dospem2 tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else " Angkatan tidak boleh kosong",

        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    // Fungsi untuk menyimpan data mahasiswa
    fun insertMahasiswa(){
        if (validateFields()){
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                }catch (e: Exception){
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }
    // Fungsi untuk mereset form ke kondisi awal
    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }
    // Fungsi untuk mereset pesan snackbar
    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }

}

// Kelas sealed untuk mendefinisikan state form
sealed class FormState{
    object Idle: FormState()
    object Loading : FormState()
    data class Success(val message: String): FormState()
    data class Error(val message: String): FormState()
}

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val judulSkripsi: String? = null,
    val dospem1: String? = null,
    val dospem2: String? = null,
    val angkatan: String? = null,

){
    fun isValid(): Boolean{
        return nim == null &&  nama == null && jenisKelamin == null && alamat == null && judulSkripsi == null && dospem1 == null && dospem2 == null &&
                angkatan == null
    }
}

data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val judulSkripsi: String= "",
    val dospem1: String = "",
    val dospem2: String = "",
    val angkatan: String = ""
)

fun MahasiswaEvent.toMhsModel(): Kampus = Kampus(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    judulSkripsi = judulSkripsi,
    dospem1 = dospem1,
    dospem2 = dospem1,
    angkatan = angkatan
)