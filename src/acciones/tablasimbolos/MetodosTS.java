/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.tablasimbolos;

import acciones.c3d.Instruccion;
import acciones.jojaphviz.Metodos;
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
public class MetodosTS {
    
    /**
     * SímboloActual va a actuar como un puntero que indique a que tabla de
     * simbolos se va a estar colocando nuevos simbolos o en que tabla se 
     * está buscando.
     **/
    public static Stack<Simbolo> SimboloActual = new Stack<>();
    public static Stack<String> BreakActual = new Stack<>();
    public static Stack<String> ReturnActual = new Stack<>();
    public static HashMap<String,Simbolo> TablaDeTipos = new HashMap<>();
    public static ArrayList<Error> ListaErrores = new ArrayList<>();
    public static int contadorPunteroHeap=0;
    public static int contadorPunteroStack=0;
    public static boolean errorazo=false;
    public static boolean heredando=false;
    public static String textoConsola="";
    public static String texto3d="";
    public static HashMap<String, String> InitsDeExtends = new HashMap<>();
        
    public static int contadorTerminales=0;
    public static int contadorEtiquetas=0;
    
    //Ejecucion de c3d
    public static HashMap<String, Float> TablaVariables = new HashMap<>();
    public static HashMap<String, Integer> TablaEtiquetas = new HashMap<>();
    public static HashMap<String, Instruccion> TablaMetodos = new HashMap<>();
    public static Float[] heap = new Float[1500000];
    public static Float[] stack = new Float[15000];
    public static Stack<Integer> indiceInstruccion = new Stack<>(); 

    
    public static void MetodosTS(){
        
    }
    
    
    public static void crearClase(String inVisibilidad, String inTipoDato, String inIden,String inTipo, String inTamano, String inFila, String inColumna, String inPuntero, Simbolo herencia){
    
            Simbolo sim = new Simbolo(inVisibilidad, inTipoDato, inIden, inTipo, inTamano, inFila, inColumna, inPuntero, null); 
            sim.padre = herencia;
            if(sim.padre!=null)
            {
                sim.setTamano(sim.padre.tamano);
            }
            else
            {
                sim.setTamano("0");
            }
            MetodosTS.SimboloActual.push(sim);
            TablaDeTipos.put(inIden, sim);
            
        
    }
    
    public static void Declara_Var(String inVisibilidad, String inTipoDato, String inTipo ,ArrayList<String[]> inLista, String inTipoPuntero){
        
        for(int i=0; i<inLista.size(); i++)
        {
            String id = inLista.get(i)[0];
            String fila = inLista.get(i)[1];
            String columna = inLista.get(i)[2];
            String dimension = inLista.get(i)[3];
            Simbolo simTipo = TablaDeTipos.get(inTipoDato.toLowerCase());
            if(simTipo!=null)
            {//El tipo de dato si existe.
                if(existeSimbolo2(SimboloActual.peek(), id)==null)
                {
                    String ptr = getPuntero(inTipoPuntero);
                    Simbolo sim = new Simbolo(inVisibilidad, inTipoDato, id, inTipo, simTipo.tamano ,fila, columna, ptr, inTipoPuntero);
                    sim.tabla = simTipo.tabla;
                    sim.padre = SimboloActual.peek();
                    sim.dimensiones = dimension;
                    SimboloActual.peek().tabla.put(id, sim);
                    SimboloActual.peek().tamano = actualizaTamano(SimboloActual.peek().tamano, 1);
                    if(SimboloActual.peek().tipo==null)
                    {
                        Simbolo simTemp = getFuncionActual();
                        simTemp.tamano = actualizaTamano(simTemp.tamano, 1);
                    }
                }
                else
                {
                    addError("Semantico", 
                          "El identificador \"" + id + "\" ya fue declarado.", 
                          SimboloActual.peek().iden, 
                          fila, 
                          columna);
                }
            }
            else
            {
                addError("Semantico", 
                          "El tipo de dato \"" + inTipoDato + "\" no existe o no ha sido declarado.", 
                          SimboloActual.peek().iden, 
                          fila, 
                          columna);
            }
        }
        
    }
    
    public static void Declara_Metodo(String inVisibilidad, String inTipoDato, String inId, String inTipo, String inFila, String inColumna, ArrayList<String[]> inLista){
        
        
        String param="";
        for(int i=0; i< inLista.size();i++)
        {
            param = param + "_"+ inLista.get(i)[1];
        }
        String identificador = inId + param;
        String idClase = SimboloActual.peek().iden;
        identificador = idClase + "_" + identificador;
        Simbolo simTipo = TablaDeTipos.get(inTipoDato.toLowerCase());
        if(simTipo!=null)
        {//El tipo de dato si existe
            if(existeSimbolo2(SimboloActual.peek(), identificador)==null)
            {//No existe, quiere decir que todo fresh.
                Simbolo sim = new Simbolo(inVisibilidad, inTipoDato, identificador, inTipo, simTipo.tamano ,inFila, inColumna, String.valueOf(contadorPunteroStack++), "sptr");
                Simbolo ret = new Simbolo(null, inTipoDato, "return", "variable", simTipo.tamano, inFila, inColumna, String.valueOf(contadorPunteroStack++), "sptr");
                ret.tabla = simTipo.tabla;
                sim.padre = SimboloActual.peek();
                sim.tabla.put("this", SimboloActual.peek());
                sim.tabla.put("return", ret);
                sim.setTamano("2");
                SimboloActual.peek().tabla.put(identificador, sim);
                SimboloActual.push(sim);
                insertarParametros(inFila, inColumna, inLista);
                
            }
            else
            {
                addError("Semantico", 
                          "El identificador \"" + identificador + "\" ya fue declarado", 
                          SimboloActual.peek().iden, 
                          inFila, 
                          inColumna);
                Simbolo sim = new Simbolo();
                SimboloActual.push(sim);
            }
        }
        else
        {
            addError("Semantico", 
                      "El tipo de dato \"" + inTipoDato + "\" no existe o no ha sido declarado.", 
                      identificador, 
                      inFila, 
                      inColumna);
            Simbolo sim = new Simbolo();
            SimboloActual.push(sim);

        }
    }
    
    public static String actualizaTamano(String inTamanoActual, int inAumento){
        try
        {
        int resultadoInt= Integer.valueOf(inTamanoActual) + inAumento;
        return String.valueOf(resultadoInt);
        }catch(Exception e)
        {
            System.out.println("Error en MetodosTS.actualizaTamano()");
        }
        return "0";
    }
    
    public static void creaAmbito(){
    
        Simbolo sim = new Simbolo();
        sim.padre = SimboloActual.peek();
        sim.tamano = "0";
        SimboloActual.peek().ambitos.add(sim);
        SimboloActual.push(sim);
    }
    
    public static void salirAmbito(boolean inEsMetodo){
        SimboloActual.pop();
        if (inEsMetodo)
            contadorPunteroStack = 0;
        
    }
    
    public static void insertarParametros(String inFila, String inColumna, ArrayList<String[]> inLista){
        for(int i =0; i < inLista.size(); i++)
        {
            //(String inVisibilidad, String inTipoDato, String inTipo ,ArrayList<String[]> inLista)
            String tipoDato = inLista.get(i)[1];
            String param = "param_ref";
            if(inLista.get(i)[0]==null) 
                param = "param_val";
            ArrayList<String[]> lista = new ArrayList<>();
            String[] str = {inLista.get(i)[2], inFila, inColumna,inLista.get(i)[3]};
            lista.add(str);
            Declara_Var("local", tipoDato, param, lista, "sptr");
        }
    }
    
    public static void importar(String inNombre, String inExtension){
        String inNombreArchivo = inNombre + "." +inExtension;
        try
        {
            String ruta = Metodos.proyectoActual.ruta;
            ruta = ruta + inNombreArchivo;
            String inArchivo = Metodos.readArchivo(ruta);

            JojaphvizLex jojaLexer = new JojaphvizLex(new StringReader(inArchivo));
            JojaphvizCup jojaParser = new JojaphvizCup(jojaLexer);
            jojaParser.parse();
            //Simbolo sim = new Simbolo();
            //sim = SimboloActual.peek();
            //TablaDeTipos.put(inNombre, sim);
            SimboloActual.pop();
            contadorPunteroHeap = 0;
            contadorPunteroStack = 0;
            
        }catch (Exception ex){
            System.out.println("Error al leer el archivo. MetodosTS.importar()");
            System.out.println(ex.toString());
        }
        
    }
    
    public static Simbolo heredar(String inNombre){
        String inNombreArchivo = inNombre + "." +"frc";
        try
        {
            String ruta = Metodos.proyectoActual.ruta;
            ruta = ruta + inNombreArchivo;
            String inArchivo = Metodos.readArchivo(ruta);

            JojaphvizLex jojaLexer = new JojaphvizLex(new StringReader(inArchivo));
            JojaphvizCup jojaParser = new JojaphvizCup(jojaLexer);
            jojaParser.parse();
            Simbolo sim = SimboloActual.peek();
            sim.herencia = true;
            SimboloActual.pop();
            return sim;
            
        }catch (Exception ex){
            System.out.println("Error al leer el archivo. MetodosTS.heredar()");
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public static String getPuntero(String inTipoPuntero){
        if(inTipoPuntero==null)
        {
            return "";
        }
        else if(inTipoPuntero.equalsIgnoreCase("hptr"))
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
    
    
    public static Simbolo existeSimbolo(Simbolo inAmbito, String inId){
        
        if(inAmbito==null)
        {//no ya no hay más
            return null;
        }
        else
        {
            Simbolo sim = inAmbito.tabla.get(inId);
            if(sim==null)
            {
                //aqui tendría que ir a ver mas aarriba
                return existeSimbolo(inAmbito.padre, inId);
            }
            else
            {
                if((inAmbito.herencia==true)&&(sim.visibilidad.equalsIgnoreCase("private")))
                {
                    return existeSimbolo(inAmbito.padre, inId);
                }
                else
                {
                    return sim;
                }
            }
        }
    }
    
    
    /**
     * existeSimbolo2() devuelve un simbolo con el nombre inId buscando desde
     * inAmbito hasta llegar al nivel superior de la clase. 
     * Si no la encuentra busca si la clase extiende de otra clase para buscar
     * ahí y verifica que sea publico el atributo/funcion.
     * 
     * @param inAmbito
     * @param inId
     * @return 
     **/
    
    public static Simbolo existeSimbolo2(Simbolo inAmbito, String inId){
        //Primero busco dentro de la clase en la que estoy y luego en el extends.
        
        Simbolo ambitoClase=null;
        while(inAmbito!=null)
        {
            Simbolo sim = inAmbito.tabla.get(inId);
            if(sim!=null)
            {
                return sim;
            }
            String tipo = inAmbito.tipo;
            if((tipo!=null)&&(tipo.equalsIgnoreCase("clase")))
            {
                ambitoClase=inAmbito;
                break;
            }
            inAmbito = inAmbito.padre;
        }
        
        if(ambitoClase.padre!=null)
        {
            Simbolo sim = ambitoClase.padre.tabla.get(inId);
            //falta ver si es public
            if(sim!=null)
            {
                if(sim.visibilidad.equalsIgnoreCase("public"))
                {
                    return sim;
                }
            }
        }
        
        return null;
    }
    
    
    
    public static Simbolo getClasePadre(Simbolo inAmbito){
    
        if(inAmbito==null)
        {//no ya no hay más
            System.out.println("Hint-error #1: MetodosTS.getClasePadre()");
            return null;
        }
        else
        {
            if(inAmbito.tipo.equalsIgnoreCase("clase")==true)
            {
                return inAmbito;
            }
            else
            {
                return getClasePadre(inAmbito.padre);
            }
        }
    }
    
    public static void cargarTiposPrimitivos(){
        
        Simbolo simInt = new Simbolo(null, "int", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("int", simInt);
        Simbolo simFloat = new Simbolo(null, "float", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("float", simFloat);
        Simbolo simString = new Simbolo(null, "string", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("string", simString);
        Simbolo simChar = new Simbolo(null, "char", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("char", simChar);
        Simbolo simBoolean = new Simbolo(null, "boolean", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("boolean", simBoolean);
        Simbolo simVoid = new Simbolo(null, "void", null, "tipo", "1", null, null, null, null);
        TablaDeTipos.put("void", simVoid);
    }
    
    public static void clearAll(){
        SimboloActual = new Stack<>();
        TablaDeTipos = new HashMap<>();
        BreakActual = new Stack<>();
        errorazo = false;
        texto3d = "";
        textoConsola = "";
        contadorPunteroHeap = 0;
        contadorPunteroStack = 0;
    }
    
    public static void addError(String inTipo, String inMensaje, String inAmbito, String inFila, String inColumna){
        ListaErrores.add(new Error(inTipo, 
                          inMensaje, 
                          inAmbito, 
                          inFila, 
                          inColumna));
        errorazo = true;
        System.out.println("Error " + inTipo + ". " + inMensaje + ". En el ambito " + inAmbito + " . fila: " + inFila + "/ columna " + inColumna);
    }
    
    /***************************************************************************
     * METODOS PARA GENERACION DE CODIGO DE TRES DIRECCIONES
     **************************************************************************/
    
    /**
     * 
     * @param str1
     * @param operador
     * @param str2
     * @return 
     **/
    
    public static String[] EXPR(String[] str1,String operador , String []str2 ){
        String cod1 = str1[0];
        String val1 = str1[1];
        String tipo1 = str1[2];
        String cod2 = str2[0];
        String val2 = str2[1];
        String tipo2 = str2[2];
        String[] result={"","",""};
        try
        {
            switch(operador)
            {
                case "+":
                    if(tipo1.equalsIgnoreCase("int") && tipo2.equalsIgnoreCase("int"))
                    {
                        String valor = getNewTerminal(); 
                        String codigo = cod1 + cod2 + valor + " = " + val1 + " + " + val2 + "\n"; 
                        String[] res = {codigo, valor, "int"};
                        return res;
                    }
                    else if(tipo1.equalsIgnoreCase("string") && tipo2.equalsIgnoreCase("string"))
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
        Simbolo sim = existeSimbolo2(inAmbito, inId);
        if(sim!=null)
        {
            return sim.puntero;
        }
        return "";
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
    
    public static void setObjetoAsAmbito(String inId){
        Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
        if(sim!=null)
        {
            String nombreTipo = sim.tipoDato;
            Simbolo tipo = TablaDeTipos.get(nombreTipo);
            SimboloActual.push(sim);
        }
        else
        {
            addError("Semantico", "La variable " + inId + " no fue declarada dentro del ambito.", "Ambito " + SimboloActual.peek().iden, "0", "0");
        }
    }
    
    public static void setAmbitoPadre(){
        
        Simbolo sim = getClasePadre(SimboloActual.peek());
        if(sim!=null)
        {   
            SimboloActual.push(sim);
        }
        else
        {
            System.out.println("Error en MetodosTS.setAmbitosPadre(). getClasePadre la está cagando.");
        }
        
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

            JojaphvizLex jojaLexer2 = new JojaphvizLex(new StringReader(inArchivo));
            JojaphvizCup2 jojaParser2 = new JojaphvizCup2(jojaLexer2);
            SimboloActual.push(SimboloActual.peek().padre);
            jojaParser2.parse();
            SimboloActual.pop();
            
        }catch (Exception ex){
            System.out.println("Error al leer el archivo. MetodosTS.heredar2()");
            System.out.println(ex.toString());
            SimboloActual.pop();
        }
    }
    
    public static String[] stringToC3d(String inString){
        String[] respuesta={"","",""};
        String cod = "";
        String val = getNewTerminal();
        String tipo = "string";
        
        cod = val + " = hptr;" + "\n";
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
    
    public static String[] charToC3d(String inChar){
        
        String cod="";
        String val="";
        String tipo="char";
        
        int ascii = (int)inChar.charAt(0);
        val = String.valueOf(ascii);
        
        String[] respuesta={cod,val,tipo};
        return respuesta;
    }
    
    public static String[] exprSumaExpr(String[] expr1, String[] expr2){
        
        
        String codR = "";
        String valR = getNewTerminal();
        String tipoR = "";
        
        String cod1 = expr1[0];
        String val1 = expr1[1];
        String tipo1 = expr1[2];
        
        String cod2 = expr2[0];
        String val2 = expr2[1];
        String tipo2 = expr2[2];
        
        if((tipo1.equalsIgnoreCase("int"))&&(tipo2.equalsIgnoreCase("int")))
        {//sumar en tres direcciones los strings
            codR = cod1 + cod2 + valR + " = " + val1 + " + " + val2 + ";\n";
            tipoR = "int";
        }
        else if((tipo1.equalsIgnoreCase("float"))&&(tipo2.equalsIgnoreCase("float")))
        {
            codR = cod1 + cod2 + valR + " = " + val1 + " + " + val2 + ";\n";
            tipoR = "float";
        }
        else if((tipo1.equalsIgnoreCase("string"))&&(tipo2.equalsIgnoreCase("string")))
        {
            codR = cod1 
                   + cod2
                   + valR + " = conca(" + val1 + ", " + val2 + ");\n";
            tipoR = "string";
            
        }
        else if((tipo1.equalsIgnoreCase("char"))&&(tipo2.equalsIgnoreCase("char")))
        {
            codR = cod1 + cod2 + valR + " = " + val1 + " + " + val2 + ";\n";
            tipoR = "char";
        }
        else if((tipo1.equalsIgnoreCase("float") &&  tipo2.equalsIgnoreCase("int"))
                || (tipo1.equalsIgnoreCase("int") &&  tipo2.equalsIgnoreCase("float")))
        {
            codR = cod1 + cod2 + valR + " = " + val1 + " + " + val2 + ";\n";
            tipoR = "float";
        }
        else if((tipo1.equalsIgnoreCase("int") &&  tipo2.equalsIgnoreCase("char"))
                || (tipo1.equalsIgnoreCase("char") &&  tipo2.equalsIgnoreCase("int")))
        {
            codR = cod1 + cod2 + valR + " = " + val1 + " + " + val2 + ";\n";
            tipoR = "int";
        }
        else
        {
            MetodosTS.addError("Semantico", "Error al sumar, tipos no compatibles", "MetodosTS.exprSumaExpr()", "0", "0");
        }
        String[] respuesta={codR,valR,tipoR};
        return respuesta;
    }
    
    public static String[] exprOpExpr(String[] expr1, String op, String[] expr2){
        
        String codR = "";
        String valR = getNewTerminal();
        String tipoR = "";
        
        if(expr1!=null && expr2!=null)
        {
            String cod1 = expr1[0];
            String val1 = expr1[1];
            String tipo1 = expr1[2];

            String cod2 = expr2[0];
            String val2 = expr2[1];
            String tipo2 = expr2[2];
            
            if(tipo1.equalsIgnoreCase("int") && tipo2.equalsIgnoreCase("int"))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "int";
            }
            else if(tipo1.equalsIgnoreCase("float") && tipo2.equalsIgnoreCase("float"))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "float";
            }
            else if(tipo1.equalsIgnoreCase("char") && tipo2.equalsIgnoreCase("char"))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "int";
            }
            else if((tipo1.equalsIgnoreCase("int") && tipo2.equalsIgnoreCase("float"))
                    || (tipo1.equalsIgnoreCase("float") && tipo2.equalsIgnoreCase("int")))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "float";
            }
            else if((tipo1.equalsIgnoreCase("char") && tipo2.equalsIgnoreCase("int"))
                    || (tipo1.equalsIgnoreCase("int") && tipo2.equalsIgnoreCase("char")))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "int";
            }
            else if((tipo1.equalsIgnoreCase("char") && tipo2.equalsIgnoreCase("float"))
                    || (tipo1.equalsIgnoreCase("float") && tipo2.equalsIgnoreCase("char")))
            {
                codR = cod1 + cod2 + valR + " = " + val1 + " " + op + " " + val2 + ";\n";
                tipoR = "float";
            }
            else
            {
                MetodosTS.addError("Semantico", "Error al *-^, tipos no compatibles", "MetodosTS.exprOpExpr()", "0", "0");
            }
        }
        else 
        {
            System.out.println("Error en MetodosTS.exprOpExpr(). Hay nulls");
        }
        
        String[] respuesta={codR,valR,tipoR};
        return respuesta;
    }
    
    public static String[] exprDividirExpr(String[] expr1, String[] expr2){
        
        String codR = "";
        String valR = getNewTerminal();
        String tipoR = "";
        
        String cod1 = expr1[0];
        String val1 = expr1[1];
        String tipo1 = expr1[2];
        
        String cod2 = expr2[0];
        String val2 = expr2[1];
        String tipo2 = expr2[2];
        
        if((tipo1.equalsIgnoreCase("int") && (tipo2.equalsIgnoreCase("int") || tipo2.equalsIgnoreCase("float") || tipo2.equalsIgnoreCase("char")))
            || (tipo1.equalsIgnoreCase("float") && (tipo2.equalsIgnoreCase("int") || tipo2.equalsIgnoreCase("float") || tipo2.equalsIgnoreCase("char")))
            || (tipo1.equalsIgnoreCase("char") && (tipo2.equalsIgnoreCase("int") || tipo2.equalsIgnoreCase("float") || tipo2.equalsIgnoreCase("char") ))
                )
        {
            codR = cod1 
                    + cod2 
                    + valR + " = " + val1 + " / " + val2 + ";\n";
            tipoR = "float";
        }
        else
        {
            addError("Semantico", "Error al intentar division.", "exprDividirExpr", "0", "0");
        }
        String[] respuesta = {codR,valR,tipoR};
        return respuesta;
    }
    
    public static String[] declaradorVar(String inId, String[] inExpr, String inTipo){
        String[] resultado = {"","",""};
        
        String codR = "";
        String valR = "";
        String tipoR = "";
        
        String cod;
        String val;
        String tipo;
        
        Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
        
        if(inExpr!=null)
        {//Si existe asignacion
            cod = inExpr[0];
            val = inExpr[1];
            tipo = inExpr[2];
            if(inTipo==null)
            {
                if(sim.tipoDato.equalsIgnoreCase(tipo))
                {
                    String ed = "";
                    if(sim.tipoPuntero.equalsIgnoreCase("sptr"))
                    {
                        //valR = getNewTerminal();
                        ed = "stack";
                        String puntero = getNewTerminal();
                        codR = cod 
                                + puntero + " = " +sim.tipoPuntero + " + " + sim.puntero + ";\n"
                                + ed +"[" + puntero + "] = " + val + ";\n";
                        resultado[0] = codR;
                        resultado[1] = puntero;
                        resultado[2] = tipo;
                    }
                    else
                    {

                        String tPuntero = getNewTerminal();
                        String tThis = getNewTerminal();
                        String tPosicion = getNewTerminal();
                        codR =  cod
                                + tPuntero + " = sptr + 0;\n"
                                + tThis + " = stack[" + tPuntero + "];\n"
                                + tPosicion + " = " +tThis + " + " + sim.puntero +";\n"
                                + "heap[" + tPosicion + "] = " + val + ";\n"
                                ;
                        valR = "heap[" + tPosicion + "]";

                        resultado[0] = codR;
                        resultado[1] = valR;
                        resultado[2] = tipo;
                    }

                }
                else
                {
                    addError("Semantico", "Error cuando se asigna Y declara la variable " +inId, "ambito(1)", "0", "0");
                    return resultado;
                }
            }
            else
            {
                if(inTipo.equalsIgnoreCase("array"))
                {
                    if(sim.tipoPuntero.equalsIgnoreCase("sptr"))
                    {
                        String t1 = getNewTerminal();
                        codR = t1 + " = sptr + " + sim.puntero + ";\n"
                                + "stack[" + t1 + "] = hptr;\n"
                                + "heap[hptr] = " +sim.dimensiones + ";\n"
                                + "hptr = hptr + 1;\n"
                                + cod
                                ;
                        resultado[0] = codR;
                        resultado[1] = valR;
                        resultado[2] = tipo;
                    }
                    else
                    {
                        String t1 = getNewTerminal();
                        String t2 = getNewTerminal();
                        String t3 = getNewTerminal();
                        
                        codR =  t1 + " = sptr + 0;\n"
                                + t2 + " = stack[" + t1 + "];\n"
                                + t3 + " = " + t2 + " + " + sim.puntero + ";\n"
                                + "heap[" + t3 + "] = hptr;\n"
                                + "heap[hptr] = " +sim.dimensiones + ";\n"
                                + "hptr = hptr + 1;\n"
                                + cod
                                ;
                        
                        resultado[0] = codR;
                        resultado[1] = valR;
                        resultado[2] = tipo;
                    }
                }
                else
                {
                    if(sim.tipoDato.equalsIgnoreCase(tipo))
                    {
                        String ed = "";
                        if(sim.tipoPuntero.equalsIgnoreCase("sptr"))
                        {
                            //valR = getNewTerminal();
                            ed = "stack";
                            String puntero = getNewTerminal();
                            codR = cod 
                                    + puntero + " = " +sim.tipoPuntero + " + " + sim.puntero + ";\n"
                                    + ed +"[" + puntero + "] = " + val + ";\n";
                            resultado[0] = codR;
                            resultado[1] = puntero;
                            resultado[2] = tipo;
                        }
                        else
                        {

                            String tPuntero = getNewTerminal();
                            String tThis = getNewTerminal();
                            String tPosicion = getNewTerminal();
                            codR =  cod
                                    + tPuntero + " = sptr + 0;\n"
                                    + tThis + " = stack[" + tPuntero + "];\n"
                                    + tPosicion + " = " +tThis + " + " + sim.puntero +";\n"
                                    + "heap[" + tPosicion + "] = " + val + ";\n"
                                    ;
                            valR = "heap[" + tPosicion + "]";

                            resultado[0] = codR;
                            resultado[1] = valR;
                            resultado[2] = tipo;
                        }

                    }
                    else
                    {
                        addError("Semantico", "Error cuando se asigna Y declara la variable " +inId, "ambito(2)", "0", "0");
                        return resultado;
                    }
                }
            }
            
        }
        else
        {//se les asigna cero o -1337
            cod = "";
            tipo = sim.tipoDato;
            if((sim.tipoPuntero.equalsIgnoreCase("sptr")))
            {
                if(tipo.equalsIgnoreCase("int")||tipo.equalsIgnoreCase("float"))
                {
                    String ed = "stack";
                    String puntero = getNewTerminal();
                    codR = puntero + " = " + sim.tipoPuntero + " + " + sim.puntero + ";\n"
                            + ed +"[" + puntero + "] = 0;\n";

                    resultado[0] = codR;
                    resultado[1] = "";
                    resultado[2] = "";
                }
                else
                {
                    String ed = "stack";
                    String puntero = getNewTerminal();
                    codR = puntero + " = " + sim.tipoPuntero + " + " + sim.puntero + ";\n"
                            + ed +"[" + puntero + "] = -1337;\n";

                    resultado[0] = codR;
                    resultado[1] = "";
                    resultado[2] = "";
                }
            }
            else
            {
                if(tipo.equalsIgnoreCase("int")||tipo.equalsIgnoreCase("float"))
                {
                    String tPuntero = getNewTerminal();
                    String tThis = getNewTerminal();
                    String tPosicion = getNewTerminal();
                    codR =  cod
                            + tPuntero + " = sptr + 0;\n"
                            + tThis + " = stack[" + tPuntero + "];\n"
                            + tPosicion + " = " +tThis + " + " + sim.puntero +";\n"
                            + "heap[" + tPosicion + "] = " + "0" + ";\n"
                            ;
                    valR = "heap[" + tPosicion + "]";
                    
                    resultado[0] = codR;
                    resultado[1] = valR;
                    resultado[2] = tipo;
                }
                else
                {
                    String tPuntero = getNewTerminal();
                    String tThis = getNewTerminal();
                    String tPosicion = getNewTerminal();
                    codR =  cod
                            + tPuntero + " = sptr + 0;\n"
                            + tThis + " = stack[" + tPuntero + "];\n"
                            + tPosicion + " = " +tThis + " + " + sim.puntero +";\n"
                            + "heap[" + tPosicion + "] = " + "-1337" + ";\n"
                            ;
                    valR = "heap[" + tPosicion + "]";
                    
                    resultado[0] = codR;
                    resultado[1] = valR;
                    resultado[2] = tipo;
                }
            }
        }
        
        return resultado;
    }
    
    public static String[] variable(String inId){
        String codR="", valR="", tipoR="";
        Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
        if(sim!=null)
        {
            if(sim.tipoPuntero.equalsIgnoreCase("sptr"))
            {
                String puntero = getNewTerminal();
                String ed = "stack";
                tipoR = sim.tipoDato;
                codR = puntero + " = " + sim.tipoPuntero + " + " + sim.puntero + ";\n";
                valR = ed + "[" + puntero + "]";
            }
            else
            {
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                String t3 = getNewTerminal();
                tipoR = sim.tipoDato;
                codR = t1 + " = sptr + 0;\n"
                        + t2 + " = stack[" + t1 + "];\n"
                        + t3 + " = " + t2 + " + " + sim.puntero + ";\n"
                        ;
                valR = "heap[" + t3 + "]";
            }
            
        }
        else
        {
            addError("Semantico", "Variable no declarada, no se encuentra disponible.", "ambito", "0", "0");
        }
        
        String[] respuesta = {codR,valR,tipoR};
        return respuesta;
    }
    
    public static String[] variable2(String inId){
        String codR="", valR="", tipoR="", punR="";
        
        Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
        if(sim!=null)
        {
            String t1 = getNewTerminal();
            String t2 = getNewTerminal();
            codR = t2 + " = " + t1 + " + " + sim.puntero + ";\n"
                    ;
            valR = "heap[" + t2 + "]";
            tipoR = sim.tipoDato;
            punR = t1;
        }
        else
        {
            addError("Semantico", "Variable2 no declarada, no se encuentra disponible", "ambito " + SimboloActual.peek().iden, "0", "0");
        }
        
        String[] respuesta = {codR,valR,tipoR,punR};
        return respuesta;
    }
    
    public static String[] objetoAtributo(String inId, String[] inAtributo){
        String codR="", valR="", tipoR="", punR="";
        Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
        if(sim!=null && inAtributo!=null)
        {
            if(sim.tipoPuntero.equalsIgnoreCase("hptr"))
            {
                String codA = inAtributo[0];
                String valA = inAtributo[1];
                String tipoA = inAtributo[2];
                String punA = inAtributo[3];
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                String t3 = getNewTerminal();
                
                codR =  t2 + " = " + t1  + " + "+ sim.puntero + ";\n"
                        + t3 + " = heap[" + t2 +"];\n"
                        + punA + " = " + t3 + ";\n"
                        + codA
                        ;
                valR = valA;
                tipoR = tipoA;
                punR = t1;
            }
            else
            {
                String tipoPuntero = sim.tipoPuntero;
                String estructuraDatos = "stack";
                if(tipoPuntero.equalsIgnoreCase("sptr"))
                    estructuraDatos = "stack";
                
                String codA = inAtributo[0];
                String valA = inAtributo[1];
                String tipoA = inAtributo[2];
                String punA = inAtributo[3];
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                
                codR =  t1 + " = "+ tipoPuntero +" + "+ sim.puntero + ";\n"
                        + t2 + " = " + estructuraDatos + "[" + t1 + "];\n"
                        + punA + " = " + t2 + ";\n"
                        + codA
                        ;
                valR = valA;
                tipoR = tipoA;
            }
        }
        else
        {
            addError("Semantico", "Variable2 no declarada, no se encuentra disponible", "ambito " + SimboloActual.peek().iden, "0", "0");
        }
        String[] respuesta = {codR,valR,tipoR,punR};
        return respuesta;
    }
    
    public static String[] asignacion(String[] inVar, String[] inExpr){
        String codR="",valR="",tipoR="";
        
        
        if(inVar!=null && inExpr!=null)
        {
            String codV = inVar[0], valV = inVar[1], tipoV = inVar[2];
            String codE = inExpr[0], valE = inExpr[1], tipoE = inExpr[2];
            if(tipoV.equalsIgnoreCase(tipoE))
            {
                codR = codE 
                        + codV 
                        + valV + " = " + valE + ";\n";
                valR = valV;
                tipoR = tipoV;
            }
            else if((tipoV.equalsIgnoreCase("int")||tipoV.equalsIgnoreCase("char") ||tipoV.equalsIgnoreCase("boolean")) && tipoE.equalsIgnoreCase("float"))
            {
                codR = codE 
                        + codV 
                        + valV + " = makeInt(" + valE + ");\n";
                valR = valV;
                tipoR = tipoV;
            }
            else if((tipoV.equalsIgnoreCase("float")) && (tipoE.equalsIgnoreCase("int") || tipoE.equalsIgnoreCase("char") || tipoE.equalsIgnoreCase("boolean")))
            {
                codR = codE 
                        + codV 
                        + valV + " = makeInt(" + valE + ");\n";
                valR = valV;
                tipoR = tipoV;
            }
            else
            {
                addError("Semantico", "Error en asignacion, los tipos no son iguales.", SimboloActual.peek().iden, "0", "0");
            }
        }
        else
        {
            System.out.println(">>>>>Error al sintetisar VARIABLE o EXPRESION. En MetodosTS.asignacion()<<<<<");
        }
        
        String[] respuesta = {codR, valR, tipoR};
        return respuesta;
    }
    
    public static String[] asignacionOp(String[] inVar, String inOp,String[] inExpr){
       
        String codR="",valR="",tipoR="";
        
        if(inVar!=null && inExpr!=null)
        {
            String codV = inVar[0], valV = inVar[1], tipoV = inVar[2];
            String codE = inVar[0], valE = inVar[1], tipoE = inVar[2];
            
            if(tipoV.equalsIgnoreCase(tipoE))
            {
                switch (inOp)
                {
                    case "+":
                        
                        break;
                    case "-":
                        
                        break;
                    case "*":
                        
                        break;
                    case "/":
                        
                        break;
                }
            }
            else
            {
                addError("Semantico", "Error en asignacion, los tipos no son iguales.", SimboloActual.peek().iden, "0", "0");
            }
        }
        else
        {
            System.out.println(">>>>>Error al sintetisar VARIABLE o EXPRESION. En MetodosTS.asignacionOp()<<<<<");
        }
        
        String[] respuesta = {codR,valR,tipoR};
        return respuesta;
    }
    
    public static String[] incremento(String[] inVar, String inOp){
        String codR="",valR="",tipoR="";
        
        if(inVar!=null)
        {
            String codV = inVar[0], valV = inVar[1], tipoV = inVar[2];
            if(tipoV.equalsIgnoreCase("int"))
            {
                String puntero = getNewTerminal();
                String result = getNewTerminal();
                codR = codV 
                        + result + " = " + valV + ";\n"
                        + puntero + " = " + result + " " + inOp + " 1;\n" 
                        + valV + " = " + puntero + ";\n"
                        ;
                valR = result;
                tipoR = "int";
            }
            else if(tipoV.equalsIgnoreCase("float"))
            {
                String puntero = getNewTerminal();
                codR = codV + puntero + " = " + valV + ";\n"
                        + puntero + " = " + puntero + " " + inOp + " 1;\n" ;
                valR = valV;
                tipoR = "float";
            }
            else
            {
                addError("Semantico", "Error en incremento, los tipos no son iguales.", SimboloActual.peek().iden, "0", "0");
            }
        }
        else
        {
            System.out.println(">>>>>Error al sintetisar VARIABLE. En MetodosTS.incremento()<<<<<");
        }
        
        String[] respuesta = {codR,valR,tipoR};
        return respuesta;
    }
    
    public static String declaraMetodo(String inNombre, String[] inCod){
        String resultado="";
        
        //Un parche para que el main se llame solo main()
        String[] parts = inNombre.split("_");
        if(parts.length>=2)
        {
            if(parts[1].equalsIgnoreCase("main"))
            {
                inNombre = "main";
            }
        }
        if(inCod!=null)
        {
            resultado = "\n" 
                    + "method " + inNombre + "(){ \n"
                    + inCod[0] + "\n"
                    + getReturn() + ":\n"
                    + "}\n\n";
        }
        else
        {
            System.out.println("Error en MetodosTS.declaraMetodo()");
        }
        return resultado;
    }
    
    public static String[] condicion(String[] expr1, String inOp, String[] expr2){
        
        String codR = "";
        String valR = getNewTerminal();
        String tipoR = "boolean";
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        String lb = getNewEtiqueta();
        
        if((expr1!=null) && (expr2!=null))
        {
            String cod1 = expr1[0];
            String val1 = expr1[1];
            String tipo1 = expr1[2];

            String cod2 = expr2[0];
            String val2 = expr2[1];
            String tipo2 = expr2[2];
            
            if((tipo1.equalsIgnoreCase("int") && (tipo2.equalsIgnoreCase("int")||tipo2.equalsIgnoreCase("float")||tipo2.equalsIgnoreCase("char")||tipo2.equalsIgnoreCase("boolean")))
               || (tipo1.equalsIgnoreCase("float") && (tipo2.equalsIgnoreCase("int")||tipo2.equalsIgnoreCase("float")||tipo2.equalsIgnoreCase("char")||tipo2.equalsIgnoreCase("boolean")))
               || (tipo1.equalsIgnoreCase("char") && (tipo2.equalsIgnoreCase("int")||tipo2.equalsIgnoreCase("float")||tipo2.equalsIgnoreCase("char")||tipo2.equalsIgnoreCase("boolean")))
               || (tipo1.equalsIgnoreCase("boolean") && (tipo2.equalsIgnoreCase("int")||tipo2.equalsIgnoreCase("float")||tipo2.equalsIgnoreCase("char")||tipo2.equalsIgnoreCase("boolean")))
                    )
            {
                codR = cod1 + cod2 + "if " + val1 +" "+ inOp + " " + val2 + " then goto " + lv + ";\n"
                        +valR + " = 0;\n"
                        +"goto " + lf + ";\n"
                        +lv + ": \n"
                        +valR + " = 1;\n"
                        +lf + ": \n"
                        ;
                
            }
            else if(tipo1.equalsIgnoreCase("string") && tipo2.equalsIgnoreCase("string"))
            {
                //Generacion de código para comprar juails.
                
            }
        }
        
        String[] resultado = {codR,valR,tipoR};
        return resultado;
    }
    
    public static String[] condiciones(String[] inCond1, String op, String[] inCond2){
        String codR = "";
        String valR = getNewTerminal();
        String tipoR = "boolean";
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        String la = getNewEtiqueta();
        
        if(inCond1!=null && inCond2!=null)
        {
            String cod1 = inCond1[0];
            String val1 = inCond1[1];
            String tipo1 = inCond1[2];

            String cod2 = inCond2[0];
            String val2 = inCond2[1];
            String tipo2 = inCond2[2];
            
            if(op.equalsIgnoreCase("or"))
            {
                codR = cod1 + cod2
                        +"if " + val1 + " == 1 then goto " + lv +";\n"
                        +"goto " + lf + ";\n"
                        +lf + ":\n"
                        +"if " + val2 + " == 1 then goto " + lv +";\n"
                        +valR + " = 0;\n"
                        +"goto " + la + ";\n"
                        +lv + ":\n"
                        +valR + " = 1;\n"
                        +la + ":\n"
                        ;
            }
            else if(op.equalsIgnoreCase("and"))
            {
                codR = cod1 + cod2
                        +"if " + val1 + " == 1 then goto " + lv +";\n"
                        +valR + " = 0;\n"
                        +"goto " + lf + ";\n"
                        +lv + ":\n"
                        +"if " + val2 + " == 1 then goto " + la +";\n"
                        +valR + " = 0;\n"
                        +"goto " + lf + ";\n"
                        +la + ":\n"
                        +valR + " = 1;\n"
                        +lf + ":\n"
                        ;
            }
            
        }
        else if(inCond1!=null && inCond2==null)
        {   
            String cod1 = inCond1[0];
            String val1 = inCond1[1];
            String tipo1 = inCond1[2];
            if(op.equalsIgnoreCase("not"))
            {
                codR = cod1
                        + "if " + val1 + " == 1 then goto " + lv + ";\n"
                        + valR + "= 1;\n"
                        + "goto " + lf
                        + lv + ":\n"
                        + valR + "= 0;\n"
                        + lf + ":\n"
                        ;
            }
        }
        else
        {
            System.out.println("Error en MetodosTS.condiciones()");
        }
        
        String[] resultado = {codR,valR,tipoR};
        return resultado;
    }
    
    public static String[] sentenciaIf(String[] inCond, String[] inIns, String[] inElse){
        String cod="";
        String val="";
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        if(inCond!=null && inElse!=null && inIns!=null)
        {
            String cod1 = inCond[0];
            String val1 = inCond[1];
            val = inElse[1];
            cod = cod1 
                    + "if " + val1 + " == 1 then goto " + lv + ";\n"
                    + "goto " +lf + ";\n"
                    + lv + ":\n"
                    + inIns[0] 
                    + "goto " + inElse[1] + ";\n"
                    + lf + ":\n"
                    + inElse[0] 
                    ;
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaIf()");
        }
        String[] resultado = {cod, val, ""};
        return resultado;
    }
    
    public static String[] sentenciaElse(String[] inIns){
        
        String cod="";
        String val=getNewEtiqueta();
        if(inIns!=null)
        {
            cod = inIns[0]
                    + val + ":\n"
                    ;
        }
        String[] resultado = {cod, val, ""};
        return resultado;
    }
    
    public static String[] getValorVariable(String[] inVar){
        String codR="";
        String valR= getNewTerminal();
        String tipoR="";
        
        if(inVar!=null)
        {
            String cod = inVar[0];
            String val = inVar[1];
            String tipo = inVar[2];
            
            codR = cod + 
                    valR + " = " + val + ";\n"
                    ;
            tipoR = tipo;
        }
        else
        {
            System.out.println("Error en MetodosTS.getValorVariable(). Vino un null.");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] instruccioneslow(String[] inInstruccioneslow, String[] inInstruccionlow){
        String codR="";
        String valR= "";
        String tipoR="";
        
        if(inInstruccioneslow!=null && inInstruccionlow!=null)
        {
            codR = inInstruccioneslow[0] 
                    + inInstruccionlow[0];
        }
        else
        {
            System.out.println("Error en MetodosTS.instruccioneslow(). Hay nulls");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaFor(String[] inAsigna1, String[] inCond, String[] inIns, String[] inAsigna2){
        String codR="";
        String valR= "";
        String tipoR="";
        String li = getNewEtiqueta();
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        
        if(inAsigna1!=null && inCond!=null && inIns!=null && inAsigna2!=null && BreakActual.peek()!=null)
        {
            String tv = inCond[1];
            
            codR = inAsigna1[0]
                    + li + ":\n"
                    + inCond[0]
                    + "if " + tv + " == 1 then goto " + lv + ";\n"
                    + "goto " +lf + ";\n"
                    + lv + ":\n"
                    + inIns[0]
                    + inAsigna2[0]
                    +"goto " + li + ";\n"
                    + lf + ":\n"
                    + getBreak() + ":\n"
                    ;
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaFor(). Hay nulls.");
        }
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaBreak(){
        String codR="";
        String valR= "";
        String tipoR="";
        if(BreakActual.peek()!=null)
        {
            codR = "goto " + getBreak() + ";\n";
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaBreak().");
        }
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaWhile(String[] inCond, String[] inIns){
        String codR="";
        String valR= "";
        String tipoR="";
        String li = getNewEtiqueta();
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        
        if(inCond!=null && inIns!=null &&BreakActual.peek()!=null)
        {
            String tv = inCond[1];
            codR = li + ":\n"
                    +inCond[0]
                    +"if " + tv + " == 1 then goto " + lv + ";\n"
                    +"goto " +lf + ";\n"
                    +lv + ":\n"
                    +inIns[0]
                    +"goto " + li + ";\n"
                    +lf + ":\n"
                    + getBreak() + ":\n";
                    ;
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaDoWhile(String[] inCond, String[] inIns){
        String codR="";
        String valR= "";
        String tipoR="";
        String li = getNewEtiqueta();
        String lf = getNewEtiqueta();
        
        if(inCond!=null && inIns!=null && BreakActual.peek()!=null)
        {
            String tv = inCond[1];
            codR = li + ":\n"
                    +inIns[0]
                    +inCond[0]
                    +"if " + tv + " == 1 then goto " + li+";\n"
                    +"goto " + lf + ";\n"
                    + lf + ":\n"
                    + getBreak() + ":\n"
                    ;
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaDoWhile(). Hay nulls :(");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    
    public static String[] sentenciaCase(String[] inVar, String[] inExp, String[] inIns){
        String codR="";
        String valR= getNewTerminal();
        String tipoR="";
        String lv = getNewEtiqueta();
        String lf = getNewEtiqueta();
        
        if(inVar!=null && inIns!=null && inExp!=null && BreakActual.peek()!=null)
        {
            String codV = inVar[0];
            String valV = inVar[1];
            String tipoV = inVar[2];
            
            String codE = inExp[0];
            String valE = inExp[1];
            String tipoE = inExp[2];
            
            if(tipoV.equalsIgnoreCase(tipoE))
            {
                if(tipoV.equalsIgnoreCase("string"))
                {
                    
                }
                else if(tipoV.equalsIgnoreCase("int")||tipoV.equalsIgnoreCase("char"))
                {
                    codR = codV
                            + valR + " = " +valV + ";\n"
                            + codE
                            + "if " + valR + " == " +valE + " then goto "+ lv +";\n"
                            + "goto " +lf + ";\n"
                            + lv + ":\n"
                            + inIns[0]
                            + lf + ":\n"
                            ;
                    valR = valV;
                    tipoR = tipoV;
                }
                else
                {
                    addError("Semantico", 
                          "Tipos de dato no compatibles, tiene que ser iguales.", 
                          "ambito switch", 
                          "0", 
                          "0");
                }
            }
            
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaSwitch(). Hay nulls :(");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    
    public static String[] sentenciaSwitch(String[] inCase, String[] inDefault){
        String codR="";
        String valR= "";
        String tipoR="";
        
        if(inCase!=null && inDefault!=null && BreakActual.peek()!=null)
        {
            codR = inCase[0]
                    + inDefault[0]
                    + getBreak() + ":\n"
                    ;
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaDoWhile(). Hay nulls :(");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String crearInit(String inId, String inContenido, boolean inExisteConstructor){
        String resultado="";
        String nombreConstructor = inId + "_" + inId + "()";
        if(inExisteConstructor==true)
            nombreConstructor = inId + "_init()";
        
        if(SimboloActual.peek()!=null)
        {
            Simbolo si = existeSimbolo(SimboloActual.peek(), inId);
            Simbolo sim = SimboloActual.peek();
            String size = sim.tamano;
            String tThis = getNewTerminal();
            String tReturn = getNewTerminal();
            String tPtr = getNewTerminal();
            String ins = tThis + " = hptr;\n"
                    + tPtr + " = sptr + 0;\n"
                    + "stack[" + tPtr + "] = hptr;\n"
                    + "hptr = hptr + " + size + ";\n"
                    + inContenido
                    + tReturn + " = sptr + 1;\n"
                    + "stack[" +tReturn + "] = " + tThis + ";\n"
                    ;
            String codR = "method " + nombreConstructor + "{\n"
                        + ins
                        + "}\n\n";
            resultado = codR;
        }
        else
        {
            System.out.println("Error en MetodosTS.crearInit()");
        }
        return resultado;
    }
    
    public static String crearConstructor(String inClase, String inId, String[] inIns){
        String resultado = "";
        String nombreInit = inClase + "_init()";
        if(SimboloActual.peek()!=null && inIns!=null)
        {
            Simbolo sim = existeSimbolo(SimboloActual.peek(), inId);
            if(sim != null)
            {
                String size = sim.tamano;
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                String t3 = getNewTerminal();
                String t4 = getNewTerminal();
                String codR = "sptr = sptr + " +size + ";\n"
                            + "call " + nombreInit + ";\n"
                            + t1 + " = sptr + 1;\n"
                            + t2 + " = stack[" + t1 + "];\n"
                            + "sptr = sptr - " + size + ";\n"
                            + t3 + " = sptr + 1;\n"
                            + "stack[" + t3 + "] = " + t2 + ";\n"
                            + t4 + " = sptr + 0;\n"
                            + "stack[" + t4 + "] = " + t2 + ";\n"
                            + inIns[0]
                            ;
                resultado = "method " + inId + "(){\n"
                        + codR
                        + getReturn() + ":\n"
                        +"}\n\n"
                        ;
            }
            else
            {
                System.out.println("Error en MetodosTS.crearConstructor(1)");
            }
        }
        else
        {
            System.out.println("Error en MetodosTS.crearConstructor(2)");
        }
        
        return resultado;
    }
    
    public static String[] argumentosArray(String[] inExpr, String[] inDim){
        String codR="", valR="", sizeR="";
        
        if(inExpr!=null && inDim==null)
        {
            
            String codE = inExpr[0];
            String valE = inExpr[1];
            String tipoE = inExpr[2];
            if(tipoE.equalsIgnoreCase("int"))
            {
                String t1 = getNewTerminal();
                codR = codE
                        + t1 + " = " + valE + ";\n"
                        + "heap[hptr] = " + t1 + ";\n"
                        + "hptr = hptr + 1;\n"
                        ;
                valR = t1;
            }
            else
            {
                addError("Semantico", "Jojaphviz solo acepta valores tipo int en las dimesiones del array", "ambito " + SimboloActual.peek().iden, "0", "0");
            }
        }
        else if(inExpr!=null && inDim!=null)
        {
            String codE = inExpr[0];
            String valE = inExpr[1];
            String tipoE = inExpr[2];
            
            String codD = inDim[0];
            String valD = inDim[1];
            
            if(tipoE.equalsIgnoreCase("int"))
            {
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                codR = codD
                        + codE
                        + t1 + " = " + valE + ";\n"
                        + "heap[hptr] = " + t1 + ";\n"
                        + "hptr = hptr + 1;\n"
                        + t2 + " = " + valD + " * " + t1 + ";\n"
                        ;
                valR = t2;
            }
            else
            {
                addError("Semantico", "Jojaphviz solo acepta valores tipo int en las dimesiones del array", "ambito " + SimboloActual.peek().iden, "0", "0");
            }
        }
        else
        {
            System.out.println("Error en MetodosTS.argumentosArray()");
        }
        String[] resultado = {codR, valR, sizeR};
        return resultado;
    }
    
    public static String[] newArray(String[] argsArray){
        String codR="", valR="", tipoR="";
        
        if(argsArray!=null)
        {
            String codA = argsArray[0];
            String valA = argsArray[1];
            
            codR = codA
                    + "hptr = hptr + " + valA + ";\n"
                    ;
            tipoR = "array";
        }
        else
        {
            System.out.println("Error en MetodosTS.newArray()");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] getVector(String inId, String[] inExpr, String[] inVector){
        String codR="", valR="", tipoR="";
        String punA="", indA="", punD="";
        
        if(inId!=null && inExpr!=null && inVector==null)
        {
            Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
            if(sim!=null)
            {
                tipoR = sim.tipoDato;
                String codE = inExpr[0];
                String valE = inExpr[1];

                if(sim.tipoPuntero.equalsIgnoreCase("sptr"))
                {//Es un array local
                    String t1 = getNewTerminal();
                    String t2 = getNewTerminal();
                    String t3 = getNewTerminal();
                    String t4 = getNewTerminal();
                    String t5 = getNewTerminal();
                    String t5a = getNewTerminal();
                    String t6 = getNewTerminal();
                    String t7 = getNewTerminal();
                    codR =  "//Comienza acceso a array local.\n"
                            + t1 + " = sptr + " + sim.puntero + ";\n"
                            + t2 + " = stack[" + t1 + "];\n"
                            + codE
                            + t3 + " = " + valE + ";\n"
                            + t4 + " = " + t2 + " + 1;\n"  
                            + t5 + " = heap[" + t2 + "];\n"
                            + t5a +" = " + t5 + " + " + t2 + ";\n"
                            + t6 + " = " + t5a + " + 1;\n"
                            + t7 + " = " + t6 + " + " + t3 + ";\n"
                            ;
                    valR = "heap[" + t7 + "]";
                            ;
                    indA = t4;
                    punD = t3;
                    punA = t2;
                }
                else
                {//Es un 
                    String ta = getNewTerminal();
                    String t0 = getNewTerminal();
                    String t1 = getNewTerminal();
                    String t2 = getNewTerminal();
                    String t3 = getNewTerminal();
                    String t4 = getNewTerminal();
                    String t5 = getNewTerminal();
                    String t5a = getNewTerminal();
                    String t6 = getNewTerminal();
                    String t7 = getNewTerminal();
                    codR = "//Comienza un acceso a un array que es atributo. \n"
                            + ta + " = sptr + 0;\n"
                            + t0 + " = stack[" + ta + "]; \n"
                            + t1 + " = " + t0 + " + " + sim.puntero + ";\n"
                            + t2 + " = heap[" + t1 + "];\n"
                            + codE
                            + t3 + " = " + valE + ";\n"
                            + t4 + " = " + t2 + " + 1;\n"  
                            + t5 + " = heap[" + t2 + "];\n"
                            + t5a +" = " + t5 + " + " + t2 + ";\n"
                            + t6 + " = " + t5a + " + 1;\n"
                            + t7 + " = " + t6 + " + " + t3 + ";\n"
                            ;
                    valR = "heap[" + t7 + "]";
                            ;
                    indA = t3;
                    punD = t4;
                    punA = t2;
                }

            }
            else
            {
                addError("Semantico", "Array no ha sido declarado en el ambito (1).", "Ambito " + SimboloActual.peek().iden, "0", "0");
            }
        }
        else if(inId==null && inExpr!=null && inVector!=null)
        {
            String codE = inExpr[0];
            String valE = inExpr[1];
            
            String codV = inVector[0];
            String valV = inVector[1];
            String tipoV = inVector[2];
            String punAV = inVector[3];
            String indV = inVector[4];
            String punV = inVector[5];
            
            String t1 = getNewTerminal();
            String t2 = getNewTerminal();
            String t3 = getNewTerminal();
            String t4 = getNewTerminal();
            String t5 = getNewTerminal();
            String t6 = getNewTerminal();
            String t6a = getNewTerminal();
            String t7 = getNewTerminal();
            String t8 = getNewTerminal();
            
            codR = codV 
                    + t1 + " = " + punV + " + 1;\n"                             //Puntero dim
                    + t2 + " = heap["+t1+"];\n"
                    + codE
                    + t3 + " = " + valE + ";\n"                                 //IndiceAnterior
                    + t4 + " = " + indV + " * " + t2 +";\n"
                    + t5 + " = " + t4 + " + " + t3 + ";\n"
                    + t6 + " = heap[" + punAV + "];\n"
                    + t6a+ " = " + t6 + " + " + punAV + ";\n"
                    + t7 + " = " + t6a + " + 1;\n"
                    + t8 + " = " + t7 + " + " + t5 + ";\n"
                    ;
            valR = "heap[" + t8 +"]";
            tipoR = tipoV;
            
            punA = punAV;
            indA = t3;
            punD = t1;
            
        }
        else
        {
            System.out.println("Vergueo en MetodosTS.getVector();");
        }
        
        // punA="", indA="", punD="";
        String[] resultado = {codR, valR, tipoR, punA, indA, punD};
        return resultado;
    }
    
    public static String[] getVector2(String inId, String[] inExpr, String[] inVector){
        String codR="", valR="", tipoR="";
        String puntTx="", punA="", indA="", punD="";
        
        if(inId!=null && inExpr!=null && inVector==null)
        {
            Simbolo sim = existeSimbolo2(SimboloActual.peek(), inId);
            String codE = inExpr[0];
            String valE = inExpr[1];
            
            if(sim!=null)
            {
                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                String t3 = getNewTerminal();
                String t4 = getNewTerminal();
                String t5 = getNewTerminal();
                String t6 = getNewTerminal();
                String t6a = getNewTerminal();
                String t7 = getNewTerminal();
                String t8 = getNewTerminal();
                
                codR = t2 + " = " + t1 + " + " + sim.puntero + ";\n"
                        + t3 + " = heap["+ t2 + "];\n"
                        + codE
                        + t4 + " = " + valE + ";\n"
                        + t5 + " = " + t3 + " + 1;\n"  
                        + t6 + " = heap[" + t3 + "];\n"
                        + t6a+ " = " + t6 + " + " + t3 +";\n"
                        + t7 + " = " + t6a + " + 1;\n"
                        + t8 + " = " + t7 + " + " + t4 + ";\n"
                        ;
                valR = "heap[" + t8 + "]";
                tipoR = sim.tipoDato;
                
                puntTx = t1;
                punA = t3;
                indA = t4;
                punD =  t5;
            }
            else
            {
                addError("Semantico", "Array no ha sido declarado en el ambito (2).", "Ambito " + SimboloActual.peek().iden, "0", "0");
            }
        }
        else if(inId==null && inExpr!=null && inVector!=null)
        {
            String codE = inExpr[0];
            String valE = inExpr[1];
            
            String codV = inVector[0];
            String valV = inVector[1];
            String tipoV = inVector[2];
            String tx   = inVector[3];
            String punAV = inVector[4];
            String indV = inVector[5];
            String punV = inVector[6];
            
            String t1 = getNewTerminal();
            String t2 = getNewTerminal();
            String t3 = getNewTerminal();
            String t4 = getNewTerminal();
            String t5 = getNewTerminal();
            String t6 = getNewTerminal();
            String t6a = getNewTerminal();
            String t7 = getNewTerminal();
            String t8 = getNewTerminal();
            
            codR = codV 
                    + t1 + " = " + punV + " + 1;\n"                             //Puntero dim
                    + t2 + " = heap["+t1+"];\n"
                    + codE
                    + t3 + " = " + valE + ";\n"                                 //IndiceAnterior
                    + t4 + " = " + indV + " * " + t2 +";\n"
                    + t5 + " = " + t4 + " + " + t3 + ";\n"
                    + t6 + " = heap[" + punAV + "];\n"
                    + t6a+ " = " + t6 + " + " + punAV +";\n"
                    + t7 + " = " + t6a + " + 1;\n"
                    + t8 + " = " + t7 + " + " + t5 + ";\n"
                    ;
            valR = "heap[" + t8 +"]";
            tipoR = tipoV;
            
            punA = punAV;
            indA = t3;
            punD = t1;
            puntTx = tx;
        }
        
        String[] resultado = {codR, valR, tipoR,puntTx, punA, indA, punD};
        return resultado;
    }
    
    public static Simbolo getFuncionActual(){
        
        Simbolo sim = SimboloActual.peek();
        while(sim!=null)
        {
            if(sim.tipo!=null)
            {
                if(sim.tipo.equalsIgnoreCase("metodo") || sim.tipo.equalsIgnoreCase("constructor"))
                {
                    return sim;
                }
            }
            sim = sim.padre;
        }
        System.out.println("Cagadales en MetodosTS.getFuncionActual(). No se encontró la funcion padre.");
        return null;
    }
    
    public static String[] newObjeto(String inId, ArrayList<String[]> inLista){
        String codR = "", valR = "", tipoR = "";                  //Para guardar los temporales con el val de los param
        String listaTipos = "";                                                 //Para comparar los tipos
        String codigoAsignaciones="";
        String codigoParametros = "";
        String codigoPasoParametros="";
        String nombreConstructor = inId + "_" + inId;
        String tP = getNewTerminal();
        String tR = getNewTerminal();
        
        Simbolo sim = getFuncionActual();
        if(sim!=null)
        {
            for(int i =0; i<inLista.size(); i++)
            {
                codigoParametros = codigoParametros                             //0
                        + inLista.get(i)[0];

                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                codigoAsignaciones = codigoAsignaciones                         //1
                        + t1 + " = " + inLista.get(i)[1] + ";\n";
                
                codigoPasoParametros = codigoPasoParametros                     //3
                        + t2 + " = sptr + " + String.valueOf((2+i)) + ";\n"
                        + "stack[" + t2 + "] = " + t1 + ";\n"
                        ;
                
                listaTipos = listaTipos + "_"+ inLista.get(i)[2];
            }
            
            //String idAux = nombreConstructor + listaTipos;
            Simbolo tipo = TablaDeTipos.get(inId);
            if(tipo!=null)
            {
                String nombreFun =  nombreConstructor + listaTipos;
                Simbolo simFun = existeSimbolo2(tipo, nombreFun);
                try
                {
                    nombreConstructor = nombreConstructor + listaTipos + "()";
                    String entroFuncion = "sptr = sptr + " + sim.tamano + ";\n";
                    String salgoFuncion = "sptr = sptr - " + sim.tamano + ";\n";
                    
                   
                    codR = "//Inicio de un new Objeto(). newObjeto()  \n"
                            + codigoParametros
                            + codigoAsignaciones
                            + entroFuncion
                            + codigoPasoParametros
                            + "call " + nombreConstructor + ";\n"
                            + tP + " = sptr + 1;\n"
                            + tR + " = stack[" + tP + "];\n"
                            + salgoFuncion
                            ;
                    valR = tR;
                    tipoR = inId.toLowerCase();
                }
                catch(Exception ex)
                {
                    addError("Semanatico", "Error en los parametros del new()", SimboloActual.peek().iden, "0", "0");
                    
                }
                
            }
            else
            {
                addError("Semanatico", "Error, la clase que intenta instanciar no ha sido importada.", SimboloActual.peek().iden, "0", "0");

            }
            
        }
        else
        {
            System.out.println("Error masivo en MetodosTS.newObjeto()");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaReturn(String[] inExpr){
        String codR="", valR="", tipoR="";
        if(inExpr!=null)
        {
            String codE = inExpr[0];
            String valE = inExpr[1];
            String tipoE = inExpr[2];
            Simbolo simFun = getFuncionActual();
            if(simFun!=null)
            {
                //Si da mucho clavo esto se puede quitar y no se verifica el tipo de dato del return
                if(tipoE.equalsIgnoreCase(simFun.tipoDato))
                {
                    String t2 = getNewTerminal();
                    String t1 = getNewTerminal();
                    codR =   "//Inicio del return. CodE + lo del return y un break. sentenciaReturn() \n"
                            + codE
                            + t2 + " = " + valE + ";\n"
                            + t1 + " = sptr + 1;\n"
                            + "stack["+ t1 + "] = " + t2 + ";\n"
                            ;
                    valR = valE;
                    tipoR = tipoE;
                    if(ReturnActual.peek()!=null)
                    {
                        codR = codR +
                                "goto " + getReturn() + ";\n";
                    }
                    else
                    {
                        System.out.println("Error en MetodosTS.sentenciaReturn(0).");
                    }
                }
                else
                {
                    addError("Semantico", "Error en el return, tipo de dato incorreccto. ", simFun.iden, simFun.fila, simFun.fila);
                }
            }
            else{System.out.println("Error en MetodosTS.sentenciaReturn(2)");}
        }
        else
        {
            System.out.println("Error en MetodosTS.sentenciaReturn(1)");
        }
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] sentenciaFuncion2(String inClase, String inNombre, ArrayList<String[]> inLista){
        String codR = "", valR = "", tipoR = "", punR = "";
        
        String listaTipos = "";                                                 //Para comparar los tipos
        String codigoAsignaciones="";
        String codigoParametros = "";
        String codigoPasoParametros="";
        
        //Simbolo tipoClase = existeSimbolo2(SimboloActual.peek(), inClase);
        String nombreFuncion = SimboloActual.peek().tipoDato + "_" + inNombre;
        
        for(int i = 0; i <inLista.size(); i++)
        {
            codigoParametros = codigoParametros                                 //0
                        + inLista.get(i)[0];

                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                codigoAsignaciones = codigoAsignaciones                         //1
                        + t1 + " = " + inLista.get(i)[1] + ";\n";
                
                codigoPasoParametros = codigoPasoParametros                     //3
                        + t2 + " = sptr + " + String.valueOf((2+i)) + ";\n"
                        + "stack[" + t2 + "] = " + t1 + ";\n"
                        ;
                
            listaTipos = listaTipos + "_" + inLista.get(i)[2];
        }
        
        nombreFuncion = nombreFuncion + listaTipos;
        Simbolo simFun = existeSimbolo2(SimboloActual.peek(), nombreFuncion);
        Simbolo simCallingFun = getCallingFunction();
        
        if(simFun!=null && simCallingFun!=null)
        {
            String t1 = getNewTerminal();                                       //este es el this o punR
            String t2 = getNewTerminal();
            String tP = getNewTerminal();
            String tR = getNewTerminal();
            String entroFuncion = "sptr = sptr + " + simCallingFun.tamano + ";\n";
            String salgoFuncion = "sptr = sptr - " + simCallingFun.tamano + ";\n";
            String codThis = t2 + " = sptr + 0;\n"
                            + "stack[" + t2 + "] = " + t1 + ";\n"
                            ;
            
            codR =  "//Inicio de llamada a una funcion de un objeto. Obj.fun1(). sentenciaFuncion2() \n"
                    + codigoParametros
                    + codigoAsignaciones
                    + entroFuncion
                    + codThis
                    + codigoPasoParametros
                    + "call " + nombreFuncion + "();\n"
                    + tP + " = sptr + 1;\n"
                    + tR + " = stack[" + tP + "];\n"
                    + salgoFuncion
                    ;
            
            punR = t1;
            valR = tR;
            tipoR = simFun.tipoDato;
            
        }
        else
        {
            addError("Semantico", "Metodo " + nombreFuncion + " no existe en la clase " + inClase + ". MetodosTS.sentenciaFuncion()", inClase, "0", "0");
        }   
        
        String[] resultado = {codR, valR, tipoR, punR};
        return resultado;
    }
    
    public static String[] sentenciaFuncion(String inClase, String inNombre, ArrayList<String[]> inLista){
        String codR = "", valR = "", tipoR = "";
        
        String nombreFuncion= inClase + "_" + inNombre;
        String listaTipos = "";                                                 //Para comparar los tipos
        String codigoAsignaciones="";
        String codigoParametros = "";
        String codigoPasoParametros="";
        
        for(int i = 0; i <inLista.size(); i++)
        {
            codigoParametros = codigoParametros                                 //0
                        + inLista.get(i)[0];

                String t1 = getNewTerminal();
                String t2 = getNewTerminal();
                codigoAsignaciones = codigoAsignaciones                         //1
                        + t1 + " = " + inLista.get(i)[1] + ";\n";
                
                codigoPasoParametros = codigoPasoParametros                     //3
                        + t2 + " = sptr + " + String.valueOf((2+i)) + ";\n"
                        + "stack[" + t2 + "] = " + t1 + ";\n"
                        ;
                
            listaTipos = listaTipos + "_" + inLista.get(i)[2];
        }
        
        nombreFuncion = nombreFuncion + listaTipos;
        Simbolo simFun = existeSimbolo2(SimboloActual.peek(), nombreFuncion);
        Simbolo simFunActual = getFuncionActual();
        if(simFun!=null)
        {
            String entroFuncion = "sptr = sptr + " + simFunActual.tamano + ";\n";
            String salgoFuncion = "sptr = sptr - " + simFunActual.tamano + ";\n";
            
            String t1 = getNewTerminal();
            String t2 = getNewTerminal();
            String t3 = getNewTerminal();
            String tP = getNewTerminal();
            String tR = getNewTerminal();
            String codigoThis = t1 + " = sptr + 0;\n"
                                + t2 + " = stack[" +t1 +"];\n"
                                ;
            
            String codigoSetThis = t3 + " = sptr + 0;\n"
                            + "stack[" + t3 + "] = " + t2 + ";\n"
                            ;
            
            codR = "//Inicio de funcion de la misma clase \n"
                    + codigoParametros
                    + codigoAsignaciones
                    + codigoThis
                    + entroFuncion
                    + codigoSetThis
                    + codigoPasoParametros
                    + "call " + nombreFuncion + "();\n"
                    + tP + " = sptr + 1;\n"
                    + tR + " = stack[" + tP + "];\n"
                    + salgoFuncion
                    ;
            
            valR = tR;
            tipoR = simFun.tipoDato;
            
        }
        else
        {
            addError("Semantico", "Metodo " + nombreFuncion + " no existe en la clase " + inClase + ". MetodosTS.sentenciaFuncion(). Puede que los parametros estén malos.", inClase, "0", "0");
        }
        
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static Simbolo getCallingFunction(){
        
        for(int i = SimboloActual.size()- 1; i>=0; i-- )
        {
            Simbolo sim = SimboloActual.get(i);
            if(sim.tipo.equalsIgnoreCase("metodo")|| sim.tipo.equalsIgnoreCase("constructor"))
            {
                return sim;
            }
        }
        System.out.println("Algo se me tuvo que haber olvidado. MetodosTS.getCallingFunction()");
        return null;
    }
    
    public static String[] casteo (String inTipo, String[] inExpr){
        String codR = "", valR = "", tipoR = "";
        if(inExpr!=null)
        {
            String cod = inExpr[0];
            String val = inExpr[1];
            String tipo = inExpr[2];
            if(inTipo.equalsIgnoreCase(tipo))
            {
                codR = cod;
                valR = val;
                tipoR = tipo;
            }
            else if((tipo.equalsIgnoreCase("int")||tipo.equalsIgnoreCase("char")||tipo.equalsIgnoreCase("boolean")) && inTipo.equalsIgnoreCase("float") )
            {
                codR = cod;
                valR = val;
                tipoR = inTipo;
            }
            else if((tipo.equalsIgnoreCase("int")||tipo.equalsIgnoreCase("char")||tipo.equalsIgnoreCase("boolean")) && (inTipo.equalsIgnoreCase("int")||inTipo.equalsIgnoreCase("char")||inTipo.equalsIgnoreCase("boolean")))
            {
                codR = cod;
                valR = val;
                tipoR = inTipo;
            }
            else if(tipo.equalsIgnoreCase("float") && (inTipo.equalsIgnoreCase("int")|| inTipo.equalsIgnoreCase("char")|| inTipo.equalsIgnoreCase("boolean")))
            {
                String t1 = getNewTerminal();
                codR = cod
                        + "//Casteo de float a " + inTipo + "\n"
                        + t1 + " = floatToInt(" + val + ");\n"
                        ;
                valR = t1;
                tipoR = inTipo; 
            }
            else
            {
                codR = cod;
                valR = val;
                tipoR = tipo;
                addError("Semantico", "Imposible castear esos tipos de datos, esto no es Java. " + tipo + "->" +inTipo + ". MetodosTS.casteo()", SimboloActual.peek().iden, "0", "0");
            }
        }
        else
        {
            System.out.println("Error en MetodosTS.casteo()");
        }
        String[] resultado = {codR, valR, tipoR};
        return resultado;
    }
    
    public static String[] funcionEspecial(String inFuncion, ArrayList<String[]> inLista){
        String codR="";
        String codArgs="";
        String listaTipos="";
        String listaArgs="";
        for(int i = 0; i<inLista.size(); i++)
        {
            String cod = inLista.get(i)[0];
            String val = inLista.get(i)[1];
            String tipo = inLista.get(i)[2];
            
            String t1 = getNewTerminal();
            
            codArgs = codArgs
                    + cod
                    + t1 + " = " + val + ";\n"
                    ;
            if(listaArgs.length()>0)
            {listaArgs = listaArgs + "," + t1;}
            else{listaArgs = t1;}
            
            if(listaTipos.length()>0)
            {listaTipos = listaTipos + "_" + tipo;}
            else{listaTipos = tipo;}
        }
        
        if(isTiposCorrecto(inFuncion, listaTipos))
        {
            if(inFuncion.equalsIgnoreCase("imprimir"))
            {
                int modulo = getTipoImprimir(listaTipos);
                codR = codArgs
                        + "callf imprimir(" + listaArgs + ", " + String.valueOf(modulo) + ");\n"
                        ;
            }
            else
            {
                codR = codArgs
                        + "callf " + inFuncion +"(" + listaArgs + ");\n"
                        ;
            }
        }
        else
        {
            codR = "//Error.funcionEspecial \n";
            addError("Semantico", "Error en la funcion especial " + inFuncion +". Tipos de argumentos no validos.", SimboloActual.peek().iden, "0", "0");
        }
        
        String[] respuesta = {codR,"",""};
        return respuesta;
    }
    
    public static boolean isTiposCorrecto(String inFuncion, String inTipos){
    
        switch(inFuncion){
            case "linea":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int_int_int"))
                {return true;}
                break;
            case "texto":
                if(inTipos.equalsIgnoreCase("string_int_int_int_int_int"))
                {return true;}
                break;
            case "arco":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int_int_int_int_int_boolean"))
                {return true;}
                break;
            case "rectangulo":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int_int_int_boolean"))
                {return true;}
                break;  
            case "ovalo":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int_int_int_boolean"))
                {return true;}
                break;
            case "poligono":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int_boolean"))
                {return true;}
                break;    
            case "lienzo":
                if(inTipos.equalsIgnoreCase("int_int_int_int_int"))
                {return true;}
                break;  
            case "imprimir":
                if(inTipos.equalsIgnoreCase("string") || inTipos.equalsIgnoreCase("int") || inTipos.equalsIgnoreCase("float") || inTipos.equalsIgnoreCase("char") || inTipos.equalsIgnoreCase("boolean"))
                {return true;}
                break;  
                
            default:
                System.out.println("Error, el tipo de funcion no existe. Verifique en MetodosTS.isTiposCorrecto()");
                break;
        }
        return false;
    }
    
    public static int getTipoImprimir(String inTipo){
        switch(inTipo)
        {
            case "string":
                return 0;
            case "int":
                return 1;
            case "float":
                return 2;
            case "char":
                return 3;
            case "boolean":
                return 4;
        }
        //Si no es ninguno de esos entonces que sea float.
        return 2;
    }
    
    public static String[] opTernario(String[] inCond, String[] inExpr1, String[] inExpr2){
        String codR ="", valR = "", tipoR ="";
        if(inCond!=null && inExpr1!=null & inExpr2!=null)
        {
            String codC = inCond[0];
            String valC = inCond[1];
            
            String codE1 = inExpr1[0];
            String valE1 = inExpr1[1];
            String tipoE1 = inExpr1[2];
            
            String codE2 = inExpr2[0];
            String valE2 = inExpr2[1];
            String tipoE2 = inExpr2[2];
            
            String tV =getNewTerminal();
            String lv =getNewTerminal();
            String lf =getNewTerminal();
            
            codR = codC
                    +codE1
                    +codE2
                    + "if " + valC + " == 1 then goto " +lv + ";\n"
                    + tV + " = " +  valE2 + ";\n"
                    + "goto " + lf + ";\n"
                    + lv + ":\n"
                    + tV + " = " +  valE1 + ";\n"
                    + lf + ":\n"
                    ;
            valR = tV;
            tipoR = tipoE1;
        }
        else
        {
            System.out.println("Error en MetodosTS.opTernario().");
        }
        String[] respuesta = {codR,valR,tipoR};
        return respuesta;
    }
    
    public static void putPrincipalAsTipo(){
        TablaDeTipos.put(SimboloActual.peek().iden, SimboloActual.peek());
    }
    
    public static void addTexto3d(String inTexto){
        texto3d = texto3d + inTexto;
    }
    
    public Simbolo getThis(String inClase){
        Simbolo sim=null;
        
        return sim;
    }
            
    public static String getNewTerminal(){
        String terminal = "t" + String.valueOf(contadorTerminales++);
        return terminal;
    }
    
    public static String getNewEtiqueta(){
        String terminal = "L" + String.valueOf(contadorEtiquetas++);
        return terminal;
    }
    
    public static void setNewBreak(){
        String etiquetaBreak = getNewEtiqueta();
        BreakActual.push(etiquetaBreak);
    }
    
    public static void popBreak(){
        if(BreakActual.peek()!=null)
        {
            BreakActual.pop();
        }
        else
        {
            System.out.println("Error en Metodos.popBreak(). No hay etiquetas en el stack.");
        }
    }
    
    public static String getBreak(){
        if(BreakActual.peek()!=null)
        {
            return BreakActual.peek();
        }
        else
        {
            System.out.println("Error en Metodos.getBreak(). No hay etiquetas en el stack.");
            return "";
        }
    }
    
    public static void setNewReturn(){
        String etiquetaBreak = getNewEtiqueta();
        ReturnActual.push(etiquetaBreak);
    }
    
    public static void popReturn(){
        if(ReturnActual.peek()!=null)
        {
            ReturnActual.pop();
        }
        else
        {
            System.out.println("Error en Metodos.popReturn(). No hay etiquetas en el stack.");
        }
    }
    
    public static String getReturn(){
        if(ReturnActual.peek()!=null)
        {
            return ReturnActual.peek();
        }
        else
        {
            System.out.println("Error en Metodos.getReturn(). No hay etiquetas en el stack.");
            return "";
        }
        
    }
    
    
    
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    
    public static void  saveEtiqueta(String inEtiqueta, int inLinea){
        TablaEtiquetas.put(inEtiqueta, inLinea);
    }
    
    public static void ejecutarCodigoIntermedio(){
        iniciaPunteros();
        iniciaHeakStack();
        TablaMetodos.get("main").operar();
    }
    
    
    public static void iniciaPunteros(){
        TablaVariables.put("sptr", 0f);
        TablaVariables.put("hptr", 0f);
    }
    
    public static void iniciaHeakStack(){
        for(int i = 0; i<heap.length;i++)
        {
            heap[i]=-1337f;
        }
        for(int i = 0; i<stack.length;i++)
        {
            stack[i]=-1337f;
        }
    }
    
    
}
