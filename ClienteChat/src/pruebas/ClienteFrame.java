package pruebas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.MainCliente;

public class ClienteFrame extends JFrame implements ActionListener{
	//Variables
	String nombre;
	public JTextField mensaje = new JTextField();
	JScrollPane scrollpanel;
	public JTextArea textarea;
	JButton botonEnviar= new JButton("enviar");
	JButton botonSalir= new JButton("salir");
	
	//Constructor
	public ClienteFrame(String nombre) {
		// TODO Auto-generated constructor stub
		super("Conexión con chat:"+nombre);
		this.nombre=nombre;
		setSize(530,420);
		setLayout(null);
		mensaje.setBounds(10,10,400,30);
		this.add(mensaje);
		textarea=new JTextArea();
		//barra para subir y bajar en el textarea
		scrollpanel=new JScrollPane(textarea);
		scrollpanel.setBounds(10,50,400,300);
		botonEnviar.setBounds(420,10,100,30);
		botonEnviar.addActionListener(this);
		this.add(botonEnviar);
		botonSalir.setBounds(420,50,100,30);
		botonSalir.addActionListener(this);
		this.add(botonSalir);
		textarea.setBounds(0,0,400,300);
		textarea.setEditable(false);
		this.add(scrollpanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Cuando pulse el boton de enviar, realiza el metodo
		if (e.getSource()==botonEnviar) {
			MainCliente.escribirCliente(nombre,this);
			mensaje .setText("");
		}
		//Cuando pulse el boton de salir, realiza el metodo
		if (e.getSource()==botonSalir) {
			MainCliente.cerrarConexion(nombre);
			
		}
	}

}
