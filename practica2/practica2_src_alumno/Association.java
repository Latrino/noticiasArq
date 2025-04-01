import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.Color;
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
	// constructor para asociaciones con una clase
	public Association(Class _origen) {
		this.origen = _origen;
		this.destino = null;
		this.identifier = count++;
	}

	// constructor para asociaciones con dos clases
	public Association(Class _origen, Class _destino) {
		this.identifier = count++;
		_origen.addAssociation(this);
		if (_origen != _destino){
			_destino.addAssociation(this);
		}
		this.origen = _origen;
		this.destino = _destino;
	}

	public Class setOrigen(Class _origen) {
		return this.origen = _origen;
	}

	public Class setDestino(Class _destino) {
		return this.destino = _destino;
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

		boolean superpuestos = false;

		if (origen == destino) {
            // Dibujar un bucle
            int loopX = oX + oWidth / 2;
            int loopY = oY - 20; // Ajustar la posición del bucle
            graphics2d.drawArc(loopX, loopY, 40, 40, 0, 360);
        }
		else{
			if(oX < dX){
				if ((oX + oWidth) < dX){
					graphics2d.drawLine((oX+oWidth), (oY+(oHeight/2)), dX, (dY+(dHeight/2)));
				}
				// Ahora comprobar cual esta arriba y cual abajo
				else {
					superpuestos = true;
				}
			}
			else{	// dX <= oX
				if ((dX + dWidth) < oX){
					graphics2d.drawLine(dX+dWidth, dY+(dHeight/2), oX, (oY+(oHeight/2)));
				}
				else{
					superpuestos = true;
				}
			}

			if (superpuestos) {
				if(oY > dY){
					graphics2d.drawLine(dX+(dWidth/2), dY+dHeight, (oX+(oWidth/2)), oY);
				}
				else{
					graphics2d.drawLine(oX+(oWidth/2), oY+oHeight, (dX+(dWidth/2)), dY);
				}
			}
		}
	}

	// funcion para borrar la asociacion
	// como Java se encarga de la memoria, no es necesario hacer nada
	public void delete() {
		
	}
	// Más métodos
	// ...

}
