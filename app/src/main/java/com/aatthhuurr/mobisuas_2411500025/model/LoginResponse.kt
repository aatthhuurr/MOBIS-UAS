package com.aatthhuurr.mobisuas_2411500025.model

data class LoginResponse(val status: Boolean, val message: String, val data: UserData?)
data class UserData(val id_user: Int, val nomor_hp: String)