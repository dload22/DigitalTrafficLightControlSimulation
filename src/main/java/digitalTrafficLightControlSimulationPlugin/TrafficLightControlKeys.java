package digitalTrafficLightControlSimulationPlugin;

import de.neemann.digital.core.element.Key;

public final class TrafficLightControlKeys {
	public static final Key<Integer> TLC_WIDTH = new Key.KeyInteger("tlcwidth", 1000).setName(PluginLang.getTranslation("tlcwidth"))
			.setDescription(PluginLang.getTranslation("tlcwidth_des"));
	public static final Key<Integer> TLC_HEIGHT = new Key.KeyInteger("tlcheight", 1000).setName(PluginLang.getTranslation("tlcheight"))
			.setDescription(PluginLang.getTranslation("tlcheight_des"));	
	public static final Key<Boolean> TLC_CLOCK = new Key<>("tlcclock", true).setName(PluginLang.getTranslation("tlcclock"))
			.setDescription(PluginLang.getTranslation("tlcclock_des"));
	public static final Key<Integer> TLC_SPEED = new Key.KeyInteger("tlcspeed", 100).setName(PluginLang.getTranslation("tlcspeed"))
			.setDescription(PluginLang.getTranslation("tlcspeed_des"));
}