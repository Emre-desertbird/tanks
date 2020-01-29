package tanks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
  private ImageView colourPlayer1;
  @FXML
  private ImageView colourPlayer2;
  @FXML
  private ImageView colourPlayer3;
  @FXML
  private ImageView colourPlayer4;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public Parent getRoot() {
    return root;
  }
}
