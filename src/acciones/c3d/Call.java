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
public class Call extends Instruccion {
    
    String nombreMetodo;
    
    public Call(String inNombreMetodo){
        this.nombreMetodo = inNombreMetodo;
    }

    @Override
    public void operar() {
        
        if(MetodosTS.TablaMetodos.get(nombreMetodo)!=null)
        {
            MetodosTS.TablaMetodos.get(nombreMetodo).operar();
        }
        else
        {
            System.out.println("Error en Call.operar(). El metodo " + nombreMetodo + " no esta en MetodosTS.TablaMetodos");
        }
    }
    
    
    
}
