package com.example.wordcupsheetsapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import com.example.wordcupsheetsapp.R.drawable;
import com.example.wordcupsheetsapp.R.id;



import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * realiza la implementaciones de las extensiones sobre BaseAdapter requeridas
 * para el despliegue de los mensajes de chat de pasajero
 * 
 * @author andres.serrano
 * 
 */
public class Adapter_GridView extends BaseAdapter {

	// listado de datos a desplegar
	private ArrayList<DataJugadores> ListaDatosEstadoJugadores;
	// objeto que gestionar� la vista contra el framework
	private LayoutInflater inflater;
	private Context context1;
	ArrayList<Integer> arl;
	
	dbEstado db;

	
	int angulo=90;
    
	
	/**
	 * constructor parametrizado
	 * 
	 * @param context
	 *            contexto asociado al layout que gestionar� el adapter
	 * @param results
	 *            datos a desplegar
	 */
	/**
	 * retorna una referencia al listado de datos a desplegar
	 * 
	 * @return
	 */
	public ArrayList<DataJugadores> getListdataDirecciones() {
		return this.ListaDatosEstadoJugadores;
	}

	public Adapter_GridView(Context context) {
		context1 = context;
		db = new dbEstado(context1);
		arl = new ArrayList<Integer>();
		 
		arl.add(drawable.foto1);
		arl.add(drawable.foto2);
		arl.add(drawable.foto3);
		arl.add(drawable.foto4);
		arl.add(drawable.foto5);
		arl.add(drawable.foto6);
		arl.add(drawable.foto7);
		arl.add(drawable.foto8);
		arl.add(drawable.foto9);
		arl.add(drawable.foto10);
		arl.add(drawable.foto11);
		arl.add(drawable.foto12);
		arl.add(drawable.foto13);
		arl.add(drawable.foto14);
		arl.add(drawable.foto15);
		arl.add(drawable.foto16);
		arl.add(drawable.foto17);
		arl.add(drawable.captura6);
		arl.add(drawable.captura7);
		arl.add(drawable.captura8);
		arl.add(drawable.captura9);
		arl.add(drawable.captura10);
		arl.add(drawable.captura11);
		arl.add(drawable.captura12);
		arl.add(drawable.captura13);
		arl.add(drawable.captura14);
		arl.add(drawable.captura15);
		arl.add(drawable.captura16);
		arl.add(drawable.captura17);
		arl.add(drawable.captura18);
		arl.add(drawable.captura19);
		arl.add(drawable.captura20);
		arl.add(drawable.captura21);
		arl.add(drawable.captura22);
		arl.add(drawable.captura23);
		arl.add(drawable.captura24);
		arl.add(drawable.captura25);
		arl.add(drawable.captura26);
		arl.add(drawable.captura27);
		arl.add(drawable.captura28);
		arl.add(drawable.captura29);
		arl.add(drawable.captura30);
		arl.add(drawable.captura31);
		arl.add(drawable.captura32);
		arl.add(drawable.captura33);
		arl.add(drawable.captura34);
		arl.add(drawable.captura35);
		arl.add(drawable.captura36);
		arl.add(drawable.captura37);
		
		
		
		
		
		
		
		this.ListaDatosEstadoJugadores = db.getAll();
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this.ListaDatosEstadoJugadores.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.ListaDatosEstadoJugadores.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// declarar la vista plantilla
		PlantillaVista vista;

		if (convertView == null) {
			// cear una vista con base en el layout de info servicio por fila
			convertView = this.inflater.inflate(R.layout.temgridview, null);

			// configurar la vista de plantilla a partir de la vista creada
			vista = new PlantillaVista();
			vista.lblDireccion = (TextView) convertView
					.findViewById(R.id.lblnombres);
			
			vista.foto = (ImageView)convertView.findViewById(id.foto);
			vista.Estado = (ImageView)convertView.findViewById(id.ivestado);
			
			// almacenar una referencia a la vista de plantilla en la vista
			// creada
			convertView.setTag(vista);
		} else {
			// intanciar la vista de plantilla usando cast sobre la vista a
			// transformar
			vista = (PlantillaVista) convertView.getTag();
		}

		// cargar los valores a desplegar en la vista de plantilla
		//vista.lblDireccion.setText(String.valueOf(this.ListaDatosEstadoJugadores.get(position)
			//	.getEstado()));
		ListaDatosEstadoJugadores = db.getAll();
		
		if (!this.ListaDatosEstadoJugadores.get(position).getDireccion().equals("null")) {
			FileCache fileCache=new FileCache(context1);
			File f=fileCache.getFile(this.ListaDatosEstadoJugadores.get(position).getDireccion());
			 
	        //from SD cache
			
			
	        Bitmap b = decodeFile(f);
	        
	        vista.foto.setImageBitmap(b);
            Matrix matrix=new Matrix();
            vista.foto.setScaleType(ScaleType.MATRIX); 
           
            matrix.postRotate((float) angulo,95 , 105);
            vista.foto.setImageMatrix(matrix);
          
	       
	        if(this.ListaDatosEstadoJugadores.get(position).getEstado())
			{
				
				vista.Estado.setImageResource(drawable.check);
				vista.lblDireccion.setText("Agregada");
			}
			
			else
			{
				
				vista.Estado.setImageResource(drawable.uncheck);
				vista.lblDireccion.setText("Pendiente");
			}
		}
		
		else if(this.ListaDatosEstadoJugadores.get(position).getEstado())
			{
				vista.foto.setImageResource(arl.get(position));
				vista.Estado.setImageResource(drawable.check);
				vista.lblDireccion.setText("Agregada");
			}
			
			else
			{
				vista.foto.setImageResource(arl.get(position));
				vista.Estado.setImageResource(drawable.uncheck);
				vista.lblDireccion.setText("Pendiente");
			}
		
		
		
		
		return convertView;
	}

	// clase que se usa internamente para gestionar
	// los controles que desplegar�n un objeto de datos
	static class PlantillaVista {

		TextView lblDireccion;
		ImageView foto;
		ImageView Estado;
		

	}


	//decodes image and scales it to reduce memory consumption
    public Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
 
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=105;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
 
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } 
        
        catch (FileNotFoundException e) {}
        return null;
    }
	


}
