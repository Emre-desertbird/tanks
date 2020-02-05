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

  public int selectedColors = 0;

  private ColorSelectContext context;

  World world = new World();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setButtonColor();

    bluePlayer1.setOnAction(e -> {
      greenPlayer1.setDisable(true);
      yellowPlayer1.setDisable(true);
      redPlayer1.setDisable(true);
      bluePlayer2.setDisable(true);
      bluePlayer3.setDisable(true);
      bluePlayer4.setDisable(true);
      bluePlayer1.setStyle("-fx-background-color: #0000cd; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer1(0);
    });

    bluePlayer2.setOnAction(e -> {
      greenPlayer2.setDisable(true);
      yellowPlayer2.setDisable(true);
      redPlayer2.setDisable(true);
      bluePlayer1.setDisable(true);
      bluePlayer3.setDisable(true);
      bluePlayer4.setDisable(true);
      bluePlayer2.setStyle("-fx-background-color: #0000cd; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer2(0);
    });

    bluePlayer3.setOnAction(e -> {
      greenPlayer3.setDisable(true);
      yellowPlayer3.setDisable(true);
      redPlayer3.setDisable(true);
      bluePlayer1.setDisable(true);
      bluePlayer2.setDisable(true);
      bluePlayer4.setDisable(true);
      bluePlayer3.setStyle("-fx-background-color: #0000cd; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer3(0);
    });

    bluePlayer4.setOnAction(e -> {
      greenPlayer4.setDisable(true);
      yellowPlayer4.setDisable(true);
      redPlayer4.setDisable(true);
      bluePlayer1.setDisable(true);
      bluePlayer2.setDisable(true);
      bluePlayer3.setDisable(true);
      bluePlayer4.setStyle("-fx-background-color: #0000cd; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer4(0);
    });

    greenPlayer1.setOnAction(e -> {
      bluePlayer1.setDisable(true);
      yellowPlayer1.setDisable(true);
      redPlayer1.setDisable(true);
      greenPlayer2.setDisable(true);
      greenPlayer3.setDisable(true);
      greenPlayer4.setDisable(true);
      greenPlayer1.setStyle("-fx-background-color: #008000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer1(3);
    });

    greenPlayer2.setOnAction(e -> {
      bluePlayer2.setDisable(true);
      yellowPlayer2.setDisable(true);
      redPlayer2.setDisable(true);
      greenPlayer1.setDisable(true);
      greenPlayer3.setDisable(true);
      greenPlayer4.setDisable(true);
      greenPlayer2.setStyle("-fx-background-color: #008000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer2(3);
    });

    greenPlayer3.setOnAction(e -> {
      bluePlayer3.setDisable(true);
      yellowPlayer3.setDisable(true);
      redPlayer3.setDisable(true);
      greenPlayer1.setDisable(true);
      greenPlayer2.setDisable(true);
      greenPlayer4.setDisable(true);
      greenPlayer3.setStyle("-fx-background-color: #008000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer3(3);
    });

    greenPlayer4.setOnAction(e -> {
      bluePlayer4.setDisable(true);
      yellowPlayer4.setDisable(true);
      redPlayer4.setDisable(true);
      greenPlayer1.setDisable(true);
      greenPlayer2.setDisable(true);
      greenPlayer3.setDisable(true);
      greenPlayer4.setStyle("-fx-background-color: #008000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer4(3);
    });

    yellowPlayer1.setOnAction(e -> {
      bluePlayer1.setDisable(true);
      greenPlayer1.setDisable(true);
      redPlayer1.setDisable(true);
      yellowPlayer2.setDisable(true);
      yellowPlayer3.setDisable(true);
      yellowPlayer4.setDisable(true);
      yellowPlayer1.setStyle("-fx-background-color: #daa520; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer1(2);
    });

    yellowPlayer2.setOnAction(e -> {
      bluePlayer2.setDisable(true);
      greenPlayer2.setDisable(true);
      redPlayer2.setDisable(true);
      yellowPlayer1.setDisable(true);
      yellowPlayer3.setDisable(true);
      yellowPlayer4.setDisable(true);
      yellowPlayer2.setStyle("-fx-background-color: #daa520; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer2(2);
    });

    yellowPlayer3.setOnAction(e -> {
      bluePlayer3.setDisable(true);
      greenPlayer3.setDisable(true);
      redPlayer3.setDisable(true);
      yellowPlayer1.setDisable(true);
      yellowPlayer2.setDisable(true);
      yellowPlayer4.setDisable(true);
      yellowPlayer3.setStyle("-fx-background-color: #daa520; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer3(2);
    });

    yellowPlayer4.setOnAction(e -> {
      bluePlayer4.setDisable(true);
      greenPlayer4.setDisable(true);
      redPlayer4.setDisable(true);
      yellowPlayer1.setDisable(true);
      yellowPlayer2.setDisable(true);
      yellowPlayer3.setDisable(true);
      yellowPlayer4.setStyle("-fx-background-color: #daa520; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer4(2);
    });

    redPlayer1.setOnAction(e -> {
      bluePlayer1.setDisable(true);
      greenPlayer1.setDisable(true);
      yellowPlayer1.setDisable(true);
      redPlayer2.setDisable(true);
      redPlayer3.setDisable(true);
      redPlayer4.setDisable(true);
      redPlayer1.setStyle("-fx-background-color: #ff0000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer1(1);
    });

    redPlayer2.setOnAction(e -> {
      bluePlayer2.setDisable(true);
      greenPlayer2.setDisable(true);
      yellowPlayer2.setDisable(true);
      redPlayer1.setDisable(true);
      redPlayer3.setDisable(true);
      redPlayer4.setDisable(true);
      redPlayer2.setStyle("-fx-background-color: #ff0000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer2(1);
    });

    redPlayer3.setOnAction(e -> {
      bluePlayer3.setDisable(true);
      greenPlayer3.setDisable(true);
      yellowPlayer3.setDisable(true);
      redPlayer1.setDisable(true);
      redPlayer2.setDisable(true);
      redPlayer4.setDisable(true);
      redPlayer3.setStyle("-fx-background-color: #ff0000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer3(1);
    });

    redPlayer4.setOnAction(e -> {
      bluePlayer4.setDisable(true);
      greenPlayer4.setDisable(true);
      yellowPlayer4.setDisable(true);
      redPlayer1.setDisable(true);
      redPlayer2.setDisable(true);
      redPlayer3.setDisable(true);
      redPlayer4.setStyle("-fx-background-color: #ff0000; -fx-border-color: black; -fx-border-width: 6px");
      buttonAction1();
      world.setColorPlayer4(1);
    });
  }

  private void setButtonColor() {
    bluePlayer1.setStyle("-fx-background-color: #0000cd");
    greenPlayer1.setStyle("-fx-background-color: #008000");
    yellowPlayer1.setStyle("-fx-background-color: #daa520");
    redPlayer1.setStyle("-fx-background-color: #ff0000");

    bluePlayer2.setStyle("-fx-background-color: #0000cd");
    greenPlayer2.setStyle("-fx-background-color: #008000");
    yellowPlayer2.setStyle("-fx-background-color: #daa520");
    redPlayer2.setStyle("-fx-background-color: #ff0000");

    bluePlayer3.setStyle("-fx-background-color: #0000cd");
    greenPlayer3.setStyle("-fx-background-color: #008000");
    yellowPlayer3.setStyle("-fx-background-color: #daa520");
    redPlayer3.setStyle("-fx-background-color: #ff0000");

    bluePlayer4.setStyle("-fx-background-color: #0000cd");
    greenPlayer4.setStyle("-fx-background-color: #008000");
    yellowPlayer4.setStyle("-fx-background-color: #daa520");
    redPlayer4.setStyle("-fx-background-color: #ff0000");
  }

  interface ColorSelectContext {
    void start();
  }

  private void buttonAction1() {
    selectedColors++;
    if (selectedColors == 4) {
      context.start();
    }
  }

  public Parent getRoot() {
    return root;
  }

  public void setContext(ColorSelectContext context) {
    this.context = context;
  }
}
