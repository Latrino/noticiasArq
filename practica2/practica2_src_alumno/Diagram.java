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

	private Class selectedClass = null; //la clase seleccionada

	private boolean eligiendoAsociacion = false; //indica si el usuario está eligiendo una asociación
	
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
		classes.add(new Class(50 + (classes.size() * 10), 50));
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
		super.paint(g);
		for (Class c : classes) {
			c.draw(g);
		}
		for (Association a : associations) {
			a.draw(g);
		}
	}
	
	/********************************************/
	/** MÈtodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();




			// Si es clic derecho, eliminar la clase
		if (SwingUtilities.isRightMouseButton(e)) { // ESTE ESTA BIEN
			classes.removeIf(c -> c.contains(mx, my));
			for (Association a : associations) {
				if (a.contains(mx, my)) { // esto hay que mirarlo (no he diseñado la clase aun)
					associations.remove(a);
					break;
				}
			}
			repaint();
			return;
		}

		// lo que de verdad tiene sentido:
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Verificar si el clic fue sobre una clase existente
			for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
				Class c = classes.get(i);
				if (c.contains(mx, my)) {
					c.moving = true;
				}
			}

   		}
	}
    
    public void mouseReleased(MouseEvent e) {
		for (Class c : classes) {
			c.moving = false;
		}
		// revisar, lo que hace es que si se suelta el boton del raton, se deja de mover la clase
    }
    
	// revisar
	public void mouseEntered(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY(); 

		for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
			Class c = classes.get(i);
			if (c.contains(mx, my)) {

				// Traer la clase al frente
				classes.remove(i);
				classes.add(c);
				
				repaint();
				return;
			}
		}
    }
    
	public void mouseExited(MouseEvent e) {
		// aqui hace falta algo?
    }
    
	public void mouseClicked(MouseEvent e) {
		// para que demonios sive esto?
    }

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/    
    public void mouseMoved(MouseEvent e) {
		//…
	}
    
	public void mouseDragged(MouseEvent e) {
		// funcion para mover la clase seleccionada
		int mx = e.getX();
		int my = e.getY();
		for (Class c : classes) {
			if (c.moving) {
				c.move(mx - c.x, my - c.y);
				repaint();
			}
		}
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
		// idk para que se utiliza
    }
    
	public void keyPressed(KeyEvent e) {
		// si se pulsa la tecla S se selecciona la clase sobre la que esta el cursor
		// si se pulsa S cuando ya hay una clase seleccionada, se deselecciona
		if (e.getKeyCode() == KeyEvent.VK_S) {
			for (Class c : classes) {
				if (c.contains(mx, my)) {
					if (c.isSelected()){
						c.toggleSelection();
						selectedClass = null;
					}
					else{
						selectedClass = c;
						c.toggleSelection();
					}					
					repaint();
					return;
				}
			}
			if (selectedClass != null) {
				selectedClass.toggleSelection();
				selectedClass = null;
				repaint();
			}
		}
	}
    
    public void keyReleased(KeyEvent e) {
		// idk para que se utiliza
    }
}
