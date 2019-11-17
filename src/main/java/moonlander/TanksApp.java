package moonlander;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.libsdl.SDL_Error;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

public class TanksApp extends Application {
  private static final int HEIGHT = 500;
  private static final int WIDTH = 500;

  public static void main(String[] args) {
    launch(args);
  }

  SDL2ControllerManager controllerManager = new SDL2ControllerManager();


  World world = new World();
  AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());
  ArrayList<String> input = new ArrayList<String>();
  String typed = null;

  @Override
  public void start(Stage theStage) {
    theStage.setTitle("Tanks");

    Group group = new Group();
    Rectangle bg = new Rectangle(WIDTH, HEIGHT);
    bg.setFill(Color.DARKBLUE.darker().darker());
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    group.getChildren().addAll(bg, canvas);
    
    world.setWidth(WIDTH);
    world.setHeight(HEIGHT);

    BorderPane root = new BorderPane();
    root.setCenter(group);
    root.widthProperty().addListener((obs, oldV, newV)->{
      canvas.setWidth(newV.doubleValue());
      bg.setWidth(newV.doubleValue());
      world.setWidth(newV.doubleValue());
    });
    root.heightProperty().addListener((obs, oldV, newV)->{
      canvas.setHeight(newV.doubleValue());
      bg.setHeight(newV.doubleValue());
      world.setHeight(newV.doubleValue());
    });
    Scene theScene = new Scene(root);
    theStage.setScene(theScene);

    theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        if (!input.contains(code))
          input.add(code);
      }
    });

    theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        input.remove(code);
      }
    });

    theScene.setOnKeyTyped(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent e) {
        typed = e.getCharacter();
      }
    });

    GraphicsContext gc = canvas.getGraphicsContext2D();

    

    for (Controller ctrl : controllerManager.getControllers()) {
      addPlayer(ctrl);
    }
    controllerManager.addListener(new ControllerAdapter() {
      @Override
      public void connected(Controller ctrl) {
        addPlayer(ctrl);
      }

      @Override
      public void disconnected(Controller ctrl) {
        removePlayer(ctrl);
      }

    });

    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        // calculate time since last update.
        double elapsedTime = (currentNanoTime - lastNanoTime.get()) / 1000000000.0;
        lastNanoTime.set(currentNanoTime);

        try {
          controllerManager.pollState();
        } catch (SDL_Error sdl_error) {
          sdl_error.printStackTrace();
        }
        
        world.update(elapsedTime);
        
        for (Tank player : world.getPlayers()) {
          player.update(elapsedTime);
        }
        for (Bullet bullet : world.getBullets()) {
          bullet.update(elapsedTime);
        }

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Tank player : world.getPlayers()) {
          player.render(gc);
        }
        for (Bullet bullet : world.getBullets()) {
          bullet.render(gc);
        }
      }
    }.start();

    theStage.show();
  }

  private void addPlayer(Controller ctrl) {
    System.out.println("TanksApp.addPlayer() ctrl=" + ctrl);
    world.addTank(ctrl);
  }

  private void removePlayer(Controller ctrl) {
    for (Tank tank : world.getPlayers()) {
      if (tank.hasController(ctrl)) {
        world.remove(tank);
        break;
      }
    }
  }

}
