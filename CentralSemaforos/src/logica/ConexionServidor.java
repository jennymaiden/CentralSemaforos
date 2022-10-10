/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cvelez
 */
public class ConexionServidor {

    public String host; //IP del servidor
    public int puerto;
    protected ServerSocket socketServidor; //Socket del servidor
    protected Socket socketCliente; //Socket del cliente
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    Scanner escaner = new Scanner(System.in);
    final String COMANDO_TERMINACION = "salir()";
    
    public void levantarConexion(int puerto) {
        try {
            socketServidor = new ServerSocket(puerto);
            mostrarTexto("Esperando conexion entrante en el puerto " + String.valueOf(puerto) + "...");
            socketCliente = socketServidor.accept();
            mostrarTexto("Conexion establecida con: " + socketCliente.getInetAddress().getHostName() + "\n\n\n");
        } catch (Exception e) {
            mostrarTexto("Error en levantarConexion(): " + e.getMessage());
            System.exit(0);
        }
    }

    public void abrirFlujos() {
        try {
            bufferDeEntrada = new DataInputStream(socketCliente.getInputStream());
            bufferDeSalida = new DataOutputStream(socketCliente.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en la apertura de flujos");
        }
    }

    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferDeEntrada.readUTF();
                mostrarTexto("\n[Cliente] => " + st);
                //System.out.print("\n[Usted] => ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
      
    }
    
    public void enviar(String s) {
        try {
            bufferDeSalida.writeUTF(s);
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("IOException on enviar");

        }
    }

    public void cerrarConexion() {
        try {
            bufferDeEntrada.close();
            bufferDeSalida.close();
            socketCliente.close();
        } catch (IOException e) {
            mostrarTexto("Excepción en cerrarConexion(): " + e.getMessage());
        } finally {
            mostrarTexto("Conversación finalizada....");
            System.exit(0);

        }
    }
    
    public void ejecutarConexion(int puerto) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        levantarConexion(puerto);
                        abrirFlujos();
                        recibirDatos();
                    } finally {
                        cerrarConexion();
                    }
                }
            }
        });
        hilo.start();
    }

//    public void escribirDatos() {
//        while (true) {
//            System.out.print("[Usted] => ");
//            enviar(escaner.nextLine());
//        }
//    }
    
    public static void mostrarTexto(String s) {
        System.out.println(s);
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

}
