package com.sapato.simarropop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sapato.simarropop.R;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.api.UsuarioAPI;
import com.sapato.simarropop.fragments.ArticuloFragment;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etMail;
    private EditText etPass;
    private Button mLoginButton, guardar, recuperar;
    private TextView mSignUpLink;
    String ipGlobal = ArticuloFragment.ipGlobal;
    private UsuarioAPI usuarioService;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        etMail = findViewById(R.id.usernameMail);
        etPass = findViewById(R.id.passwordLogin);
        mLoginButton = findViewById(R.id.login_button);
        mSignUpLink = findViewById(R.id.sign_up_link);

        etMail.setText("pakitofdez@gmail.com");
        etPass.setText("1234");


        guardar = findViewById(R.id.btLoginGuardar);
        recuperar = findViewById(R.id.btLoginRecuperar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", etMail.getText().toString());
                editor.commit();
            }
        });


        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = prefs.getString("email", "Sin valor");
                etMail.setText(correo);
            }
        });



        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.usuarioService = retrofit.create(UsuarioAPI.class);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();


            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void attemptLogin() {
        if (etMail.length() > 0 && etPass.length() > 0) {
            String mail = etMail.getText().toString();
            String pass = etPass.getText().toString();

            Call<Usuario> call = usuarioService.validateOne(mail, pass);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("usuario", response.body());
                            startActivity(i);

                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}

