package com.sapato.simarropop.api;

import com.sapato.simarropop.pojo.Articulo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ArticuloAPI {

        @GET("articulos")
        Call<List<Articulo>> getAll();

        @GET("articulos/ajenos/noVendidos/{id}/")
        Call<List<Articulo>> getAllAjenos(@Path("id") String id);

        @GET("articulos/{id}")
        Call<Articulo> getOneById(@Path("id") String id);

        @GET("articulos/titulo/{titulo}")
        Call<List<Articulo>> getArticulosByTitulo(@Path("titulo") String titulo);

        @GET("articulos/ajenos/{id}/titulo/{titulo}")
        Call<List<Articulo>> getArticulosAjenosByTitulo(@Path("id") String id, @Path("titulo") String titulo);

        @POST("articulos/nuevo")
        Call<Articulo> nuevoArticulo(@Body Articulo articulo);

        @GET("articulos/titulo/asc/{id}")
        Call<List<Articulo>> getAllOrderTituloASC(@Path("id") String id);

        @GET("articulos/titulo/desc/{id}")
        Call<List<Articulo>> getAllOrderTituloDESC(@Path("id") String id);

        @GET("articulos/usuarioVendedor/{id}")
        Call<List<Articulo>> getArticulosUsuario(@Path("id") String id);

        @GET("articulos/usuarioVendedor/{id}/noVendidos")
        Call<List<Articulo>> getArticulosUsuarioNoVendidos(@Path("id") String id);

        @GET("articulos/usuarioVendedor/{id}/vendidos")
        Call<List<Articulo>> getArticulosUsuarioVendidos(@Path("id") String id);

        @GET("articulos/usuarioComprador/{id}")
        Call<List<Articulo>> getArticulosUsuarioComprador(@Path("id") String id);

        @GET("articulos/precio/lower/{id}")
        Call<List<Articulo>> getAllOrderPrecioLower(@Path("id") String id);

        @GET("articulos/precio/higher/{id}")
        Call<List<Articulo>> getAllOrderPrecioHigher(@Path("id") String id);

        @PUT("articulos/{id}")
        Call<Articulo> updateArticulo(@Body Articulo articulo, @Path("id") String id);

        @DELETE("articulos/{id}")
        Call<Response> deleteArticulo(@Path("id") String id);
    }


