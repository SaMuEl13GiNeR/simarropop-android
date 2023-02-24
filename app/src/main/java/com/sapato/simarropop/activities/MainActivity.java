package com.sapato.simarropop.activities;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sapato.simarropop.R;
import com.sapato.simarropop.fragments.ArticuloFragment;
import com.sapato.simarropop.fragments.MensajeFragment;
import com.sapato.simarropop.fragments.MisArticulosFragment;
import com.sapato.simarropop.fragments.ProfileFragment;
import com.sapato.simarropop.fragments.SubirArticuloFragment;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Usuario usuario;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawer();

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Lo primero que te muestra al entrar a la app, para que no se quede en blanco
        if (savedInstanceState == null) {
            ArticuloFragment fragment = new ArticuloFragment(usuario);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedorPrincipal, fragment)
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productos:
                        if (savedInstanceState == null) {
                            ArticuloFragment fragment = new ArticuloFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;
                    case R.id.mensajes:
                        if (savedInstanceState == null) {
                            MensajeFragment fragment = new MensajeFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;
                    case R.id.new_product:
                        if (savedInstanceState == null) {
                            SubirArticuloFragment fragment = new SubirArticuloFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }

                        break;
                    case R.id.mis_productos:
                        if (savedInstanceState == null) {

                            MisArticulosFragment fragment = new MisArticulosFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;

                    case R.id.perfil:
                        if (savedInstanceState == null) {

                            ProfileFragment fragment = new ProfileFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;
                }
                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_myProducts:
                        if (savedInstanceState == null) {

                            MisArticulosFragment fragment = new MisArticulosFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;

                    case R.id.nav_profile:
                        if (savedInstanceState == null) {

                            ProfileFragment fragment = new ProfileFragment(usuario);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contenedorPrincipal, fragment)
                                    .commit();
                        }
                        break;

                    case R.id.nav_share:
                        if (savedInstanceState == null) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Compartir");
                            startActivity(Intent.createChooser(intent, "Compartir con"));

                        }
                        break;


                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


    }

    private void setupToolbar() {
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));*/
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(this);

        setupNavigationView();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set the default selected item
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        setupHeader();
    }

    private void setupHeader() {
        View header = navigationView.getHeaderView(0);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int title = getTitle(menuItem);
        showFragment(title);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getTitle(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                return R.string.profile;
            case R.id.nav_myProducts:
                return R.string.myarticles;
            case R.id.nav_tools:
                return R.string.menu_tools;
            case R.id.nav_share:
                return R.string.menu_share;
    
            default:
                throw new IllegalArgumentException("No esta");
        }
    }

    private void showFragment(@StringRes int titleId) {
        /*Fragment fragment = HomeContentFragment.newInstance(titleId);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(R.id., fragment)
                .commit();

        setTitle(getString(titleId));*/
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }

}