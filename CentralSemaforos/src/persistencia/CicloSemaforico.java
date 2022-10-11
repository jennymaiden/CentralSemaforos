/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import logica.ConexionServidor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cvelez
 */
public class CicloSemaforico {
    
    private final ConexionServidor servidor;

    private final String path = "/Users/cvelez/Documents/Personal/Especializacion/Informatica 1/gitSemaforo/servidores/CentralSemaforos/src/persistencia/";
    
    public Timer tiempoConexion = new Timer();
    public Timer tiempoCiclo = new Timer();
    public Timer tiempoDesconexion = new Timer();

    public CicloSemaforico(ConexionServidor servidor) {
        this.servidor = servidor;
    }

    
    // Este metodo lee el archivo de plan de conexion y vuelve la informacion en una matriz de 4 x 16
    public int[][] getLeerArchivo(String nombreArchivo) {
        int[][] matriz;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + nombreArchivo));
            //Primera linea nos dice longitud de la matriz
            String linea = br.readLine();
            //System.out.println("linea : " + linea);
            String[] enterosLinea = linea.split(",");
            int longitud = enterosLinea.length;
            //System.out.println("longitud : "+longitud);
            matriz = new int[4][longitud];
            //Las siguientes lineas son filas de la matriz
            //linea = br.readLine();
            //System.out.println("linea 2 : "+linea);
            int fila = 0; //Para recorrer las filas de la matriz
            while (linea != null) {
                /*
				 * Tenemos todos los enteros JUNTOS en el String linea.
				 * Con split() los SEPARAMOS en un array donde cada entero
				 * es un String individual. Con un bucle, los parseamos a Integer
				 * para guardarlos en la matriz
                 */
                String[] enteros = linea.split(",");
                for (int i = 0; i < enteros.length; i++) {
                    matriz[fila][i] = Integer.parseInt(enteros[i]);
                }

                fila++; //Incrementamos fila para la próxima línea de enteros
                linea = br.readLine(); //Leemos siguiente línea
            }
            br.close(); //Cerramos el lector de ficheros
            //System.out.println("fila : " + fila);
            //Mostramos la matriz leída
//            for (int i = 0; i < fila; i++) {
//                for (int j = 0; j < longitud; j++) {
//                    System.out.print(matriz[i][j] + "-");
//                }
//                System.out.println();
//            }

            return matriz;

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra archivo");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("No se pudo convertir a entero");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error accediendo al archivo.");
            e.printStackTrace();
        }
        return null;
    }

    // Traer la informacion por grupo que tiene en cada x segundo
    public JSONArray getSenalSegundo(int[][] matriz, int segundo) {
        
        JSONArray jsonArray = new JSONArray();
        for (int j = 0; j < matriz.length; j++) {
            JSONObject json = new JSONObject();
            json.put("idGrupo", matriz[j][0]);
            json.put("senal", matriz[j][segundo]);
         
            jsonArray.add(json);
        }
        //System.out.println("segundo: "+segundo+" enviar " + jsonArray.toJSONString());
        return jsonArray;
    }

    // Recorrer el tiempo hasta el segundo 14 
    public void getControlTiempoConexion(int[][] matriz) {
        TimerTask timerTask = new TimerTask() {
            int cicloConexion = 14; // 14 segundos dura el ciclo de conexion
            int segundo = 0;
            JSONArray jsonArray = new JSONArray();

            public void run() {
                // Aquí el código que queremos ejecutar.
                //System.out.println("segundo "+segundo);
                switch (segundo) {
                    case 0:
                        //Enviar primer grupo de conexion
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 3:
                        //Enviar cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 9:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 14:
                        //Iniciar ciclo
                        System.out.println("termino");
                        segundo = 0;
                        cancel();
                        break;
                }
                segundo++;
            }
        };

        //Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        tiempoConexion.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    
    // Recorre el tiempo del ciclo semaforico completo y se repite hasta que alla alguna interupcion
    // Ciclo dura 90 segundos y vuelve a empezar
    public void getControlTiempoSemaforo(int[][] matriz) {
        TimerTask timerTask = new TimerTask() {
            int cicloConexion = 90; // 90 segundos dura el ciclo de conexion
            int segundo = 0;
            JSONArray jsonArray = new JSONArray();

            public void run() {
                // Aquí el código que queremos ejecutar.
                //System.out.println("segundo "+segundo);
                switch (segundo) {
                    case 0:
                        //Enviar primer grupo de conexion
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 2:
                        //Enviar cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 4:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 6:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 9:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 10:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 11:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 49:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 51:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 53:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 56:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 57:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 58:
                        //Enviar otro cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                   
                    case 90:
                        //Iniciar ciclo
                        System.out.println("termino");
                        segundo = 0;
                        
                        break;
                    case 100:
                        //Interumpir ciclo
                        System.out.println("Interumpir ciclo");
                        segundo = 0;
                        cancel();
                        break;
                }
                segundo++;
            }
        };

        
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        tiempoCiclo.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    // Recorre el tiempo de desconexion del semaforos
    // Ciclo dura 13 segundos y se apaga todo
    public void getControlTiempoDesconexion(int[][] matriz) {
        TimerTask timerTask = new TimerTask() {
            int cicloConexion = 13; // 13 segundos dura el ciclo de desconexion
            int segundo = 0;
            JSONArray jsonArray = new JSONArray();

            public void run() {
                // Aquí el código que queremos ejecutar.
                //System.out.println("segundo "+segundo);
                switch (segundo) {
                    case 0:
                        //Enviar primer grupo de conexion
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    case 5:
                        //Enviar cambio
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        break;
                    
                    case 14:
                        //Iniciar ciclo
                        System.out.println("termino");
                        jsonArray = getSenalSegundo(matriz, segundo + 1);
                        servidor.enviar(jsonArray.toJSONString());
                        segundo = 0;
                        cancel();
                        break;
                }
                segundo++;
            }
        };

        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        tiempoDesconexion.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public Timer getTiempoConexion() {
        return tiempoConexion;
    }

    public void setTiempoConexion(Timer tiempoConexion) {
        this.tiempoConexion = tiempoConexion;
    }

    public Timer getTiempoCiclo() {
        return tiempoCiclo;
    }

    public void setTiempoCiclo(Timer tiempoCiclo) {
        this.tiempoCiclo = tiempoCiclo;
    }

    public Timer getTiempoDesconexion() {
        return tiempoDesconexion;
    }

    public void setTiempoDesconexion(Timer tiempoDesconexion) {
        this.tiempoDesconexion = tiempoDesconexion;
    }
    
    
    
}
