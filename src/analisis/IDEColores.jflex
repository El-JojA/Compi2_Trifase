package analisis;
import acciones.proyectos.*;
import java.awt.Color;
import java.util.*;
import java_cup.runtime.Symbol;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

%%

%class IDEColores
%unicode
%yylexthrow{ 
    BadLocationException
%yylexthrow}

%public
%standalone
%line
%char
%ignorecase

%{
    
%}


Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

InputCharacter = [^\r\n]
LineTerminator = \r|\n|\r\n
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

digito = [0-9]
entero = {digito}+
decimal = {entero} "." {entero}
letra = [a-zA-ZñÑ]+
cadena = [\"] [^\"\n]* [\"]
caracter = [\'] [a-zA-ZñÑ] [\']
iden = {letra}({letra}|{digito}|"_")*




%state A

%% 

/* Palabras Reservadas */

"int"           {//int
                MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"float"         {//float
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"string"        {//string
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"bool"          {//bool
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"char"          {//char
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"while"         {//while
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"do"            {//do
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"for"           {//for
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"continue"      {//continue
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"new"           {//new
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"true"          {//true
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"false"         {//false
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"break"         {//break
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"if"            {//if
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"else"          {//else
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"switch"        {//switch
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"case"          {//case
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"default"       {//default
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"public"        {//public
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"private"       {//private
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"return"        {//return
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"void"          {//void
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"class"         {//class
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"extends"       {//extends
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"this"          {//this
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"#import"       {//#import
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"array"         {//array
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"void"          {//void
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}

/* Funciones especiales */
"linea"         {//linea
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"texto"         {//texto
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"arco"          {//arco
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"rectangulo"    {//rectangulo
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"ovalo"         {//ovalo
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"poligono"      {//poligono
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"lienzo"        {//lienzo
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}
"imprimir"      {//imprimir
                 MetodosProyectos.addPalabraColoreada( yytext(), 0);}





/* Operadores Aritméticos */


"+"             {//+
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"-"             {//-
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"*"             {//*
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"/"             {// /
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"^"             {//^
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"++"            {//++
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"--"            {//--
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}



/* Operadores Comparadoras */

"<"             {//<
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
">"             {//>
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"<="             {//<=
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
">="             {//>=
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"=="             {//==
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}
"!="             {//!=
                 MetodosProyectos.addPalabraColoreada( yytext(), 1);}



/* Operadores Comunes */ 

"("    {//(
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
")"    {//)
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"["    {//[
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"]"    {//]
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"{"    {//{
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"}"    {//}
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
":"    {//:
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"."    {//.
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
","    {//,
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"&"    {//&
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"?"    {//?
        MetodosProyectos.addPalabraColoreada( yytext(), 4); }

/* Operadores Lógicos */    

"||"    {//||
        MetodosProyectos.addPalabraColoreada( yytext(), 1); }
"&&"    {//&&
        MetodosProyectos.addPalabraColoreada( yytext(), 1); }
"!"    {//!
        MetodosProyectos.addPalabraColoreada( yytext(), 1); }

/* Operadores de asignación */


"="                 {
                    MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"+="                {
                    MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"-="                {
                    MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"*="                {
                    MetodosProyectos.addPalabraColoreada( yytext(), 4); }
"/="                {
                    MetodosProyectos.addPalabraColoreada( yytext(), 4); }


/* Literales */

    {entero}        {
                    MetodosProyectos.addPalabraColoreada( yytext(), 3);}
    {decimal}       {
                    MetodosProyectos.addPalabraColoreada( yytext(), 3);}
    {iden}          {
                    MetodosProyectos.addPalabraColoreada( yytext(), 2);}
    {caracter}      {
                    MetodosProyectos.addPalabraColoreada( yytext(), 3);}
    {cadena}        {
                    MetodosProyectos.addPalabraColoreada( yytext(), 3);}
    [\n]            {
                    yychar = 0;
                    MetodosProyectos.addPalabraColoreada( yytext(), 4);}

/* Commentarios */
    {Comment}       {
                     /* Se ignoran */} 


/* Caracteres ignorados */ 
    [ \t\r\f]+  	{
                        MetodosProyectos.addPalabraColoreada( yytext(), 4);
                        /* Se ignoran */}

/* Cualquier Otro */  

.				{ /** rojo **/
                                /** Metodos.agregarErrorLexico( "Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); **/
                                /**Metodos.addError("lexico", yyline, yychar, "Caracter no reconocido: " + yytext());**/
                                System.out.println("Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); 
                                MetodosProyectos.addPalabraColoreada( yytext(), 5);
                                }


