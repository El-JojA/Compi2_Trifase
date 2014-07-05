/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

/**
 *
 * @author joja
 */
public class Imprimir extends Instruccion {
    
    String puntero;
    String tipoDato;
    
    public Imprimir(String inPuntero, String inTipoDato){
        this.puntero = inPuntero;
        this.tipoDato = inTipoDato;
    }

    @Override
    public void operar() {
        
    }
    
    
    
}
