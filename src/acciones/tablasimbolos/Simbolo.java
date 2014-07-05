/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.tablasimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author joja
 */
public class Simbolo {
    
    String iden;
    String visibilidad;
    String fila;
    String columna;
    String tipo;
    String tipoDato;
    String puntero;
    String tipoPuntero;
    String tamano;
    String dimensiones;
    ArrayList<String> parametros;
    HashMap<String, Simbolo> tabla;
    ArrayList<Simbolo> ambitos;
    Simbolo padre;
    boolean revisado=false;
    boolean herencia=false;

    public Simbolo(){
        tabla = new HashMap<>();
        ambitos = new ArrayList<>();
        parametros = new ArrayList<>();
    }
    
    public Simbolo(String inVisibilidad, String inTipoDato, String inIden,String inTipo, String inTamano, String inFila, String inColumna, String inPuntero, String inTipoPuntero){
        iden = inIden;
        visibilidad = inVisibilidad;
        fila = inFila;
        columna = inColumna;
        tipo = inTipo;
        tipoDato = inTipoDato;
        puntero = inPuntero;
        tipoPuntero = inTipoPuntero;
        tamano = inTamano;
        tabla = new HashMap<>();
        ambitos = new ArrayList<>();
        parametros = new ArrayList<>();
    }


    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }
    
    
    
}
