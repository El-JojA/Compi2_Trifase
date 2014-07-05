/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.tablasimbolos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author joja
 */
public class TablaSimbolos {
    
    HashMap<String, String> TS;
    ArrayList<TablaSimbolos> ambitos;
    
    public TablaSimbolos(){
        TS = new HashMap<>();
        ambitos = new ArrayList<>();
    }
    
}
