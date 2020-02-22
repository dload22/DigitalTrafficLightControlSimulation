package digitalTrafficLightControlSimulationPlugin;

import de.neemann.digital.lang.Lang;

public final class PluginLang {
	private static final String lang = Lang.currentLanguage().getName();
	
	public static String getTranslation(String key) {
		if (lang.equals("de")) {
			switch (key) {
			case "element_traffic_light_control":
				return "Ampelsteuerung";
			case "node_traffic_light_control":
				return "Ampelsteuerung entwickelt von dload22 auf Basis von Autowaschanlage von TOdan";
			case "pin_input_1":
				return "hR";
			case "pin_input_2":
				return "hGe";
			case "pin_input_3":
				return "hGr";
			case "pin_input_4":
				return "vR";
			case "pin_input_5":
				return "vGe";
			case "pin_input_6":
				return "vGr";
			case "pin_input_7":
				return "fR";
			case "pin_input_8":
				return "fGr";
			case "pin_input_1_des":
				return "horizontal Rot";
			case "pin_input_2_des":
				return "horizontal Gelb";
			case "pin_input_3_des":
				return "horizontal Grün";
			case "pin_input_4_des":
				return "vertikal Rot";
			case "pin_input_5_des":
				return "vertikal Gelb";
			case "pin_input_6_des":
				return "vertikal Grün";
			case "pin_input_7_des":
				return "Fußgänger Rot";
			case "pin_input_8_des":
				return "Fußgänger Grün";
			case "pin_output_1":
				return "SA";
			case "pin_output_2":
				return "SB";
			case "pin_output_3":
				return "R";
			case "pin_output_4":
				return "C";
			case "pin_output_1_des":
				return "Sensor A";
			case "pin_output_2_des":
				return "Sensor B";
			case "pin_output_3_des":
				return "Reset";
			case "pin_output_4_des":
				return "Takt";
			case "tlcwidth":
				return "Fensterbreite";
			case "tlcwidth_des":
				return "Breite des Fensters für die Anzeige der Waschanlagen-Simulation";
			case "tlcheight":
				return "Fensterhöhe";
			case "tlcheight_des":
				return "Höhe des Fensters für die Anzeige der Waschanlagen-Simulation";
			case "tlcclock":
				return "Taktausgang";
			case "tlcclock_des":
				return "Auswahl, ob der Block einen Taktausgang besitzt";
			case "tlcspeed":
				return "Prozentuale Geschwindigkeit";
			case "tlcspeed_des":
				return "Angabe eines Faktors in Prozent, mit dem die Geschwindigkeit der Simulation verändert werden kann";
			case "button_1":
				return "Reset";
			case "text_1":
				return "Zu viel rot";
			case "text_2":
				return "Zu viel Grün";
			case "text_3":
				return "Licht fehlt";
			case "text_4":
				return "Zu lange Rot";
			case "text_5":
				return "Zu lange Grün";
			case "text_6":
				return "Zu viele Lichter";
			case "text_7":
				return "Kein Rot-Gelb";
			case "text_8":
				return "Zu wenige Zustände";
			}
		} else {
			switch (key) {
			case "element_traffic_light_control":
				return "Traffic light control";
			case "node_traffic_light_control":
				return "Traffic light control developed by dload22 based on CarWash by TOdan";
			case "pin_input_1":
				return "hR";
			case "pin_input_2":
				return "hY";
			case "pin_input_3":
				return "hG";
			case "pin_input_4":
				return "vR";
			case "pin_input_5":
				return "vY";
			case "pin_input_6":
				return "vG";
			case "pin_input_7":
				return "pR";
			case "pin_input_8":
				return "pG";
			case "pin_input_1_des":
				return "horizontal red";
			case "pin_input_2_des":
				return "horizontal yellow";
			case "pin_input_3_des":
				return "horizontal green";
			case "pin_input_4_des":
				return "vertical red";
			case "pin_input_5_des":
				return "vertical yellow";
			case "pin_input_6_des":
				return "vertical green";
			case "pin_input_7_des":
				return "pedestrian red";
			case "pin_input_8_des":
				return "pedestrian green";
			case "pin_output_1":
				return "SA";
			case "pin_output_2":
				return "SB";
			case "pin_output_3":
				return "R";
			case "pin_output_4":
				return "C";
			case "pin_output_1_des":
				return "sensor A";
			case "pin_output_2_des":
				return "sensor B";
			case "pin_output_3_des":
				return "reset";
			case "pin_output_4_des":
				return "clock";
			case "tlcwidth":
				return "window width";
			case "tlcwidth_des":
				return "Width of the car wash simulation screen window";
			case "tlcheight":
				return "window height";
			case "tlcheight_des":
				return "Height of the car wash simulation screen window";
			case "tlcclock":
				return "clock output";
			case "tlcclock_des":
				return "Selection of whether the block has a clock output which is switched on a change";
			case "tlcspeed":
				return "percentage speed";
			case "tlcspeed_des":
				return "Specification of a factor in percent with which the speed of the simulation can be changed";
			case "button_1":
				return "reset";
			case "text_1":
				return "Too many red";
			case "text_2":
				return "Too many green";
			case "text_3":
				return "Light missing";
			case "text_4":
				return "Too long red";
			case "text_5":
				return "Too long green";
			case "text_6":
				return "Too many lights";
			case "text_7":
				return "No red-yellow";
			case "text_8":
				return "Not enough states";
			}
		}
		return null;
	}
}