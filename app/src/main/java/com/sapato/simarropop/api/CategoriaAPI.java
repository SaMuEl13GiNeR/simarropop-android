package com.sapato.simarropop.api;

import com.sapato.simarropop.pojo.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriaAPI {

    @GET("categorias")
    Call<List<Categoria>> getAll();

    @GET("categorias/{id}")
    Call<Categoria> getOneById(@Path("id") String id);

}
