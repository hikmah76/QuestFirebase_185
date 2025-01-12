package com.example.cloud_firestore.ui.navigation
// Interface DestinasiNavigasi mendefinisikan kontrak untuk objek navigasi
interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}
// Objek DestinasiHome mengimplementasikan interface DestinasiNavigasi.
object DestinasiHome: DestinasiNavigasi{
    override val route: String = "home"
    override val titleRes: String = "Home"
}
object DestinasiInsert : DestinasiNavigasi{
    override val route: String = "Insert"
    override val titleRes: String = "Insert"
}