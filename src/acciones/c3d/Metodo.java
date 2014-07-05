/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

import acciones.tablasimbolos.MetodosTS;
import java.util.ArrayList;

/**
 *
 * @author joja
 */
public class Metodo extends Instruccion {
    
    String id;
    ArrayList<Instruccion> instrucciones;
    
    public Metodo(String inId, ArrayList<Instruccion> inInstrucciones){
        this.id = inId;
        this.instrucciones = inInstrucciones;
    }

    @Override
    public void operar() {
        
        MetodosTS.indiceInstruccion.push(0);
        float x = MetodosTS.stack.length + MetodosTS.heap.length;
        int y = MetodosTS.TablaVariables.size();
        String z = MetodosTS.texto3d;
        while(instrucciones.size()>MetodosTS.indiceInstruccion.peek())
        {
            float debSptr = MetodosTS.TablaVariables.get("sptr");
            float debHptr = MetodosTS.TablaVariables.get("hptr");
            int i = MetodosTS.indiceInstruccion.peek();
            instrucciones.get(i).operar();
            MetodosTS.indiceInstruccion.setElementAt(MetodosTS.indiceInstruccion.peek() + 1, MetodosTS.indiceInstruccion.size()-1);
        }
        MetodosTS.indiceInstruccion.pop();
    }
    
    
    
}
