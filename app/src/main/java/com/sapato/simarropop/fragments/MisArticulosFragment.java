package com.sapato.simarropop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sapato.simarropop.R;
import com.sapato.simarropop.adapter.ArticuloAdapter;
import com.sapato.simarropop.adapter.ViewPagerAdapter;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisArticulosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private List<Articulo> articuloList;
    private ArticuloAPI articuloService;
    public static final String ipGlobal = ArticuloFragment.ipGlobal;
    private Usuario usuario;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MisArticulosFragment() {
        // Required empty public constructor
    }


    public MisArticulosFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_articulos, container, false);

        viewPager = view.findViewById(R.id.viewPagerFragMisArticulos);
        tabLayout = view.findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new NoVendidosFragment(usuario), "A la venta");
        adapter.addFragment(new VendidosFragment(usuario), "Vendidos");
        adapter.addFragment(new CompradosFragment(usuario), "Comprados");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        return view;

    }



}
