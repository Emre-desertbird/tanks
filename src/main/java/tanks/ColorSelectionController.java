package tanks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ColorSelectionController implements Initializable {


  private static final String RED_STYLE = "-fx-background-color: #ff0000";
  private static final String YELLOW_STYLE = "-fx-background-color: #daa520";
  private static final String GREEN_STYLE = "-fx-background-color: #008000";
  private static final String BLUE_STYLE = "-fx-background-color: #0000cd";
  private static final String RED_BORDER_STYLE =
      "-fx-background-color: #ff0000; -fx-border-color: black; -fx-border-width: 6px";
  private static final String YELLOW_BORDER_STYLE =
      "-fx-background-color: #daa520; -fx-border-color: black; -fx-border-width: 6px";
  private static final String GREEN_BORDER_STYLE =
      "-fx-background-color: #008000; -fx-border-color: black; -fx-border-width: 6px";
  private static final String BLUE_BORDER_STYLE =
      "-fx-background-color: #0000cd; -fx-border-color: black; -fx-border-width: 6px";

  public static ColorSelectionController load() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ColorSelectionController.class.getResource("colorSelection.fxml"));
    loader.load();
    return loader.getController();
  }

  @FXML
  private Parent root;
  @FXML
  private Label captionLabel;
  @FXML
  private Label labelPlayer1;
  @FXML
  private Label labelPlayer2;
  @FXML
  private Label labelPlayer3;
  @FXML
  private Label labelPlayer4;
  @FXML
  private Button bluePlayer1;
  @FXML
  private Button greenPlayer1;
  @FXML
  private Button yellowPlayer1;
  @FXML
  private Button redPlayer1;
  @FXML
  private Button bluePlayer2;
  @FXML
  private Button greenPlayer2;
  @FXML
  private Button yellowPlayer2;
  @FXML
  private Button redPlayer2;
  @FXML
  private Button bluePlayer3;
  @FXML
  private Button greenPlayer3;
  @FXML
  private Button yellowPlayer3;
  @FXML
  private Button redPlayer3;
  @FXML
  private Button bluePlayer4;
  @FXML
  private Button greenPlayer4;
  @FXML
  private Button yellowPlayer4;
  @FXML
  private Button redPlayer4;

  private int selectedColors = 0;

  private boolean[] playerColorSelected = new boolean[4];

  private ColorSelectContext context;

  World world = new World();

  private enum PlayerColor {
    BLUE(0), RED(1), YELLOW(2), GREEN(3);

    private final int colorIndex;

    PlayerColor(int colorIndex) {
      this.colorIndex = colorIndex;
    }

    public int getColorIndex() {
      return colorIndex;
    }
  }

  private PlayerColor[] playerColor = new PlayerColor[4];
  private Button[][] buttons = new Button[4][4];

  private void setPlayerColor(int playerIndex, PlayerColor color) {
    playerColor[playerIndex] = color;
    for (int i = 0; i < 4; ++i) {
      if (i != color.getColorIndex()) {
        buttons[playerIndex][i].setDisable(true);
      }
    }
    for (int i = 0; i < 4; ++i) {
      if (i != playerIndex) {
        buttons[i][color.getColorIndex()].setDisable(true);
      }
    }
    playerColorSelected[playerIndex] = true;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    buttons[0][0] = bluePlayer1;
    buttons[0][1] = redPlayer1;
    buttons[0][2] = yellowPlayer1;
    buttons[0][3] = greenPlayer1;
    buttons[1][0] = bluePlayer2;
    buttons[1][1] = redPlayer2;
    buttons[1][2] = yellowPlayer2;
    buttons[1][3] = greenPlayer2;
    buttons[2][0] = bluePlayer3;
    buttons[2][1] = redPlayer3;
    buttons[2][2] = yellowPlayer3;
    buttons[2][3] = greenPlayer3;
    buttons[3][0] = bluePlayer4;
    buttons[3][1] = redPlayer4;
    buttons[3][2] = yellowPlayer4;
    buttons[3][3] = greenPlayer4;

    setButtonColor();

    bluePlayer1.setOnAction(e -> {
      buttonAction1(playerColorSelected[0]);
      setPlayerColor(0, PlayerColor.BLUE);
      bluePlayer1.setStyle(BLUE_BORDER_STYLE);
      world.setColorPlayer1(0);
    });

    bluePlayer2.setOnAction(e -> {
      buttonAction1(playerColorSelected[1]);
      setPlayerColor(1, PlayerColor.BLUE);
      bluePlayer2.setStyle(BLUE_BORDER_STYLE);
      world.setColorPlayer2(0);
    });

    bluePlayer3.setOnAction(e -> {
      buttonAction1(playerColorSelected[2]);
      setPlayerColor(2, PlayerColor.BLUE);
      bluePlayer3.setStyle(BLUE_BORDER_STYLE);
      world.setColorPlayer3(0);
    });

    bluePlayer4.setOnAction(e -> {
      buttonAction1(playerColorSelected[3]);
      setPlayerColor(3, PlayerColor.BLUE);
      bluePlayer4.setStyle(BLUE_BORDER_STYLE);
      world.setColorPlayer4(0);
    });

    greenPlayer1.setOnAction(e -> {
      buttonAction1(playerColorSelected[0]);
      setPlayerColor(0, PlayerColor.GREEN);
      greenPlayer1.setStyle(GREEN_BORDER_STYLE);
      world.setColorPlayer1(3);
    });

    greenPlayer2.setOnAction(e -> {
      buttonAction1(playerColorSelected[1]);
      setPlayerColor(1, PlayerColor.GREEN);
      greenPlayer2.setStyle(GREEN_BORDER_STYLE);
      world.setColorPlayer2(3);
    });

    greenPlayer3.setOnAction(e -> {
      buttonAction1(playerColorSelected[2]);
      setPlayerColor(2, PlayerColor.GREEN);
      greenPlayer3.setStyle(GREEN_BORDER_STYLE);
      world.setColorPlayer3(3);
    });

    greenPlayer4.setOnAction(e -> {
      buttonAction1(playerColorSelected[3]);
      setPlayerColor(3, PlayerColor.GREEN);
      greenPlayer4.setStyle(GREEN_BORDER_STYLE);
      world.setColorPlayer4(3);
    });

    yellowPlayer1.setOnAction(e -> {
      buttonAction1(playerColorSelected[0]);
      setPlayerColor(0, PlayerColor.YELLOW);
      yellowPlayer1.setStyle(YELLOW_BORDER_STYLE);
      world.setColorPlayer1(2);
    });

    yellowPlayer2.setOnAction(e -> {
      buttonAction1(playerColorSelected[1]);
      setPlayerColor(1, PlayerColor.YELLOW);
      yellowPlayer2.setStyle(YELLOW_BORDER_STYLE);
      world.setColorPlayer2(2);
    });

    yellowPlayer3.setOnAction(e -> {
      buttonAction1(playerColorSelected[2]);
      setPlayerColor(2, PlayerColor.YELLOW);
      yellowPlayer3.setStyle(YELLOW_BORDER_STYLE);
      world.setColorPlayer3(2);
    });

    yellowPlayer4.setOnAction(e -> {
      buttonAction1(playerColorSelected[3]);
      setPlayerColor(3, PlayerColor.YELLOW);
      yellowPlayer4.setStyle(YELLOW_BORDER_STYLE);
      world.setColorPlayer4(2);
    });

    redPlayer1.setOnAction(e -> {
      buttonAction1(playerColorSelected[0]);
      setPlayerColor(0, PlayerColor.RED);
      redPlayer1.setStyle(RED_BORDER_STYLE);
      world.setColorPlayer1(1);
    });

    redPlayer2.setOnAction(e -> {
      buttonAction1(playerColorSelected[1]);
      setPlayerColor(1, PlayerColor.RED);
      redPlayer2.setStyle(RED_BORDER_STYLE);
      world.setColorPlayer2(1);
    });

    redPlayer3.setOnAction(e -> {
      buttonAction1(playerColorSelected[2]);
      setPlayerColor(2, PlayerColor.RED);
      redPlayer3.setStyle(RED_BORDER_STYLE);
      world.setColorPlayer3(1);
    });

    redPlayer4.setOnAction(e -> {
      buttonAction1(playerColorSelected[3]);
      setPlayerColor(3, PlayerColor.RED);
      redPlayer4.setStyle(RED_BORDER_STYLE);
      world.setColorPlayer4(1);
    });
  }


  private void setButtonColor() {
    bluePlayer1.setStyle(BLUE_STYLE);
    greenPlayer1.setStyle(GREEN_STYLE);
    yellowPlayer1.setStyle(YELLOW_STYLE);
    redPlayer1.setStyle(RED_STYLE);

    bluePlayer2.setStyle(BLUE_STYLE);
    greenPlayer2.setStyle(GREEN_STYLE);
    yellowPlayer2.setStyle(YELLOW_STYLE);
    redPlayer2.setStyle(RED_STYLE);

    bluePlayer3.setStyle(BLUE_STYLE);
    greenPlayer3.setStyle(GREEN_STYLE);
    yellowPlayer3.setStyle(YELLOW_STYLE);
    redPlayer3.setStyle(RED_STYLE);

    bluePlayer4.setStyle(BLUE_STYLE);
    greenPlayer4.setStyle(GREEN_STYLE);
    yellowPlayer4.setStyle(YELLOW_STYLE);
    redPlayer4.setStyle(RED_STYLE);
  }

  interface ColorSelectContext {
    void start(World world);
  }

  private void buttonAction1(boolean buttonColorSelected) {
    if (!buttonColorSelected) {
      selectedColors++;
    }
    if (selectedColors == 4) {
      context.start(world);
    }
  }

  public Parent getRoot() {
    return root;
  }

  public void setContext(ColorSelectContext context) {
    this.context = context;
  }
}
