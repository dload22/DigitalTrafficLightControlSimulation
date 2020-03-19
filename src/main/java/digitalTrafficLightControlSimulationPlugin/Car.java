package digitalTrafficLightControlSimulationPlugin;


import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class Car {
	private CarState carState;
	private CarType carType;
	private GeneralPath carShape;
	private int carPosition;
	
	public int posX = 0;
	public int posY = 0;
	public int sleepTime = 0;
	public boolean colision = false;
	
	private static final Random random = new Random();	
	/**
     * Creates a new car instance
     */
	public Car () {
		this.carState = CarState.WAITING;
		this.carType = randomEnum(CarType.class);
		this.carPosition = random.nextInt(4) + 1;
		this.carShape = GenerateCar(getWidth(), getHeight(), this.carType, this.carPosition);
		
		switch (carPosition) {
		case 1:	
			updatePosition(0 - getWidth(), 1100-(getHeight()/2));
			break;					
		case 2:		
			updatePosition(1100 - (getHeight()/2), 2000);
			break;
		case 3:		
			updatePosition(2000, 900-(getHeight()/2));
			break;
		case 4:		
			updatePosition(900 - (getHeight()/2), 0-getWidth());
			break;
		default:
			break;
		}
	}
	
	public CarState getCarState() {
		return carState;
	}
	
	public void setCarState(CarState carState) {
		this.carState = carState;
	}
	
	public CarType getCarType() {
		return carType;
	}
	
	public Shape getCarShape() {
		return carShape;
	}
	
	public int getCarPosition() {
		return carPosition;
	}
	
	public void setCarPosition(int carPosition) {
		this.carPosition = carPosition;
	}
	
	public void updatePosition(int dX, int dY) {
		this.posX += dX;
		this.posY += dY;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(dX, dY);
		this.carShape.transform(at);
	}
	
	//returns a random state from given enum
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public int getWidth() {
		switch (carType) {
		case COMBI:
			return 180;
		case BUS:
			return 300;
		case PICKUP:
			return 150;
		default:
			return 0;
		}
	}
	
	public int getHeight() {
		switch (carType) {
		case COMBI:
			return 60;
		case BUS:
			return 100;
		case PICKUP:
			return 60;
		default:
			return 0;
		}
	}
	
	public int getSpeed() {
		switch (carType) {
		case COMBI:
			return 20;
		case BUS:
			return 20;
		case PICKUP:
			return 20;
		default:
			return 0;
		}
	}
	
	//paint cars
	private GeneralPath GenerateCar(double width, double height, CarType carType, int carPosition) {
		GeneralPath p = new GeneralPath();
		AffineTransform at;
		Shape s;
		switch (carType) {
		case COMBI:
			// generate path for combi
			p.moveTo(0, 0);
			p.lineTo(width, 0);
			p.lineTo(width, height);
			p.lineTo(0, height);
			p.lineTo(0, 0);
			
			p.moveTo(0.05*width, 0);
			p.lineTo(0.05*width, height);
			
			p.moveTo(0.7*width, 0);
			p.lineTo(0.7*width, height);
			break;
		case BUS:
			// generate path for bus			
			p.moveTo(0, 0);
			p.lineTo(width, 0);
			p.lineTo(width, height);
			p.lineTo(0, height);
			p.lineTo(0, 0);
			
			p.moveTo(0.7*width, 0);
			p.lineTo(0.7*width, height);
			
			break;
		case PICKUP:
			// generate path for pickup
			p.moveTo(0, 0);
			p.lineTo(width, 0);
			p.lineTo(width, height);
			p.lineTo(0, height);
			p.lineTo(0, 0);
			
			p.moveTo(0.2*width, 0);
			p.lineTo(0.2*width, height);
			
			p.moveTo(0.7*width, 0);
			p.lineTo(0.7*width, height);
						
			break;
		default:
			break;
		}
		//rotate car by position
		/*1: left - no rotation
		 *2: bottom - 90 degree rotation
		 *3: right - 180 degree rotation
		 *4: top - 270 degree rotation
		 */
		switch (carPosition) {
		case 2:				

			at = new AffineTransform();
			at.setToTranslation(0, getWidth());
			at.rotate(-(Math.PI /2));
			s = at.createTransformedShape(p);
			p.reset();
			p.append(s, false);
			
			break;
		case 3:				

			at = new AffineTransform();
			at.setToTranslation(getWidth(), getHeight());
			at.rotate(Math.PI);			
			s = at.createTransformedShape(p);
			p.reset();
			p.append(s, false);
			
			break;
		case 4:				

			at = new AffineTransform();
			at.setToTranslation(getHeight(), -getWidth());
			at.rotate(Math.PI /2);		
			s = at.createTransformedShape(p);
			p.reset();
			p.append(s, false);
			
			break;			
		default:
			break;
		}
		return p;
	}
}

enum CarState {
	WAITING, ENTER_CROSSING, BEFORE_TRAFFIC_LIGHT, DRIVE_IN, DELETE;
}

enum CarType {
	COMBI, BUS, PICKUP;
}