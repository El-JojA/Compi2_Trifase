/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.tablasimbolos;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author joja
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    
    static Stack<Simbolo> pila = new Stack<>();
    static HashMap<String, Simbolo> hash = new HashMap<>();
    
    public static void main(String[] args) {
        test1();
    }
    
    public static void test1(){
    
       
        HashMap<String, Float> hm = new HashMap<>();
        hm.put("uno", 1.0f);
        hm.put("dos", 2.0f);
        hm.put("tres", 3.0f);
        
        String id = "uno";
        float valorFloat = 22.3f;
        
        if(hm.containsKey(id))
        {
            hm.put(id, valorFloat);
        }
        else
        {
            hm.replace(id, valorFloat);
        }
       
    }
    
    
    
}
