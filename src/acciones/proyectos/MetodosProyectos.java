/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acciones.proyectos;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.Color;


public class MetodosProyectos {
        
    public static ArrayList<NodoColores> listaColores = new ArrayList<>();
    public static String htmlErrorLex="";
    public static String htmlErrorSin="";
    public static String htmlErrorSem="";
    public static Objeto objProyectos;
    public static boolean correcto;
    public static DefaultStyledDocument documento = new DefaultStyledDocument();
    
    static StyleContext context = new StyleContext();
    // 
    static Style estiloPalabraReservada;
    static Style estiloOperador;
    static Style estiloId;
    static Style estiloValor;
    static Style estiloNormal;
    static Style estiloError;
        // 
    
    
    public static void MetodosProyectos(){
        initEstilos();
    }
    
    public static void setCorrecto(boolean inCorrecto){
        correcto = inCorrecto;
    }
    
    public static void initEstilos(){
        MetodosProyectos.documento = new DefaultStyledDocument();
        
        estiloPalabraReservada = context.addStyle("palabraReservada", null);
        estiloOperador = context.addStyle("operador", null);
        estiloId = context.addStyle("id", null);
        estiloValor = context.addStyle("valor", null);
        estiloNormal = context.addStyle("valor", null);
        estiloError = context.addStyle("valor", null);
        
        StyleConstants.setForeground(estiloPalabraReservada, new Color(102,205,170));
        StyleConstants.setForeground(estiloOperador, Color.BLACK);
        StyleConstants.setForeground(estiloId, new Color(139,137,137));
        StyleConstants.setForeground(estiloValor, new Color(223,133,38));
        StyleConstants.setForeground(estiloNormal, Color.BLACK);
        StyleConstants.setForeground(estiloError, Color.RED);
        
    }
    
    /**
     * 0 - PalabraReservada // 1 - Operadores // 2 - Ids 
     * 3 - Valores // 4 - Normal // 5 - Errores
     * @param inIndex
     * @param inText
     * @param inTipo
     * @throws BadLocationException
     */
    public static void addPalabraColoreada(String inText, int inTipo) throws BadLocationException{
    //insertString(MetodosProyectos.documento.getLength(), yytext(), estiloPalabraReservada)
        
        NodoColores nc = new NodoColores(inText, inTipo);
        listaColores.add(nc);
        
    }
    
    public static Proyecto makeProyecto(){
        if(correcto)
        {
            Proyecto proy;
            String nombre="", ruta="";
            ArrayList<Archivo> listaArchivos = new ArrayList<>();
            
            for(int i=0; i<objProyectos.atributos.size(); i++)
            {
                Atributo tempAtr = (Atributo)objProyectos.atributos.get(i);
                if("nombre".equalsIgnoreCase(tempAtr.nombre))
                {
                    nombre = tempAtr.valor;
                }
                else if("ruta".equalsIgnoreCase(tempAtr.nombre))
                {
                    ruta = tempAtr.valor;
                }
            }
            
            for(int i=0; i<objProyectos.contenido.size(); i++)
            {//Archivos - Principal
                Objeto tempObj = (Objeto)objProyectos.contenido.get(i);
                if("archivos".equalsIgnoreCase(tempObj.nombre))
                {
                    for(int k=0; k<tempObj.contenido.size();k++)
                    {
                        Objeto tempArchivo = (Objeto)tempObj.contenido.get(k);
                        if("archivo".equalsIgnoreCase(tempArchivo.nombre))
                        {//Se de fijo que es un archivo
                            for(int h = 0; h<tempArchivo.atributos.size(); h++)
                            {
                                Atributo tempAtri = (Atributo)tempArchivo.atributos.get(h);
                                if("nombre".equalsIgnoreCase(tempAtri.nombre))
                                {//Se de fijo que es un archivo
                                    String nombreArchivo;
                                    nombreArchivo = tempAtri.valor;
                                    listaArchivos.add(new Archivo(nombreArchivo));
                                }
                            }
                        }
                    }
                }
                else if("principal".equalsIgnoreCase(tempObj.nombre))
                {
                    for(int k=0; k<tempObj.contenido.size();k++)
                    {
                        Objeto tempArchivo = (Objeto)tempObj.contenido.get(k);
                        if("archivo".equalsIgnoreCase(tempArchivo.nombre))
                        {//Se de fijo que es un archivo
                            for(int h = 0; h<tempArchivo.atributos.size(); h++)
                            {
                                Atributo tempAtri = (Atributo)tempArchivo.atributos.get(h);
                                if("nombre".equalsIgnoreCase(tempAtri.nombre))
                                {//Se de fijo que es un archivo
                                    String nombreArchivo;
                                    nombreArchivo = tempAtri.valor;
                                    for(int j=0; j<listaArchivos.size(); j++)
                                    {
                                        if(listaArchivos.get(j).nombre.equalsIgnoreCase(nombreArchivo))
                                        {
                                            listaArchivos.get(j).setPrincipal();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            proy = new Proyecto(nombre, ruta, listaArchivos);
            return proy;
        }
        else{
            System.out.println("Hay errores en el archivo. No se pudo crear el objeto Proyecto");
            return null;
        }
        
    }
    
    public static void clearAll(){
        htmlErrorLex="";
        htmlErrorSin="";
        htmlErrorSem="";
    }
    
    public static void addError(String inTipo, int fila, int columna, String inMensaje  ){
        switch (inTipo)
        {
            case "lexico":
                htmlErrorLex = htmlErrorLex + 
                    "\n <tr>" +
                    "\n <td>"+ inTipo +"</td>" +
                    "\n <td>"+ fila +"</td>" +
                    "\n <td>"+ columna +"</td>" +
                    "\n <td>"+ inMensaje +"</td>" +
                    "\n </tr>" +
                    "\n ";
                break;
                
            case "sintactico":
                htmlErrorSin = htmlErrorSin + 
                    "\n <tr>" +
                    "\n <td>"+ inTipo +"</td>" +
                    "\n <td>"+ fila +"</td>" +
                    "\n <td>"+ columna +"</td>" +
                    "\n <td>"+ inMensaje +"</td>" +
                    "\n </tr>" +
                    "\n ";
                break;
            case "semantico":
                htmlErrorSem = htmlErrorSem + 
                    "\n <tr>" +
                    "\n <td>"+ inTipo +"</td>" +
                    "\n <td>"+ fila +"</td>" +
                    "\n <td>"+ columna +"</td>" +
                    "\n <td>"+ inMensaje +"</td>" +
                    "\n </tr>" +
                    "\n ";
                break;
        }
    }
    
    public static String crearErrores(){
            String html="\n <html>" + 
                    "\n <body bgcolor=\"#BFFF00\"> "+
                    "\n <center>" +
                    "\n <h1>Errores Léxicos</h1>" +
                    "\n"+
                    "\n <table border=\"1\">" +
                    "\n <tr>" +
                    "\n <td><b>Tipo</b></td>" +
                    "\n <td><b>Fila</b></td>" +
                    "\n <td><b>Columna</b></td>" +
                    "\n <td><b>Mensaje</b></td>" +
                    "\n </tr>" +
                    "\n " +
                    htmlErrorLex+
                    "\n </table>" +
                    "\n </center>";
            
                    html= html+ 
                    "\n <center>" +
                    "\n <h1>Errores Sintacticos</h1>" +
                    "\n"+
                    "\n <table border=\"1\">" +
                    "\n <tr>" +
                    "\n <td><b>Tipo</b></td>" +
                    "\n <td><b>Fila</b></td>" +
                    "\n <td><b>Columna</b></td>" +
                    "\n <td><b>Mensaje</b></td>" +
                    "\n </tr>" +
                    "\n "+
                    htmlErrorSin+
                    "\n </table>" +
                    "\n </center>";
                            
                    html= html+ 
                    "\n <center>" +
                    "\n <h1>Errores Semánticos</h1>" +
                    "\n"+
                    "\n <table border=\"1\">" +
                    "\n <tr>" +
                    "\n <td><b>Tipo</b></td>" +
                    "\n <td><b>Fila</b></td>" +
                    "\n <td><b>Columna</b></td>" +
                    "\n <td><b>Mensaje</b></td>" +
                    "\n </tr>" +
                    "\n "+
                    htmlErrorSem+
                    "\n </table>" +
                    "\n </center>"+
                    "\n </body>"+
                    "\n </html>";
            
            return html;
        }
    
}
