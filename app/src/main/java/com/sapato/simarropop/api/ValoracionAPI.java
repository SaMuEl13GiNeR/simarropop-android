package com.sapato.simarropop.api;

import com.sapato.simarropop.pojo.Categoria;
import com.sapato.simarropop.pojo.Valoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ValoracionAPI {

    @GET("valoraciones")
    Call<List<Valoracion>> getAll();

    @GET("valoraciones/{id}")
    Call<Valoracion> getOneById(@Path("id") String id);

    @GET("valoraciones/media/{id}")
    Call<Valoracion> getValoracionMedia(@Path("id") String id);

    @PUT("valoraciones/{id}")
    Call<Valoracion> updateValoracion(@Path("id") String id, @Body Valoracion valoracion);

    @POST("valoraciones/{id}")
    Call<Valoracion> newValoracion(@Path("id") String id, @Body Valoracion valoracion);

    @DELETE("valoraciones/{id}")
    Call<Valoracion> deleteValoracion(@Path("id") String id);

}
