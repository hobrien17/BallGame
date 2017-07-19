package leveldata;

public enum Tutorial {
	GAMESTART ("file:resources/GameStartTutorial.png"),
	CLICK ("file:resources/ClickTutorial.png"),
	SLIDER ("file:resources/SliderTutorial.png"),
	BOUNCER ("file:resources/BouncerTutorial.png");
	
	private String url;
	Tutorial(String url) {
		this.url = url;
	}
	
	public String getImage() {
		return url;
	}
}
