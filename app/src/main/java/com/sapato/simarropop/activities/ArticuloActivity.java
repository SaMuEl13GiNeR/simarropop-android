package com.sapato.simarropop.activities;

import static com.sapato.simarropop.fragments.ArticuloFragment.ipGlobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sapato.simarropop.R;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.api.UsuarioAPI;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticuloActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton ibBack, ibLike;
    Button btComprar;
    boolean isLiked = false;
    int icon;
    private ArticuloAPI articuloService;
    ImageView ivArticulo, ivFotoUsuario;
    TextView tvVendedor, tvTitulo, tvPrecio, tvDescripcion, tvLike;
    Articulo articulo;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.articuloService = retrofit.create(ArticuloAPI.class);

        articulo = (Articulo) getIntent().getSerializableExtra("articulo");
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        ivFotoUsuario = (ImageView) findViewById(R.id.ivAvatarUsuarioArt);
        ivArticulo = (ImageView) findViewById(R.id.ivArticuloActFotoProducto);
        ivFotoUsuario.setVisibility(View.INVISIBLE);

        if (articulo.getFoto() != null) {


            String base64String = articulo.getFoto();
            byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            ivArticulo.setImageBitmap(bitmap);
        }


        if (usuario.getAvatar() != null) {


            String base64String = usuario.getAvatar();
            byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


            ivFotoUsuario.setImageBitmap(bitmap);
        }


        ibBack = (ImageButton) findViewById(R.id.ibArticuloActBack);
        ibLike = (ImageButton) findViewById(R.id.ibArticuloActLike);
        ibLike.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heartwhite32));

        tvPrecio = (TextView) findViewById(R.id.tvArticuloActPrice);
        tvTitulo = (TextView) findViewById(R.id.tvArticuloActTitle);
        tvVendedor = (TextView) findViewById(R.id.tvArticuloActVendedor);
        tvDescripcion = (TextView) findViewById(R.id.tvArticuloActDescripcion);
        tvLike = (TextView) findViewById(R.id.tvArticuloNumLikes);

        tvLike.setText(String.valueOf(articulo.getLikes()));
        tvTitulo.setText(articulo.getTitulo());
        tvPrecio.setText(String.valueOf(articulo.getPrecio()) + " €");
        tvVendedor.setText(articulo.getUsuarioVendedor().getNombre());
        tvDescripcion.setText(articulo.getDescripcion());


        btComprar = (Button) findViewById(R.id.btArticuloActComprar);
        btComprar.setOnClickListener(this);

        ibBack.setOnClickListener(this);
        ibLike.setOnClickListener(this);

        if(articulo.getUsuarioComprador() != null || articulo.getUsuarioVendedor().getCorreo().equals(usuario.getCorreo())){
            ibLike.setEnabled(false);
        } else{
            ibLike.setEnabled(true);
        }


        if(articulo.getUsuarioVendedor().getCorreo().equals(usuario.getCorreo()) || articulo.isVendido() || articulo.getUsuarioComprador() != null){
            btComprar.setEnabled(false);
        }
        else{
            btComprar.setEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btArticuloActComprar:
                comprarArticulo();
                break;


            case R.id.ibArticuloActBack:
                i = new Intent(ArticuloActivity.this, MainActivity.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
                break;

            case R.id.ibArticuloActLike:
                if (isLiked) {
                    articulo.setLikes(articulo.getLikes() - 1);
                    tvLike.setText(String.valueOf(articulo.getLikes()));

                    icon = R.drawable.heartwhite32;
                    ibLike.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                    isLiked = false;

                } else {
                    articulo.setLikes(articulo.getLikes() + 1);
                    tvLike.setText(String.valueOf(articulo.getLikes()));

                    icon = R.drawable.heartgreen32;
                    ibLike.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                    isLiked = true;
                }

                break;
        }

    }


    public void comprarArticulo() {



        articulo.setVendido(true);
        articulo.setUsuarioComprador(usuario);


        Call<Articulo> call = articuloService.updateArticulo(articulo, articulo.getId().toString());
        call.enqueue(new Callback<Articulo>() {
            @Override
            public void onResponse(Call<Articulo> call, Response<Articulo> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Articulo comprado", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(ArticuloActivity.this, MainActivity.class);
                        i.putExtra("usuario", usuario);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Articulo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}