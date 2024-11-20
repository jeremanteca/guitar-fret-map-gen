package com.jeremy.guitarfretmapgen.Models

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritasViewModel(private val context: Context? = null) : ViewModel() {
    private val _favoritasList = mutableStateListOf<String>()
    val favoritasList: List<String> get() = _favoritasList

    init {
        cargarAfinaciones()
    }

    fun agregarAfinacion(afinacion: String) {
        if (afinacion.isNotBlank() && !favoritasList.contains(afinacion)) {
            _favoritasList.add(afinacion)
            guardarAfinaciones()
        }
    }

    fun eliminarAfinacion(afinacion: String) {
        _favoritasList.remove(afinacion)
        guardarAfinaciones()
    }

    fun guardarAfinaciones() {
        context?.let {
            val sharedPreferences = it.getSharedPreferences("FavoritasPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putStringSet("afinacionesFavoritas", favoritasList.toSet())
            editor.apply()
        }
    }

    private fun cargarAfinaciones() {
        context?.let {
            val sharedPreferences = it.getSharedPreferences("FavoritasPrefs", Context.MODE_PRIVATE)
            val afinacionesGuardadas = sharedPreferences.getStringSet("afinacionesFavoritas", emptySet())
            _favoritasList.clear()
            _favoritasList.addAll(afinacionesGuardadas ?: emptyList())
        }
    }
}

