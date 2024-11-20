package com.jeremy.guitarfretmapgen

object Routes {
    var screenPrincipal = "Principal_"
    var screenFavoritas = "Favoritas_"

    fun screenPrincipalWithTuning(tuning: String): String {
        return "$screenPrincipal?tuning=${tuning}"
    }
}