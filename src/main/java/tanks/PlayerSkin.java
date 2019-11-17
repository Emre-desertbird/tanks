package tanks;

import javafx.scene.image.Image;

public enum PlayerSkin {
	BLUE("/graphics/tank-lower-blue.png", "/graphics/tank-upper-blue.png", 1, 0), //
	RED("/graphics/tank-lower-red.png", "/graphics/tank-upper-red.png", 0, 0), //
	MAGENTA("/graphics/tank-lower-magenta.png", "/graphics/tank-upper-magenta.png", 1, 1), //
	GREEN("/graphics/tank-lower-green.png", "/graphics/tank-upper-green.png", 0, 1), //
	;

	private final Image bodyImage;
	private final Image turretImage;
	private final int x;
	private final int y;

	private PlayerSkin(String bodyFilename, String turretFilename, int x, int y) {
		bodyImage = new Image(getClass().getResource(bodyFilename).toExternalForm(), 19 * 4, 21 * 4, true, false);
		turretImage = new Image(getClass().getResource(turretFilename).toExternalForm(), 16 * 4, 9 * 4, true, false);
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
