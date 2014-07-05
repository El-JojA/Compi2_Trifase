/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.proyectos;

import java.util.ArrayList;
import javax.swing.JTextPane;

/**
 *
 * @author joja
 */
public class Proyecto {
    public String nombre;
    public String ruta;
    public ArrayList <Archivo> listaArchivos = new ArrayList<>();
    
    public Proyecto(String inNombre, String inRuta, ArrayList<Archivo> inListaArchivos){
        nombre = inNombre;
        ruta = inRuta;
        listaArchivos = inListaArchivos;
    }
}
