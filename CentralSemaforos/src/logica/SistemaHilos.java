/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author cvelez
 */
public class SistemaHilos  extends Thread{
    
    private String nombre;
    private String msg;

    public SistemaHilos(String nombre) {
        this.nombre = nombre;
    }
    
    public void run() {
        System.out.println ("Hola soy el cliente: "+this.nombre);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
