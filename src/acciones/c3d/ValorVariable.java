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
public class ValorVariable extends Instruccion{
    
    String id;
    
    public ValorVariable(String inId){
        this.id = inId;
    }

    @Override
    public void operar() {
        float f = MetodosTS.TablaVariables.get(id);
        String s = String.valueOf(f);
        super.result = s;
    }
    
    
}
