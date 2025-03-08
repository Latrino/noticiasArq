import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Diagram 
		extends JPanel 
		implements MouseListener, 
			   MouseMotionListener, 
			   KeyListener {
	
	//atributos
	private Window window;//Ventana en la que está el diagrama
	public Class clase; 
	
	private Vector classes = new Vector(); //las clases que crea el usuario
	private Vector associations = new Vector(); // las asociaciones que crea el usuario
	
	// ... (otros posibles atributos)


	//metodos
	public Diagram(Window theWindow) {
		window = theWindow;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void addClass() {
		//Añade una clase al diagrama
		classes.add(new Rectangle(50 + (classes.size() * 10), 50, 100, 50));
        window.updateNClasses(this);
        repaint();
	}
	
	public int getNClasses(){
		//Devuelve el número de clases
		return classes.size();
	}
	
	public int getNAssociations(){
		//Devuelve el numero de asociaciones
		return associations.size();
	}

	public void paint(Graphics g) {
		//Dibuja el diagrama de clases
		classes.draw();
		associations.draw();
	}
	
	/********************************************/
	/** MÈtodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();


// ----------------  DE DONDE COÑO SALE QUE ESTO FUNCIONE ASI  -----------------------------------------------------

			// Si es clic derecho, eliminar la clase
		if (SwingUtilities.isRightMouseButton(e)) {
			classes.removeIf(c -> c.contains(mouseX, mouseY));
			repaint();
			return;
		}

		// Verificar si el clic fue sobre una clase existente
		for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
			Class c = classes.get(i);
			if (c.contains(mx, my)) {
				c.toggleSelection();

				// Traer la clase al frente
				classes.remove(i);
				classes.add(c);
				
				repaint();
				return;
			}
		}

    	// Si no se hizo clic en ninguna clase, agregar una nueva
    	Class newClass = new UMLClass(mx, my);
    	classes.add(newClass);
    	repaint();
   		}
    
    public void mouseReleased(MouseEvent e) {
 		//…		
    	}
    
	public void mouseEntered(MouseEvent e) {
    	}
    
	public void mouseExited(MouseEvent e) {
    	}
    
	public void mouseClicked(MouseEvent e) {
    	}

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/    
    public void mouseMoved(MouseEvent e) {
		//…
	}
    
	public void mouseDragged(MouseEvent e) {
		//…
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
    }
    
	public void keyPressed(KeyEvent e) {
		//…
	}
    
    public void keyReleased(KeyEvent e) {
    }
}
