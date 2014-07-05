/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.proyectos;

import javax.swing.JTextPane;

/**
 *
 * @author joja
 */
public class Archivo {
    String nombre;
    boolean principal= false;
    public String texto;
    public JTextPane pane;
    
    public Archivo(String inNombre){
        nombre = inNombre;
    }
    
    public void setPrincipal(){
        principal = true;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public boolean getPrincipal(){
        return principal;
    }

    
    
}
