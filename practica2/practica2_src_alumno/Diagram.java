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
	
	private Vector<Class> classes = new Vector(); //las clases que crea el usuario
	private Vector<Association> associations = new Vector(); // las asociaciones que crea el usuario

	private Class selectedClass = null; //la clase seleccionada

	private boolean eligiendoAsociacion = false; //indica si el usuario está eligiendo una asociación

	private Class mouseOverClass = null; //la clase sobre la que está el cursor
	
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

	/*public void paint(Graphics g) {
		//Dibuja el diagrama de clases
		super.paint(g);
		for (Class c : classes) {
			c.draw(g);
		}
		for (Association a : associations) {
			a.draw(g);
		}
	}*/

	@Override
	public void paintComponent(Graphics g) {
		//Dibuja el diagrama de clases
		super.paintComponent(g);
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
			for (Class cl : classes) {
				if (cl.contains(mx, my)) {
					c = cl;
					break;
				}
			}
			for (Association a : c.getAssociations()) {
				if (a.perteneceAClase(c)) { 
					// a.delete(); se puede poner para depurar pero como java quita la basura no deberia hacer falta
					// solo con quitar la asociacion de la lista de asociaciones de la clase deberia bastar
					associations.remove(a);
				}
			}
			classes.removeIf(z -> z.contains(mx, my));
			window.updateNClasses(this);
			window.updateNAssociations(this);
			repaint();
			return;
		}

		// lo que de verdad tiene sentido:
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Verificar si el clic fue sobre una clase existente
			if (selectedClass == null) {
				for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
					Class c = classes.get(i); 
					if (c.contains(mx, my)) {
						c.setMoving(true);
						break;
					}
				}
			}
			else {
				// esperamos y guardamos el origen 
				if (selectedClass.contains(mx, my)) {
					// creamos la asociacion solo con el origen
					/*
					Association a = new Association(selectedClass);
					associations.add(a);
					*/
					eligiendoAsociacion = true;
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
		for (Class c : classes) {
				c.setMoving(false);
		}
		if (eligiendoAsociacion) {
			int mx = e.getX();
			int my = e.getY();
			for (int i = classes.size() - 1; i >= 0; i--) {
				Class c = classes.get(i);
				if (c.contains(mx, my)) {
					// añadir el destino a la asociacion
					/*
					Association a = associations.get(associations.size() - 1);
					a.setDestino(c);
					*/
					Association a = new Association(selectedClass, c);
					associations.add(a);
					window.updateNAssociations(this);
					return;
				}
			}
			eligiendoAsociacion = false;
			repaint();
		}
		// revisar, lo que hace es que si se suelta el boton del raton, se deja de mover la clase
    }
    
	// revisar
	public void mouseEntered(MouseEvent e) {
		/*int mx = e.getX();
		int my = e.getY(); 

		for (int i = classes.size() - 1; i >= 0; i--) {  // Iterar en orden inverso (de arriba hacia abajo)
			Class c = classes.get(i);
			if (c.contains(mx, my)) {

				// Traer la clase al frente
				classes.remove(i);
				classes.add(c);

				mouseOverClass = c;
				
				repaint();
				return;
			}
		}*/
    }
    
	public void mouseExited(MouseEvent e) {
		mouseOverClass = null;
		repaint();
    }
    
	public void mouseClicked(MouseEvent e) {
		// para que demonios sive esto?
    }

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/    
    public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		boolean found = false;

		for (int i = classes.size() - 1; i >= 0; i--) {  
			Class c = classes.get(i);
			if (c.contains(mx, my)) {
				mouseOverClass = c;
				found = true;
				classes.remove(i);
				classes.add(c);
				repaint();
				break;
			}
		}

		if (!found) {
			mouseOverClass = null;
			repaint();
		}
	}

	public void mouseDragged(MouseEvent e) {
    int mx = e.getX();
    int my = e.getY();

    // Mover la clase que está siendo arrastrada
    for (Class c : classes) {
        if (c.isMoving()) {
            c.move(mx - c.getX(), my - c.getY());
            repaint();
        }
    }

    // Lógica de selección de clases (similar a mouseMoved)
    boolean found = false;
    for (int i = classes.size() - 1; i >= 0; i--) {
        Class c = classes.get(i);
        if (c.contains(mx, my)) {
            // Deseleccionar la clase anterior si es necesario
            if (mouseOverClass != null && mouseOverClass != c && mouseOverClass != selectedClass) {
                mouseOverClass.setSelected(false);
            }

            // Seleccionar la clase bajo el cursor si se está eligiendo una asociación
            mouseOverClass = c;
            if (eligiendoAsociacion) {
                mouseOverClass.setSelected(true);
            }

            // Mover la clase al frente
            classes.remove(i);
            classes.add(c);

            found = true;
            repaint();
            break;
        }
    }

    // Si el cursor no está sobre ninguna clase
    if (!found) {
        if (mouseOverClass != null && mouseOverClass != selectedClass) {
            mouseOverClass.setSelected(false); // Deseleccionar la clase anterior
        }
        mouseOverClass = null;
        repaint();
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
		//int mx = e.getX();
		//int my = e.getY();
		if (e.getKeyCode() == KeyEvent.VK_S) {
			for (Class c : classes) {
				if (mouseOverClass != null && c == mouseOverClass) {
					if (mouseOverClass.isSelected()){
						mouseOverClass.setSelected(false);
						selectedClass = null;
					}
					else{
						if (selectedClass != null && selectedClass != mouseOverClass){
							selectedClass.setSelected(false);
							selectedClass = null;
						}
						selectedClass = mouseOverClass;
						mouseOverClass.setSelected(true);
					}					
					repaint();
					return;
				}
			}
			if (selectedClass != null) {
				selectedClass.setSelected(false);
				selectedClass = null;
				repaint();
			}
		}
	}
    
    public void keyReleased(KeyEvent e) {
		// idk para que se utiliza
    }
}
