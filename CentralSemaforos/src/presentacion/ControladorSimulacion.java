/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author cvelez
 */
public class ControladorSimulacion implements ActionListener{
    
    private final VistaServidor simulacion;
    private final Modelo modelo;
    
    public ControladorSimulacion(VistaServidor vista) {
        this.simulacion = vista;
        this.modelo = vista.getModelo();
    }
    
    public Modelo getModelo() {
        return modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btnButton = (JButton) e.getSource();
            if (btnButton == simulacion.getBtn_Conexion1()){
                
            }else if (btnButton == simulacion.getBtn_Iniciar1()) {
                getModelo().iniciarSecuencia();
            }
        }
    }
}
