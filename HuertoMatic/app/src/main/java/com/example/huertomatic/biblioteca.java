package com.example.huertomatic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link biblioteca#newInstance} factory method to
 * create an instance of this fragment.
 */
public class biblioteca extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ConstraintLayout cl;
    private ScrollView sc;
    public biblioteca() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment biblioteca.
     */
    // TODO: Rename and change types and number of parameters
    public static biblioteca newInstance(String param1, String param2) {
        biblioteca fragment = new biblioteca();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biblioteca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // preparamos nuestros elementos para posteriormente rellenar lo que necesitemos
        sc = (ScrollView) view.findViewById(R.id.ScrollViewBiblio);
        // utilizamos Grid Layout porque las demás opciones no permiten eel posicionamiento en columnas sin tener que anidar
        GridLayout GrL = (GridLayout) sc.getChildAt(0);
        Bundle data = new Bundle();
        // creamos una instancia a la base de datos
        AdminSQLiteOpenHelper administrador = new AdminSQLiteOpenHelper(getContext(), "huerto.db", null, 4);
        SQLiteDatabase BaseDeDatos = administrador.getWritableDatabase();

        // variables donde guardaremos los datos que nos hacen falta.
        ArrayList<byte[]> imagenes = new ArrayList<>(); // imagenes descargadas de la base de datos
        ArrayList<String> nombres = new ArrayList<>(); // los nombres de los vegetales


        Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM vegetales", null); // hacemos la consulta y la ejecutamos
        while(fila.moveToNext()) {

            String nombreVegetal = fila.getString(1);
            byte[] imagen = fila.getBlob(12);
            // lo que obtengamos lo añadimos a los ArrayList anteriormente creados
            imagenes.add(imagen);
            nombres.add(nombreVegetal);

        }
        // en este bucle lo que vamos a hacer es recorrer los arrays que tenemos de imagenes y nombres
        for(int i = 0 ; i<imagenes.size(); i++){
            int finalI = i+1;
            ImageView iconoVegetal = new ImageView(getContext());
            iconoVegetal.setImageBitmap(BitmapFactory.decodeByteArray(imagenes.get(i), 0,imagenes.get(i).length));
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();

            int paddingV= 60;
            int paddingH= 50;
            int size = 500; // parametrizamos los tamaños para no tener que cambiar 4 veces lo mismo
            lp.width= size;
            lp.height= size;
            lp.setMargins(size*20/100,size*20/100,size*20/100,size*20/100); // establecemos los margenes externos
            iconoVegetal.setLayoutParams(new ViewGroup.LayoutParams(lp)); // seteamos los parametros anteriores
            iconoVegetal.setPadding(paddingH,paddingV,paddingH,paddingV); // establecemos los margenes internos
            iconoVegetal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.putInt("0", finalI);
                    Navigation.findNavController(v).navigate(R.id.action_biblioteca_to_vegetalIndividual, data);
                    Toast.makeText(getContext(), "Vamos a plantar...." + nombres.get(finalI-1), Toast.LENGTH_SHORT).show();
                }
            });
            GrL.addView(iconoVegetal);
        }
    }
}

