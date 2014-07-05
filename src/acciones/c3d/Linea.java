/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

import acciones.tablasimbolos.MetodosTS;
import java.awt.Color;
import java.awt.geom.Line2D;

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
        int fx1, fx2, fy1,fy2, fr,fg,fb;
        fx1 = Math.round(MetodosTS.TablaVariables.get(x1));
        fx2 = Math.round(MetodosTS.TablaVariables.get(x2));
        fy1 = Math.round(MetodosTS.TablaVariables.get(y1));
        fy2 = Math.round(MetodosTS.TablaVariables.get(y2));
        
        fr = Math.round(MetodosTS.TablaVariables.get(r));
        fg = Math.round(MetodosTS.TablaVariables.get(g));
        fb = Math.round(MetodosTS.TablaVariables.get(b));
        
        Line2D lin = new Line2D.Float(fx1, fy1, fx2, fy2);
        Color color = new Color(fr, fg, fb);
        
        MetodosTS.listaShapes.add(lin);
        MetodosTS.ListaColores.add(color);
        
        System.out.println("Ejecuta Linea("+ fx1 + "," + fx2 + "," + fy1+ "," + fy2+ "," + fr+ "," + fg+ "," + fb +")");
        
    }
    
    
    
    
}
