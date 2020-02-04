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

public class ColourSelectionController implements Initializable {


  public static ColourSelectionController load() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ColourSelectionController.class.getResource("colourSelection.fxml"));
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
  private Button magentaPlayer1;
  @FXML
  private Button redPlayer1;
  @FXML
  private Button bluePlayer2;
  @FXML
  private Button greenPlayer2;
  @FXML
  private Button magentaPlayer2;
  @FXML
  private Button redPlayer2;
  @FXML
  private Button bluePlayer3;
  @FXML
  private Button greenPlayer3;
  @FXML
  private Button magentaPlayer3;
  @FXML
  private Button redPlayer3;
  @FXML
  private Button bluePlayer4;
  @FXML
  private Button greenPlayer4;
  @FXML
  private Button magentaPlayer4;
  @FXML
  private Button redPlayer4;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    bluePlayer1.setStyle("-fx-background-color: #0000ff");
    greenPlayer1.setStyle("-fx-background-color: #008000");
    magentaPlayer1.setStyle("-fx-background-color: #ff00ff");
    redPlayer1.setStyle("-fx-background-color: #ff0000");

    bluePlayer2.setStyle("-fx-background-color: #0000ff");
    greenPlayer2.setStyle("-fx-background-color: #008000");
    magentaPlayer2.setStyle("-fx-background-color: #ff00ff");
    redPlayer2.setStyle("-fx-background-color: #ff0000");

    bluePlayer3.setStyle("-fx-background-color: #0000ff");
    greenPlayer3.setStyle("-fx-background-color: #008000");
    magentaPlayer3.setStyle("-fx-background-color: #ff00ff");
    redPlayer3.setStyle("-fx-background-color: #ff0000");

    bluePlayer4.setStyle("-fx-background-color: #0000ff");
    greenPlayer4.setStyle("-fx-background-color: #008000");
    magentaPlayer4.setStyle("-fx-background-color: #ff00ff");
    redPlayer4.setStyle("-fx-background-color: #ff0000");

    bluePlayer1.setOnAction(e -> {
      greenPlayer1.setDisable(true);
      magentaPlayer1.setDisable(true);
      redPlayer1.setDisable(true);
      bluePlayer2.setDisable(true);
      bluePlayer3.setDisable(true);
      bluePlayer4.setDisable(true);
      bluePlayer2.setStyle("-fx-border-color: black; -fx-border-width: 5px");
    });

    bluePlayer2.setOnAction(e -> {
      greenPlayer2.setDisable(true);
      magentaPlayer2.setDisable(true);
      redPlayer2.setDisable(true);
      bluePlayer1.setDisable(true);
      bluePlayer3.setDisable(true);
      bluePlayer4.setDisable(true);
      bluePlayer2.setStyle("-fx-border-color: black; -fx-border-width: 5px");
    });
  }

  public Parent getRoot() {
    return root;
  }
}
