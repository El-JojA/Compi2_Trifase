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
public class Texto extends Instruccion {
    
    String cadena;
    String x;
    String y;
    String r;
    String g;
    String b;
    
    public Texto(String inCadena, String inX, String inY, String inR, String inG, String inB){
        this.cadena = inCadena;
        this.x = inX;
        this.y = inY;
        this.r = inR;
        this.g = inG;
        this.b = inG;
    }

    @Override
    public void operar() {
        System.out.println("Ejecuta Texto");
    }
    
    
    
}
