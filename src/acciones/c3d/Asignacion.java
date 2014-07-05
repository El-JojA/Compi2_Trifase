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
public class Asignacion extends Instruccion {
    
    int tipo;
    String id;
    Instruccion valor;
    
    public Asignacion(int inTipo, String inId, Instruccion inValor){
        tipo = inTipo;
        id = inId;
        valor = inValor;
    }

    @Override
    public void operar() {
        valor.operar();
        float valorFloat = Float.valueOf(valor.result);
        
        switch(tipo)
        {
            case 0:// ID = EXPR
                MetodosTS.TablaVariables.put(id, valorFloat);
                break;
            case 1:// stack[id] = VAL_UNI
                ValorVariable valVar1 = new ValorVariable(id);
                valVar1.operar();
                int posInt1 = Math.round(Float.valueOf(valVar1.result));
                MetodosTS.stack[posInt1] = valorFloat;
                break;
            case 2:// heap[id] = VAL_UNI
                
                ValorVariable valVar2 = new ValorVariable(id);
                valVar2.operar();
                int posInt2 = Math.round(Float.valueOf(valVar2.result));
                MetodosTS.heap[posInt2] = valorFloat;
                break;
            default:
                System.out.println("Llegó a default. Asignacion.operar()");
                        
                break;
        }
        
    }
    
    
    
}
