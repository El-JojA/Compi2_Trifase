/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.jojaphviz;

import acciones.proyectos.MetodosProyectos;
import acciones.proyectos.Proyecto;
import analisis.IDEColores;
import analisis.JojaphvizCup;
import analisis.JojaphvizLex;
import analisis.ProyectosCup;
import analisis.ProyectosLex;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author joja
 */
public class Metodos {
    
    public static Proyecto proyectoActual;
    public static ArrayList<Error> listaErrores = new ArrayList<>();
    public static DefaultTableModel TablaSimbolos = new DefaultTableModel();
    public static String claseActual;
    public static String ambitoActual;
    public static int contadorPunteroStack;
    public static int contadorPunteroHeap;
    public static int contadorAtributos;
    public static int contadorLocales;
    public static boolean heredando=false;
    
    public static int contadorTerminales;
    public static int contadorEtiquetas;
    
    
    public static void Metodos(){
        initTablaSimbolos();
    }
    
    public static void initTablaSimbolos(){
        TablaSimbolos.addColumn("Simbolo");
        TablaSimbolos.addColumn("Tipo");
        TablaSimbolos.addColumn("TipoDato");
        TablaSimbolos.addColumn("Clase");
        TablaSimbolos.addColumn("Ambito");
        TablaSimbolos.addColumn("Puntero");
        TablaSimbolos.addColumn("Tamano");
        TablaSimbolos.addColumn("Parametros");
        TablaSimbolos.addColumn("Visibilidad");
        TablaSimbolos.addColumn("Hereda");
        //TablaSimbolos.addColumn("Dimensiones");
        
    }
    
    public static void agregarAtributos(ArrayList<String> inLista, String inTipoDato, String inVisibilidad){
        if(!(heredando==true && inVisibilidad.equalsIgnoreCase("private")))
        {
            int tamanoInt = getTipoSize(inTipoDato);
            String tamano = String.valueOf(tamanoInt);
            for(int i=0; i< inLista.size(); i++)
            {
                if(getPosAtributo(inLista.get(i),"GLOBAL") == -1)
                {//No existe la variable
                    TablaSimbolos.addRow(new Object[]{inLista.get(i), 
                                                        "atributo" ,
                                                        inTipoDato, 
                                                        claseActual, 
                                                        claseActual, 
                                                        "hptr + " + String.valueOf(contadorPunteroHeap),
                                                        tamano,
                                                        null,
                                                        inVisibilidad,
                                                        null
                                                     });
                    contadorPunteroHeap = contadorPunteroHeap + tamanoInt;
                    contadorAtributos = contadorAtributos + tamanoInt;
                }
                else
                {
                    addError("Semantico", "Error al crear variable. Id ya usando, en la clase " + claseActual,"0","0");
                }
            }
        }
        
    }
    
    public static void agregarClase(String inNombre, String inVisibilidad, String inHereda){
    
        TablaSimbolos.addRow(new Object[]{inNombre, 
                                                "clase" ,
                                                null, 
                                                null, 
                                                null, 
                                                null,
                                                "0",
                                                null,
                                                inVisibilidad,
                                                inHereda
                                             });
        
    }
    
    public static void agregarMetodo(String inNombre,String inTipo, ArrayList<String[]> inParam, String inVisibilidad){
        
        if(!(heredando==true && inVisibilidad.equalsIgnoreCase("private")))
        {
            String param="";
            String paramNombre="";
            String tamano = "0";//
            if(inParam!=null)
            {
                paramNombre = "_" + inParam.get(0)[0];
                param = inParam.get(0)[0];
                tamano = String.valueOf(inParam.size());
                for(int i =1; i < inParam.size(); i++)
                {
                    paramNombre = paramNombre + "_" + inParam.get(i)[0];
                    param = param + "," + inParam.get(i)[0];
                }
                
            }
            if(getPosMetodo(paramNombre)==-1)
            {
                TablaSimbolos.addRow(new Object[]{inNombre + paramNombre, 
                                                        "metodo" ,
                                                        inTipo, 
                                                        claseActual, 
                                                        null, 
                                                        null,
                                                        tamano,
                                                        param,
                                                        inVisibilidad,
                                                        null
                                                     });
                if(inParam!=null)
                {
                    for(int i=0; i< inParam.size(); i++)
                    {
                        if(getPosParam(inParam.get(i)[1],inNombre + paramNombre) == -1)
                        {//No existe la variable
                            String size = String.valueOf(getTipoSize(inParam.get(i)[0]));
                            TablaSimbolos.addRow(new Object[]{inParam.get(i)[1], 
                                                                "parametro" ,
                                                                inParam.get(i)[0], 
                                                                claseActual, 
                                                                inNombre + paramNombre, 
                                                                "ptr + " + String.valueOf(contadorPunteroStack),
                                                                size,
                                                                null,
                                                                null,
                                                                null
                                                             });
                            
                            contadorPunteroStack = contadorPunteroStack + getTipoSize(inParam.get(i)[0]);
                        }
                        else
                        {
                            addError("Semantico", "Error al crear parametro"+ inParam.get(i)[1] + " en "+ inNombre + paramNombre +". Id ya usando, en la clase " + claseActual,"0","0");
                        }
                    }
                }
                if(!"void".equals(inTipo))
                {
                    TablaSimbolos.addRow(new Object[]{"return", 
                                                            "parametro" ,
                                                            inTipo, 
                                                            claseActual, 
                                                            inNombre + paramNombre, 
                                                            "ptr + " + String.valueOf(contadorPunteroStack),
                                                            null,
                                                            null,
                                                            null,
                                                            null
                                                         });
                        contadorPunteroStack++;
                }
            }
            else
            {
                addError("Semantico", "Error al crear metodo. Id ya usando, en la clase " + claseActual,"0","0");
            }
            ambitoActual = inNombre + paramNombre;  
        }
        
    }
    
    public static void addVariables(ArrayList<String> inLista, String inTipoDato){
        if(heredando == false)
        {
            int tamanoInt = getTipoSize(inTipoDato);
            String tamano = String.valueOf(tamanoInt);
            for(int i=0; i< inLista.size(); i++)
            {
                if(getPosVariable(inLista.get(i),ambitoActual) == -1)
                {//No existe la variable
                    TablaSimbolos.addRow(new Object[]{inLista.get(i), 
                                                        "variable" ,
                                                        inTipoDato, 
                                                        claseActual, 
                                                        ambitoActual, 
                                                        "ptr + " + String.valueOf(contadorPunteroStack),
                                                        tamano,
                                                        null,
                                                        null,
                                                        null
                                                     });
                    actualizaMetodoSize(ambitoActual, tamanoInt);
                    contadorPunteroStack = contadorPunteroStack + tamanoInt;
                }
                else
                {
                    addError("Semantico", "Error al crear variable. Id ya usando en ese ambito.","0","0");
                }
            }
        }
    }
    
    public static void creaHTMLTS() throws FileNotFoundException{
    
        String html="\n <html>" + 
                    "\n <body bgcolor=\"#cdd3d8\"> "+
                    "\n <center>" +
                    "\n <h1>Tabla de Simbolos</h1>" +
                    "\n"+
                    "\n <table border=\"1\">" +
                    "\n <tr>" +
                    "\n <td><b>Simbolo</b></td>" +
                    "\n <td><b>Tipo</b></td>" +
                    "\n <td><b>TipoDato</b></td>" +
                    "\n <td><b>Clase</b></td>" +
                    "\n <td><b>Ambito</b></td>" +
                    "\n <td><b>Puntero</b></td>" +
                    "\n <td><b>Tamano</b></td>" +
                    "\n <td><b>Parametros</b></td>" +
                    "\n <td><b>Visibilidad</b></td>" +
                    "\n <td><b>Hereda</b></td>" +
                    "\n <td><b>Dimensiones</b></td>" +
                    "\n </tr>" +
                    "\n ";
                            
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            html = html +
                    "\n <tr>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 0) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 1) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 2) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 3) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 4) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 5) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 6) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 7) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 8) +"</b></td>" +
                    "\n <td><b>"+ TablaSimbolos.getValueAt(i, 9) +"</b></td>" +
                    "\n <td><b> </b></td>" +
                    "\n </tr>";
        }
                            
            html= html + "\n </table>" +
                    "\n </center>";
            
              String rutaFinal = "C:\\Users\\joja\\Documents\\NetBeansProjects\\Compi2_Trifase\\TablaSimbolos.html";
            try (PrintWriter out = new PrintWriter(rutaFinal)) {
                    out.println(html);
                }
    }
    
    public static void crearHTMLErrores() throws FileNotFoundException{
            String html="\n <html>" + 
                    "\n <body bgcolor=\"#BFFF00\"> "+
                    "\n <center>" +
                    "\n <h1>Errores Léxicos</h1>" +
                    "\n"+
                    "\n <table border=\"1\">" +
                    "\n <tr>" +
                    "\n <td><b>Tipo</b></td>" +
                    "\n <td><b>Mensaje</b></td>" +
                    "\n <td><b>Fila</b></td>" +
                    "\n <td><b>Columna</b></td>" +
                    "\n </tr>" +
                    "\n ";
                    
            for(int i =0; i<listaErrores.size(); i++)
            {
                html =  html + "\n <tr>" +
                    "\n <td><b>" + listaErrores.get(i).tipo +  "</b></td>" +
                    "\n <td><b>" + listaErrores.get(i).mensaje +  "</b></td>" +
                    "\n <td><b>" + listaErrores.get(i).fila +  "</b></td>" +
                    "\n <td><b>" + listaErrores.get(i).columna +  "</b></td>" +
                    "\n </tr>"+
                    "\n";    
            }    
            
                html=   html +  "\n </table>" +
                    "\n </center>"+
                    "\n </body>"+
                    "\n </html>";
            String rutaFinal = "C:\\Users\\joja\\Documents\\NetBeansProjects\\Compi2_Trifase\\errores.html";
            try (PrintWriter out = new PrintWriter(rutaFinal)) {
                    out.println(html);
                }
        }
    
    public static void compilarHerencia(String inArchivo) throws IOException{
    
        String ruta = proyectoActual.ruta;
        String rutaFinal = ruta + inArchivo + ".frc";
        String archivo = readArchivo(rutaFinal);
        compilar(archivo,"joja");
        
    }
    
    public static String readArchivo(String inRuta) throws FileNotFoundException, IOException {
        
        String resultado = null;
        BufferedReader br = new BufferedReader(new FileReader(inRuta));
        try 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            resultado = sb.toString();
        }
        finally 
        {
            br.close();
        }
        return resultado;
    }
    
    public static void compilar(String inArchivo, String tipoArchivo){
        
        switch(tipoArchivo)
        {
            case "joja":
                try
                {
                JojaphvizLex jojaLexer = new JojaphvizLex(new StringReader(inArchivo));
                JojaphvizCup jojaParser = new JojaphvizCup(jojaLexer);
                jojaParser.parse();
                }catch (Exception ex){System.out.println(ex.toString());}
                break;
                
            case "proy":
                try
                {
                ProyectosLex proyLexer = new ProyectosLex(new StringReader(inArchivo));
                ProyectosCup proyParser = new ProyectosCup(proyLexer);
                proyParser.parse();
                }catch (Exception ex){System.out.println(ex.toString());}
                break;
                
            case "ide":
                try
                {
                MetodosProyectos.listaColores = new ArrayList<>();
                IDEColores proyLexer = new IDEColores(new StringReader(inArchivo));
                proyLexer.yylex();
                }catch (IOException | BadLocationException ex){System.out.println(ex.toString());}
                break;
        }
        
    }
    
    public static void actualizaMetodoSize(String inMetodo, int inSize){
    
        int pos = getPosMetodo(inMetodo);
        String sizeStr = (String)TablaSimbolos.getValueAt(pos, 6);
        int size = Integer.valueOf(sizeStr);
        size = size + inSize;
        TablaSimbolos.setValueAt(String.valueOf(size), pos, 6);
    }
    
    public static void actualizaClaseSize(String inClase){
    
        int pos = getPosClase(inClase);
        String sizeStr = (String)TablaSimbolos.getValueAt(pos, 6);
        int size = Integer.valueOf(sizeStr);
        size = size + contadorAtributos;
        TablaSimbolos.setValueAt(String.valueOf(size), pos, 6);
    }
    
    
    
    /**
     Funciones funcionales internas jajajaja, soy tan chistoso.
     **/
    
    /**
     * @param inNombre
     * @param inAmbito
     * @return 
     **/ 
    
    public static int getPosClase(String inNombre){
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            String tipo = (String)TablaSimbolos.getValueAt(i, 1);
            String simbolo = (String)TablaSimbolos.getValueAt(i, 0);
            if(simbolo.equalsIgnoreCase(inNombre) && tipo.equalsIgnoreCase("clase"))
            {
                return i;
            }
        }
        return -1;
    }
    
    public static int getPosAtributo(String inNombre, String inAmbito){
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            String tipo = (String)TablaSimbolos.getValueAt(i, 1);
            String simbolo = (String)TablaSimbolos.getValueAt(i, 0);
            String clase = (String)TablaSimbolos.getValueAt(i, 3);
            String ambito = (String)TablaSimbolos.getValueAt(i, 4);
            if(simbolo.equalsIgnoreCase(inNombre) && clase.equalsIgnoreCase(claseActual) && ambito.equalsIgnoreCase(inAmbito) && tipo.equalsIgnoreCase("atributo"))
            {
                return i;
            }
        }
        return -1;
    }
    
    public static String operacionAritmetica(String inNum1, String inOperador, String inNum2){
        
        float num1=0;
        float num2=0;
        String resultado;
                
       num1 = stringToFloat(inNum1);
       num2 = stringToFloat(inNum2);
       
       switch(inOperador)
       {
           case "+":
               return String.valueOf(num1+num2);
            case "-":
               return String.valueOf(num1-num2);
            case "*":
               return String.valueOf(num1*num2);
            case "/":
               return String.valueOf(num1/num2);
            case "^":
               return String.valueOf(Math.pow(num1, num2));
       }
       
       return "0";
    }
    
    private static float stringToFloat(String inValor){
        float resultado = 0;
        try{ resultado = Float.parseFloat(inValor); }
        catch(Exception e){ resultado = 0; }
        return resultado;
    }
   
    /**
     *
     * @param inNombre
     * @param inAmbito
     * @return 
     **/
    public static int getPosVariable(String inNombre, String inAmbito){
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            String tipo = (String)TablaSimbolos.getValueAt(i, 1);
            String simbolo = (String)TablaSimbolos.getValueAt(i, 0);
            String clase = (String)TablaSimbolos.getValueAt(i, 3);
            String ambito = (String)TablaSimbolos.getValueAt(i, 4);
            if(simbolo.equalsIgnoreCase(inNombre) && clase.equalsIgnoreCase(claseActual) && ambito.equalsIgnoreCase(inAmbito) && tipo.equalsIgnoreCase("local"))
            {
                return i;
            }
        }
        return -1;
    }
    
    public static int getPosParam(String inNombre, String inAmbito){
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            String tipo = (String)TablaSimbolos.getValueAt(i, 1);
            String simbolo = (String)TablaSimbolos.getValueAt(i, 0);
            String clase = (String)TablaSimbolos.getValueAt(i, 3);
            String ambito = (String)TablaSimbolos.getValueAt(i, 4);
            if(simbolo.equalsIgnoreCase(inNombre) && clase.equalsIgnoreCase(claseActual) && ambito.equalsIgnoreCase(inAmbito) && tipo.equalsIgnoreCase("parametro"))
            {
                return i;
            }
        }
        return -1;
    }
    
    public static int getPosMetodo(String inNombre){
        for(int i =0; i<TablaSimbolos.getRowCount(); i++)
        {
            String tipo = (String)TablaSimbolos.getValueAt(i, 1);
            String simbolo = (String)TablaSimbolos.getValueAt(i, 0);
            String clase = (String)TablaSimbolos.getValueAt(i, 3);
            if(simbolo.equalsIgnoreCase(inNombre) && clase.equalsIgnoreCase(claseActual) && tipo.equalsIgnoreCase("metodo"))
            {
                return i;
            }
        }
        return -1;
    }
    
    public static void addError(String inTipo, String inMensaje, String inFila, String inColumna){
        
        listaErrores.add(new Error(inTipo, inMensaje, inFila, inColumna));
    }
   
    public static void clearAll(){
        listaErrores.clear();
        TablaSimbolos = new DefaultTableModel(); //No encontré una manera más facil de reiniciarlo
        initTablaSimbolos();
        contadorPunteroStack=0;
        contadorPunteroHeap=0;
        contadorAtributos=0;
        contadorLocales=0;
        heredando=false;
        contadorEtiquetas=0;
        contadorTerminales=0;
    }
    
    public static int getTipoSize(String inTipo){
        switch(inTipo)
        {
            case "int":
                return 1;
            case "float":
                return 1;
            case "string":
                return 1;
            case "char":
                return 1;
            case "boolean":
                return 1;
            default:
                //Aqui voy a buscar el tamaño en la tabla de simbolos de la clase con nombre inTipo
                return 1;
        }
    }
    
    /**********************************************************
     * 
     * 
     * 
     * METODOS PARA GENERACION DE CÓDIGO DE TRES DIRECCIONES
     *
     * 
     * 
     **********************************************************/
    
    /**
     * METODOS PARA GENERACION DE CÓDIGO DE TRES DIRECCIONES
     * @return
     */
    public static String getNewTerminal(){
        String terminal = "t" + String.valueOf(contadorTerminales);
        contadorTerminales++;
        return terminal;
    }
    
    public static String getNewEtiqueta(){
        String terminal = "L" + String.valueOf(contadorEtiquetas);
        contadorEtiquetas++;
        return terminal;
    }
    
    
    
}
