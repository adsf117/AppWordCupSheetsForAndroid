package com.example.wordcupsheetsapp;


/**
 * Objeto de tranporte para los datos 
 * @author andres.serrano
 *
 */
public class DataJugadores {
	
	    

		private int Id = 0;
		private String Direccion = "";
		private Boolean estado;
		
		public void setId(int s) { this.Id = s; }
		public int getId() { return Id; }
		
		public void setEstado(Boolean s) { this.estado = s; }
		public Boolean getEstado() { return estado; }
		
		public void setDireccion(String s) { this.Direccion =  s; }
		public String getDireccion() { return this.Direccion; }
		
		
		
		
}
