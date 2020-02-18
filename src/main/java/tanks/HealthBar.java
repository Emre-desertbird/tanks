package tanks;

import javafx.scene.control.ProgressBar;

public class HealthBar extends ProgressBar {
  HealthBar(String styleClass) {
    getStyleClass().add(styleClass);
  }
}
