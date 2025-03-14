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
        paint();
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
			Class c = null;
			classes.forEach(cl -> {
				if (cl.contains(mx, my)) {
					c = cl;
				}
			});
			classes.removeIf(c -> c.contains(mx, my));
			for (Association a : c.associations) {
				if (a.perteneceAClase(c)) { 
					// a.delete(); se puede poner para depurar pero como java quita la basura no deberia hacer falta
					// solo con quitar la asociacion de la lista de asociaciones de la clase deberia bastar
					associations.remove(a);
					break;
				}
			}
			paint();
			return;
		}

		// lo que de verdad tiene sentido:
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Verificar si el clic fue sobre una clase existente
			if (selectedClass = null) {
				for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
					Class c = classes.get(i); 
					if (c.contains(mx, my)) {
						c.moving = true;
					}
				}
			}
			else {
				// esperamos y guardamos el origen
				for (int i = classes.size() - 1; i >= 0; i--) {
					Class c = classes.get(i); 
					if (c.contains(mx, my)) {
						// creamos la asociacion solo con el origen
						Association a = new Association(c);
						associations.add(a);
						eligiendoAsociacion = true;
					}
				}
			}

   		}
	}
    
    public void mouseReleased(MouseEvent e) {
		/*
		* si se estaba eligiendo una asociacion cuando se suelta el raton se mira a ver si se ha soltado sobre una clase
		* si es asi, se añade la clase como destino de la asociacion
		* si no, se cancela la creacion de la asociacion y se deselecciona la clase
		*/
		if (eligiendoAsociacion) {
			int mx = e.getX();
			int my = e.getY();
			for (int i = classes.size() - 1; i >= 0; i--) {
				Class c = classes.get(i);
				if (c.contains(mx, my)) {
					// añadir el destino a la asociacion
					Association a = associations.get(associations.size() - 1);
					a.setDestino(c);
					eligiendoAsociacion = false;
					paint();
					return;
				}
			}
			eligiendoAsociacion = false;
			Association a = associations.get(associations.size() - 1);
			associations.remove(a);
			selectedClass = null;
			paint();
		}
		else {
			for (Class c : classes) {
				c.moving = false;
			}
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
				
				paint();
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
				paint();
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
					paint();
					return;
				}
			}
			if (selectedClass != null) {
				selectedClass.toggleSelection();
				selectedClass = null;
				paint();
			}
		}
	}
    
    public void keyReleased(KeyEvent e) {
		// idk para que se utiliza
    }
}
