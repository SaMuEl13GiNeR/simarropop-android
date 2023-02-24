package com.sapato.simarropop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sapato.simarropop.R;
import com.sapato.simarropop.api.UsuarioAPI;
import com.sapato.simarropop.fragments.ArticuloFragment;
import com.sapato.simarropop.pojo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etLastName, etPassword, etConfirmPassword, etMail;
    private TextView back;
    private Button signUp;
    String ipGlobal = ArticuloFragment.ipGlobal;
    private UsuarioAPI usuarioService;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText) findViewById(R.id.etsubirName);
        etLastName = (EditText) findViewById(R.id.lastname_layout);
        etMail = (EditText) findViewById(R.id.email_layout);
        etPassword = (EditText) findViewById(R.id.password_layout);
        etConfirmPassword = (EditText) findViewById(R.id.confirmpassword_layout);
        back = (TextView) findViewById(R.id.backlink);
        signUp = (Button) findViewById(R.id.register_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaUsuario();
            }
        });


    }


    public void creaUsuario() {

        if (!etName.getText().toString().equals("") && !etLastName.getText().toString().equals("") && !etMail.getText().toString().equals("") && !etPassword.getText().toString().equals("") && !etConfirmPassword.getText().toString().equals("") && (etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
            usuario = new Usuario();
            usuario.setNombre(etName.getText().toString());
            usuario.setApellidos(etLastName.getText().toString());
            usuario.setCorreo(etMail.getText().toString());
            usuario.setContrasenya(etPassword.getText().toString());

            Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
            this.usuarioService = retrofit.create(UsuarioAPI.class);


            Call<Usuario> call = usuarioService.insertarUsuario(usuario);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    try {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se ha creado el usuario", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
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

        } else
            Toast.makeText(getApplicationContext(), "Revisa que los campos sean correctos", Toast.LENGTH_SHORT).show();
    }
}