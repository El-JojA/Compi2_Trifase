
package compi2_trifase;

/**
 *
 * @author joja
 */
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class Exito extends JPanel{

    ArrayList <Shape> listaShapes = new ArrayList<>();
    ArrayList <Color> listaColors = new ArrayList<>();
    ArrayList <String> listaTexto = new ArrayList<>();
    
    public Exito(int ancho, int alto, int r, int g, int b){
        setSize(ancho, alto);
        setOpaque(true);
        Color colorFondo = new Color(r,g,b);
        setBackground(colorFondo);
        
    }

    public void setListaShapes(ArrayList<Shape> listaShapes) {
        this.listaShapes = listaShapes;
    }

    public void setListaColors(ArrayList<Color> listaColors) {
        this.listaColors = listaColors;
    }

    public void setListaTexto(ArrayList<String> listaTexto) {
        this.listaTexto = listaTexto;
    }
    
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i = 0; i<listaShapes.size() || i<listaColors.size() ;i++)
        {
            g2.setColor(listaColors.get(i));
            g2.draw(listaShapes.get(i));
        }
        
        for(int i=0; i<listaTexto.size(); i++)
        {
            
        }
        
    }
    
    

}