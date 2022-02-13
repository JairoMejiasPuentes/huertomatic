package com.example.huertomatic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Esta clase la dejo para posteriores ampliaciones en las que el usuario pueda crear su propia aplicacion con los vegetales que quiera
 */
public class registro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText etCodigo, etNombre, etDefTax, etSemillIni, etSemillFin, etSiembraIni, etSiembraFin, etCosechaIni, etCosechaFin, etDescripcion, etBeneficiosas, etPerjudiciales;
    private RadioButton rbInsert,rbModify,rbDelete,rbSearch;
    private Button btnBack,btnOk;


    public registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registro.
     */
    // TODO: Rename and change types and number of parameters
    public static registro newInstance(String param1, String param2) {
        registro fragment = new registro();
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
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etCodigo = (EditText) view.findViewById(R.id.etCodigo);
        etNombre = (EditText) view.findViewById(R.id.etNombre);
        etDefTax = (EditText) view.findViewById(R.id.etDefTax);
        etSemillIni = (EditText) view.findViewById(R.id.etIniSemillero);
        etSemillFin = (EditText) view.findViewById(R.id.etFinSemillero);
        etSiembraIni = (EditText) view.findViewById(R.id.etIniSiembra);
        etSiembraFin = (EditText) view.findViewById(R.id.etFinSiembra);
        etCosechaIni = (EditText) view.findViewById(R.id.etIniCosecha);
        etCosechaFin = (EditText) view.findViewById(R.id.etFinCosecha);
        etDescripcion = (EditText) view.findViewById(R.id.Descripcion);
        etBeneficiosas = (EditText) view.findViewById(R.id.etBenef);
        etPerjudiciales = (EditText) view.findViewById(R.id.etPerjudiciales);
        rbInsert = (RadioButton) view.findViewById(R.id.rbInsert);
        rbModify = (RadioButton) view.findViewById(R.id.rbModify);
        rbDelete = (RadioButton) view.findViewById(R.id.rbDelete);
        rbSearch = (RadioButton) view.findViewById(R.id.rbSearch);
        btnBack = (Button) view.findViewById(R.id.btnBackIni);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.biblioteca);
            }
        });
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbInsert.isChecked() == true) {
                    insertar();
                }else if (rbModify.isChecked() == true){
                    modificar();
                }else if(rbSearch.isChecked() == true){
                    buscar();
                }else if(rbDelete.isChecked() == true){
                    borrar();
                }
            }
        });
    }

    public void insertar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "huerto", null , 4);
        SQLiteDatabase database = admin.getWritableDatabase();

        ArrayList <String> textos = new ArrayList();


        String codigoSTR = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String defTax = etDefTax.getText().toString();

        String semilleroIniSTR = etSemillIni.getText().toString();
        String semilleroFinSTR = etSemillFin.getText().toString();
        String siembraIniSTR = etSiembraIni.getText().toString();
        String siembraFinSTR = etSiembraFin.getText().toString();
        String cosechaIniSTR = etCosechaIni.getText().toString();
        String cosechaFinSTR = etCosechaFin.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String perjudicialesSTR = etPerjudiciales.getText().toString();
        String beneficiosasSTR = etBeneficiosas.getText().toString();

        if (!codigoSTR.isEmpty()
                && !nombre.isEmpty()
                && !defTax.isEmpty()
                && !semilleroIniSTR.isEmpty()
                && !semilleroFinSTR.isEmpty()
                && !siembraIniSTR.isEmpty()
                && !siembraFinSTR.isEmpty()
                && !cosechaIniSTR.isEmpty()
                && !cosechaFinSTR.isEmpty()
                && !perjudicialesSTR.isEmpty()
                && !beneficiosasSTR.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigoSTR);
            registro.put("nombre", nombre);
            registro.put("defTax", defTax);
            registro.put("semilleroIni", semilleroIniSTR);
            registro.put("semilleroFin", semilleroFinSTR);
            registro.put("siembraIni", siembraIniSTR);
            registro.put("siembraFin", siembraFinSTR);
            registro.put("cosechaIni", cosechaIniSTR);
            registro.put("cosechaFin", cosechaFinSTR);
            registro.put("descripcion", descripcion);
            registro.put("beneficiosas", beneficiosasSTR);
            registro.put("perjudiciales", perjudicialesSTR);

            database.insert("vegetales", null, registro);

            etCodigo.setText("");
            etNombre.setText("");
            etDefTax.setText("");
            etSemillIni.setText("");
            etSemillFin.setText("");
            etSiembraIni.setText("");
            etSiembraFin.setText("");
            etCosechaIni.setText("");
            etCosechaFin.setText("");
            etDescripcion.setText("");
            etPerjudiciales.setText("");
            etBeneficiosas.setText("");

            Toast.makeText(getContext(), "se ha producido la inserción", Toast.LENGTH_LONG).show();
            database.close();
        }else{
            System.out.println("he entrado");
            Toast.makeText(getContext(), "rellena todos los campos", Toast.LENGTH_LONG).show();

        }
    }

    public void modificar (){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "huerto", null , 4);
        SQLiteDatabase database = admin.getWritableDatabase();

        String codigoSTR = etCodigo.getText().toString();

        String nombre = etNombre.getText().toString();
        String defTax = etDefTax.getText().toString();
        String semilleroIniSTR = etSemillIni.getText().toString();
        String semilleroFinSTR = etSemillFin.getText().toString();
        String siembraIniSTR = etSiembraIni.getText().toString();
        String siembraFinSTR = etSiembraFin.getText().toString();
        String cosechaIniSTR = etCosechaIni.getText().toString();
        String cosechaFinSTR = etCosechaFin.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String beneficiosasSTR = etBeneficiosas.getText().toString();
        String perjudicialesSTR = etPerjudiciales.getText().toString();

        if(!codigoSTR.isEmpty() && !nombre.isEmpty() && !defTax.isEmpty() && !semilleroIniSTR.isEmpty() && !semilleroFinSTR.isEmpty()
                && !siembraIniSTR.isEmpty() && !siembraFinSTR.isEmpty() && !cosechaIniSTR.isEmpty() && !cosechaFinSTR.isEmpty() && !perjudicialesSTR.isEmpty()
                && !beneficiosasSTR.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigoSTR);
            registro.put("nombre", nombre);
            registro.put("defTax", defTax);
            registro.put("semilleroIni", semilleroIniSTR);
            registro.put("semilleroFin", semilleroFinSTR);
            registro.put("siembraIni", siembraIniSTR);
            registro.put("siembraFin", siembraFinSTR);
            registro.put("cosechaIni", cosechaIniSTR);
            registro.put("cosechaFin", cosechaFinSTR);
            registro.put("descripcion", descripcion);
            registro.put("beneficiosas", beneficiosasSTR);
            registro.put("perjudiciales", perjudicialesSTR);

            int numModificados = database.update("vegetales",registro,"codigo = "+codigoSTR,null);
            etCodigo.setText("");
            etNombre.setText("");
            etDefTax.setText("");
            etSemillIni.setText("");
            etSemillFin.setText("");
            etSiembraIni.setText("");
            etSiembraFin.setText("");
            etCosechaIni.setText("");
            etCosechaFin.setText("");
            etDescripcion.setText("");
            etPerjudiciales.setText("");
            etBeneficiosas.setText("");
            if(numModificados >= 1){
                    Toast.makeText(getActivity(), "artículo Modificado", Toast.LENGTH_LONG).show();
            }else{
                    Toast.makeText(getActivity(), "no se ha podido modificar el artículo ", Toast.LENGTH_LONG).show();
            }
        }else{
                Toast.makeText(getActivity(), "Rellena todos los campos", Toast.LENGTH_LONG).show();
        }
     database.close();
    }
    public void buscar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"huerto",null,4);
        SQLiteDatabase database = admin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = database.rawQuery("SELECT * FROM vegetales WHERE codigo="+ codigo,null);

            if(fila.moveToFirst()){
                etCodigo.setText(fila.getString(0));
                etNombre.setText(fila.getString(1));
                etDefTax.setText(fila.getString(2));
                etSemillIni.setText(fila.getString(3));
                etSemillFin.setText(fila.getString(4));
                etSiembraIni.setText(fila.getString(5));
                etSiembraFin.setText(fila.getString(6));
                etCosechaIni.setText(fila.getString(7));
                etCosechaFin.setText(fila.getString(8));
                etDescripcion.setText(fila.getString(9));
                etBeneficiosas.setText(fila.getString(10));
                etPerjudiciales.setText(fila.getString(11));
            }else{
                Toast.makeText(getContext(),"El vegetal no existe",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(), "Escriba el código del vegetal", Toast.LENGTH_LONG).show();
        }
    }
    public void borrar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"huerto",null,4);
        SQLiteDatabase database = admin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();

        if(!codigo.isEmpty()){
            int numBorrados = database.delete("vegetales", "codigo="+codigo, null);

            etCodigo.setText("");
            etNombre.setText("");
            etDefTax.setText("");
            etSemillIni.setText("");
            etSemillFin.setText("");
            etSiembraIni.setText("");
            etSiembraFin.setText("");
            etCosechaIni.setText("");
            etCosechaFin.setText("");
            etDescripcion.setText("");
            etPerjudiciales.setText("");
            etBeneficiosas.setText("");

            if(numBorrados >= 1){
                Toast.makeText(getContext(), "registro borrado", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(), "no se ha podido borrar el registro ", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getContext(), "Introduce un código válido", Toast.LENGTH_LONG).show();
        }

    }
}