package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

import pruebas.ClienteFrame;

public class MainCliente {

	//Variable public static para que la puedan ver otras clases que no esten en el mismo paquete
	public static DataOutputStream salida=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puerto = 4444;
		Socket s = null;
		File nombreClientes = new File("./nombre_clientes.txt");
		boolean comprobacion;
		try {
			s = new Socket("localhost", puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Pedimos el nombre al cliente
		String nombre = JOptionPane.showInputDialog("introduce tu nombre");
		JOptionPane.showMessageDialog(null, "uno", "nombre", JOptionPane.ERROR_MESSAGE, null);
		//Llamamos al metodo comprobar nombre que devuelve un boolean
		comprobacion = comprobarNombre(nombre, nombreClientes);
		//Mientras que comprobar sea false, seguira pidiendo el nombre al usuario
		while (!comprobacion) {
			JOptionPane.showMessageDialog(null, "introduce otra vez el nombre", "nombre", JOptionPane.ERROR_MESSAGE,null);
			nombre = JOptionPane.showInputDialog("introduce tu nombre");
			JOptionPane.showMessageDialog(null, "uno", "nombre", JOptionPane.ERROR_MESSAGE, null);
			comprobacion = comprobarNombre(nombre, nombreClientes);
		}
		//Instanciamos un objeto de la clase ClienteFrame
		ClienteFrame clf = new ClienteFrame(nombre);
		//Ponemos la ventana en visible
		clf.setVisible(true);
		
		//DataOutputStream al que le pasamos el socket del cliente
		try {
			salida = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Creamos un hilo para estar leyendo continuamente los mensajes que mandan los
		// clientes que esten conectados
		HiloLecturaCliente hiloLectura = new HiloLecturaCliente(s, clf);
		hiloLectura.start();

	}
	
	//Método para mandar al server el mensaje que escribe el cliente
	public static void escribirCliente(String nombre,ClienteFrame clf) {
		String cadena="";
				cadena=clf.mensaje.getText();
				try {
					salida.writeUTF(nombre+">"+cadena);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	//Método para cerra el el DataOutputStream
	public static void cerrarConexion(String nombre) {
		try {
			salida.writeUTF(nombre+">"+"*");
			salida.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Metodo  para comprbar que el nombre de ususario no se repita y lo guarda en un fichero 
	public static boolean comprobarNombre(String nombre,File fichero){
		boolean comprobar = true;
		try {
			BufferedReader lectura = new BufferedReader(new FileReader(fichero));
			BufferedWriter escritura;
			String linea;
			
			if (nombre.equals(null)) {
				comprobar=false;
			}
			while((linea=lectura.readLine())!=null) {
				if (nombre.equals(linea)) {
					System.out.println("Ese usuario ya existe");
					comprobar=false;
				}
			}
			lectura.close();
			if (comprobar==true) {
				escritura=new BufferedWriter(new FileWriter(fichero,true));
				escritura.write(nombre+"\n");
				escritura.close();
				comprobar= true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comprobar;
		
	}

}
