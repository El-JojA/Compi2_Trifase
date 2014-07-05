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
public class Imprimir extends Instruccion {
    
    String puntero;
    String tipoDato;
    
    public Imprimir(String inPuntero, String inTipoDato){
        this.puntero = inPuntero;
        this.tipoDato = inTipoDato;
    }

    @Override
    public void operar() {
        
        float pI = MetodosTS.TablaVariables.get(puntero);
        int ptInicial = Math.round(pI);
        
        float letraActual = MetodosTS.heap[ptInicial];
        
        
        String resultado="";
        
        if(tipoDato.equalsIgnoreCase("0"))
        {
            int contador = 0;
            while(letraActual!=-1.0f && contador<500)
            {
                int letraActualInt = Math.round(letraActual);
                resultado = resultado + Character.toString((char)letraActualInt);

                ptInicial++;
                contador++;
                letraActual = MetodosTS.heap[ptInicial];
            }
        }
        else if(tipoDato.equalsIgnoreCase("1"))
        {
            resultado = String.valueOf(Math.round(letraActual));
        }
        else if(tipoDato.equalsIgnoreCase("2"))
        {
            resultado = String.valueOf((letraActual));
        }
        else if(tipoDato.equalsIgnoreCase("3"))
        {
            int letraActualInt = Math.round(letraActual);
            resultado = resultado + Character.toString((char)letraActualInt);
        }
        else if(tipoDato.equalsIgnoreCase("4"))
        {
            if(letraActual==0){resultado = "false";}
            else{resultado="true";}
        }
        System.out.print("imprimir>>> " + resultado);
    }
    
    
    
}
