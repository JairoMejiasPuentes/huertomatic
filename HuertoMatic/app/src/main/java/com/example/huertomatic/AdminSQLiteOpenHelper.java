 package com.example.huertomatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private final static String db_path = "/data/data/com.example.huertomatic/databases/";
    private final static String db_name = "huerto.db";
    private SQLiteDatabase baseDeDatos;

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * método que crea una base de datos si no existe
     * @throws IOException
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.d("error", "createDataBase error : " + e );
                throw new Error("Error copiando database");
            }
        }
    }

    /**
     * método que comprueba si existe una bbdd en nuestra aplicacion.
     * @return
     */
    public boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String path = db_path + db_name;
            checkDB = SQLiteDatabase.openDatabase( path, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException ex){

        }
        if (checkDB != null) {
            checkDB.close();
            return true;
        }else{
            return false;
        }
    }

    public void openDataBase(){
        String path = db_path + db_name;
        baseDeDatos = SQLiteDatabase.openDatabase( path, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close(){
        if(baseDeDatos != null){
            baseDeDatos.close();
        }
        super.close();
    }

    /**
     * método que copia la base de datos desde la carpeta de assets
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
//  Abrimos la base de datos

        String packageName = getClass().getPackage().getName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        Log.i("ruta", "la ruta es: "+DB_PATH);
//  Creamos el directorio si no existe

        File directory = new File(DB_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String DB_NAME = "huerto.db";
        InputStream myInput = context.getAssets().open("huerto.db");

//  En la ruta que le pasamos creamos una base de datos vacía

        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

//  Rellenamos la base de datos con los datos vacía con los datos de la nuestra
        byte[] buffer = new byte[1024];
        int length;
        int x=0;
        while ((length = myInput.read(buffer)) > 0) {
            x++;
            myOutput.write(buffer, 0 , length);
        }
        System.out.println( "el valor de x es igual: " + x );

//  Cerramos el flujo.
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

}
