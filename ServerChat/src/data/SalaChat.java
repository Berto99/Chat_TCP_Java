package data;

import java.net.Socket;

public class SalaChat {
	//Variables
	private int conexiones;
	private int actuales;
	private int MAXIMO;
	private Socket tabla[]=new Socket[MAXIMO];
	private String mensajes;
	
	//Constructor
	public SalaChat(int mAXIMO,int conexiones, int actuales,Socket[] tabla) {
		
		this.setConexiones(conexiones);
		this.setActuales(actuales);
		MAXIMO = mAXIMO;
		this.tabla = tabla;
		this.mensajes="";
		
	}

	public int getConexiones() {
		return conexiones;
	}

	public void setConexiones(int conexiones) {
		this.conexiones = conexiones;
	}

	public int getActuales() {
		return actuales;
	}

	public void setActuales(int actuales) {
		this.actuales = actuales;
	}
	public int getMAXIMO() {
		return MAXIMO;
	}

	public void setMAXIMO(int mAXIMO) {
		MAXIMO = mAXIMO;
	}
	public String getMensajes() {
		return mensajes;
	}

	public void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}
	public void addTabla (Socket s,int i) {
		tabla[i]=s;
	}
	public Socket getElementTabla(int i) {
		return tabla[i];
	}

	
}
