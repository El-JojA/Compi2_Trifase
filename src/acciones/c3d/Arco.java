/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.c3d;

import acciones.tablasimbolos.MetodosTS;
import java.awt.geom.Arc2D;

/**
 *
 * @author joja
 */
public class Arco extends Instruccion {
    
    String x;
    String y;
    String alto;
    String ancho;
    String angInit;
    String grados;
    String r;
    String g;
    String b;
    String fill;
    
    public Arco(String inX, String inY, String inAlto, String inAncho, String inAngInit, String inGrados, String inR, String inG, String inB, String inFill){
        x = inX;
        y = inY;
        alto = inAlto;
        ancho = inAncho;
        angInit = inAngInit;
        grados = inGrados;
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
        iAncho = Math.round(MetodosTS.TablaVariables.get(ancho));
        iAng = Math.round(MetodosTS.TablaVariables.get(angInit));
        iGrados =  Math.round(MetodosTS.TablaVariables.get(grados));
        iR = Math.round(MetodosTS.TablaVariables.get(r));
        iG = Math.round(MetodosTS.TablaVariables.get(g));
        iB = Math.round(MetodosTS.TablaVariables.get(b));
        iFill =  Math.round(MetodosTS.TablaVariables.get(fill));
        
        
        
        System.out.println("Ejecuta Archo");
    }
    
    
    
    
}
