/*
 * 
 */
package digitalTrafficLightControlSimulationPlugin;

import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.gui.Main;
import de.neemann.digital.gui.components.CircuitComponent;

import javax.swing.*;

import static de.neemann.digital.core.element.PinInfo.input;

/**
 */
public class TrafficLightControl extends Node implements Element {
	
	
	/**
	 * The traffic light control description
	 */
	public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(TrafficLightControl.class,
			//add inputs to building block
			input(PluginLang.getTranslation("pin_input_1"), PluginLang.getTranslation("pin_input_1_des")),
			input(PluginLang.getTranslation("pin_input_2"), PluginLang.getTranslation("pin_input_2_des")),
			input(PluginLang.getTranslation("pin_input_3"), PluginLang.getTranslation("pin_input_3_des")),
			input(PluginLang.getTranslation("pin_input_4"), PluginLang.getTranslation("pin_input_4_des")),
			input(PluginLang.getTranslation("pin_input_5"), PluginLang.getTranslation("pin_input_5_des")),
			input(PluginLang.getTranslation("pin_input_6"), PluginLang.getTranslation("pin_input_6_des")),
			input(PluginLang.getTranslation("pin_input_7"), PluginLang.getTranslation("pin_input_7_des")),
			input(PluginLang.getTranslation("pin_input_8"), PluginLang.getTranslation("pin_input_8_des")))
			.addAttribute(TrafficLightControlKeys.TLC_WIDTH)
			.addAttribute(TrafficLightControlKeys.TLC_HEIGHT)
			.addAttribute(TrafficLightControlKeys.TLC_CLOCK)
			.addAttribute(TrafficLightControlKeys.TLC_SPEED)
			.addAttribute(Keys.ROTATE)
			.addAttribute(Keys.LABEL)
			.setShortName(PluginLang.getTranslation("element_traffic_light_control"));
	
	private final String label;
	private final ObservableValue sensorA;
	private final ObservableValue sensorB;
	private final ObservableValue reset;
	private final ObservableValue clock;
	private boolean setClock;
    private final ElementAttributes attr;
	private TrafficLightControlDialog trafficLightControlDialog;
	
	private ObservableValue horizontalRed;
	private ObservableValue horizontalYellow;
	private ObservableValue horizontalGreen;
	private ObservableValue verticalRed;
	private ObservableValue verticalYellow;
	private ObservableValue verticalGreen;
	private ObservableValue pedestrianRed;
	private ObservableValue pedestrianGreen;
	
	/**
     * Creates a new traffic light control instance
     *
     * @param attributes the attributes
     */
	public TrafficLightControl(ElementAttributes attributes) {
		sensorA = new ObservableValue("SA", 1).setDescription(PluginLang.getTranslation("pin_output_1_des"));
		sensorB = new ObservableValue("SB", 1).setDescription(PluginLang.getTranslation("pin_output_2_des"));
		reset = new ObservableValue("R", 1).setDescription(PluginLang.getTranslation("pin_output_3_des"));
		clock = new ObservableValue("C", 1).setDescription(PluginLang.getTranslation("pin_output_4_des"));
		
		label = attributes.getLabel();
		attr = attributes;
		setClock = attributes.get(TrafficLightControlKeys.TLC_CLOCK);
	}
	
	
	
	@Override
	public void setInputs(ObservableValues inputs) throws NodeException {
		horizontalRed = inputs.get(0).addObserverToValue(this).checkBits(1, this);
		horizontalYellow = inputs.get(1).addObserverToValue(this).checkBits(1, this);
		horizontalGreen = inputs.get(2).addObserverToValue(this).checkBits(1, this);
		verticalRed = inputs.get(3).addObserverToValue(this).checkBits(1, this);
		verticalYellow = inputs.get(4).addObserverToValue(this).checkBits(1, this);
		verticalGreen = inputs.get(5).addObserverToValue(this).checkBits(1, this);
		pedestrianRed = inputs.get(6).addObserverToValue(this).checkBits(1, this);
		pedestrianGreen = inputs.get(7).addObserverToValue(this).checkBits(1, this);
	}
	
	@Override
    public ObservableValues getOutputs() {
		if (setClock)
			return new ObservableValues(sensorA, sensorB, reset, clock);
		else
			return new ObservableValues(sensorA, sensorB, reset);
    }
	
	@Override
    public void readInputs() throws NodeException {
		SwingUtilities.invokeLater(() -> {
            if (trafficLightControlDialog == null || !trafficLightControlDialog.isVisible()) {
            	trafficLightControlDialog = new TrafficLightControlDialog(getModel().getWindowPosManager().getMainFrame(), attr,this.getModel(), this);
                getModel().getWindowPosManager().register("trafficLightControl_" + label, trafficLightControlDialog);
            }
           trafficLightControlDialog.update(horizontalRed.getBool(), horizontalYellow.getBool(), horizontalGreen.getBool(), verticalRed.getBool(), verticalYellow.getBool(), verticalGreen.getBool(), pedestrianRed.getBool(), pedestrianGreen.getBool());
        });
    }
	
	@Override
    public void writeOutputs() throws NodeException {
    }
	
	public void setOutputs(boolean senA, boolean senB, boolean reset, boolean clock) throws NodeException {
		sensorA.setBool(senA);
		sensorB.setBool(senB);
		this.reset.setBool(reset);
		
		if (setClock) {
			this.getModel().fireManualChangeEvent();
			this.getModel().doStep();
			this.clock.setBool(clock);
		}
		
		this.getModel().fireManualChangeEvent();
		this.getModel().doStep();
		
		Main main = ((Main) getModel().getWindowPosManager().getMainFrame());
		CircuitComponent cc = main.getCircuitComponent();
		SwingUtilities.invokeLater(() -> {
			cc.paintImmediately();
		});
	}
}