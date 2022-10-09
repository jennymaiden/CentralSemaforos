/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.io.PrintStream;
import logica.ConexionServidor;

/**
 *
 * @author cvelez
 */
public class Modelo {
    
    private ConexionServidor servidor;
    private VistaServidor ventanaSimulacion;
    private PrintStream standardOut;    
    
    public VistaServidor getVistaSimulacion() {

        if (ventanaSimulacion == null) {
            ventanaSimulacion = new VistaServidor(this);
        }
        return ventanaSimulacion;
    }
    
    public void iniciarServidor(){
        if (servidor == null) {
            servidor = new ConexionServidor();
        }
        servidor.ejecutarConexion(5050);
    }
    
    public void iniciarVista(){
        getVistaSimulacion().setSize(800, 600);
        getVistaSimulacion().setVisible(true);
        getVistaSimulacion().setLocationRelativeTo(null);
        getVistaSimulacion().getTxa_consola().setSize(70, 529);
        PrintStream printStream = new PrintStream(new CustomOutputStream(getVistaSimulacion().getTxa_consola()));
        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);
    }
    
    public void enviarMsg(String s) {
        System.out.print("[Servidor] => ");
        servidor.enviar(s);
    }
    
    public void iniciarSecuencia() {
        // Leer los datos 
        enviarMsg("los datos son");
        //Respuesta del cliente
        
        
    }
    
//    public void imprimirMensaje(String msg){
//        listTexto.add(msg);
//        // Imprimir arreglo
//        for (String texto : listTexto) {
//            getVistaSimulacion().getTxa_consola().setLineWrap(true);
//            getVistaSimulacion().getTxa_consola().setText(texto);
//        }
//        
//        
//    }
    
}
