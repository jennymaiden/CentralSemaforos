/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package centralsemaforos;

import java.io.IOException;
import logica.SistemaServidor;
import presentacion.Modelo;

/**
 *
 * @author cvelez
 */
public class CentralSemaforos {
    
    private final Modelo miServidor;

    public CentralSemaforos() {
        miServidor = new Modelo();
        miServidor.iniciarVista();
        miServidor.iniciarServidor();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new CentralSemaforos();
    }
    
}
