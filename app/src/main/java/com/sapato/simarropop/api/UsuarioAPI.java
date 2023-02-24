package com.sapato.simarropop.api;

import com.google.gson.JsonObject;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {

    @GET("usuarios")
    Call<List<Usuario>> getAll();

    @GET("usuarios/{id}")
    Call<Usuario> getOneById(@Path("id") String id);

    @GET("usuarios/validar/{correo}/{contrasenya}")
    Call<Usuario> validateOne(@Path("correo") String correo, @Path("contrasenya") String contrasenya);

    @POST("usuarios/nuevo")
    Call<Usuario> insertarUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}")
    Call<Usuario> updateOneById(@Body Usuario usuario,@Path("id") String id);

    @DELETE("usuarios/{id}")
    Call<Usuario> deleteOneById(@Path("id") String id);


}
