package com.example.wordcupsheetsapp;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;




import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
/**
 * Permite controlar la conexion con la base de datos 
 * permite 
 * Generar datos por defecto
 * actualizar el estado de las fotos
 * actualizar la direccion en memoria de la imagen
 * 
 * @author andres.serrano
 *
 */
public class dbEstado extends SQLiteOpenHelper {


	Context contexto;
	public static final String DB_NAME = "TF";
	public static final String TABLE_NAME = "Imagenes";
	
	public static final String COL_Estado = "estado";
	public static final String COL_Direccion = "nombreimagen";
	
	
	public dbEstado(Context context) {
		
		super(context, DB_NAME, null, 1);
		contexto=context;
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE "+TABLE_NAME+" (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "estado TEXT,nombreimagen TEXT);";
		db.execSQL(query);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_Pasada,
			int version_Nueva) {
		String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(query);
		onCreate(db);

	}

	/**
	 *  permite crear un id que sera el manejado por la aplicacion
	 *  segenerara los campos como nulos para que estos luego sean actualizados
	 * 
	 * @return rta del querry 
	 */

	public String PoblarDatosdefaul() {

		try {

			ContentValues datos = new ContentValues();
			for (int i = 0; i < 47; i++) {
				
				if(i==2)
				{
					datos.put(COL_Estado, "true");	
				}
				else
				{
					datos.put(COL_Estado, "False");
				}
				
				datos.put(COL_Direccion, "null");
				this.getWritableDatabase().insert(TABLE_NAME, null, datos);
			}
			
			
			return "True";
		} 
		catch (Exception e) {
			return e.getMessage();
		}

	}

	
	

	
	
	/**
	 * Actualiza el estado y direccion de la imagen
	 * @param id
	 * @param estado
	 * @param Direccion
	 * @return
	 */
	public String updEstado(String id,String estado, String Direccion) {
		try {
			ContentValues datos = new ContentValues();
			datos.put(COL_Estado,estado );
			datos.put(COL_Direccion,Direccion);
			String[] args = {String.valueOf(id)};
			this.getWritableDatabase().update(TABLE_NAME, datos,
					_ID + "=?", args);
			
			return "True";

		} 
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public String updDireccion(int id,String Direccion,String estado) {
		try {
			ContentValues datos = new ContentValues();
			datos.put(COL_Direccion,Direccion);
			datos.put(COL_Estado,estado );
			String[] args = {String.valueOf(id)};
			this.getWritableDatabase().update(TABLE_NAME, datos,
					_ID + "=?", args);
			
			return "True";

		} 
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	
	/**
	 * Permite obtener todos los datos existentes dentro de la base de datos
	 * @return DataJugadores
	 */
	public ArrayList<DataJugadores> getAll() {
		ArrayList<DataJugadores> tags = new ArrayList<DataJugadores>();
	    String selectQuery = "SELECT  * FROM " + TABLE_NAME;
	 
	    Log.e("error", selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	DataJugadores t = new DataJugadores();
	            t.setId(c.getInt((c.getColumnIndex(_ID))));
	            t.setDireccion(c.getString(c.getColumnIndex(COL_Direccion)));
	            t.setEstado(Boolean.valueOf(c.getString(c.getColumnIndex(COL_Estado))));
	            
	            // adding to tags list
	            tags.add(t);
	        } while (c.moveToNext());
	    }
	    return tags;
	}

}


