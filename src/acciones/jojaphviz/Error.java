/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.jojaphviz;

/**
 *
 * @author joja
 */
public class Error {
    
    String tipo;
    String mensaje;
    String fila;
    String columna;
    
    public Error(String inTipo, String inMensaje, String inFila, String inColumna){
        tipo = inTipo;
        mensaje = inMensaje;
        fila = inFila;
        columna = inColumna;
    }
    
}
