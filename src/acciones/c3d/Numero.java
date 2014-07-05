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
public class Numero extends Instruccion {
    
    String valor;
    
    public Numero(String inValor){
        this.valor = inValor;
    }

    @Override
    public void operar() {
        float f = Float.valueOf(valor);
        String s = String.valueOf(f);
        super.result = s;
    }
    
}
