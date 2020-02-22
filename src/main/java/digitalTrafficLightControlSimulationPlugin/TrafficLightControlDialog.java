package digitalTrafficLightControlSimulationPlugin;

import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrafficLightControlDialog extends JDialog {
	
	private TrafficLightControlAnimation animation;
	private JPanel animationPane;
	private Timer timer;
	private EVENTS events = new EVENTS();
	private Model model;
	private TrafficLightControl trafficLightControl;
	
	private int speed;
	
	private static String getDialogTitle(ElementAttributes attr, TrafficLightControl trafficLightControl) {
        String t = attr.getLabel();
        if (t.length() > 0) return t;
        
        return PluginLang.getTranslation("node_traffic_light_control");
    }
	
	/**
     * Creates a new instance
     *
     * @param parent the parent window
     * @param attr the traffic light control attributes
     */
	public TrafficLightControlDialog(JFrame parent, ElementAttributes attr,Model model, TrafficLightControl trafficLightControl) {
		super(parent, getDialogTitle(attr, trafficLightControl), false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.model = model;
		this.trafficLightControl = trafficLightControl;
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(new BorderLayout());
		
		animation = new TrafficLightControlAnimation(events, this, attr.get(TrafficLightControlKeys.TLC_CLOCK));
		
		animationPane = new JPanel(new ResizeAspectRatioLayout());
		animationPane.setBackground(Color.white);
		animationPane.add(animation);
		getContentPane().add(animationPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		JButton buttonReset = new JButton(PluginLang.getTranslation("button_1"));

		animation.start = true;
		
		buttonReset.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.initCars();
				animation.initPedestrians();
				animation.reset = true;
				animation.start = true;
				for (int i = 0; i<3; i++) {
					animation.errorArray[i] = 0;
				}
				for (int i = 0; i<3; i++) {
					animation.stateArray[i] = -1;
				}
				animation.timerCount = 0;
				animation.timerCountRed = 0;
				animation.timerCountGreen = 0;
	        }  
	    });
		
		toolBar.add(buttonReset);
		toolBar.addSeparator();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		setSize(attr.get(TrafficLightControlKeys.TLC_WIDTH), attr.get(TrafficLightControlKeys.TLC_HEIGHT));
		
		setVisible(true);		
		
		speed = (int) (33/(attr.get(TrafficLightControlKeys.TLC_SPEED)/100.0));
		timer = new Timer(speed, new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e ) {
				animation.TimerTick();
				animation.repaint();
			}
		});
		
		timer.start();
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent event) {
				timer.stop();
				event.getWindow().dispose();
			}

			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {
				timer.stop();
			}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
		
	}
	
	public void update(boolean horizontalRed, boolean horizontalYellow, boolean horizontalGreen, boolean verticalRed, boolean verticalYellow, boolean verticalGreen, boolean pedestrianRed, boolean pedestrianGreen) {
		events.horizontalRed = horizontalRed;
		events.horizontalYellow = horizontalYellow;
		events.horizontalGreen = horizontalGreen;
		events.verticalRed = verticalRed;
		events.verticalYellow = verticalYellow;
		events.verticalGreen = verticalGreen;
		events.pedestrianRed = pedestrianRed;
		events.pedestrianGreen = pedestrianGreen;
	}
	
	public void updateOutput(boolean senA, boolean senB, boolean reset, boolean clock) {
		try {
			model.accessNEx(() -> {
				try {
					trafficLightControl.setOutputs(senA, senB, reset, clock);
				} catch (NodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} catch (NodeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class EVENTS {
	public Boolean horizontalRed = false;
	public Boolean horizontalYellow = false;
	public Boolean horizontalGreen = false;
	public Boolean verticalRed = false;
	public Boolean verticalYellow = false;
	public Boolean verticalGreen = false;
	public Boolean pedestrianRed = false;
	public Boolean pedestrianGreen = false;
}