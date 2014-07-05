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
public class Poligono extends Instruccion {
    
    String puntosX;
    String puntosY;
    String r;
    String b;
    String g;
    String fill;
    
    public Poligono(String inPuntosX, String inPuntosY, String inR, String inG, String inB, String inFill){
        puntosX = inPuntosX;
        puntosY = inPuntosY;
        r = inR;
        g = inG;
        b = inB;
        fill = inFill;
    }
    
    @Override
    public void operar() {
        System.out.println("Ejecuta Poligono"); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
