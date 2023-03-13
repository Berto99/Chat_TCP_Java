package hilos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import data.SalaChat;

public class HiloServidorChat extends Thread {
	// Variables
	Socket socket = null;
	SalaChat sala;
	DataInputStream fentrada;
	DataOutputStream salida;
	File fichero;
	String linea;

	// Constructor con tres parametes, socket, objeto SalaChat y el fichero
	public HiloServidorChat(Socket s, SalaChat salita, File fichero) {
		// TODO Auto-generated constructor stub
		this.socket = s;
		this.sala = salita;
		this.fichero = fichero;
		// Recogemos los datos que pasa el cliente
		try {
			fentrada = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

	}

	public void run() {
		String[] separado = null;
		System.out.println("lanzado hilo");
		// Metodo para escribir el historial del chat a los clientes nuevos que se
		// conecten
		escribirHistorial();
		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				// utilizamos la funcion split para quedarnos solo con el mensaje y no el nombre
				// del usuario
				separado = cadena.split(">");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// si el mensaje del cliente, equivale a *, salimos del while
			if (separado[1].trim().equals("*")) {
				break;// salir
			} else {
				escribirFichero(cadena);
				escribirHilos(sala, cadena);
			}
		}

		try {
			System.out.println("cerramos socket");
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Conexion cerrada");
		}

	}

	public synchronized void escribirFichero(String cadena) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true));
			bw.write(cadena + "\n");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void escribirHilos(SalaChat sala, String cadena) {
		// Este for lo recorremos tantas veces como clientes haya conectados
		for (int i = 0; i < sala.getActuales(); i++) {
			try {
				// Para poder seleccionar los sockets de cada cliente, la clase Salachat tienen
				// un metodo public Socket getElementTabla(int i)
				// en el que nos devuelve un socket con la posición que elijamos.
				salida = new DataOutputStream(sala.getElementTabla(i).getOutputStream());
				salida.writeUTF(cadena);
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
	}

	public void escribirHistorial() {
		System.out.println("Mandando historial del chat");
		try {
			//Recogemos el numero de conexiones actuales -1 porque el array de sockets empieza por 0
			int numConexionActual = sala.getActuales() - 1;
			BufferedReader lectura = new BufferedReader(new FileReader(fichero));
			//Un DataOutputStream al que le pasamos el socket del cliente que acaba de conectarse
			salida = new DataOutputStream(sala.getElementTabla(numConexionActual).getOutputStream());
			//Va leyendo del fichero, y escribe los datos al cliente
			while ((linea = lectura.readLine()) != null) {
				salida.writeUTF(linea);
			}
			lectura.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("No hay historial");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Final fichero");
		}
	}

}
