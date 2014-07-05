/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author joja
 */
import acciones.tablasimbolos.Simbolo;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.*;
import javax.swing.text.*;

public class Test extends JFrame {

    static int contadorPunteroHeap=0;
    static int contadorPunteroStack=0;
    static int contadorTerminales=0;
    
    static Stack <Simbolo> pila;
    
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }
    
    public static void test1(){
        HashMap<String, Integer> hash = new HashMap<>();
        hash.put("uno", 1);
        hash.put("dos", 2);
        hash.put("tres", 3);
        hash.put("cuatro", 4);
        
    }
    
    public void test2(){
        
        Object ob = null;
        ArrayList<String> str = new ArrayList<>();
    ArrayList<ArrayList<String>> lista = (ArrayList<ArrayList<String>>)ob;
    lista.add(str);
        
        int in=0;
        String str1;
        str1 = String.valueOf(in);
        
                
    }
    
    public static void test3(){
    
        String str = "";
        String hola;
        hola = str + "s" + "" + "1";
        System.out.println(hola);
    
    }
    
    public static void test4(){
    
        Simbolo sim1= new Simbolo();
        sim1.setTamano("11");
        pila.push(sim1);
        Simbolo sim2= new Simbolo();
        sim1.setTamano("22");
        
    }
    
    public static void test5(){
    
        String inString = "hola";
        
        for(int i =0; i<inString.length(); i++)
        {
            char letra = inString.charAt(i);
            System.out.println(letra);
            int asciiLetra = (int)letra;
            System.out.println(asciiLetra);
        }
        
    }
    
    public static String[] stringToC3d(String inString){
        String[] respuesta={"","",""};
        String cod = "";
        String val = getNewTerminal();
        String tipo = "string";
        
        cod = val + " = hptr" + "\n";
        for(int i =0; i<inString.length(); i++)
        {
            char letra = inString.charAt(i);
            int asciiLetra = (int)letra;
            cod = cod + "heap[hptr] = " + String.valueOf(asciiLetra) + ";" + "\n";
            cod = cod + "hptr = hptr + 1;" + "\n";
        }
        cod = cod + "heap[hptr] = -1; " + "\n";
        cod = cod + "hptr = hptr + 1;" + "\n";
        
        respuesta[0] = cod;
        respuesta[1] = val;
        respuesta[2] = tipo;
        
        return respuesta;
    }
    
    public static void test6(){
    
        char a = 'a';
        char b = 'b';
        char c = (char) (a + b);
        System.out.println(c);
    }
    
    public static String getNewTerminal(){
        String terminal = "t" + String.valueOf(contadorTerminales++);
        return terminal;
    }
    
    public static String getPuntero(String inTipoPuntero){
        if(inTipoPuntero.equalsIgnoreCase("hptr"))
        {
            return String.valueOf(contadorPunteroHeap++);
        }
        else if(inTipoPuntero.equalsIgnoreCase("sptr"))
        {
            return String.valueOf(contadorPunteroStack++);
        }
        else
        {
            return "-1337";
        }
    }
    //21 // 596 // 100
    public static void test7(){
    
        char c1='a';
        char c2='b';
        if(c1>c2)
        {
            System.out.println(c1);
        }
        else
        {
            System.out.println(c2);
        }
        
    }
    
    public static void test8(){
       
       float a = 1.5f;
       
     
       
    }
    
    public static void main (String args[]) {
        
        test8();
        
    }

    public Test () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(private|public|protected)"))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(private|public|protected)")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        JTextPane txt = new JTextPane(doc);
        txt.setText("public class Hi {}");
        add(new JScrollPane(txt));
        setVisible(true);
    }
    
}
