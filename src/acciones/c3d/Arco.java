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
        System.out.println("Ejecuta Archo");
    }
    
    
    
    
}
