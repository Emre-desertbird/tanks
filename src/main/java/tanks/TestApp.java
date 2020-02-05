package tanks;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  SimpleDoubleProperty width = new SimpleDoubleProperty();
  SimpleDoubleProperty height = new SimpleDoubleProperty();
  
  SimpleDoubleProperty centerX = new SimpleDoubleProperty();
  SimpleDoubleProperty centerY = new SimpleDoubleProperty();
  
  SimpleDoubleProperty offsetX = new SimpleDoubleProperty();
  SimpleDoubleProperty offsetY = new SimpleDoubleProperty();
  
  SimpleDoubleProperty positionX = new SimpleDoubleProperty();
  SimpleDoubleProperty positionY = new SimpleDoubleProperty();
  
  SimpleDoubleProperty rotation = new SimpleDoubleProperty();

  
  public double getWidth() {
    return width.get();
  }

  public double getHeight() {
    return height.get();
  }
  
  public double getOffsetX() {
    return offsetX.get();
  }

  public double getOffsetY() {
    return offsetY.get();
  }

  public double getCenterX() {
    return centerX.get();
  }
  
  public double getCenterY() {
    return centerY.get();
  }
  
  public double getPositionX() {
    return positionX.get();
  }

  public double getPositionY() {
    return positionY.get();
  }
  
  private double getRotation() {
    return rotation.doubleValue();
  }

  @Override
  public void start(Stage stage) throws Exception {
    BorderPane borderPane = new BorderPane();

    Canvas canvas = new Canvas();
    borderPane.setCenter(canvas);
    borderPane.widthProperty().addListener((obs, oldV, newV) -> {
      canvas.setWidth(newV.doubleValue());
    });
    borderPane.heightProperty().addListener((obs, oldV, newV) -> {
      canvas.setHeight(newV.doubleValue());
    });

    
    width.set(100);
    height.set(40);
    
    centerX.set(width.get()/2D);
    centerY.set(height.get()/2D);
    
    BorderPane root = new BorderPane();
    root.setCenter(borderPane);
    VBox vbox = new VBox();
    
    Slider widthSlider = new Slider();
    widthSlider.valueProperty().bindBidirectional(width);
    vbox.getChildren().add(widthSlider);
    
    Slider heightSlider = new Slider();
    heightSlider.valueProperty().bindBidirectional(height);
    vbox.getChildren().add(heightSlider);
    
    Slider xSlider = new Slider();
    xSlider.valueProperty().bindBidirectional(positionX);
    vbox.getChildren().add(xSlider);
    
    Slider ySlider = new Slider();
    ySlider.valueProperty().bindBidirectional(positionY);
    vbox.getChildren().add(ySlider);
    
    Slider rSlider = new Slider();
    rSlider.valueProperty().bindBidirectional(rotation);
    vbox.getChildren().add(rSlider);
    
    Slider centerXSlider = new Slider();
    centerXSlider.valueProperty().bindBidirectional(centerX);
    vbox.getChildren().add(centerXSlider);
    
    Slider centerYSlider = new Slider();
    centerYSlider.valueProperty().bindBidirectional(centerY);
    vbox.getChildren().add(centerYSlider);
    
    Slider offsetXSlider = new Slider();
    offsetXSlider.valueProperty().bindBidirectional(offsetX);
    vbox.getChildren().add(offsetXSlider);
    
    Slider offsetYSlider = new Slider();
    offsetYSlider.valueProperty().bindBidirectional(offsetY);
    vbox.getChildren().add(offsetYSlider);
    
    root.setTop(vbox);
    
    
    Scene scene = new Scene(root);
    stage.setScene(scene);

    stage.show();

    new AnimationTimer() {
      public void handle(long currentNanoTime) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        render(gc);

      }
    }.start();
  }

  public void render(GraphicsContext gc) {
    gc.save();
    gc.setFill(Color.CHOCOLATE);
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    gc.translate(getPositionX(), getPositionY());
    gc.setFill(Color.BURLYWOOD);
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    gc.rotate(getRotation());
    gc.setFill(Color.DEEPPINK);
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    gc.translate(-getCenterX(), -getCenterY());
    gc.setFill(Color.GREENYELLOW);
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    gc.translate(getOffsetX(), getOffsetY());
    gc.setFill(Color.GREEN);
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    gc.restore();
  }

  

}
