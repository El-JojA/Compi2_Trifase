/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

import acciones.tablasimbolos.MetodosTS;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author joja
 */
public class Ovalo extends Instruccion {
    
     String x;
    String y;
    String alto;
    String ancho;
    String r;
    String g;
    String b;
    String fill;
    
    public Ovalo(String inX, String inY, String inAlto, String inAncho, String inR, String inG, String inB, String inFill){
        x = inX;
        y = inY;
        alto = inAlto;
        ancho = inAncho;
        r = inR;
        g = inG;
        b = inB;
        fill = inFill;
    }
    
    @Override
    public void operar() {
        int iX,iY, iAlto, iAncho, iAng, iGrados, iR,iG, iB, iFill;
        iX =  Math.round(MetodosTS.TablaVariables.get(x));
        iY =  Math.round(MetodosTS.TablaVariables.get(y));
        iAlto = Math.round(MetodosTS.TablaVariables.get(alto));
        iAncho = Math.round(MetodosTS.TablaVariables.get(ancho));;
        iR = Math.round(MetodosTS.TablaVariables.get(r));
        iG = Math.round(MetodosTS.TablaVariables.get(g));
        iB = Math.round(MetodosTS.TablaVariables.get(b));
        iFill =  Math.round(MetodosTS.TablaVariables.get(fill));
        
        Ellipse2D oval = new Ellipse2D.Double(iX, iY, iAncho, iAlto);
        Color color = new Color(iR,iG,iB);
        
        MetodosTS.listaShapes.add(oval);
        MetodosTS.ListaColores.add(color);
        
    }
    
}
