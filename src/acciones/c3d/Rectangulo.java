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
public class Rectangulo extends Instruccion {
    
    String x;
    String y;
    String alto;
    String ancho;
    String r;
    String g;
    String b;
    String fill;
    
    public Rectangulo(String inX, String inY, String inAlto, String inAncho, String inR, String inG, String inB, String inFill){
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
        System.out.println("Ejecuta Rectangulo."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
