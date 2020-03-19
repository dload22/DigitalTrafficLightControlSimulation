package digitalTrafficLightControlSimulationPlugin;
/*
 * 
 */


import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class TrafficLightControlAnimation extends JPanel {
	public ArrayList<Car> cars = new ArrayList<Car>();
	private ArrayList<Car> carsToRemove = new ArrayList<Car>();
	
	public ArrayList<Pedestrian> pedestrians = new ArrayList<Pedestrian>();
	private ArrayList<Pedestrian> pedestriansToRemove = new ArrayList<Pedestrian>();
	
	private double scale;
	private EVENTS events;
	private TrafficLightControlDialog tlcd;
	
	private boolean sensorA;
	private boolean sensorB;

	public int timerCount = 0;
	public int timerCountYellow = 0;
	public int timerCountRed = 0;
	
	public boolean start;
	public boolean reset;
	
	int[] errorArray = new int[3];
	int[] stateArray = {-1, -1, -1};
	
	public TrafficLightControlAnimation(EVENTS events, TrafficLightControlDialog tlcd, boolean clock) {
		
		this.events = events;
		this.tlcd = tlcd;
		
		initCars();
		initPedestrians();
		setDoubleBuffered(true);
		
		start = false;
		reset = false;
		
		this.scale = 0;
		
		setPreferredSize(new Dimension(2000, 2000));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		setBackground(new Color(255,255,255));
		super.paintComponent(g);
		
		Rectangle2D rectangle = new Rectangle2D.Double();
		RoundRectangle2D roundRectangle= new RoundRectangle2D.Double();
		Ellipse2D ellipse = new Ellipse2D.Double();
		Arc2D arc = new Arc2D.Double();
		g2.setStroke(new BasicStroke((float)(4)));
		
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		scale = (double)getWidth()/2000.0;
		
		g2.scale(scale, scale);
		
		// border
		rectangle.setRect(0, 0, 5, 2000);
		g2.fill(rectangle);
		g2.draw(rectangle);

		rectangle.setRect(1995, 0, 5, 2000);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(0, 0, 2000, 5);
		g2.fill(rectangle);
		g2.draw(rectangle);

		rectangle.setRect(0, 1995, 2000, 5);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		// draw left
		rectangle.setRect(0, 800, 600, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		//pedestrian crossing
		rectangle.setRect(500, 800, 1, 400);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(600, 800, 1, 400);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(0, 1000, 50, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(150, 1000, 100, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(350, 1000, 100, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(450, 1000, 1, 200);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(0, 1200, 600, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		// draw right
		rectangle.setRect(1400, 800, 600, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1500, 1000, 100, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1700, 1000, 100, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1900, 1000, 100, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1400, 800, 1, 200);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1400, 1200, 600, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		// draw top
		rectangle.setRect(800, 0, 1, 600);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1000, 0, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1000, 200, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1000, 400, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(800, 600, 200, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1200, 0, 1, 600);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		// draw bottom
		rectangle.setRect(800, 1400, 1, 600);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1000, 1500, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1000, 1700, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		rectangle.setRect(1000, 1900, 1, 100);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1000, 1400, 200, 1);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		rectangle.setRect(1200, 1400, 1, 600);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		//draw arcs between streets
		arc.setArc(400, 1200, 400, 400, 0, 90, Arc2D.OPEN);
		g2.draw(arc);
		
		arc.setArc(400, 400, 400, 400, 0, -90, Arc2D.OPEN);
		g2.draw(arc);
		
		arc.setArc(1200, 400, 400, 400, 180, 90, Arc2D.OPEN);
		g2.draw(arc);
		
		arc.setArc(1200, 1200, 400, 400, 180, -90, Arc2D.OPEN);
		g2.draw(arc);
		
		// draw bottom car traffic light
		roundRectangle.setRoundRect(1225, 1400, 100, 320, 80, 80);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(1235, 1420, 80, 80);
		if (events.verticalRed) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(1235, 1520, 80, 80);
		if (events.verticalYellow) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(1235, 1620, 80, 80);
		if (events.verticalGreen) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw top car traffic light
		roundRectangle.setRoundRect(675, 280, 100, 320, 80, 80);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(685, 300, 80, 80);
		if (events.verticalGreen) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(685, 400, 80, 80);
		if (events.verticalYellow) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(685, 500, 80, 80);
		if (events.verticalRed) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw left car traffic light
		roundRectangle.setRoundRect(130, 1225, 320, 100, 80, 80);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(350, 1235, 80, 80);
		if (events.horizontalRed) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(250, 1235, 80, 80);
		if (events.horizontalYellow) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(150, 1235, 80, 80);
		if (events.horizontalGreen) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw right car traffic light
		roundRectangle.setRoundRect(1400, 675, 320, 100, 80, 80);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(1420, 685, 80, 80);
		if (events.horizontalRed) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(1520, 685, 80, 80);
		if (events.horizontalYellow) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(1620, 685, 80, 80);
		if (events.horizontalGreen) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw left pedestrian traffic light
		roundRectangle.setRoundRect(375, 555, 100, 220, 80, 80);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(385, 575, 80, 80);
		if (events.pedestrianRed) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(385, 675, 80, 80);
		if (events.pedestrianGreen) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw sensor car bottom
		ellipse.setFrame(1085, 1435, 30, 30);
		if (sensorA) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw sensor car bottom
		ellipse.setFrame(885, 535, 30, 30);
		if (sensorA) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw sensor pedestrian
		ellipse.setFrame(535, 1235, 30, 30);
		if (sensorB) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		//print last 3 errors
		g2.setFont(new Font("Arial", Font.PLAIN, 80));
		g2.setColor(new Color(255,0,0));
		for (int i = 0; i<3; i++) {
			switch (errorArray[i]) {
			case 1: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_1")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_1")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_1")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 2: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_2")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_2")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_2")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 3: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_3")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_3")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_3")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 4: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_4")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_1")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_4")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 5: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_5")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_5")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_5")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 6: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_6")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_6")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_6")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 7: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_7")), 1350, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_7")), 1350, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_7")), 1350, 300);
					break;
				default:
					break;
				}
				break;
			case 8: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_8")), 1250, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_8")), 1250, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_8")), 1250, 300);
					break;
				default:
					break;
				}
				break;
			case 9: 
				switch (i) {
				case 0: 
					g2.drawString((PluginLang.getTranslation("text_9")), 1250, 100);
					break;
				case 1: 
					g2.drawString((PluginLang.getTranslation("text_9")), 1250, 200);
					break;
				case 2: 
					g2.drawString((PluginLang.getTranslation("text_9")), 1250, 300);
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		
		for (Car car : cars) {
			if (car.getCarState() != CarState.WAITING) {
				g2.setColor(getBackground());
				if (car.colision)
					g2.setColor(Color.red);
				g2.fill(car.getCarShape());
				g2.setColor(Color.black);
				g2.draw(car.getCarShape());
				
				if (car.colision && errorArray[0] == 0 && errorArray[1] == 0 && errorArray[2] == 0)
					errorHandler(8);
			}
		}
		
		for (Pedestrian pedestrian : pedestrians) {
			if (pedestrian.getPedestrianState() != PedestrianState.WAITING) {
				g2.setColor(getBackground());
				if (pedestrian.colision)
					g2.setColor(Color.red);
				g2.fill(pedestrian.getPedestrianShape());
				g2.setColor(Color.black);
				g2.draw(pedestrian.getPedestrianShape());
				
				if (pedestrian.colision  && errorArray[0] == 0 && errorArray[1] == 0 && errorArray[2] == 0)
					errorHandler(8);
			}
		}
	}
	
	public void timerTick() {
		sensorA = false;
		sensorB = false;
		Boolean lready = true;
		Boolean bready = true;
		Boolean rready = true;
		Boolean tready = true;
		Boolean pedestrian_ready = true;
		
		stateHistory();
		errorCheck();
		
		if (start) {
			for (Car car : cars) {
				for (Car car2 : cars) {
					if (car != car2) {
						switch (car.getCarPosition()) {
						case 1:				
							if (car2.getCarPosition() == 1 && (car.getCarState() == CarState.ENTER_CROSSING || car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT))
								lready = false;
							break;					
						case 2:				
							if (car2.getCarPosition() == 2 && (car.getCarState() == CarState.ENTER_CROSSING || car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT))
								bready = false;
							break;
						case 3:	
							if (car2.getCarPosition() == 3 && (car.getCarState() == CarState.ENTER_CROSSING || car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT))
								rready = false;
							break;
						case 4:		
							if (car2.getCarPosition() == 4 && (car.getCarState() == CarState.ENTER_CROSSING || car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT))
								tready = false;
							break;
						default:
							break;
						}
					}
				}
			}
			
			for (Car car : cars) {
				switch (car.getCarState()) {
				case WAITING:
					switch (car.getCarPosition()) {
					case 1:				
						if (lready) {
							car.setCarState(CarState.ENTER_CROSSING);
							lready = false;
						}
						break;					
					case 2:				
						if (bready) {
							car.setCarState(CarState.ENTER_CROSSING);
							bready = false;
						}
						break;
					case 3:	
						if (rready) {
							car.setCarState(CarState.ENTER_CROSSING);
							rready = false;
						}
						break;
					case 4:		
						if (tready) {
							car.setCarState(CarState.ENTER_CROSSING);
							tready = false;
						}
						break;
					default:
						break;
					}
					break;
				case ENTER_CROSSING:
					switch (car.getCarPosition()) {
					case 1:				
						car.updatePosition(car.getSpeed(), 0);
						if (car.posX + car.getWidth() > 440)
							car.setCarState(CarState.BEFORE_TRAFFIC_LIGHT);
						break;					
					case 2:				
						car.updatePosition(0, -car.getSpeed());
						if (car.posY < 1410) {
							car.setCarState(CarState.BEFORE_TRAFFIC_LIGHT);
							sensorA = true;
						}
						break;
					case 3:	
						car.updatePosition(-car.getSpeed(), 0);
						if (car.posX < 1410)
							car.setCarState(CarState.BEFORE_TRAFFIC_LIGHT);
						break;
					case 4:		
						car.updatePosition(0, car.getSpeed());
						if (car.posY > 590) {
							car.setCarState(CarState.BEFORE_TRAFFIC_LIGHT);
							sensorA = true;
						}
						break;
					default:
						break;
					}
					break;
				case BEFORE_TRAFFIC_LIGHT:
					switch (car.getCarPosition()) {
					case 1:				
						if (events.horizontalGreen) {
							car.setCarState(CarState.DRIVE_IN);
						}
						break;					
					case 2:	
						sensorA = true;
						if (events.verticalGreen) {
							car.setCarState(CarState.DRIVE_IN);
						}
						break;
					case 3:	
						if (events.horizontalGreen) {
							car.setCarState(CarState.DRIVE_IN);
						}
						break;
					case 4:	
						sensorA = true;
						if (events.verticalGreen) {
							car.setCarState(CarState.DRIVE_IN);
						}
						break;
					default:
						break;
					}
					break;
				case DRIVE_IN:				
					switch (car.getCarPosition()) {
					case 1:
						car.updatePosition(car.getSpeed(), 0);
						if (car.posX > 2000)
							car.setCarState(CarState.DELETE);
						break;					
					case 2:	
						car.updatePosition(0, -car.getSpeed());
						if (car.posY < 0- car.getWidth()) {
							car.setCarState(CarState.DELETE);
						}
						break;
					case 3:	
						car.updatePosition(-car.getSpeed(), 0);
						if (car.posX < 0-car.getWidth())
							car.setCarState(CarState.DELETE);
						break;
					case 4:		
						car.updatePosition(0, car.getSpeed());
						if (car.posY > 2000 + car.getWidth()) {
							car.setCarState(CarState.DELETE);
						}
						break;
					default:
						break;
					}
					break;
				case DELETE:
					switch (car.getCarPosition()) {
					case 1:				
						carsToRemove.add(car);
						break;					
					case 2:				
						carsToRemove.add(car);
						break;
					case 3:	
						carsToRemove.add(car);
						break;
					case 4:		
						carsToRemove.add(car);
						break;
					default:
						break;
					}
					break;
				}
			}
				
			for (Car car : carsToRemove) {
				cars.remove(car);
				cars.add(new Car());
			}
			carsToRemove.clear();
			
			for (Car car : cars) {
				if (car.getCarState() != CarState.WAITING) {
					for (Car car2 : cars) {
						if (car2.getCarState() != CarState.WAITING && car != car2) {
							car.colision = car.getCarShape().intersects(car2.getCarShape().getBounds2D());
							car2.colision = car.colision;
							if (car.colision)
								break;
						}
					}
					if (car.colision) {
						start = false;
						break;
					}
				}
			}
										
			for (Pedestrian pedestrian : pedestrians) {
				if (pedestrian.getPedestrianState() == PedestrianState.ENTER_CROSSING ||pedestrian.getPedestrianState() == PedestrianState.BEFORE_PEDESTRIAN_LIGHT ) {
					pedestrian_ready = false;
				}
			}
			
			for (Pedestrian pedestrian : pedestrians) {
				switch (pedestrian.getPedestrianState()) {
				case WAITING:
					if (pedestrian_ready) {
						pedestrian.setPedestrianState(PedestrianState.ENTER_CROSSING);
						pedestrian_ready = false;
					}
					break;
				case ENTER_CROSSING:			
					pedestrian.updatePosition(0, -pedestrian.getSpeed());
					if (pedestrian.posY < 1230) {
						pedestrian.setPedestrianState(PedestrianState.BEFORE_PEDESTRIAN_LIGHT);						
						sensorB = true;
					}
					break;					
				case BEFORE_PEDESTRIAN_LIGHT:
					sensorB = true;
					if (events.pedestrianGreen) {
						pedestrian.setPedestrianState(PedestrianState.WALK_IN);
					}
					break;										
				case WALK_IN:
					pedestrian.updatePosition(0, -pedestrian.getSpeed());
					if (pedestrian.posY < 0)
						pedestrian.setPedestrianState(PedestrianState.DELETE);
					break;					
				case DELETE:			
					pedestriansToRemove.add(pedestrian);
					break;					
				default:
					break;
				}
			}
	
			for (Pedestrian pedestrian : pedestriansToRemove) {
				pedestrians.remove(pedestrian);
				pedestrians.add(new Pedestrian());
			}
			pedestriansToRemove.clear();
			
			for (Pedestrian pedestrian : pedestrians) {
				if (pedestrian.getPedestrianState() != PedestrianState.WAITING) {
					for (Pedestrian pedestrian2 : pedestrians) {
						if (pedestrian2.getPedestrianState() != PedestrianState.WAITING && pedestrian != pedestrian2) {
							pedestrian.colision = pedestrian.getPedestrianShape().intersects(pedestrian2.getPedestrianShape().getBounds2D());
							pedestrian2.colision = pedestrian.colision;
							if (pedestrian.colision)
								break;
						}
					}
					if (pedestrian.colision) {
						start = false;
						break;
					}
				}
			}

			for (Car car : cars) {
				for (Pedestrian pedestrian : pedestrians) {
					if (car.colision == false && pedestrian.colision == false) {
						car.colision = car.getCarShape().intersects(pedestrian.getPedestrianShape().getBounds2D());
						pedestrian.colision = car.colision;
						if (car.colision)
							break;
					}
				}
				if (car.colision) {
					start = false;
					break;
				}
			}
		}
		
		timerCount++;
		//sets clock every second
		if (timerCount == 30) {
			tlcd.updateOutput(sensorA, sensorB, reset, true);
			timerCount = 0;
			reset = false;
		} else {
			tlcd.updateOutput(sensorA, sensorB, false, false);
		}
	}
	
	protected void errorCheck() {
		if (events.horizontalYellow || events.verticalYellow)
			timerCountYellow++;
		if (!events.horizontalYellow && !events.verticalYellow)
			timerCountYellow = 0;
		if (timerCountYellow > 67) {
			errorHandler(5);
			timerCountYellow = 0;
		}
		
		if (events.horizontalRed || events.verticalRed || events.pedestrianRed)
			timerCountRed++;
		if (events.horizontalYellow || events.verticalYellow)
			timerCountRed = 0;
		if (timerCountRed > 231) {
			errorHandler(4);
			timerCountRed = 0;
		}
		
		//checks if all traffic lights are red
		if (events.horizontalRed && (events.verticalRed || events.pedestrianRed) && !events.horizontalYellow && !events.verticalYellow)
			errorHandler(1);
		//checks if a traffic light is off
		if ((!events.horizontalRed && !events.horizontalYellow && !events.horizontalGreen) || (!events.verticalRed && !events.verticalYellow && !events.verticalGreen) || (!events.pedestrianRed && !events.pedestrianGreen))
			errorHandler(3);
		//checks if car light is same as pedestrian light
		//if ((events.verticalRed && !events.pedestrianRed) || (!events.verticalRed && !events.verticalYellow && events.pedestrianRed) || (events.verticalGreen && !events.pedestrianGreen) || (!events.verticalGreen && events.pedestrianGreen) )
			//errorHandler(8);
		
		//checks if too many lights are on
		if (events.horizontalGreen && (events.horizontalYellow || events.horizontalRed || events.verticalYellow))
			errorHandler(6);
		if (events.verticalGreen && (events.verticalYellow || events.verticalRed || events.horizontalYellow))
			errorHandler(6);
		if (events.pedestrianRed && events.pedestrianGreen)
			errorHandler(6);
		if ((stateArray[1] == 0 && events.verticalYellow) || (stateArray[1] == 3  && events.horizontalYellow))
			errorHandler(6);
		/*if ((stateArray[2] == 0 && stateArray[1] == 5 && stateArray[0] == 2) || (stateArray[2] == 3 && stateArray[1] == 2 && stateArray[0] == 5)) {
		    errorHandler(6);
		}*/
		
		//checks if too many lights are green
		if (events.horizontalGreen && (events.verticalGreen || events.pedestrianGreen))
			errorHandler(2);		
		
		//checks if too many lights are yellow
		if (events.horizontalYellow && events.verticalYellow)
			errorHandler(9);		
		
		//checks if no red-yellow is used
		if (((events.horizontalGreen && !events.verticalGreen) || (!events.horizontalGreen && events.verticalGreen)) && !((stateArray[2] == 4 && stateArray[1] == 5 && stateArray[0] == 0) || (stateArray[2] == 1 && stateArray[1] == 2 && stateArray[0] == 3) || stateArray[2] == -1 || stateArray[1] == -1 || stateArray[0] == -1)) {
			errorHandler(7);
		}
	}
	
	//creates rotating array of errors
	public void errorHandler(int errorNumber) {
		if (errorNumber != errorArray[0] && errorNumber != errorArray[1] && errorNumber != errorArray[2]) {
			if (errorArray[0] == 0) {
				errorArray[0] = errorNumber;
			} else if (errorArray[1] == 0) {
				errorArray[1] = errorArray[0];
				errorArray[0] = errorNumber;
			} else if (errorArray[2] == 0) {
				errorArray[2] = errorArray[1];
				errorArray[1] = errorArray[0];
				errorArray[0] = errorNumber;
			} else {
				errorArray[2] = errorArray[1];
				errorArray[1] = errorArray[0];
				errorArray[0] = errorNumber;
			}
		}
	}
	
	protected int stateHandler() {
		if (events.horizontalGreen)
			return 0;
		if (events.verticalRed && !events.horizontalRed && events.horizontalYellow)
			return 1;
		if (events.verticalRed && events.verticalYellow)
			return 2;
		if (events.verticalGreen)
			return 3;
		if (events.horizontalRed && events.verticalYellow && !events.verticalRed)
			return 4;
		if (events.horizontalRed && events.horizontalYellow)
			return 5;
		else
			return -1;
	}
	
	protected void stateHistory() {
		int state = stateHandler();
		//System.out.println(state);
		if (state != stateArray[0] && state != stateArray[1] && state != stateArray[2]) {
			if (stateArray[0] == -1) {
				stateArray[0] = state;
			} else if (stateArray[1] == -1) {
				stateArray[1] = stateArray[0];
				stateArray[0] = state;
			} else if (stateArray[2] == -1) {
				stateArray[2] = stateArray[1];
				stateArray[1] = stateArray[0];
				stateArray[0] = state;
			} else {
				stateArray[2] = stateArray[1];
				stateArray[1] = stateArray[0];
				stateArray[0] = state;
			}
		}
	}
	
	public void initCars() {
		cars.clear();
		for (int i=0; i<20; i++) {
			cars.add(new Car());
		}
	}
	
	public void initPedestrians() {
		pedestrians.clear();
		for (int i=0; i<3; i++) {
			pedestrians.add(new Pedestrian());
		}
	}
 }