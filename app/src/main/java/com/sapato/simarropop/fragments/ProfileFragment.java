package com.sapato.simarropop.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sapato.simarropop.R;
import com.sapato.simarropop.activities.LoginActivity;
import com.sapato.simarropop.api.UsuarioAPI;
import com.sapato.simarropop.pojo.Usuario;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private FloatingActionButton fabMain, fabSub1, fabSub2;
    private TextView t1, t2, t3, t4;
    private TextView textName, textLastName, textMail, textPassw;
    private EditText etName, etLastName, etMail, etPassw;
    boolean botonPrincipal = false;
    boolean isClicked = false;
    private Usuario usuario;
    private ImageView avatar;
    String ipGlobal = ArticuloFragment.ipGlobal;
    private UsuarioAPI usuarioService;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.usuarioService = retrofit.create(UsuarioAPI.class);


        fabMain = (FloatingActionButton) view.findViewById(R.id.fab_main);
        fabSub1 = view.findViewById(R.id.fab_sub_1);
        fabSub2 = view.findViewById(R.id.fab_sub_2);

        t1 = view.findViewById(R.id.tvProfileNombre);
        t2 = view.findViewById(R.id.tvProfileApellido);
        t3 = view.findViewById(R.id.tvProfileCorreo);
        t4 = view.findViewById(R.id.tvProfileContra);

        textName = view.findViewById(R.id.tvProfileInputNombre);
        textLastName = view.findViewById(R.id.tvProfileInputApellido);
        textMail = view.findViewById(R.id.tvProfileInputCorreo);
        textPassw = view.findViewById(R.id.tvProfileInputContra);

        textName.setText(usuario.getNombre());
        textLastName.setText(usuario.getApellidos());
        textMail.setText(usuario.getCorreo());
        textPassw.setText(usuario.getContrasenya());


        etName = view.findViewById(R.id.etProfileEditName);
        etLastName = view.findViewById(R.id.etProfileEditLastName);
        etMail = view.findViewById(R.id.etProfileEditMail);
        etPassw = view.findViewById(R.id.etProfileEditContra);


        if (usuario.getAvatar() != null) {

            avatar = view.findViewById(R.id.ivitemUsuario);

            String base64String = usuario.getAvatar();
            byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            avatar.setImageBitmap(bitmap);
        }


        fabMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int icon;

                if (fabSub1.getVisibility() == View.GONE) {
                    icon = R.drawable.cross;
                    fabSub1.show();
                    fabSub2.show();
                } else {
                    icon = R.drawable.nut;
                    fabSub1.hide();
                    fabSub2.hide();
                }

                fabMain.setImageDrawable(ContextCompat.getDrawable(view.getContext(), icon));
                fabSub2.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.edit));
                botonPrincipal = true;
                isClicked = false;

                defaultVisibility();

            }
        });

        fabSub1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });


        fabSub2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int icon;


                if (!isClicked) {
                    icon = R.drawable.check;
                    isClicked = true;
                    notDefaultVisibility();

                } else {
                    if (textName.getVisibility() == View.GONE) {

                        if (!etName.getText().toString().equals("") && !etLastName.getText().toString().equals("") && !etMail.getText().toString().equals("") && !etPassw.getText().toString().equals("")) {
                            textName.setText(etName.getText());
                            textLastName.setText(etLastName.getText());
                            textMail.setText(etMail.getText());
                            textPassw.setText(etPassw.getText());
                            modificarUsuario();

                        } else
                            Toast.makeText(getView().getContext(), "Revisa los campos vacios", Toast.LENGTH_SHORT).show();
                    }


                    icon = R.drawable.edit;
                    isClicked = false;
                    botonPrincipal = false;
                    defaultVisibility();

                }

                fabSub2.setImageDrawable(ContextCompat.getDrawable(view.getContext(), icon));


            }
        });


        return view;
    }


    public void defaultVisibility() {

        etName.setVisibility(View.GONE);
        etLastName.setVisibility(View.GONE);
        etMail.setVisibility(View.GONE);
        etPassw.setVisibility(View.GONE);
        textName.setVisibility(View.VISIBLE);
        textLastName.setVisibility(View.VISIBLE);
        textMail.setVisibility(View.VISIBLE);
        textPassw.setVisibility(View.VISIBLE);
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);
        t4.setVisibility(View.VISIBLE);

    }

    public void notDefaultVisibility() {
        etName.setVisibility(View.VISIBLE);
        etLastName.setVisibility(View.VISIBLE);
        etMail.setVisibility(View.VISIBLE);
        etPassw.setVisibility(View.VISIBLE);
        textName.setVisibility(View.GONE);
        textLastName.setVisibility(View.GONE);
        textMail.setVisibility(View.GONE);
        textPassw.setVisibility(View.GONE);
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        t4.setVisibility(View.GONE);


        etName.setText(textName.getText());
        etLastName.setText(textLastName.getText());
        etMail.setText(textMail.getText());
        etPassw.setText(textPassw.getText());

    }


    public void modificarUsuario() {

        usuario.setNombre(textName.getText().toString());
        usuario.setApellidos(textLastName.getText().toString());
        usuario.setCorreo(textMail.getText().toString());
        usuario.setContrasenya(textPassw.getText().toString());

        if(usuario.getAvatar() != null) {
            Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                usuario.setAvatar(fotoEnBase64);
            }
        }

        Call<Usuario> call = usuarioService.updateOneById(usuario, usuario.getId().toString());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(getView().getContext(), "Los datos han sido cambiados", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getView().getContext(), call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}