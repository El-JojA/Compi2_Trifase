package analisis;
import java_cup.runtime.Symbol;
import java.util.*;
import acciones.jojaphviz.Metodos;

%%

%class JojaphvizLex
%cupsym JojaphvizSym
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
cadena = [\"] [^\"\n]* [\"]
caracter = [\'] [a-zA-ZñÑ] [\']
iden = {letra}({letra}|{digito}|"_")*




%state A

%% 

/* Palabras Reservadas */

"int"           {
                 return new Symbol(JojaphvizSym.tint, yychar, yyline ,new String(yytext()));}
"float"         {
                 return new Symbol(JojaphvizSym.tfloat, yychar, yyline ,new String(yytext()));}
"string"        {
                 return new Symbol(JojaphvizSym.tstring, yychar, yyline ,new String(yytext()));}
"bool"          {
                 return new Symbol(JojaphvizSym.tbool, yychar, yyline ,new String(yytext()));}
"char"          {
                 return new Symbol(JojaphvizSym.tchar, yychar, yyline ,new String(yytext()));}
"while"         {
                 return new Symbol(JojaphvizSym.twhile, yychar, yyline ,new String(yytext()));}
"do"            {
                 return new Symbol(JojaphvizSym.tdo, yychar, yyline ,new String(yytext()));}
"for"           {
                 return new Symbol(JojaphvizSym.tfor, yychar, yyline ,new String(yytext()));}
"continue"      {
                 return new Symbol(JojaphvizSym.tcontinue, yychar, yyline ,new String(yytext()));}
"new"           {
                 return new Symbol(JojaphvizSym.tnew, yychar, yyline ,new String(yytext()));}
"true"          {
                 return new Symbol(JojaphvizSym.ttrue, yychar, yyline ,new String(yytext()));}
"false"         {
                 return new Symbol(JojaphvizSym.tfalse, yychar, yyline ,new String(yytext()));}
"break"         {
                 return new Symbol(JojaphvizSym.tbreak, yychar, yyline ,new String(yytext()));}
"if"            {
                 return new Symbol(JojaphvizSym.tif, yychar, yyline ,new String(yytext()));}
"else"          {
                 return new Symbol(JojaphvizSym.telse, yychar, yyline ,new String(yytext()));}
"switch"        {
                 return new Symbol(JojaphvizSym.tswitch, yychar, yyline ,new String(yytext()));}
"case"          {
                 return new Symbol(JojaphvizSym.tcase, yychar, yyline ,new String(yytext()));}
"default"       {
                 return new Symbol(JojaphvizSym.tdefault, yychar, yyline ,new String(yytext()));}
"public"        {
                 return new Symbol(JojaphvizSym.tpublic, yychar, yyline ,new String(yytext()));}
"private"       {
                 return new Symbol(JojaphvizSym.tprivate, yychar, yyline ,new String(yytext()));}
"return"        {
                 return new Symbol(JojaphvizSym.treturn, yychar, yyline ,new String(yytext()));}
"void"          {
                 return new Symbol(JojaphvizSym.tvoid, yychar, yyline ,new String(yytext()));}
"class"         {
                 return new Symbol(JojaphvizSym.tclass, yychar, yyline ,new String(yytext()));}
"extends"       {
                 return new Symbol(JojaphvizSym.textends, yychar, yyline ,new String(yytext()));}
"this"          {
                 return new Symbol(JojaphvizSym.tthis, yychar, yyline ,new String(yytext()));}
"#import"       {
                 return new Symbol(JojaphvizSym.timport, yychar, yyline ,new String(yytext()));}
"array"         {
                 return new Symbol(JojaphvizSym.tarray, yychar, yyline ,new String(yytext()));}
"void"          {
                 return new Symbol(JojaphvizSym.tvoid, yychar, yyline ,new String(yytext()));}

/* Funciones especiales */
"linea"         {
                 return new Symbol(JojaphvizSym.tlinea, yychar, yyline ,new String(yytext()));}
"texto"         {
                 return new Symbol(JojaphvizSym.ttexto, yychar, yyline ,new String(yytext()));}
"arco"          {
                 return new Symbol(JojaphvizSym.tarco, yychar, yyline ,new String(yytext()));}
"rectangulo"    {
                 return new Symbol(JojaphvizSym.trectangulo, yychar, yyline ,new String(yytext()));}
"ovalo"         {
                 return new Symbol(JojaphvizSym.tovalo, yychar, yyline ,new String(yytext()));}
"poligono"      {
                 return new Symbol(JojaphvizSym.tpoligono, yychar, yyline ,new String(yytext()));}
"lienzo"        {
                 return new Symbol(JojaphvizSym.tlienzo, yychar, yyline ,new String(yytext()));}
"imprimir"      {
                 return new Symbol(JojaphvizSym.timprimir, yychar, yyline ,new String(yytext()));}





/* Operadores Aritméticos */


"+"             {
                 return new Symbol(JojaphvizSym.tmas, yychar, yyline ,new String(yytext()));}
"-"             {
                 return new Symbol(JojaphvizSym.tmenos, yychar, yyline ,new String(yytext()));}
"*"             {
                 return new Symbol(JojaphvizSym.tpor, yychar, yyline ,new String(yytext()));}
"/"             {
                 return new Symbol(JojaphvizSym.tdividir, yychar, yyline ,new String(yytext()));}
"^"             {
                 return new Symbol(JojaphvizSym.tpotencia, yychar, yyline ,new String(yytext()));}
"++"            {
                 return new Symbol(JojaphvizSym.tmasmas, yychar, yyline ,new String(yytext()));}
"--"            {
                 return new Symbol(JojaphvizSym.tmenosmenos, yychar, yyline ,new String(yytext()));}



/* Operadores Comparadoras */

"<"             {
                 return new Symbol(JojaphvizSym.tmenor, yychar, yyline,new String(yytext()));}
">"             {
                 return new Symbol(JojaphvizSym.tmayor, yychar, yyline,new String(yytext()));}
"<="             {
                 return new Symbol(JojaphvizSym.tmenorigual, yychar, yyline,new String(yytext()));}
">="             {
                 return new Symbol(JojaphvizSym.tmayorigual, yychar, yyline,new String(yytext()));}
"=="             {
                 return new Symbol(JojaphvizSym.tigualigual, yychar, yyline,new String(yytext()));}
"!="             {
                 return new Symbol(JojaphvizSym.tdiferente, yychar, yyline,new String(yytext()));}



/* Operadores Comunes */ 

"("    {
        return new Symbol(JojaphvizSym.tpa, yychar,yyline,new String(yytext())); }
")"    {
        return new Symbol(JojaphvizSym.tpc, yychar,yyline,new String(yytext())); }
"["    {
        return new Symbol(JojaphvizSym.tca, yychar,yyline,new String(yytext())); }
"]"    {
        return new Symbol(JojaphvizSym.tcc, yychar,yyline,new String(yytext())); }
"{"    {
        return new Symbol(JojaphvizSym.tla, yychar,yyline,new String(yytext())); }
"}"    {
        return new Symbol(JojaphvizSym.tlc, yychar,yyline,new String(yytext())); }
":"    {
        return new Symbol(JojaphvizSym.tdospuntos, yychar,yyline,new String(yytext())); }
"."    {
        return new Symbol(JojaphvizSym.tpunto, yychar,yyline,new String(yytext())); }
","    {
        return new Symbol(JojaphvizSym.tcoma, yychar,yyline,new String(yytext())); }
"&"    {
        return new Symbol(JojaphvizSym.tamp, yychar,yyline,new String(yytext())); }
"?"    {
        return new Symbol(JojaphvizSym.tinterrogacion, yychar,yyline,new String(yytext())); }

/* Operadores Lógicos */    

"||"    {
        return new Symbol(JojaphvizSym.tor, yychar,yyline,new String(yytext())); }
"&&"    {
        return new Symbol(JojaphvizSym.tand, yychar,yyline,new String(yytext())); }
"!"    {
        return new Symbol(JojaphvizSym.tno, yychar,yyline,new String(yytext())); }

/* Operadores de asignación */


"="                 {
                    return new Symbol(JojaphvizSym.tigual, yychar,yyline,new String(yytext())); }
"+="                {
                    return new Symbol(JojaphvizSym.tmasigual, yychar,yyline,new String(yytext())); }
"-="                {
                    return new Symbol(JojaphvizSym.tmenosigual, yychar,yyline,new String(yytext())); }
"*="                {
                    return new Symbol(JojaphvizSym.tporigual, yychar,yyline,new String(yytext())); }
"/="                {
                    return new Symbol(JojaphvizSym.tdividirigual, yychar,yyline,new String(yytext())); }


/* Literales */

    {entero}        {
                    return new Symbol(JojaphvizSym.tentero, yychar,yyline,new String(yytext()));}
    {decimal}       {
                    return new Symbol(JojaphvizSym.tdecimal, yychar,yyline,new String(yytext()));}
    {iden}          {
                    return new Symbol(JojaphvizSym.tiden, yychar,yyline,new String(yytext()));}
    {caracter}      {
                    return new Symbol(JojaphvizSym.tcaracter, yychar,yyline,new String(yytext().substring(1, yytext().length()-1)));}
    {cadena}        {
                    return new Symbol(JojaphvizSym.tcadena, yychar,yyline,new String(yytext().substring(1, yytext().length()-1)));}
    [\n]            {
                    yychar = 0;
                    return new Symbol(JojaphvizSym.tenter, yychar,yyline,new String(yytext()));}

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
                                Metodos.addError("lexico",  "Caracter no reconocido: " + yytext(), String.valueOf(yyline), String.valueOf(yychar));     
                                System.out.println("Error lexico: " + yytext() + " en la linea: " + yyline + " y columna " + yychar); } 


