/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static logica.ConexionServidor.mostrarTexto;

/**
 *
 * @author cvelez
 */
public class HiloCliente extends Thread {

    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    protected Socket socketCliente; //Socket del cliente
    final String COMANDO_TERMINACION = "salir()";

    public HiloCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        while (true) {
            try {
                abrirFlujos();
                recibirDatos();
            } finally {
                cerrarConexion();
            }
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
}
