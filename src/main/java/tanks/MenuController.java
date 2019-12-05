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

public class MenuController implements Initializable {
  public static MenuController load() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MenuController.class.getResource("menu.fxml"));
    loader.load();
    return loader.getController();
  }

  interface Context {
    void start();

    void option();

    void exit();
  }

  @FXML
  private Parent root;
  @FXML
  private Label captionLabel;
  @FXML
  private Button startButton;
  @FXML
  private Button optionsButton;
  @FXML
  private Button exitButton;

  private Context context;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    startButton.setOnAction(e -> {
      context.start();
    });
  }


  public void setContext(Context context) {
    this.context = context;
  }



  public Parent getRoot() {
    return root;
  }
}
