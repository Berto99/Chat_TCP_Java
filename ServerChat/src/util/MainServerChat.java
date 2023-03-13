package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import data.SalaChat;
import hilos.HiloServidorChat;

public class MainServerChat {

	static final int MAXIMO=10; //MÁXIMO DE CONEXIONES PERMITIDAS
	public MainServerChat() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int PUERTO=4444;
		ServerSocket servidor=null;
		File fichero = new File ("./historyChat.txt");

		try {
			servidor=new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado en puerto "+PUERTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error en el arranque del servidor");
			e.printStackTrace();
		}
		//Array de sockets
		Socket tabla[]= new Socket[MAXIMO];
		//Instanciamos el objeto sala al que le pasamos por parametro el máximo numero de conexiones permitidas,
		//el numero de conexion, el numero de conexiones actuales y el array de sockets
		SalaChat sala=new SalaChat(MAXIMO,0,0,tabla);
		//Minetras que el numero de conexiones que haya sea menor a 10, el servidor acepta nuevas conexiones de los clientes
		while(sala.getConexiones()<MAXIMO) {
			Socket nuevo=null;
			try {
				nuevo=servidor.accept();
				System.out.println("conexión nueva");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//le incluimos en la sala
			sala.addTabla(nuevo, sala.getConexiones());
			//sumamaos +1 al numero de conexiones actuales
			sala.setActuales(sala.getActuales()+1);
			//sumamaon +1 al numero de conexiones
			sala.setConexiones(sala.getConexiones()+1);
		
		//se crea un hilo servidorChat por cada conexión	
		HiloServidorChat hilo=new HiloServidorChat(nuevo,sala,fichero);
		hilo.start();
		}
		
		//Cerramos la conexión
		try {
			servidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
