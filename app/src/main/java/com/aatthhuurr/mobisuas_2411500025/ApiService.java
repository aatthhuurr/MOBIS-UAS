package com.aatthhuurr.mobisuas_2411500025;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("nohp") String nohp,
            @Field("password") String password
    );

    @GET("get_riwayat.php")
    Call<List<LaporanModel>> getRiwayat(
            @Query("id_user") String idUser
    );
}