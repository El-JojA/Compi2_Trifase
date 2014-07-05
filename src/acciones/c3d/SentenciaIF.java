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
public class SentenciaIF extends Instruccion {
    
    Instruccion valor1;
    Instruccion valor2;
    String op;
    String etiqueta;
    
    
    public SentenciaIF(Instruccion inValor1, String inOp, Instruccion inValor2, String inEtiqueta){
        this.valor1 = inValor1;
        this.valor2 = inValor2;
        this.op = inOp;
        this.etiqueta = inEtiqueta;
    }

    @Override
    public void operar() {
        valor1.operar();
        valor2.operar();
        if(comparacion(valor1.result, op, valor2.result))
        {
            Integer index = MetodosTS.TablaEtiquetas.get(etiqueta);
            if(index==null)
            {
                System.out.println("Error en SentenciaIF.operar(). Con la etiqueta " + etiqueta + ". No aparece en MetodosTS.TablaEtiquetas.");
            }
            index = index - 1;
            MetodosTS.indiceInstruccion.setElementAt(index, MetodosTS.indiceInstruccion.size()-1);
        }
        
    }
    
    public boolean comparacion(String inVal1, String inOp, String inVal2){
        if(inVal1!=null && inVal2!=null && inOp!=null)
        {
            float val1 = Float.valueOf(inVal1);
            float val2 = Float.valueOf(inVal2);
            switch(inOp)
            {
                case "<":
                    if(val1<val2)
                    {return true;}
                    break;
                    
                case ">":
                    if(val1>val2)
                    {return true;}
                    break;
                    
                case "==":
                    if(val1==val2)
                    {return true;}
                    break;
                    
                case "!=":
                    if(val1!=val2)
                    {return true;}
                    break;
                    
                case "<=":
                    if(val1<=val2)
                    {return true;}
                    break;
                    
                case ">=":
                    if(val1>=val2)
                    {return true;}
                    break;
                default:
                    System.out.println("Entro al default en SentenciaIF.comparacion()");
                    break;
            }
        }
        else
        {
            System.out.println("Error en SentenciaIF.comparacion()");
        }
        
        return false;
    }
    
    
}
