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
public class Lienzo extends Instruccion {
    
    String alto;
    String ancho;
    String r;
    String g;
    String b;
    
    public Lienzo(String inAlto, String inAncho, String inR, String inG, String inB){
        alto = inAlto;
        ancho = inAncho;
        r = inR;
        g = inG;
        b = inB;
    }

    @Override
    public void operar() {
        System.out.println("Ejecuta Lienzo"); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
