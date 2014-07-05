package analisis;
import java_cup.runtime.Symbol;
import java.util.*;

%%

%class C3dLex
%cupsym C3dSym
%cup
%unicode
%public
%line
%char
%ignorecase


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
iden = {letra}({letra}|{digito}|"_")*




%state A

%% 

/* Palabras Reservadas */

"goto"          {
                 return new Symbol(C3dSym.tgoto, yychar, yyline ,new String(yytext()));}
"method"        {
                 return new Symbol(C3dSym.tmethod, yychar, yyline ,new String(yytext()));}
"call"          {
                 return new Symbol(C3dSym.tcall, yychar, yyline ,new String(yytext()));}
"callf"         {
                 return new Symbol(C3dSym.tcallf, yychar, yyline ,new String(yytext()));}
"then"          {
                 return new Symbol(C3dSym.tthen, yychar, yyline ,new String(yytext()));}
"heap"          {
                 return new Symbol(C3dSym.theap, yychar, yyline ,new String(yytext()));}
"stack"         {
                 return new Symbol(C3dSym.tstack, yychar, yyline ,new String(yytext()));}
"if"            {
                 return new Symbol(C3dSym.tif, yychar, yyline ,new String(yytext()));}
                 
/* Operadores Aritméticos */


"+"             {
                 return new Symbol(C3dSym.tmas, yychar, yyline ,new String(yytext()));}
"-"             {
                 return new Symbol(C3dSym.tmenos, yychar, yyline ,new String(yytext()));}
"*"             {
                 return new Symbol(C3dSym.tpor, yychar, yyline ,new String(yytext()));}
"/"             {
                 return new Symbol(C3dSym.tdividir, yychar, yyline ,new String(yytext()));}
"^"             {
                 return new Symbol(C3dSym.tpotencia, yychar, yyline ,new String(yytext()));}



/* Operadores Comparadoras */

"<"             {
                 return new Symbol(C3dSym.tmenor, yychar, yyline,new String(yytext()));}
">"             {
                 return new Symbol(C3dSym.tmayor, yychar, yyline,new String(yytext()));}
"<="             {
                 return new Symbol(C3dSym.tmenorigual, yychar, yyline,new String(yytext()));}
">="             {
                 return new Symbol(C3dSym.tmayorigual, yychar, yyline,new String(yytext()));}
"=="             {
                 return new Symbol(C3dSym.tigualigual, yychar, yyline,new String(yytext()));}
"!="             {
                 return new Symbol(C3dSym.tdiferente, yychar, yyline,new String(yytext()));}



/* Operadores Comunes */ 

"("    {
        return new Symbol(C3dSym.tpa, yychar,yyline,new String(yytext())); }
")"    {
        return new Symbol(C3dSym.tpc, yychar,yyline,new String(yytext())); }
"["    {
        return new Symbol(C3dSym.tca, yychar,yyline,new String(yytext())); }
"]"    {
        return new Symbol(C3dSym.tcc, yychar,yyline,new String(yytext())); }
"{"    {
        return new Symbol(C3dSym.tla, yychar,yyline,new String(yytext())); }
"}"    {
        return new Symbol(C3dSym.tlc, yychar,yyline,new String(yytext())); }
":"    {
        return new Symbol(C3dSym.tdospuntos, yychar,yyline,new String(yytext())); }
";"    {
        return new Symbol(C3dSym.tpuntocoma, yychar,yyline,new String(yytext())); }
","    {
        return new Symbol(C3dSym.tcoma, yychar,yyline,new String(yytext())); }

"="    {
       return new Symbol(C3dSym.tigual, yychar,yyline,new String(yytext())); }

/* Funciones especiales */
"imprimir"          {
                    return new Symbol(C3dSym.timprimir, yychar,yyline,new String(yytext())); }
"linea"             {
                    return new Symbol(C3dSym.tlinea, yychar,yyline,new String(yytext())); }
"arco"              {
                    return new Symbol(C3dSym.tarco, yychar,yyline,new String(yytext())); }
"rectangulo"        {
                    return new Symbol(C3dSym.trectangulo, yychar,yyline,new String(yytext())); }
"ovalo"             {
                    return new Symbol(C3dSym.tovalo, yychar,yyline,new String(yytext())); }
"poligono"          {
                    return new Symbol(C3dSym.tpoligono, yychar,yyline,new String(yytext())); }
"lienzo"            {
                    return new Symbol(C3dSym.tlienzo, yychar,yyline,new String(yytext())); }
"texto"             {
                    return new Symbol(C3dSym.ttexto, yychar,yyline,new String(yytext())); }
"floatToInt"        {
                    return new Symbol(C3dSym.tfloattoint, yychar,yyline,new String(yytext())); }
"conca"             {
                    return new Symbol(C3dSym.tconca, yychar,yyline,new String(yytext())); }



/* Literales */

    {entero}        {
                    return new Symbol(C3dSym.tentero, yychar,yyline,new String(yytext()));}
    {decimal}       {
                    return new Symbol(C3dSym.tdecimal, yychar,yyline,new String(yytext()));}
    {iden}          {
                    return new Symbol(C3dSym.tiden, yychar,yyline,new String(yytext()));}
    [\n]            {
                    yychar = 0;}

/* Commentarios */
    {Comment}       {
                     /* Se ignoran */} 


/* Caracteres ignorados */ 
    [ \t\r\f]+  	{
                        /* Se ignoran */}

/* Cualquier Otro */  

.				{ /** rojo **/
                                /** Metodos.agregarErrorLexico( "Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); **/
                                /**Metodos.addError("lexico", yyline, yychar, "Caracter no reconocido: " + yytext());**/
                                 System.out.println("Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); } 


