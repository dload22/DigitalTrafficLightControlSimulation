package digitalTrafficLightControlSimulationPlugin;


import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class Pedestrian {
	private PedestrianState pedestrianState;
	private PedestrianType pedestrianType;
	private GeneralPath pedestrianShape;
	
	public int posX = 0;
	public int posY = 0;
	
	public boolean colision = false;
	
	private static final Random random = new Random();
	
	/**
     * Creates a new pedestrian instance
     */
	public Pedestrian () {
		this.pedestrianState = PedestrianState.WAITING;
		this.pedestrianType = randomEnum(PedestrianType.class);
		this.pedestrianShape = GeneratePedestrian(getWidth(), getHeight(), this.pedestrianType);
		
		updatePosition(550 - getWidth()/2, 2000);
	}
	
	public PedestrianState getPedestrianState() {
		return pedestrianState;
	}
	
	public void setPedestrianState(PedestrianState pedestrianState) {
		this.pedestrianState = pedestrianState;
	}
	
	public PedestrianType getPedestrianType() {
		return pedestrianType;
	}
	
	public Shape getPedestrianShape() {
		return pedestrianShape;
	}
	
	public void updatePosition(int dX, int dY) {
		this.posX += dX;
		this.posY += dY;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(dX, dY);
		this.pedestrianShape.transform(at);
	}
	
	//returns a random state from given enum
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public int getWidth() {
		switch (pedestrianType) {
		case NORMAL:
			return 50;
		case MEXICAN:
			return 80;
		case BASEBALLCAP:
			return 50;
		default:
			return 0;
		}
	}
	
	public int getHeight() {
		switch (pedestrianType) {
		case NORMAL:
			return 50;
		case MEXICAN:
			return 80;
		case BASEBALLCAP:
			return 50;
		default:
			return 0;
		}
	}
	
	public int getSpeed() {
		switch (pedestrianType) {
		case NORMAL:
			return 10;
		case MEXICAN:
			return 10;
		case BASEBALLCAP:
			return 10;
		default:
			return 0;
		}
	}
	
	//paint pedestrians
	private GeneralPath GeneratePedestrian(double width, double height, PedestrianType pedestrianType) {
		GeneralPath p = new GeneralPath();
		Ellipse2D ellipse = new Ellipse2D.Double();
		Arc2D arc = new Arc2D.Double();
		
		switch (pedestrianType) {
		case NORMAL:		
			ellipse.setFrame(0, 0, width, height/2);
			p.append(ellipse, false);
			ellipse.setFrame(0.3*width, 0, 0.4*width, 0.4*height);
			p.append(ellipse, false);

			break;
		case MEXICAN:
			// generate path for pedestrian with sombrero
			ellipse.setFrame(0, 0, width, height);
			p.append(ellipse, false);
			
			break;
		case BASEBALLCAP:
			// generate path for pedestrian with baseball cap
			ellipse.setFrame(0, 0, width, height/2);
			p.append(ellipse, false);
			ellipse.setFrame(0.3*width, 0, 0.4*width, 0.4*height);
			p.append(ellipse, false);
			arc.setArc(0.3*width, -0.3*height, 0.4*width, 0.6*height, 0, 180, Arc2D.OPEN);
			p.append(arc, false);
						
			break;
		default:
			break;
		}
		return p;
	}
}

enum PedestrianState {
	WAITING, ENTER_CROSSING, BEFORE_PEDESTRIAN_LIGHT, WALK_IN, WALK_OUT;
}

enum PedestrianType {
	NORMAL, MEXICAN, BASEBALLCAP;
}