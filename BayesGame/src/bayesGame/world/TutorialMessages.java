package bayesGame.world;

import java.util.HashMap;
import java.util.Map;

public class TutorialMessages {
	
	private static Map<String, String> messages;

	public TutorialMessages(){
		messages = new HashMap<String,String>();
	}
	
	public static boolean contains(String string) {
		return messages.containsKey(string);
	}

	public static void put(String key, String object) {
		messages.put(key, object);
	}

	public static String get(String helpReference) {
		return messages.get(helpReference);
	}
	
	
	
	

}
