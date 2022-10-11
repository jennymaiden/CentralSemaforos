/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.CicloSemaforico;
import logica.ConexionServidor;
import org.json.simple.JSONArray;

/**
 *
 * @author cvelez
 */
public class Modelo {

    private ConexionServidor servidor;
    private VistaServidor ventanaSimulacion;
    private PrintStream standardOut;
    private CicloSemaforico ciclo;
    public Timer tiempo = new Timer();

    public VistaServidor getVistaSimulacion() {

        if (ventanaSimulacion == null) {
            ventanaSimulacion = new VistaServidor(this);
        }
        return ventanaSimulacion;
    }

    public void iniciarServidor() {
        if (servidor == null) {
            servidor = new ConexionServidor();
        }
        if (ciclo == null) {
            ciclo = new CicloSemaforico(servidor);
        }

        servidor.ejecutarConexion(5050);
    }

    public void iniciarVista() {
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
        TimerTask timerTask = new TimerTask() {

            int segundo = 0;

            public void run() {
                // Aquí el código que queremos ejecutar.
                //System.out.println("segundo "+segundo);
                switch (segundo) {
                    case 0:
                        //Enviar primer grupo de conexion
                        int[][] matrizConexion = ciclo.getLeerArchivo("PlanConexion.txt");

                        ciclo.getControlTiempoConexion(matrizConexion);
                        break;
                    case 14:
                        //Enviar cambio
                        int[][] matrizCiclo = ciclo.getLeerArchivo("PlanCiclo.txt");
                        ciclo.getControlTiempoSemaforo(matrizCiclo);
                        break;

                }
                segundo++;
            }
        };

        //Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        tiempo.scheduleAtFixedRate(timerTask, 0, 1000);
        //try {
        // Leer los datos de conexion
//            int[][] matrizConexion = ciclo.getLeerArchivo("PlanConexion.txt");
//            
//            ciclo.getControlTiempoConexion(matrizConexion);
//            ciclo.getTiempoConexion().cancel();
//            int[][] matrizCiclo = ciclo.getLeerArchivo("PlanCiclo.txt");
//            
//            //Thread.sleep(14 * 1000);
//            
//            ciclo.getControlTiempoSemaforo(matrizCiclo);

//        } catch (InterruptedException ex) {
//            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void detenerSecuencia() {
        //Detener los otros tiempos
        tiempo.cancel();
        ciclo.getTiempoConexion().cancel();
        ciclo.getTiempoCiclo().cancel();
        // Leer los datos de conexion
        int[][] matrizDesconexion = ciclo.getLeerArchivo("PlanDesconexion.txt");
        ciclo.getControlTiempoDesconexion(matrizDesconexion);
    }

}
