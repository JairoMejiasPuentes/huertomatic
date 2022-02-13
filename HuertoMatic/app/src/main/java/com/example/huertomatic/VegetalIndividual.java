package com.example.huertomatic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VegetalIndividual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VegetalIndividual extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvNombreVegetal, tvDescripcion, tvMesIniSemillero, tvMesFinSemillero, tvMesIniSiembra, tvMesFinSiembra, tvMesIniRecogida, tvMesFinRecogida;
    private ImageView imageV;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HorizontalScrollView ScrollBeneficios;
    private HorizontalScrollView ScrollPerjudiciales;

    // Preparamos nuestros datos
    private int valorBundle; // muy importante porque lo haremos coincidir con el id de la base de datos
    public String nombreVegetal = "";
    public ArrayList<Integer> idsBeneficio, idsPerjuicio;
    public ArrayList<String> ArrayPerjudiciales, ArrayBeneficiosas, mesesNombre;
    public LinearLayout LinLBen,LinLPer;
    public ArrayList<byte[]> ArrayImagenesBeneficiosas,ArrayImagenesPerjudiciales;


    public VegetalIndividual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VegetalIndividual.
     */
    // TODO: Rename and change types and number of parameters
    public static VegetalIndividual newInstance(String param1, String param2) {
        VegetalIndividual fragment = new VegetalIndividual();
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
        return inflater.inflate(R.layout.fragment_vegetal_individual, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDescripcion = (TextView) view.findViewById(R.id.Descripcion);
        tvNombreVegetal = (TextView) view.findViewById(R.id.nombreVegetal);
        tvMesIniSemillero = (TextView) view.findViewById(R.id.mesIniSemillero);
        tvMesFinSemillero = (TextView) view.findViewById(R.id.mesFinSemillero);
        tvMesIniSiembra = (TextView) view.findViewById(R.id.mesIniSiembra);
        tvMesFinSiembra = (TextView) view.findViewById(R.id.mesFinSiembra);
        tvMesIniRecogida = (TextView) view.findViewById(R.id.mesIniRecogida);
        tvMesFinRecogida = (TextView) view.findViewById(R.id.mesFinRecogida);
        ScrollBeneficios = (HorizontalScrollView) view.findViewById(R.id.ScrollBeneficios);
        ScrollPerjudiciales = (HorizontalScrollView) view.findViewById(R.id.ScrollPerjudiciales);

        valorBundle = getArguments().getInt("0");
        ArrayImagenesBeneficiosas = new ArrayList();
        ArrayImagenesPerjudiciales = new ArrayList();
        idsBeneficio = new ArrayList();
        idsPerjuicio = new ArrayList();
        AdminSQLiteOpenHelper administrador = new AdminSQLiteOpenHelper(getContext(), "huerto.db", null, 4);
        SQLiteDatabase BaseDeDatos = administrador.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM vegetales WHERE codigo = "+ valorBundle, null);

        while(fila.moveToNext()) {
            ArrayBeneficiosas = new ArrayList();
            ArrayPerjudiciales = new ArrayList();
            //obtenemos de la BBDD todos los datos que nos hacen falta

            String strBeneficiosas = fila.getString(10);
            String [] vectorBeneficio = strBeneficiosas.split(", ");
            for(int k = 0; k< vectorBeneficio.length; k++){
                ArrayBeneficiosas.add(vectorBeneficio[k]);
            }
            String strPerjudiciales = fila.getString(11);
            String [] vectorPerjuicio = strPerjudiciales.split(", ");
            for(int l = 0; l < vectorPerjuicio.length; l++){
                ArrayPerjudiciales.add(vectorPerjuicio[l]);
            }
        }
        imageV = (ImageView) view.findViewById(R.id.imagen);
        Buscar();
        crearArrayImagenes(ArrayImagenesBeneficiosas, ArrayBeneficiosas, idsBeneficio);
        crearArrayImagenes(ArrayImagenesPerjudiciales, ArrayPerjudiciales, idsPerjuicio);
        rellenarScroll(LinLBen,ScrollBeneficios,ArrayImagenesBeneficiosas, idsBeneficio);
        rellenarScroll(LinLPer,ScrollPerjudiciales,ArrayImagenesPerjudiciales,idsPerjuicio);
    }

    // crear un metodo para rellenar los ArrayList con los datos que nos hacen falta.

    /**
     * Método para crear un Array de imagenes que posteriormente se mostrarán en los Scrolls de beneficiosas y perjudiciales
     * @param ArImagen Parámetro del array de imagenes que queremos mostrar
     * @param ArrayNombres Array de nombres donde añadioremos las asociaciones
     * @param ArrayIds Este nos hace falta para posteriormente enlazar mediante un bundle al siguiente fragment
     */
    public void crearArrayImagenes(ArrayList<byte[]> ArImagen, ArrayList<String> ArrayNombres, ArrayList<Integer> ArrayIds){
        AdminSQLiteOpenHelper administrador = new AdminSQLiteOpenHelper(getContext(), "huerto.db", null, 4);
        SQLiteDatabase BaseDeDatos = administrador.getWritableDatabase();
        for (int i = 0 ; i< ArrayNombres.size() ; i++ ){
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM vegetales WHERE nombre = \""+ArrayNombres.get(i) +"\""  , null);
            while(fila.moveToNext()) {
                int id = fila.getInt(0);
                byte[] imagen = fila.getBlob(12);
                ArImagen.add(imagen);
                ArrayIds.add(id);
                System.out.println("--> " + imagen);
            }
        }
    }

    /**
     * Método para rellenar los Scrolls ya que tenemso distintos Arrays
     *
     * @param linLay es el LinearLayout donde queremos añadir las imagenes
     * @param hsc el Scroll Horizontal dentro del Linear Layout donde posicionaremos las mismas
     * @param ArImagen El array de imagenes a añadir
     * @param ArrayIds Los ids, para pasarmos por bundle.
     */
    public void rellenarScroll( LinearLayout linLay, HorizontalScrollView hsc, ArrayList<byte[]> ArImagen, ArrayList<Integer> ArrayIds){
        linLay = (LinearLayout) hsc.getChildAt(0);
        Bundle data = new Bundle();
        for (int i = 0; i < ArImagen.size(); i++) {
                int finalI = ArrayIds.get(i);
                ImageView iconoVegetal = new ImageView(getContext());
                iconoVegetal.setImageBitmap(BitmapFactory.decodeByteArray(ArImagen.get(i), 0,ArImagen.get(i).length));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
                lp.setMargins(100,0,100,0);
                iconoVegetal.setLayoutParams(new ViewGroup.LayoutParams(lp));
                iconoVegetal.setPadding(20,0,20,0);
                iconoVegetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.putInt("0", finalI);
                        Navigation.findNavController(v).navigate(R.id.action_vegetalIndividual_self, data);
                    }
                });
                linLay.addView(iconoVegetal);
            }
        }

    /**
     * Método para Obtener todos los datos del vegetal individual y mostrarlos, con excepoción de las asociaciones.
     */
    public void Buscar() {
        AdminSQLiteOpenHelper administrador = new AdminSQLiteOpenHelper(getContext(), "huerto.db", null, 4);
        SQLiteDatabase BaseDeDatos = administrador.getWritableDatabase();

        mesesNombre = new ArrayList <String> ();
        mesesNombre.add("No aplica");
        mesesNombre.add("Ene");
        mesesNombre.add("Feb");
        mesesNombre.add("Mar");
        mesesNombre.add("Abr");
        mesesNombre.add("May");
        mesesNombre.add("Jun");
        mesesNombre.add("Jul");
        mesesNombre.add("Ago");
        mesesNombre.add("Sep");
        mesesNombre.add("Oct");
        mesesNombre.add("Nov");
        mesesNombre.add("Dic");

        if (valorBundle != 0) {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM vegetales WHERE codigo=" + valorBundle, null);
            if (fila.moveToFirst()) {
                for (int j = 0; j < mesesNombre.size(); j++) {
                    tvMesIniSemillero.setText(mesesNombre.get(fila.getInt(3)));
                    tvMesFinSemillero.setText(mesesNombre.get(fila.getInt(4)));
                    tvMesIniSiembra.setText(mesesNombre.get(fila.getInt(5)));
                    tvMesFinSiembra.setText(mesesNombre.get(fila.getInt(6)));
                    tvMesIniRecogida.setText(mesesNombre.get(fila.getInt(7)));
                    tvMesFinRecogida.setText(mesesNombre.get(fila.getInt(8)));
                }
                tvNombreVegetal.setText(fila.getString(1));
                tvDescripcion.setText(fila.getString(9));
                byte[] imagen = fila.getBlob(12);
                imageV.setImageBitmap(BitmapFactory.decodeByteArray(imagen, 0, imagen.length));
            }else{
                Toast.makeText(getContext(), "El artículo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Escriba el código del artículo", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();
    }
}