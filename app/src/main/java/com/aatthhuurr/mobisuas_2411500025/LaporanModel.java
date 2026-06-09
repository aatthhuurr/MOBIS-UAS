package com.aatthhuurr.mobisuas_2411500025;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LaporanModel implements Serializable {
    @SerializedName("id_laporan") private String idLaporan;
    @SerializedName("judul") private String judul;
    @SerializedName("kategori") private String kategori;
    @SerializedName("status") private String status;
    @SerializedName("tanggal_lapor") private String tanggalLapor;
    @SerializedName("kronologis") private String kronologis;
    @SerializedName("tanggapan_admin") private String tanggapanAdmin;
    @SerializedName("tanggal_tanggapi") private String tanggalTanggapi;
    @SerializedName("bukti_url") private String buktiUrl;
    @SerializedName("tipe_bukti") private String tipeBukti;

    public String getIdLaporan() { return idLaporan; }
    public String getJudul() { return judul; }
    public String getKategori() { return kategori; }
    public String getStatus() { return status; }
    public String getTanggalLapor() { return tanggalLapor; }
    public String getKronologis() { return kronologis; }
    public String getTanggapanAdmin() { return tanggapanAdmin; }
    public String getTanggalTanggapi() { return tanggalTanggapi; }
    public String getBuktiUrl() { return buktiUrl; }
    public String getTipeBukti() { return tipeBukti; }
}