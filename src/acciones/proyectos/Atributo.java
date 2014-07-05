/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.proyectos;

/**
 *
 * @author joja
 */
public class Atributo extends Instruccion{
    
    String nombre="";
    String valor="";
    
    public Atributo(String inNombre, String inValor){
        nombre =inNombre;
        valor = inValor;
    }
    
    @Override
    public void operar() {
        super.operar(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
