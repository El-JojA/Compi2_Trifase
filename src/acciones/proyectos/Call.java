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
public class Call extends Instruccion{

    String metodo;
    
    public Call(String inMetodo){
        this.metodo = inMetodo;
    }
    
    @Override
    public void operar() {
        
    }
    
}
