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
public class Concatenar extends Instruccion {
    
    String posicion1;
    String posicion2;
    
    public Concatenar(String inPosicion1, String inPosicion2){
        this.posicion1 = inPosicion1;
        this.posicion2 = inPosicion2;
    }
    
    @Override
    public void operar() {
        ValorVariable valVar1 = new ValorVariable(posicion1);
        ValorVariable valVar2 = new ValorVariable(posicion2);
        valVar1.operar();
        valVar2.operar();
        
        ValorVariable valHptr = new ValorVariable("hptr");
        valHptr.operar();
        float hptrFloat = Float.valueOf(valHptr.result);
        int hptrInt = Math.round(hptrFloat);
        int resInt = hptrInt;                                                   //El puntero que va a ir en el result
        
        int punteroInicial1 = Math.round(Float.valueOf(valVar1.result));        //Puntero hacia el heap del primer caracter
        int punteroInicial2 = Math.round(Float.valueOf(valVar2.result));        //Puntero hacia el heap del primer caracter
        
        //copiar los datos desde punteroInicial1 hasta encontrar un -1
        int contador = 0;
        int letraActual = Math.round(MetodosTS.heap[punteroInicial1]);
        while(letraActual!=-1)
        {
            ValorVariable valVar = new ValorVariable("hptr");
            valVar.operar();
            int posHptr = Math.round(Float.valueOf(valVar.result));             //hptr
            MetodosTS.heap[posHptr] = (float)letraActual;                       //heap[hptr] = val
            posHptr++;                                                          
            MetodosTS.TablaVariables.put("hptr", (float)posHptr);               //hptr = hptr + 1
            punteroInicial1++;
            letraActual = Math.round(MetodosTS.heap[punteroInicial1]);
            contador++;
            if(contador>512)
            {
                break;
            }
        }
        
        letraActual = Math.round(MetodosTS.heap[punteroInicial2]);
        while(letraActual!=-1)
        {
            ValorVariable valVar = new ValorVariable("hptr");
            valVar.operar();
            int posHptr = Math.round(Float.valueOf(valVar.result));             //hptr
            MetodosTS.heap[posHptr] = (float)letraActual;                       //heap[hptr] = val
            posHptr++;                                                          
            MetodosTS.TablaVariables.put("hptr", (float)posHptr);               //hptr = hptr + 1
            punteroInicial2++;
            letraActual = Math.round(MetodosTS.heap[punteroInicial2]);
            contador++;
            if(contador>512)
            {
                break;
            }
        }
        
        //poniendo el -1
        ValorVariable valVar = new ValorVariable("hptr");
        valVar.operar();
        int posHptr = Math.round(Float.valueOf(valVar.result));
        MetodosTS.heap[posHptr] = -1f; 
        posHptr++;                                                          
        MetodosTS.TablaVariables.put("hptr", (float)posHptr); 
        
        
        super.result = String.valueOf(resInt);
    }
    
    
    
}
