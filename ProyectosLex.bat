SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_05\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
SET JFLEX_HOME= D:\Fuentes\jflex-1.4.3\jflex

cd C:\Users\joja\Documents\NetBeansProjects\Compi2_Trifase\src\analisis
java -jar %JFLEX_HOME%\lib\JFlex.jar ProyectosLex.jflex
pause