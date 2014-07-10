package com.example.wordcupsheetsapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.ls.LSInput;

import com.example.wordcupsheetsapp.R.drawable;



import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * 
 * @author AndresDavidSerrano
 *
 */
public class FragmentGridView extends Fragment {
	
	View view;
	View viewGridView;
	Context context;
	Dialog alertDialog = null;
	GridView Gv;
	
	dbEstado taskdb;
	ArrayList<DataJugadores> ListaDatosEstadoJugadores;
	DataJugadores is;
	
	Camera mCamera;
    CameraPreview mPreview;
    static final int MEDIA_TYPE_IMAGE = 1;
    /** setting last image taken **/
    static ImageView myImage;
    Bitmap bmapcamara;
    static File imgFile = null;
    static String NameFile;
    int angulo=0;
    int px=250;
    int py=250;
    
    FrameLayout preview;
    ImageButton btncancelar ;
    
	ImageButton btncamara;
	ImageButton btnguardar; 
	Button btnestado;
	Boolean Estadobtn;
	
	
	ImageView IvEstadoGV ;
	ImageView IVFotoGV ;
	TextView lblEstadoGV;
	
    int positionsave;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view=inflater.inflate(R.layout.testgridview, container, false);
		context=container.getContext();
		alertDialog = new Dialog(context);
		Gv=(GridView)view.findViewById(R.id.gvdata);
		taskdb = new dbEstado(context);
		CargarListView();
		CargarConfiguracionCamara();
		ValidadOrientacion(getActivity().getResources().getConfiguration());
	    return view;
		}
/**
 * Permite validar si la base de datos ya fue creada
 *
 */
public void CargarListView() {
		
		 ListaDatosEstadoJugadores = new ArrayList<DataJugadores>();
		
		ListaDatosEstadoJugadores=taskdb.getAll();
		if(ListaDatosEstadoJugadores.size()==0)
		{
			taskdb.PoblarDatosdefaul();
			ListaDatosEstadoJugadores=taskdb.getAll();
			CargarConfiguracion();
		}
		else
		{
			CargarConfiguracion();
		}
		
		
	}
/**
 * 
 */
public void CargarConfiguracion() 
{
	Adapter_GridView adapter = new Adapter_GridView(context);
	Gv.setAdapter(adapter);
	Gv.setOnItemClickListener(new OnItemClickListener() {

		   @Override
		   public void onItemClick(AdapterView<?> arg0, View v, int position,
		     long arg3) {
			   viewGridView=v;
			   
			   RelativeLayout RLay= (RelativeLayout)viewGridView;
			   IvEstadoGV = (ImageView)RLay.getChildAt(1);
			   IVFotoGV = (ImageView)RLay.getChildAt(0);
			   lblEstadoGV = (TextView) RLay.getChildAt(2);
			   
			   abirpoup(position);
		       
		   }
		  });
	
}

/**
 * Permite cambiar la imagenes y texto del item de la lista seleccionado 
 */

public void Displayconfigestado() {
	
	ListaDatosEstadoJugadores=taskdb.getAll();
	Estadobtn= ListaDatosEstadoJugadores.get(positionsave).getEstado();
	
	
	if(Estadobtn)
	{
		btnestado.setText("Quitar");
		
	}
	else
	{
		btnestado.setText("Agregar");
	}
	
	btnestado.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(Estadobtn)
			{
				Estadobtn=false;
				IvEstadoGV.setImageResource(drawable.uncheck);
				lblEstadoGV.setText("Pendiente");
				taskdb.updEstado(String.valueOf(is.getId()), "false",is.getDireccion());
			}
			else
			{
				Estadobtn=true;
				IvEstadoGV.setImageResource(drawable.check);
				lblEstadoGV.setText("Agregada");
				taskdb.updEstado(String.valueOf(is.getId()), "true",is.getDireccion());
			}
			
			
			alertDialog.dismiss();
		}
	});
    
}

/**
 * Permite abirr el popup de la camara y los botones de inteccion de la aplicacion
 * @param position
 */

public void abirpoup(int position) {
	positionsave=position;
	Object o = Gv.getItemAtPosition(position);
	is = (DataJugadores)o;
	Boolean Estado=Boolean.valueOf(is.getEstado());
	alertDialog.setCancelable(true);
	alertDialog.setTitle("Editar Lamina");	
	alertDialog.setContentView(R.layout.popupformulario);
	
	btnestado = (Button)alertDialog.findViewById(R.id.btnestado);
	btnestado.setVisibility(View.VISIBLE);
	btncamara = (ImageButton)alertDialog.findViewById(R.id.btnabircamara);
	btncancelar =	(ImageButton)alertDialog.findViewById(R.id.btncerrarcamara);
	btnguardar=(ImageButton)alertDialog.findViewById(R.id.btnguardarfoto);
	preview = (FrameLayout) alertDialog.findViewById(R.id.camera_preview);
    myImage = (ImageView)alertDialog.findViewById(R.id.myImage);
    btnguardar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stubt
			taskdb.updDireccion(is.getId(), NameFile,String.valueOf(is.getEstado()));
			IVFotoGV.setImageBitmap(bmapcamara);
			Matrix matrix=new Matrix();
			IVFotoGV.setScaleType(ScaleType.MATRIX);   //required
	           // matrix.postRotate( angulo);
	        matrix.postRotate((float) angulo,95 , 105);
	        IVFotoGV.setImageMatrix(matrix);
		    alertDialog.dismiss();	
		}
	});
	btncamara.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnestado.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				preview.setVisibility(View.VISIBLE);
				btncancelar.setVisibility(View.VISIBLE);
			    myImage.setVisibility(View.GONE);
			    if(mCamera==null)
				{
				
				mCamera = getCameraInstance();
		        // Create our Preview view and set it as the content of our activity.
		        mPreview = new CameraPreview(context, mCamera);
		        mCamera.setDisplayOrientation(angulo);
				}
			    else
			    {
			    	mCamera.setDisplayOrientation(angulo);
			    }
			    preview.addView(mPreview);
		       
				alertDialog.setCancelable(false);
				
			}
		});
	
	
	btncancelar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			preview.setVisibility(View.GONE);
			btncancelar.setVisibility(View.GONE);
			if(mCamera!=null)
			{
			  mCamera.stopPreview();
			  preview.removeView(mPreview);
			}
			alertDialog.setCancelable(true);
			
		}
	});	
	preview.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			preview.setVisibility(View.GONE);
           mPreview.setVisibility(View.GONE);
           myImage.setVisibility(View.VISIBLE);
           mCamera.takePicture(null, null, mPicture);
           btnguardar.setVisibility(View.VISIBLE);
           alertDialog.setCancelable(true);
		}
	});
	if(Estadobtn==null)
	{
		Estadobtn=Estado;
	}
	
    Displayconfigestado();
	alertDialog.show();
}

/**
 * Permite validar que el dispositovo tenga camara y que esta se encuatra disponible
 */

public void CargarConfiguracionCamara() {
	if(mCamera==null)
	{
	
	mCamera = getCameraInstance();
    // Create our Preview view and set it as the content of our activity.
    mPreview = new CameraPreview(context, mCamera);
    mCamera.setDisplayOrientation(angulo);
	}
	else
	{
		Toast.makeText(getActivity(), "La camara esta siendo usado por otra app porfavor cierrra esta aplicacion", Toast.LENGTH_SHORT).show();
	}
	
}
/**
 * Permite captura la imagen obtneida por la camara y almacenarla en la sd
 * 
 *@author  Tomado de Google Developer y editado por Andres David Serrano
 */
public  PictureCallback mPicture = new PictureCallback() {

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

        if (pictureFile == null){
            Log.d("ERROR", "Error creating media file, check storage permissions:" );
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d("ERROR", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("ERROR", "Error accessing file: " + e.getMessage());
        }
        setImage();
    }
};
/** Permite guardar la foto tomada 
 * Esta foto queda almacenada en el celular en la carpeta  Fotos Monitoreo
 * 
 * @author  Tomado de Google Developer y editado por Andres David Serrano
 *  */
private static File getOutputMediaFile(int type){ 
Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
if(!isSDPresent)
{	
		Log.d("ERROR", "Card not mounted");
}
File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath() + "/FotosApp");

if (! mediaStorageDir.exists()){
    if (! mediaStorageDir.mkdirs()){

        Log.d("MyCameraApp", "failed to create directory");
        return null;
    }
}

// Create a media file name
String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
File mediaFile;
if (type == MEDIA_TYPE_IMAGE){
	
	NameFile = "IMG_"+ timeStamp + ".jpg";
    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
    "IMG_"+ timeStamp + ".jpg");
    imgFile = mediaFile;
} else {
    return null;
}

return mediaFile;
}
/**
 * Permite cargar la imagen tomada desde la foto dentro de un image view
 * 
 * 
 * @author  Tomado de Google Developer y editado por Andres David Serrano
 */
public  void setImage(){
    if(imgFile !=null){
        if(imgFile.exists()){
        	bmapcamara  = decodeSampleImage(imgFile, px, py); // prevents memory out of memory exception
            myImage.setImageBitmap(bmapcamara);
            Matrix matrix=new Matrix();
            myImage.setScaleType(ScaleType.MATRIX);   //required
            matrix.postRotate((float) angulo, px, py);
            myImage.setImageMatrix(matrix);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
            btncancelar.setVisibility(View.GONE);
         
        }
    }
}


/**
 * Permite decodificar las imagenes tomadadas por la camaara 
 * @param f
 * @param width
 * @param height
 * @return el mapa de bit de la imagen para poder ser mostrada en un control imageview
 * @author tomado desde internet
 */

 public static Bitmap decodeSampleImage(File f, int width, int height) { 	    
        try { 	        
             System.gc(); // First of all free some memory 	        // Decode image size 	        
             BitmapFactory.Options o = new BitmapFactory.Options(); 	        
              o.inJustDecodeBounds = true; 	        
              BitmapFactory.decodeStream(new FileInputStream(f), null, o); 	        // The new size we want to scale to 	                            final int requiredWidth = width; 	        
final int requiredHeight = height; 	        // Find the scale value (as a power of 2) 	        
int sampleScaleSize = 10; 	        
int requiredWidth=100;
while (o.outWidth / sampleScaleSize / 2 >= requiredWidth && o.outHeight / sampleScaleSize / 2 >= requiredHeight)
	            sampleScaleSize *= 2;

	        // Decode with inSampleSize

	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = sampleScaleSize;

	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (Exception e) {
	      //  Log.d(TAG, e.getMessage()); // We don't want the application to just throw an exception
	    }

	    return null;
	}


/** A safe way to get an instance of the Camera object. */
public static Camera getCameraInstance(){
    Camera c = null;
    try {
        c = Camera.open(); // attempt to get a Camera instance
       
    }
    catch (Exception e){
        // Camera is not available (in use or does not exist)
    }
    return c; // returns null if camera is unavailable
}
@Override
public void onConfigurationChanged(Configuration newConfig) {
	// TODO Auto-generated method stub
	super.onConfigurationChanged(newConfig);
	 // Checks the orientation of the screen
	
	CargarListView();
	ValidadOrientacion(newConfig);
	
	
}
/**
 * Permite obtener la posicion  de la pantalla con el fin de determinar el angulo para mostar la camara
 * 
 */
 public void ValidadOrientacion (Configuration newConfig) {
	 // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	angulo=180;
	        if(mCamera!=null)
	        {
	        	
	           mCamera.setDisplayOrientation(angulo);
	        }
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	angulo=90;
	    	if(mCamera!=null)
	        {
	    		
	            mCamera.setDisplayOrientation(angulo);
	        }
	    	
	        
	    }
	 
}
}
