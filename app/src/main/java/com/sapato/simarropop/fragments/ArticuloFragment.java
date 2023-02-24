package com.sapato.simarropop.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sapato.simarropop.R;
import com.sapato.simarropop.adapter.ArticuloAdapter;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticuloFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private List<Articulo> articuloList;
    private ArticuloAPI articuloService;
    public static final String ipGlobal = "http://192.168.8.14:8080/simarropop/";
    private Usuario usuario;


    public ArticuloFragment() {
        // Required empty public constructor
    }


    public ArticuloFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articulo, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewArticulos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterData(query);
                return true;
            }
        });

        adapter = new ArticuloAdapter(view.getContext(), usuario);
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.articuloService = retrofit.create(ArticuloAPI.class);


        Call<List<Articulo>> call = articuloService.getAllAjenos(usuario.getId().toString());
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

        // Crea una lista de opciones para el Spinner
        List<String> opciones = new ArrayList<String>();
        opciones.add("De la A-Z");
        opciones.add("De la Z-A");
        opciones.add("Por precio ascendiente");
        opciones.add("Por precio descendiente");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, opciones);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner estadoSpinner = (Spinner) view.findViewById(R.id.spinnerFragmentArticulo);
        estadoSpinner.setAdapter(adaptador);

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cambiarSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;

    }


    private void filterData(String query) {

        Call<List<Articulo>> call = articuloService.getArticulosAjenosByTitulo(String.valueOf(usuario.getId()), query);
        call.enqueue(new Callback<List<Articulo>>() {
            @Override
            public void onResponse(Call<List<Articulo>> call, Response<List<Articulo>> response) {
                try {
                    if (response.isSuccessful()) {
                        articuloList = response.body();

                        cambiarAdapter(articuloList);

                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Articulo>> call, Throwable t) {
                Toast.makeText(getContext(), call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cambiarSpinner(int position) {
        Call<List<Articulo>> call = null;
        switch (position) {
            case 0:
                call = articuloService.getAllOrderTituloASC(String.valueOf(usuario.getId()));
                break;

            case 1:
                call = articuloService.getAllOrderTituloDESC(String.valueOf(usuario.getId()));
                break;

            case 2:
                call = articuloService.getAllOrderPrecioHigher(String.valueOf(usuario.getId()));
                break;

            case 3:
                call = articuloService.getAllOrderPrecioLower(String.valueOf(usuario.getId()));
                break;
        }

        call.enqueue(new Callback<List<Articulo>>() {
            @Override
            public void onResponse(Call<List<Articulo>> call, Response<List<Articulo>> response) {
                try {
                    if (response.isSuccessful()) {
                        articuloList = response.body();

                        cambiarAdapter(articuloList);

                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Articulo>> call, Throwable t) {
                Toast.makeText(getContext(), call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void cambiarAdapter(List<Articulo> articuloList) {
        if (articuloList.size() > 0)
            adapter.setArticuloList(articuloList);
        adapter.notifyDataSetChanged();
    }



}