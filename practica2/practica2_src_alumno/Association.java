import java.awt.Graphics;
import java.awt.Graphics2D;
//otros imports …


public class Association {

	// Atributos
	// ...
	private int identifier;
	private static int count = 0;
	private Class origen;
	private Class destino;
	
	// Constructores
	// ...
	// podemos añadir varios constructores, igual es buena idea
	public Association(Class _origen) {
		this.origen = _origen;
		this.destino = null;
		this.identifier = count++;
	}

	// no se si llegaremos a necesitar este constructor
	public Association(Class _origen, Class _destino) {
		this.identifier = count++;
		this.origen = _origen;
		this.destino = _destino;
	}

	public Class setOrigen(Class _origen) {
		this.origen = _origen;
	}

	public Class setDestino(Class _destino) {
		this.destino = _destino;
	}

	public Boolean perteneceAClase(Class _clase) {
		return (this.origen == _clase || this.destino == _clase);
	}
	
	public void draw(Graphics graphics) {
		// Dibuja la asociación
		Graphics2D graphics2d = (Graphics2D)graphics;

		// ...
		graphics2d.setPaint(Color.BLACK);

		int oX = origen.getX();
		int oY = origen.getY();
		int oWidth = origen.getWidth();
		int oHeight = origen.getHeight();

		int dX = destino.getX();
		int dY = destino.getY();
		int dWidth = destino.getWidth();
		int dHeight = destino.getHeight();

		boolean superpuestos = false

		// FALTA DETALLAR COMO SE HARIA UNA ASOCIACION A UNA MISMA CLASE

		if(oX < dX){
			if (oX + oWidth < dX){
				graphics2d.drawLine((oX+oWidth), (oY+(oHeight/2)), dX, (dY+(dHeight/2)))
			}
			// Ahora comprobar cual esta arriba y cual abajo
			else {
				superpuestos = true;
			}
		}
		else{	// dX <= oX
			if (dX + dWidth < oX){
				graphics2d.drawLine((dX+dWidth), (dY+(dHeight/2)), oX, (oY+(oHeight/2)))
			}
			else{
				superpuestos = true;
			}
		}

		if (superpuestos) {
			if(oY > dY){
				graphics2d.drawLine((oX+(oWidth/2)), (oY+oHeight), (dX+(dWidth/2), dY))
			}
			else{
				graphics2d.drawLine((dX+(dWidth/2)), (dY+dHeight), (oX+(oWidth/2), oY))
			}
		}

		if (origen == destino) {
            // Dibujar un bucle
            int loopX = oX + oWidth / 2;
            int loopY = oY - 20; // Ajustar la posición del bucle
            graphics2d.drawArc(loopX, loopY, 40, 40, 0, 360);
        }

	}

	public void delete() {
		//...
	}
	// Más métodos
	// ...

}
