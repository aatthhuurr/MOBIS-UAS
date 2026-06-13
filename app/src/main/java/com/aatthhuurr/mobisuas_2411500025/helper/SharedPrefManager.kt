package com.aatthhuurr.mobisuas_2411500025.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(idUser: Int, nomorHp: String) {
        val editor = sharedPreferences.edit()
        editor.putInt("id_user", idUser)
        editor.putString("nomor_hp", nomorHp)
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean("is_logged_in", false)

    fun getIdUser(): Int = sharedPreferences.getInt("id_user", -1)

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}