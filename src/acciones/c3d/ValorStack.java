/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

import acciones.tablasimbolos.MetodosTS;

/**
 *
 * @author joja
 */
public class ValorStack extends Instruccion{
    
    String posicion;
    
    public ValorStack(String inPosicion){
        this.posicion = inPosicion;
    }

    @Override
    public void operar() {
        ValorVariable valVar = new ValorVariable(posicion);
        valVar.operar();
        int intPos = Math.round(Float.valueOf(valVar.result));
        float f = MetodosTS.stack[intPos];
        String s = String.valueOf(f);
        super.result = s;
    }
    
    
    
}
