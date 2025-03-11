import java.awt.Graphics;
import java.awt.Graphics2D;
//otros imports …


public class Association {

	// Atributos
	// ...
	private int identifier;
	private static int count = 0;
	
	// Constructores
	// ...
	public Association() {
		this.identifier = count++;
	}
	
	public void draw(Graphics graphics) {
		// Dibuja la asociación
		Graphics2D graphics2d = (Graphics2D)graphics;

		// ...
	}

	// Más métodos
	// ...

}
