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
// Objek DestinasiInsert juga mengimplementasikan interface DestinasiNavigasi.
object DestinasiInsert : DestinasiNavigasi{
    override val route: String = "Insert"
    override val titleRes: String = "Insert"
}
object DestinasiDetail : DestinasiNavigasi{
    override val route: String = "Detail"
    override val titleRes: String = "Detail"
}