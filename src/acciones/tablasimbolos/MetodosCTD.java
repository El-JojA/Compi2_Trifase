/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.tablasimbolos;

import acciones.jojaphviz.Metodos;///
import analisis.JojaphvizCup;
import analisis.JojaphvizCup2;
import analisis.JojaphvizLex;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author joja
 */
public class MetodosCTD {
    
    public static Stack<Simbolo> SimboloActual = new Stack<>();
    public static HashMap<String,Simbolo> TablaDeTipos = new HashMap<>();
    
    static int contadorTerminales=0;
    static int contadorEtiquetas=0;
    
    
    public static String[] EXPR(String[] str1,String operador , String []str2 ){
        String[] result={"","",""};
        try
        {
            switch(operador)
            {
                case "+":
                    if(str1[2].equalsIgnoreCase("int") && str2[2].equalsIgnoreCase("int"))
                    {
                        String valor = getNewTerminal(); 
                        String codigo = str1[0] + str2[0] + valor + " = " + str1[1] + " + " + str2[1] + "\n"; 
                        String[] res = {codigo, valor, "int"};
                        return res;
                    }
                    else if(str1[2].equalsIgnoreCase("string") && str2[2].equalsIgnoreCase("int"))
                    {
                        //Convertir el int a string en c3d.
                        return null;
                    }
                    else if(str1[2].equalsIgnoreCase("string") && str2[2].equalsIgnoreCase("int"))
                    {
                        //Convertir el int a string en c3d.
                        return null;
                    }
                    
                    break;
            }                                                                       
            return result;
        }
        catch(Exception ex){System.out.println("Error en MetodosTS.EXPR();");}
        return result;
    }
    
    public static String getPunteroDeVariable(Simbolo inAmbito,String inId){
        if(inAmbito!=null)
        {
            Simbolo res = inAmbito.tabla.get(inId);
            if(res!=null)
            {
                return res.puntero;
            }
            else
            {
                return getPunteroDeVariable(inAmbito.padre,inId);
            }
        }
        else
        {
            return "";
        }
    }
    
    public static Simbolo getSiguienteAmbito(Simbolo inAmbito){
        if((inAmbito!=null) && (inAmbito.ambitos!=null))
        {
            for(int i=0; i<inAmbito.ambitos.size(); i++)
            {
                if(inAmbito.ambitos.get(i).isRevisado()==false)
                {
                    inAmbito.ambitos.get(i).setRevisado(true);
                    return inAmbito.ambitos.get(i);
                }
            }
        }
        System.out.println("Error en MetodosTS.getSiguienteNoRevisado. Con return null;");
    return null;
    }
    
    
    public static void setAmbito(String inId){
    
        SimboloActual.push(SimboloActual.peek().tabla.get(inId));
    
    }
    
    public static void importar2(String inNombre, String inExtension){
        String inNombreArchivo = inNombre + "." +inExtension;
        try
        {
            String ruta = Metodos.proyectoActual.ruta;
            ruta = ruta + inNombreArchivo;
            String inArchivo = Metodos.readArchivo(ruta);
            
            Simbolo sim = TablaDeTipos.get(inNombre);
            SimboloActual.push(sim);
            JojaphvizLex jojaLexer2 = new JojaphvizLex(new StringReader(inArchivo));
            JojaphvizCup2 jojaParser2 = new JojaphvizCup2(jojaLexer2);
            jojaParser2.parse();
            SimboloActual.pop();
        }catch (Exception ex){
            System.out.println("Error al leer el archivo. MetodosTS.importar2()");
            System.out.println(ex.toString());
        }
        
    }
    
    public static void heredar2(String inNombre){
        String inNombreArchivo = inNombre + "." +"frc";
        try
        {
            String ruta = Metodos.proyectoActual.ruta;
            ruta = ruta + inNombreArchivo;
            String inArchivo = Metodos.readArchivo(ruta);

            JojaphvizLex jojaLexer = new JojaphvizLex(new StringReader(inArchivo));
            JojaphvizCup jojaParser = new JojaphvizCup(jojaLexer);
            SimboloActual.push(SimboloActual.peek().padre);
            jojaParser.parse();
            SimboloActual.pop();
            
        }catch (Exception ex){
            System.out.println("Error al leer el archivo. MetodosTS.heredar2()");
            System.out.println(ex.toString());
            SimboloActual.pop();
        }
    }
    
    
    public static String getNewTerminal(){
        String terminal = "t" + String.valueOf(contadorTerminales++);
        return terminal;
    }
    
    public static String getNewEtiqueta(){
        String terminal = "L" + String.valueOf(contadorEtiquetas++);
        return terminal;
    }
    
    
    
}
