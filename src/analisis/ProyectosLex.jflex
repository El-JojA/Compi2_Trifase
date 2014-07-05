package analisis;
import java_cup.runtime.Symbol;
import java.util.*;

%%

%class ProyectosLex
%cupsym ProyectosSym
%cup
%unicode
%public
%line
%char
%ignorecase

digito = [0-9]
letra = [a-zA-ZñÑ]+
cadena = [\"] [^\"\n]* [\"]
iden = {letra}({letra}|{digito}|"_")*




%state A

%% 

/* Palabras Reservadas */


"<"                 {
                    return new Symbol(ProyectosSym.tmenor, yychar, yyline ,new String(yytext()));}
">"                 {
                    return new Symbol(ProyectosSym.tmayor, yychar, yyline ,new String(yytext()));}
"="                 {
                    return new Symbol(ProyectosSym.tigual, yychar, yyline ,new String(yytext()));}
"/"                 {
                    return new Symbol(ProyectosSym.tslash, yychar, yyline ,new String(yytext()));}

/* Literales */

    {iden}          {
                    return new Symbol(ProyectosSym.tiden, yychar,yyline,new String(yytext()));}
    {cadena}        {
                    return new Symbol(ProyectosSym.tcadena, yychar,yyline,new String(yytext().substring(1, yytext().length()-1)));}
    
    [\n]            {
                    yychar = 0;}




/* Caracteres ignorados */ 
    [ \t\r\f]+  	{
                        /* Se ignoran */}

/* Cualquier Otro */  

.				{ /** rojo **/
                                /** Metodos.agregarErrorLexico( "Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); **/
                                /**Metodos.addError("lexico", yyline, yychar, "Caracter no reconocido: " + yytext());**/
                                System.out.println("Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); } 


