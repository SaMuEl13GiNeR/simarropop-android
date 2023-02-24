package com.sapato.simarropop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sapato.simarropop.R;
import com.sapato.simarropop.activities.ArticuloActivity;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {
    private Context context;
    private List<Articulo> articuloList;
    private Usuario usuario;

    public ArticuloAdapter(Context context, List<Articulo> articuloList, Usuario usuario) {
        this.context = context;
        this.articuloList = articuloList;
        this.usuario = usuario;
    }

    public ArticuloAdapter(Context context, Usuario usuario) {
        this.context = context;
        articuloList = new ArrayList<>();
        this.usuario = usuario;

    }

    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }


    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.articulo_item, parent, false);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = articuloList.get(position);

        holder.tvTitulo.setText(articulo.getTitulo());
        holder.tvPrecio.setText(String.valueOf(articulo.getPrecio()) + " €");

        if (articulo.getFoto() != null) {


            String base64String = articulo.getFoto();
            byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            holder.imagen.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return articuloList.size();
    }

    class ArticuloViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvPrecio;
        ImageView imagen;

        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imagen = itemView.findViewById(R.id.ivitemUsuario);


                 /*Convertir imagen a base 64 para pasarla:

} */


            //Picasso.get().load("http://example.com/image.png").into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Articulo articulo = articuloList.get(getAdapterPosition());

                    Intent i = new Intent(v.getContext(), ArticuloActivity.class);
                    i.putExtra("articulo", articulo);
                    i.putExtra("usuario", usuario);
                    context.startActivity(i);

                }
            });

        }
    }
}