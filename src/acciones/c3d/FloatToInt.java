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
public class FloatToInt extends Instruccion {
    
    Instruccion valor1;
    
    public FloatToInt(Instruccion inValor1){
        this.valor1 = inValor1;
    }

    @Override
    public void operar() {
        valor1.operar();
        float valFloat = Float.valueOf(valor1.result);
        int valInt = Math.round(valFloat);
        String res = String.valueOf(valInt);
        super.result = res;
    }
    
    
    
}
