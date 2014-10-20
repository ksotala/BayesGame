package bayesGame.ui;

public interface InterfaceView {

	public static final int eventListenerTypeTutorialChooser = 0;
	public static final int eventListenerTypeAssumingChooser = 1;
	public static final int eventListenerTypeInteractionChooser = 2;
	public static final int eventListenerTypeProceedChooser = 3;
	
	public static final int callbackLocationKeyMessage = 0;
	public static final int callbackLocationMouseMessage = 1;
	public static final int callbackLocationGenericMessage = 2;
	public static final int callbackLocationGenericMessageObject = 3;
	
	public void addText(String text);
	public void addRefreshDisplay();
	public void processEventQueue();
	
}
