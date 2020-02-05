package tanks;

import javafx.scene.image.Image;

public enum PlayerSkin {
	BLUE("/graphics/hull-blue.png", "/graphics/gun-blue.png", 1, 0), //
	RED("/graphics/hull-red.png", "/graphics/gun-red.png", 0, 0), //
	YELLOW("/graphics/hull-yellow.png", "/graphics/gun-yellow.png", 1, 1), //
	GREEN("/graphics/hull-green.png", "/graphics/gun-green.png", 0, 1), //
	
	;

	private final Image bodyImage;
	private final Image turretImage;
	private final int x;
	private final int y;

	private PlayerSkin(String bodyFilename, String turretFilename, int x, int y) {
		bodyImage = new Image(getClass().getResource(bodyFilename).toExternalForm(), 256/2, 256/2, true, false);
		turretImage = new Image(getClass().getResource(turretFilename).toExternalForm(), 218/2, 86/2, true, false);

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getBodyImage() {
		return bodyImage;
	}

	public Image getTurretImage() {
		return turretImage;
	}

}
