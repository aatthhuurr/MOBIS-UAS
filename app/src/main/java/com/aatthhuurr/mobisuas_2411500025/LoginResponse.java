package com.aatthhuurr.mobisuas_2411500025;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status") private String status; // "success" atau "failed"
    @SerializedName("message") private String message;
    @SerializedName("id_user") private String idUser; // Untuk mengambil data riwayat milik user ini

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public String getIdUser() { return idUser; }
}