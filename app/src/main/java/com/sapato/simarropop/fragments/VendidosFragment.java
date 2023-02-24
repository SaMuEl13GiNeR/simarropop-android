package com.sapato.simarropop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sapato.simarropop.R;
import com.sapato.simarropop.adapter.ArticuloAdapter;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class VendidosFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private List<Articulo> articuloList;
    private ArticuloAPI articuloService;
    public static final String ipGlobal = ArticuloFragment.ipGlobal;
    private Usuario usuario;

    public VendidosFragment() {
    }


    public VendidosFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendidos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewVendidos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));



        adapter = new ArticuloAdapter(view.getContext(), usuario);
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.articuloService = retrofit.create(ArticuloAPI.class);


        Call<List<Articulo>> call = articuloService.getArticulosUsuarioVendidos(usuario.getId().toString());
        call.enqueue(new Callback<List<Articulo>>() {
            @Override
            public void onResponse(Call<List<Articulo>> call, Response<List<Articulo>> response) {
                try {
                    if (response.isSuccessful()) {
                        articuloList = response.body();
                        cambiarAdapter(articuloList);

                    }
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Articulo>> call, Throwable t) {
                Toast.makeText(view.getContext(), call.toString(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;

    }




    public void cambiarAdapter(List<Articulo> articuloList) {
        if (articuloList.size() > 0)
            adapter.setArticuloList(articuloList);
        adapter.notifyDataSetChanged();
    }

}