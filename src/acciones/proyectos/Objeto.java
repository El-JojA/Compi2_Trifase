/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.proyectos;

import java.util.ArrayList;

/**
 *
 * @author joja
 */
public class Objeto extends Instruccion{
    
    String nombre;
    ArrayList<Instruccion> atributos = new ArrayList<>() ;
    ArrayList<Instruccion> contenido = new ArrayList<>() ; 
    
    public Objeto(String inNombre, ArrayList<Instruccion> inAtributos, ArrayList<Instruccion> inContenido){
        atributos = inAtributos;
        nombre = inNombre;
        contenido = inContenido;
    }    
    
    @Override
    public void operar() {
        super.operar(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
