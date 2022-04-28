package com.example.retrait_qr;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RepoServiceAPI {
    String BASE_URL = "http://192.168.1.6:8787/";

    @PUT("banque/retrait/{id}/{mnt}")
    Call<Compte> Retrait(@Path("id") Long id, @Path("mnt") Double mnt);

}
