package moonlander;

import javafx.scene.image.Image;

public enum PlayerSkin {
  BLUE("/graphics/tank-lower-blue.png", "/graphics/tank-upper-blue.png"), //
  RED("/graphics/tank-lower-red.png", "/graphics/tank-upper-red.png"), //
  ;

  private final Image bodyImage;
  private final Image turretImage;

  private PlayerSkin(String bodyFilename, String turretFilename) {
    bodyImage = new Image(getClass().getResource(bodyFilename).toExternalForm(), 19 * 4, 21 * 4,
        true, false);
    turretImage = new Image(getClass().getResource(turretFilename).toExternalForm(), 16 * 4, 9 * 4,
        true, false);
  }
  
  public Image getBodyImage() {
    return bodyImage;
  }
  
  public Image getTurretImage() {
    return turretImage;
  }

}
