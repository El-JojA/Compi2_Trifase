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
public class Goto extends Instruccion{
    
    String etiqueta;
    
    public Goto(String inEtiqueta){
        this.etiqueta = inEtiqueta;
    }
    
    @Override
    public void operar() {
        
        Integer index = MetodosTS.TablaEtiquetas.get(etiqueta);
        if(index==null)
        {
            System.out.println("Error en Goto.operar(). Con la etiqueta " + etiqueta + ". No aparece en MetodosTS.TablaEtiquetas.");
        }
        index = index - 1;
        MetodosTS.indiceInstruccion.setElementAt(index, MetodosTS.indiceInstruccion.size()-1);
        
    }
    
    
    
}
