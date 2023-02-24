package com.sapato.simarropop.fragments;

import static com.sapato.simarropop.fragments.ArticuloFragment.ipGlobal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.sapato.simarropop.R;
import com.sapato.simarropop.activities.MainActivity;
import com.sapato.simarropop.api.ArticuloAPI;
import com.sapato.simarropop.pojo.Articulo;
import com.sapato.simarropop.pojo.Categoria;
import com.sapato.simarropop.pojo.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubirArticuloFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private Button newArticle;
    private EditText etName,etDesc,etPrice;
    private ImageView ivFoto;
    private ArticuloAPI articuloService;
    private Spinner spinner;
    private Articulo articulo;
    private Usuario usuario;

    public SubirArticuloFragment() {
        // Required empty public constructor
    }

    public SubirArticuloFragment(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subir_articulo, container, false);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), REQUEST_CODE);

        etName = (EditText) view.findViewById(R.id.etsubirName);
        etDesc = (EditText) view.findViewById(R.id.etSubirDesc);
        etPrice = (EditText) view.findViewById(R.id.etSubirPrice);
        ivFoto = (ImageView) view.findViewById(R.id.ivSubirFoto);
        spinner = (Spinner) view.findViewById(R.id.spSubir);

        // Crea una lista de opciones para el Spinner
        List<String> opciones = new ArrayList<String>();
        opciones.add("Nuevo");
        opciones.add("En buen estado");
        opciones.add("En mal estado");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, opciones);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner estadoSpinner = (Spinner) view.findViewById(R.id.spSubir);
        estadoSpinner.setAdapter(adaptador);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ipGlobal).addConverterFactory(GsonConverterFactory.create()).build();
        this.articuloService = retrofit.create(ArticuloAPI.class);

        newArticle = (Button) view.findViewById(R.id.btSubir);
        newArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptNew();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                showImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getView().getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int columnIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(columnIdx);
        cursor.close();
        return result;
    }


    public void showImage(Bitmap bitmap) {
        ImageView imageView = getView().findViewById(R.id.ivSubirFoto);
        imageView.setImageBitmap(bitmap);
    }


    private void attemptNew(){
        if (etName.length() > 0 && etDesc.length() > 0 && etPrice.length() > 0) {

            String fotoEnBase64="iVBORw0KGgoAAAANSUhEUgAABdwAAAXcCAIAAAC3V9szAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\n" +
                    "bWFnZVJlYWR5ccllPAAAAyFpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdp\n" +
                    "bj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6\n" +
                    "eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1\n" +
                    "MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJo\n" +
                    "dHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlw\n" +
                    "dGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAv\n" +
                    "IiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RS\n" +
                    "ZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpD\n" +
                    "cmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSIgeG1wTU06SW5zdGFuY2VJ\n" +
                    "RD0ieG1wLmlpZDowNzU4NTA1RjIwNzQxMUUzOUEyMEY0NDFBRDNDQ0Y1RCIgeG1wTU06RG9jdW1l\n" +
                    "bnRJRD0ieG1wLmRpZDowNzU4NTA2MDIwNzQxMUUzOUEyMEY0NDFBRDNDQ0Y1RCI+IDx4bXBNTTpE\n" +
                    "ZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjA3NTg1MDVEMjA3NDExRTM5QTIw\n" +
                    "RjQ0MUFEM0NDRjVEIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjA3NTg1MDVFMjA3NDExRTM5\n" +
                    "QTIwRjQ0MUFEM0NDRjVEIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBt\n" +
                    "ZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+8D+MsQAAPLdJREFUeNrs3QlzEweiteF4kY0Bb5jFDEtI\n" +
                    "5mbm+///5g4ZzJZgE++WLVuypdvQX6WomQBedXp5nqKmSCYVq49cSH7T3ZrY2N77AQAAAIDxmjQB\n" +
                    "AAAAwPiJMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAA\n" +
                    "AASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASI\n" +
                    "MgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAA\n" +
                    "AAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAAB\n" +
                    "ogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwA\n" +
                    "AABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABA\n" +
                    "gCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgD\n" +
                    "AAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAA\n" +
                    "ECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDKAAAAAASIMgAAAAABogwAAABAgCgDAAAAECDK\n" +
                    "AAAAAASIMgAAAAAB0yYA4D+cnZ0d93q94+P+ySeDweD09HQ4HBZ/3zgAlzZVmJ6enpqa+exWYW6u\n" +
                    "+J+JiQnjALSTKAPAJ8PhsHvwSbfbPT4+NgjAtTv7rP/DD0dHR3/+zYmJidu3b9+d/6T4jUAD0CoT\n" +
                    "G9t7VgBoreFwuL+3t7u7u7+/PxqNDAIQNDU1tbS0tLy8fOfuXWsAtIEoA9BSJycnW5ub29vbLkoC\n" +
                    "qJrZ2dmVlZV7KytTU1PWAGgwUQagdXq93seNjd3dXVMAVNnk5OT9+/cfPHw4Pe2eAwDNJMoAtEi/\n" +
                    "31//8GFnZ8cUAHUxOTn54OEnxW+sAdAwogxAK4xGo42NjY8bG24cA1BHnU7nb0+eLC0tmQKgSUQZ\n" +
                    "gOY7Ojx8++7dic9UAqi5xcXFJ0+fdjodUwA0g8tTAZpsNBp93NhYX183BUAD7O3tHR4ePnv2bGFx\n" +
                    "0RoADeBMGYDGOj09ffPmTffgwBQADfPw4cPVx48nJiZMAVBrzpQBaKbjXm9tba3f75sCoHk+fvzY\n" +
                    "6/V+fPHCZ2YD1JpbuAM0UPfg4Ndff1VkABrsoPij/uXLwWBgCoD6EmUAmmZ/f//Vq1dnZ2emAGi2\n" +
                    "4+PjX1++lOAB6kuUAWiU/f39tVevfO41QEv0+31dBqC+RBmA5ugeHLxeW7MDQKsMBoN///vfrmMC\n" +
                    "qCNRBqAhjnu9169fO0cGoIX6Jydrr14Nh0NTANSLKAPQBKenp6/W1txHBqC1er3e2zdv7ABQL6IM\n" +
                    "QO2NRqM3b14P3FAAoN329vY2NjbsAFAjogxA7RVvwbsHXTsAsP7hw2HXKwJAbYgyAPV2dHi4sb5u\n" +
                    "BwBKb968cTUrQF2IMgA1NhqN3r59awcA/jQYDD78/rsdAGpBlAGosY2NjZOTEzsA8KWtrS0XMQHU\n" +
                    "gigDUFf9fv+jGzoC8Fd+++230WhkB4CKE2UA6urD7797ww3AX+r1ejs7O3YAqDhRBqCu77Z3d3ft\n" +
                    "AMDXrK+vD4dDOwBUmSgDUEsbLlwC4JsG/f7O9rYdAKpMlAGon5OTkz2nyQDwPR//+MOFrgBVJsoA\n" +
                    "1M/m5qYRAPiu/snJwf6+HQAqS5QBqJnhcOh0dADOaWtrywgAlSXKANTM3t7e2dmZHQA4j/39/cFg\n" +
                    "YAeAahJlAGpm10ecAnARbkMGUFmiDECdnJ2dHRwc2AGA89sVZQCqSpQBqJNut+tzNAC4kMPDQ9e9\n" +
                    "AlSTKANQJz5EA4BL6DrLEqCSRBmAWr2rPjw0AgBePgCaYdoEAHVxenp6cnwcfACzs7PT09MTkxOe\n" +
                    "C4ALGQ5Hg34/+ClIh92uZwGggkQZgNo47vUiX3d2dvbBgwcLi4udTsezAHD5P8aPj3d2drY2N8d/\n" +
                    "h5fiS49Go4kJVR2gWkQZgDq9mx//F3346NHq6qr38QBXd+vWrcePH9+/f//d27dj/ii90Wh0cnJS\n" +
                    "PADPAkCluKcMQG0U76fH/BWfPntW/PygyABco06n89PPPy8vLzf+RQSA7xJlAGrjpN8f55d79OjR\n" +
                    "ysqK2QGu3cTExLPnz+/cuTPOL9of74sIAOchygDUxmCM76dnZ2cfra7aHOCGlF1mnKciBm8zDMDX\n" +
                    "iDIAtTHOG0M+ePjQVUsAN2p2dnZxcXF8LyKnpzYHqBpRBqA2Tsf4fnqcPycAtNbi0tLYvtb4P/IJ\n" +
                    "gO8SZQBqYzQajecLzc7OTk/7eD6AGzfO28qcDUUZgMoRZQDqYWxF5ofPnwxicIAxUMABWk6UAaiH\n" +
                    "4XA4vi/mZjIA4/nj1t27ANpNlAEAAAAIEGUAAAAAAkQZAAAAgABRBgAAACBAlAEAAAAIEGUAAAAA\n" +
                    "AkQZAAAAgABRBgAAACBAlAEAAAAIEGUAAAAAAkQZAAAAgABRBgAAACBAlAEAAAAIEGUAAAAAAkQZ\n" +
                    "AAAAgABRBgAAACBAlAEAAAAIEGUAAAAAAkQZAAAAgABRBgAAACBAlAEAAAAIEGUAAAAAAkQZAAAA\n" +
                    "gABRBgAAACBAlAEAAAAImDYBABTOzs76/f5gMDg9PT0rfp2dDUejP//fyYmJyamp6c86nc7MzMzU\n" +
                    "1JTRAAC4ClEGgJYaDAaHh4dHR0e9Xu+41zs9Pb3YK+j09K25T27fvn3nzu1OZ8akAABc7C2lCQBo\n" +
                    "j+Fw2O12D/Y/6ff7V/lXnZ6edg8Oil/lX87MzCwsLMzPz9+dn5+cdHUwAADfJ8oA0Hyj0ehgf393\n" +
                    "d3dvb284HN7El+j3+5ufTU5OLi4uLi0tzS8sTExMGB8AgK8RZQBossGgv7W5tb29PRgMxvMVh8Ph\n" +
                    "zmedTmf53r3791dc2QQAwF8SZQBopl6v98cff+zu7Iy+uF/vOA0Gg48bG398/Li0tPTg4cO5uTlP\n" +
                    "CgAAXxJlAGiak5OT9Q8fdnd3q/BgRqNReeLM0tLS6uPHs7OzniAAAEqiDADNcXZ6ur6+vrW1lTo7\n" +
                    "5ht2P7t///6j1dXpaa+/AACIMgA0xfb29u+//XZ2dlblB7m5ubmzs/O3J0/u3bvnKQMAaDlRBoDa\n" +
                    "6/f77969+/PTqSvu7Ozs3du3Ozvbz549n5lxD2AAgPaaNAEAtba7s/Ov//3fuhSZP3UPusXD3tnZ\n" +
                    "8QwCALSWM2UAqKvRaPT+/fvtra2aPv6zs7O3b950u90nT55MTvrPJAAArSPKAFBLg8Hg9dra0dFR\n" +
                    "3Q9ke2vruNd78dOLTselTAAA7eK/ywFQP71e7+W//tWAIlMqDuTlv14WB+WZBQBoFVEGgJrpdru/\n" +
                    "vnw5GAyadFDF4RQHVbs74wAAcBWiDAB1sr+//+rf/x4Oh807tOKgXr16VRygZxkAoCVEGQBqY39/\n" +
                    "//Xa2mg0auoBFoe2pssAALSGKANAPXS73WYXmT8Vh+k6JgCANhBlAKiBXq+39upVG4rMD+X5Mmtr\n" +
                    "7vsLANB4ogwAVTcYDNZevWrkfWS+pjjY4pAHg75nHwCgwUQZACptNBq9Xltr2GctncenFLX2ulUp\n" +
                    "CgCgbUQZACrt/fv3R0dH7Tz23tHRb+/f+x4AAGgqUQaA6trZ2dne2mrzAtvb28UIvhMAABpJlAGg\n" +
                    "ovr9vvNECu/fvSumsAMAQPOIMgBU1Lt3b8/OzuwwHA7fvX1rBwCA5hFlAKii7e3t7kHXDqVut9vy\n" +
                    "y7gAABpJlAGgck5PT3//7Tc7fOn3338vZrEDAECTiDIAVM7G+roLl/5DMUgxix0AAJpElAGgWk5O\n" +
                    "TjY3N+3w34pZjo+P7QAA0BiiDADV8uHDByN8zbqTZQAAGkSUAaBCer3e3u6uHb6mGKeYyA4AAM0g\n" +
                    "ygBQIX98/GiEb/toIgCAphBlAKiKfr+/6zSZ79nb3S2GsgMAQAOIMgBUxdbW1mg0ssO3FRNtuREy\n" +
                    "AEAjiDIAVMJoNNre2rLDeWxvb6tXAAANIMoAUAkH+/unp6d2OI9iqP39fTsAANSdKANAJezs7Bjh\n" +
                    "/HbNBQBQf6IMAHnD4dCpHxdSzFWMZgcAgFoTZQDI6x4cSAwXUsx1cHBgBwCAWhNlAMhzmswlHBgN\n" +
                    "AKDmRBkA8vad9HGJ0UQZAICaE2UACOv3+4N+3w4XNRgUs9kNAKDGRBkAwo6OjoxwyekOD40AAFBf\n" +
                    "ogwAYcrC5afTswAA6kyUASCsd9wzwiWn65kOAKDGRBkAwnpHysJlpxNlWmx/f//Nmzej0cgUAFBf\n" +
                    "0yYAIOjsMztcfr3T06lpr+ats7+//3ptbVQYDn988WJiYsImAFBHzpQBIMnnB111wMHACG3zZ5Ep\n" +
                    "fr+3t/fm9WvnywBATYkyACT5MOwrUrXa5ssiU9JlAKC+RBkAkk5PT41wFWcGbJP/LjIlXQYAakqU\n" +
                    "ASDp1A1lDMj5fK3IlHQZAKgjUQaAJCd6GJDz+HaRKekyAFA7ogwASX58NCDfdZ4iU9JlAKBeRBkA\n" +
                    "kvz0eNUBh0MjNNv5i0xJlwGAGhFlAAAq6qJFpqTLAEBdiDIAJE1MTBjhSgNOeilvrMsVmZIuAwC1\n" +
                    "4J0cAEmSjAH5S1cpMiVdBgCqT5QBIGlqetoIBuQ/XL3IlHQZAKg4UQaApOmpKSMYkC9dV5Ep6TIA\n" +
                    "UGWiDABJ0070uBpnyjTM9RaZki4DAJUlygCQ1JmZMcJVzBiwQW6iyJR0GQCoJlEGgCRN4aoDdjpG\n" +
                    "aIabKzIlXQYAKkiUASBpamrKFUxXWc/lS81w00WmpMsAQNWIMgCE3Zq7ZYTLmZubM0IDjKfIlHQZ\n" +
                    "AKgUUQaAsLm520a47HSiTO2Ns8iUdBkAqA5RBoCw27dFmctOd+eOEWpt/EWmpMsAQEWIMgCE3bkj\n" +
                    "ylySnlVrqSJT0mUAoApEGQDCOp0Zn8F0md1m7FZj2SJT0mUAIE6UASBvfmHBCBe1MD9vhJqqQpEp\n" +
                    "6TIAkCXKAJCnL1xmNCWrnqpTZEq6DAAEiTIA5N2dn5+c9JJ0kdfvycm7SlYNVa3IlHQZAIi9qTMB\n" +
                    "APlXo8nJhcVFO5zfwsKCjFU71SwyJV0GADJvg00AQBUsLy0Z4fyWlpeNUC9VLjIlXQYAxk+UAaAS\n" +
                    "5hcWOp2OHc5jenraDWXqpfpFpqTLAMCYiTIAVMLExMS9e/fscB73VlaKuexQF3UpMiVdBgDGSZQB\n" +
                    "oCpW7msN31dMtLKyYoe6qFeRKekyADA2ogwAVdHpzLhVynctLS3NzMzYoRbqWGRKugwAjIcoA0CF\n" +
                    "PHjwwAjfmejhQyPUQn2LTEmXAYAxEGUAqJC5uTkny3zD4tJSMZEdqq/uRaakywDATRNlAKiW1dVV\n" +
                    "d5b5msePHxuh+ppRZEq6DADcKFEGgGqZnZ11I9u/dP/+/WIcO1Rck4pMSZcBgJsjygBQOauPH09N\n" +
                    "TdnhS8Ugj1ZX7VBxzSsyJV0GAG6IKANA5UxNTT158sQOX/rbkyfT09N2qLKmFpmSLgMAN0GUAaCK\n" +
                    "lu/dm5+ft0Pp7vzde/fu2aHKml1kSroMAFw7UQaAinr67JmLmH74fN7Qs2fP7VBlbSgyJV0GAK6X\n" +
                    "KANARc3MzDx99swOT58+LaawQ2W1p8iUdBkAuEaiDADVtbS01PJPYioOf2l52XdCZbWtyJR0GQC4\n" +
                    "LqIMAJX25OnT27dvt/PYiwMvDt/3QGW1s8iUdBkAuBaiDACVNjEx8eKnnzqdTtsOvDjk4sCLw/c9\n" +
                    "UE1tLjIlXQYArk6UAaDqOp3OTz//PDnZotes4mCLQ25hiqoLRaakywDAVd/1mQCA6pubm/vp559b\n" +
                    "ctpIcZjFwRaH7HmvJkXmS7oMAFyFKANAPdy9e7cNl/OUl2sVB+sZryZF5r/pMgBwaaIMALWxsLDQ\n" +
                    "7C5TFpniMD3X1aTIfI0uAwCXI8oAUCcLCws///3vjby/THFQxaEpMpWlyHybLgMAl3kHaAIA6uXu\n" +
                    "3bu//PJLw26CWxzO//zyi6uWKkuROQ9dBgAuSpQBoH5uzc398o9/3L59uxmHUxxIcTju7FtZisz5\n" +
                    "6TIAcCGiDAC1VJ5asrKyUvcDKQ7hfxp34k+TKDIXpcsAwPmJMgDU1cTExNNnz3588WJqaqqOj794\n" +
                    "2MWDLw6hJR/1XUeKzOXoMgBwTqIMAPW2tLT0z//3z/n5+Xo97OIB/+Of/ywevGewshSZq9BlAOA8\n" +
                    "RBkAaq/Tmfn5739//uPzWpwyUzzI58+fFw94ZmbGc1dZiszV6TIA8F3TJgCgGZaX7y0sLG6sr29u\n" +
                    "blbz58CJiYmV+/dXV1drer1Veygy16XsMj++eOEaPQD4S6IMAM0xNTX1tydPVu7f31hf39nZqdRj\n" +
                    "W15efrS6Ojs762mqOEXmeukyAPANogwATTM7O/v8xx8fPXr08ePHnZ2d7E/XxQ+iy8vLDx4+vHXr\n" +
                    "lqem+hSZm6DLAMDXiDIANNPsrVvPnj9fffx4a2tre2trMBiM+QF0Op17K5/4uOu6UGRuji4DAH9J\n" +
                    "lAGgyTqdzurq6qNHjw4ODnZ3doqfDIfD4Y1+xcnJycXFxaXl5fn5eT9/1ogic9N0GQD4b6IMAM1X\n" +
                    "/BC48NlwOOx2uwf7+wcHBycnJ9f4JWZmZop///zCwt27dycnfbhhzSgy46HLAMB/EGUAaJHJycmy\n" +
                    "zhS/HwwGR4XDw97x8XGvd9Hrmzqdzq1bt+bm5m4X7txxjVJ9KTLjpMsAwJdEGQBaqtPpLH5W/uVw\n" +
                    "OOz3+4PB4LT4dXZW/OXZ2dmf//DU1NTk5OT01NR055OZmRmnwzSDIjN+ugwA/EmUAYBPJicnb31m\n" +
                    "ivZQZFJ0GQD4/29BTQAAtJAik1V2GfsD0HKiDADQOopMFegyACDKAADtoshUhy4DQMuJMgBAiygy\n" +
                    "VaPLANBmogwA0BaKTDXpMgC0ligDALSCIlNlugwA7STKAADNp8hUny4DQAuJMgBAwykydaHLANA2\n" +
                    "ogwA0GSKTL3oMgC0iigDADSWIlNHugwA7SHKAADNpMjUly4DQEuIMgBAAykydafLANAGogwA0DSK\n" +
                    "TDPoMgA0nigDADSKItMkugwAzSbKAADNocg0jy4DQIOJMgBAQygyTaXLANBUogwA0ASKTLPpMgA0\n" +
                    "kigDANSeItMGugwAzSPKAAD1psi0hy4DQMOIMgBAjSkybaPLANAkogwAUFeKTDvpMgA0higDANSS\n" +
                    "ItNmugwAzSDKAAD1o8igywDQAKIMAFAzigwlXQaAuhNlAIA6UWT4ki4DQK2JMgBAbSgy/DddBoD6\n" +
                    "EmUAgHpQZPgaXQaAmhJlAIAaUGT4Nl0GgDoSZQCAqlNkOA9dBoDaEWUAgEpTZDg/XQaAehFlAIDq\n" +
                    "UmS4KF0GgBoRZQCAilJkuBxdBoC6EGUAgCpSZLgKXQaAWhBlAIDKUWS4Ol0GgOoTZQCgEjbW1/v9\n" +
                    "vh1+UGS4Pnt7e8X30nA4NAUA1STKAEDeb+/fr6+v//rypS6jyHAT31G6DADVJMoAQNhv799vbm4W\n" +
                    "vxkMBi3vMooMN+Hg4ECXAaCaRBkASPqzyJTa3GUUGW6OLgNANYkyABDzH0Wm1M4uo8hw03QZACpI\n" +
                    "lAGAjL8sMqW2dRlFhvHQZQCoGlEGAAK+UWRK7ekyigzjpMsAUCmiDACM23eLTKkNXUaRYfx0GQCq\n" +
                    "Q5QBgLE6Z5EpNbvLKDKk6DIAVIQoAwDjc6EiU2pql1FkyNJlAKgCUQYAxuQSRabUvC6jyFAFugwA\n" +
                    "caIMAIzDpYtMqUldRpGhOnQZALJEGQC4cVcsMqVmdBlFhqrRZQAIEmUA4GZdS5Ep1b3LKDJUky4D\n" +
                    "QIooAwA36BqLTKm+XUaRocp0GQAiRBkAuCnXXmRKdewyigzVp8sAMH6iDADciBsqMqV6dRlFhrrQ\n" +
                    "ZQAYM1EGAK7fjRaZUl26jCJDvegyAIyTKAMA12wMRaZU/S6jyFBHBwcH3W7XDgCMgSgDANdpbEWm\n" +
                    "VOUuo8gAAHybKAMA12bMRaZUzS6jyAAAfJcoAwDXI1JkSlXrMooMAMB5iDIAcA2CRaZUnS6jyAAA\n" +
                    "nJMoAwBXFS8ypSp0GUUGAOD8RBkAuJKKFJlStssoMgAAFyLKAMDlVarIlFJdRpEBALgoUQYALqmC\n" +
                    "RaY0/i6jyAAAXIIoAwCXUdkiUxpnl1FkAAAuR5QBgAureJEpjafLKDIAAJcmygDAxdSiyJRuusso\n" +
                    "MgAAVyHKAMAF1KjIlG6uyygyAABXJMoAwHnVrsiUbqLLKDIAAFcnygDAudS0yJSut8soMgAA10KU\n" +
                    "AYDvq3WRKV1Xl1FkAACuiygDAN/RgCJTunqXUWQAAK6RKAMA39KYIlO6SpdRZAAArpcoAwBf1bAi\n" +
                    "U7pcl1FkAACunSgDAH+tkUWmdNEus7+3p8gAAFw7UQYA/kKDi0zp/F1md3d3TZEB+D/27rY5ivNA\n" +
                    "w+hqRhJCGr0gEYFIENiJ///fyX7IVmxsl+3EYAy4ZA1IK0Iqm40TL04tc3X3nGNK5W9TdVv1dPti\n" +
                    "egbgAxBlAOAfTb7IvPM+Xea777779I9/9CsBAPAhiDIA8L+sSZF55+e7jCIDAPBBiTIA8D/Wqsi8\n" +
                    "86+6jCIDAPChiTIA8FdrWGTe+WmXUWQAAFZAlAGAt9a2yLzz911GkQEAWI1NEwDAmheZd951mV+d\n" +
                    "nn75xRd+JQAAVsA7ZQBYd4rM3yyXS0UGAGBlRBkA1poiAwBARZQBYH0pMgAAhEQZANaUIgMAQEuU\n" +
                    "AWAdKTIAAOREGQDWjiIDAMAQiDIArBdFBgCAgRBlAFgjigwAAMMhygCwLhQZAAAGRZQBYC0oMgAA\n" +
                    "DI0oA8D0KTIAAAyQKAPAxCkyAAAMkygDwJQpMgAADJYoA8BkKTIAAAyZKAPANCkyAAAMnCgDwAQp\n" +
                    "MgAADJ8oA8DUKDIAAIyCKAPApCgyAACMhSgDwHQoMgAAjIgoA8BEKDIAAIyLKAPAFCgyAACMjigD\n" +
                    "wOgpMgAAjJEoA8C4KTIAAIyUKAPAiCkyAACMlygDwFgpMgAAjJooA8AoKTIAAIydKAPA+CgyAABM\n" +
                    "gCgDwMgoMgAATIMoA8CYKDIAAEyGKAPAaCgyAABMiSgDwDgoMgAATIwoA8AIKDIAAEyPKAPA0Cky\n" +
                    "AABMkigDwKApMgAATJUoA8BwKTIAAEyYKAPAQCkyAABMmygDwBApMgAATJ4oA8DgKDIAAKwDUQaA\n" +
                    "YVFkAABYE6IMAAOiyAAAsD5EGQCGQpEBAGCtiDIADIIiAwDAuhFlAOgpMgAArCFRBoCYIgMAwHoS\n" +
                    "ZQAoffP114oMAADrSZQBoPTj5aURAABYT6IMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAA\n" +
                    "BEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkA\n" +
                    "AACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICA\n" +
                    "KAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAA\n" +
                    "ABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBl\n" +
                    "AAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAA\n" +
                    "AqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgsGkCAEIP/8IOAACsIe+UAQAAAAiIMgAAAAABUQYA\n" +
                    "AAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAg\n" +
                    "ygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAA\n" +
                    "AAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQZgHObz+cpe6/rq2uAA\n" +
                    "K3B1dWUEgHUmygDwjy4vL40AsALLFZ63Gzf/ADAwogzAeI7s2YoO7eVyeXFxYXCAD+3Fixeru4is\n" +
                    "8B2XALzv4WwCgLGYb26u7LWePXtmcIApHbabogzA8IgyAKOxtcIo8+c//Wm5XNoc4MN5/vz5Dz/8\n" +
                    "sLKX21zhRQSA9yTKAIzG1vb2yl7r6urqyWefXV/7xF+AD2K5XH7+5MlULyIAvCdRBmA0bq32fvrF\n" +
                    "ixe6DMCHsFwu/+sPf3j9+vUqX3RblAEYHm9iBBiNWzs7K37FZ8+eXV5ePjw/v3Xrlv0B/l98//z5\n" +
                    "kydPVlxk3l5EnOQAwyPKAIzGzsqjzI1Xr1795+9/f3jj6Ghvb29zc3Njw5eqAvwyV1dXy+Xy5YsX\n" +
                    "T58+XeXnyPzNfD73ThmAARJlAEZjZ2dnY2Nj9c8T3bzid3/hPwHASO3u7hoBYIB8pgzAeI7s2cxd\n" +
                    "NQD/hr29PSMADPEO3wQAI7JYLIwAwC+15/IBMEiiDMCY7B8cGAGAX3bHP5t5pwzAQI9oEwCMyO7u\n" +
                    "7nw+twMA7+/g4MBntAMMkygDMCY3d9VHR0d2AOD9HbpwAAyVKAMwMkd37hgBgPe93Z/NDjz6CjDY\n" +
                    "U9oEAOOyWCy2b92yAwDv487x8Wzmnh9goBzQAONz9+TECAC8jxOXDIABE2UAxuf45MRfewLwf1rs\n" +
                    "L27fvm0HgMFyTw8wPvP5/OTuXTsA8PNOT+8ZAWDIRBmAkd5nn3qzDAA/Y29vb39/3w4AQ+aGHmCU\n" +
                    "Njc3f3V6agcA/pWzszMjAAycKAMwVqenp1tbW3YA4KeOjo72Fgs7AAycKAMw2hN8Nnvw61/bAYCf\n" +
                    "XiDOHjywA8AITmwTAIzX0dHRwcGBHQD4e2cPHmxvb9sBYPhEGYBx+83Dh/P53A4AvLNYLE5OTuwA\n" +
                    "MAqiDMC4bW1tPTw/twMAN+bz+fmj842NDVMAjIIoAzB6h4eHvokJgBuPHj3a2vLgEsBoiDIAU3B2\n" +
                    "drbY37cDwJpfC/Z90BjAqIgyAFOwsbHx+PHjnZ0dUwCsp+Pj49N79+wAMC6iDMBEzOfzj3/7262t\n" +
                    "LVMArJvDw8PfPHxoB4DREWUApmNra+t3n3yiywCslf39/fNHj3y4L8AYiTIAk7K9vf27Tz65+WkK\n" +
                    "gHVwcHj4+KOPZjN39QCj5PgGmJp3Xeb27dumAJi24+Pjx48fKzIA4+UEB5igd88xHfgODoDpun92\n" +
                    "9vD83FNLAKMmygBM9HyfzT76+OP79++bAmBi5vP5zQl/z3ctAYzfpgkAJuze/ft7i8Vnn366XC6t\n" +
                    "ATABi8Xi/NH51pbPDgOYgo2vnz63AsC0vXnz5ssvv3z67bemABiv2Wx2dnZ2cveuR5YAJkOUAVgX\n" +
                    "L1++/OLzzy8uLkwBMDqHR0cPHjzw5XoAEyPKAKyR6+vrZ0+ffvXVV55mAhiLvb29+2dni8XCFADT\n" +
                    "I8oArJ2rq6tnT59+8803l5eX1gAYrL3F4vT01FfpAUyYKAOwpq6vr198//2fv/325qc1AIZjNpvd\n" +
                    "uXPn+ORkd3fXGgDTJsoArLvlcvn8u7devXplDYDKbDbb398/Ojo6ODy8+XeDAKwDUQaAv3rz+vWL\n" +
                    "ly9f3Xj58uLi4vr62iYAH9RsNtvd3d1bLBZ7ezc/fa0SwLoRZQD4J66vr3+8uPjx8q3lcvn69eur\n" +
                    "N2/eXL2xDMC/edv9Hxuz+Vubm5vbW1vb29u3dnZ8mxLAmts0AQD/5H8eNjZ2bt+++WMKAAD4QDyt\n" +
                    "CgAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAA\n" +
                    "EBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUA\n" +
                    "AAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAAC\n" +
                    "ogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAA\n" +
                    "AEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECU\n" +
                    "AQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAA\n" +
                    "CIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIA\n" +
                    "AAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAAB\n" +
                    "UQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAA\n" +
                    "ACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDK\n" +
                    "AAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAA\n" +
                    "BEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkA\n" +
                    "AACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICA\n" +
                    "KAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAA\n" +
                    "ABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBl\n" +
                    "AAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAA\n" +
                    "AqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwA\n" +
                    "AABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBA\n" +
                    "lAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAA\n" +
                    "AAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgy\n" +
                    "AAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAA\n" +
                    "AVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYA\n" +
                    "AAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAg\n" +
                    "ygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAA\n" +
                    "AAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZ\n" +
                    "AAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACA\n" +
                    "gCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMA\n" +
                    "AAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQ\n" +
                    "ZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAA\n" +
                    "AAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIM\n" +
                    "AAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABA\n" +
                    "QJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEA\n" +
                    "AAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiI\n" +
                    "MgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAA\n" +
                    "AAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEG\n" +
                    "AAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAg\n" +
                    "IMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAA\n" +
                    "AAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAARE\n" +
                    "GQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAA\n" +
                    "gIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgD\n" +
                    "AAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQ\n" +
                    "EGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAA\n" +
                    "AAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKi\n" +
                    "DAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAA\n" +
                    "QECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQB\n" +
                    "AAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAI\n" +
                    "iDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAA\n" +
                    "AAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFR\n" +
                    "BgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAA\n" +
                    "ICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoA\n" +
                    "AAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAE\n" +
                    "RBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAA\n" +
                    "AICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAo\n" +
                    "AwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAA\n" +
                    "EBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUA\n" +
                    "AAAAAqIMAAAAQECUAQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAAC\n" +
                    "ogwAAABAQJQBAAAACIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAA\n" +
                    "AEBAlAEAAAAIiDIAAAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECU\n" +
                    "AQAAAAiIMgAAAAABUQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAA\n" +
                    "CIgyAAAAAAFRBgAAACAgygAAAAAERBkAAACAgCgDAAAAEBBlAAAAAAKiDAAAAEBAlAEAAAAIiDIA\n" +
                    "AAAAAVEGAAAAICDKAAAAAAREGQAAAICAKAMAAAAQEGUAAAAAAqIMAAAAQECUAQAAAAiIMgAAAAAB\n" +
                    "UQYAAAAgIMoAAAAABEQZAAAAgIAoAwAAABAQZQAAAAACogwAAABAQJQBAAAACIgyAAAAAAFRBgAA\n" +
                    "ACAgygAAAAAE/luAAQDuTxHEQ8GXwwAAAABJRU5ErkJggg==";


            if(ivFoto.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
                if (bitmap != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            }

            float precio = Float.parseFloat(etPrice.getText().toString());

            articulo = new Articulo(etName.getText().toString(),0,etDesc.getText().toString(),precio,spinner.getSelectedItem().toString(),fotoEnBase64,false,usuario,new Categoria(162L,"Coches"));
            Call<Articulo> call = articuloService.nuevoArticulo(articulo);

            call.enqueue(new Callback<Articulo>() {
                @Override
                public void onResponse(Call<Articulo> call, Response<Articulo> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.putExtra("usuario", usuario);
                            startActivity(i);

                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onFailure(Call<Articulo> call, Throwable t) {
                    Toast.makeText(getContext(), call.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }else
            Toast.makeText(this.getContext(), "Comprueba los campos vacios", Toast.LENGTH_SHORT).show();
    }
    }