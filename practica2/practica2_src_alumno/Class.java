import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.Color;
//otros import

public class Class {
	
	//Atributos
	private String name;
	private int x, y, width, height;
	private boolean selected;
	private boolean moving;
	private static int count = 0; // count es de la clase en general, no de la clase especifica (static)
	private Vector<Association> associations = new Vector<Association>(); // hay que guardar todas las asociaciones para borrarlas si se borra la clase
	
	public Class(int _x, int _y) {
		this.name = "Class " + (count++);
		this.x = _x;
		this.y = _y;
		this.width = 120;
		this.height = 80;
		this.selected = false;
		this.moving = false;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public void setMoving(boolean _moving) {
		this.moving = _moving;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public Vector<Association> getAssociations() {
		return this.associations;
	}

	public int getHeight() {
		return this.height;
	}
	
	public void draw(Graphics g){
		//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		
		if (selected) {
			g2.setPaint(Color.CYAN);
		}
		else {
			g2.setPaint(Color.WHITE);
		}

		g2.fillRect(x, y, width, height);
        g2.setPaint(Color.BLACK);
        g2.drawRect(x, y, width, height);

		int attrY = y + 30;
        int methodsY = y + 70;
        g2.drawLine(x, attrY, x + width, attrY);
        g2.drawLine(x, methodsY, x + width, methodsY);

        // Texto
        g2.drawString(name, x + 10, y + 20);
        g2.drawString("Atributos", x + 10, attrY + 20);
        g2.drawString("Métodos", x + 10, methodsY + 20);
		// …
		
	}

	public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }


	/*public void toggleSelection() {
        selected = !selected;
    }*/

    public boolean isSelected() {
        return selected;
    }

	public void setSelected(boolean _selected) {
		this.selected = _selected;
	}

	public void addAssociation(Association a) {
		associations.add(a);
	}
	
	//Otros metodos	
	// …
}
