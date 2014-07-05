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
public class Operacion extends Instruccion{
    
    Instruccion valor1;
    Instruccion valor2;
    String op;
    
    public Operacion(Instruccion inValor1, String inOp, Instruccion inValor2){
        this.valor1 = inValor1;
        this.valor2 = inValor2;
        this.op = inOp;
    }

    @Override
    public void operar() {
        valor1.operar();
        valor2.operar();
        float val1 = Float.valueOf(valor1.result);
        float val2 = Float.valueOf(valor2.result);
        float resultado = makeOperacion(val1, op, val2);
        String res = String.valueOf(resultado);
        super.result = res;
    }
    
    public float makeOperacion(float inVal1, String inOp, float inVal2){
        float res =0 ;
        switch(inOp)
        {
            case "+":
                res = inVal1 + inVal2;
                break;
            case "-":
                res = inVal1 - inVal2;
                break;
            case "*":
                res = inVal1 * inVal2;
                break;
            case "/":
                if(inVal2==0)
                {
                    res = 0;
                    System.out.println("Division en cero. Operacion.makeOperacion()");
                    break;
                }
                res = inVal1 / inVal2;
                break;
                
            case "^":
                res = (float)Math.pow(inVal1,inVal2);
                break;
            default:
                    System.out.println("Llegó al default. Operacion.makeOperacion()");
                    break;
        }
        return res;
    }
    
}
