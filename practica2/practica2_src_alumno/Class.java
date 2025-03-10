import java.awt.Graphics;
import java.awt.Graphics2D;
//otros import

public class Class {
	
	//Atributos
	private String name;
	private int x, y, width, height;
	private boolean selected;
	private boolean moving;
	private static int count = 0; // count es de la clase en general, no de la clase especifica (static)
	
	public Class(int _x, int _y) {
		this.name = "Class " + (count++);
		this.x = _x;
		this.y = _y;
		this.width = 120;
		this.height = 80;
		this.selected = false;
		this.moving = false;
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


	public void toggleSelection() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }
	
	//Otros metodos	
	// …
}
