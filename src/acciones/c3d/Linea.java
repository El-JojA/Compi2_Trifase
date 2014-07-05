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
public class Linea extends Instruccion {
    
    String x1;
    String y1;
    String x2;
    String y2;
    String r;
    String g;
    String b;
    
    public Linea(String inX1, String inY1, String inX2, String inY2, String inR, String inG, String inB){
        this.x1 = inX1;
        this.x2 = inX2;
        this.y1 = inY1;
        this.y2 = inY2;
        this.r = inR;
        this.g = inG;
        this.b = inB;
    }
    
    @Override
    public void operar() {
        float fx1, fx2, fy1,fy2, fr,fg,fb;
        fx1 = MetodosTS.TablaVariables.get(x1);
        fx2 = MetodosTS.TablaVariables.get(x2);
        fy1 = MetodosTS.TablaVariables.get(y1);
        fy2 = MetodosTS.TablaVariables.get(y2);
        fr = MetodosTS.TablaVariables.get(r);
        fg = MetodosTS.TablaVariables.get(g);
        fb = MetodosTS.TablaVariables.get(b);
        
        System.out.println("Ejecuta Linea("+ fx1 + "," + fx2 + "," + fy1+ "," + fy2+ "," + fr+ "," + fg+ "," + fb +")");
        
    }
    
    
    
    
}
