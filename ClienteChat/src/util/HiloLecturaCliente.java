package util;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

import pruebas.ClienteFrame;


public class HiloLecturaCliente extends Thread{
	//Variables
	DataInputStream entrada;
	Socket socket;
	String resultado;
	File fichero;
	String linea;
	ClienteFrame clf;
	
	//Constructor
	public HiloLecturaCliente(Socket socket,ClienteFrame clf) {
		this.socket = socket;
		this.clf=clf;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				//Lee los datos que le manda el server
				entrada=new DataInputStream(socket.getInputStream());
				resultado=entrada.readUTF();
				//Imprime los datos en el textarea del cliente
				clf.textarea.setText(clf.textarea.getText() + resultado + "\n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Conexion finalizada");
				//Cerramos el socket
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Salimos del sistema
				System.exit(0);
			}
		}
		
	}
	

}
