package tanks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.libsdl.SDL;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tanks.ColorSelectionController.ColorSelectContext;
import tanks.MenuController.Context;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

public class TanksApp extends Application {
  public static final int HEIGHT = 500;
  public static final int WIDTH = 600;

  public static void main(String[] args) {
    launch(args);
    SDL.SDL_SetHint("SDL_XINPUT_ENABLED", "1");
  }


  SDL2ControllerManager controllerManager = new SDL2ControllerManager();
  World world;
  AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());
  ArrayList<String> input = new ArrayList<>();
  String typed = null;
  double[] hp = new double[4];
  public boolean setStats = false;
  boolean pauseMenu = false;
  Group group = new Group();

  HealthBar health0 = new HealthBar("health-bar");
  HealthBar health1 = new HealthBar("health-bar");
  HealthBar health2 = new HealthBar("health-bar");
  HealthBar health3 = new HealthBar("health-bar");

  @Override
  public void start(Stage theStage) throws Exception {
    startMenu(theStage);
  }

  private void addPlayer(Controller ctrl) {
    System.out.println("TanksApp.addPlayer() ctrl=" + ctrl);
    world.addTank(ctrl);
    world.playercountSet = false;
    setStats = false;
  }

  private void removePlayer(Controller ctrl) {
    for (Tank tank : world.getPlayers()) {
      if (tank.hasController(ctrl)) {
        world.remove(tank);
        break;
      }
      setStats = false;
    }
  }

  private void startMenu(Stage theStage) throws Exception {
    theStage.setTitle("Tanks - Menu");
    MenuController ctrl = MenuController.load();
    if (pauseMenu == true) {
      theStage.setTitle("Tanks - Menu / Pause");
      ctrl.setContinueEnabled();
    }
    ctrl.setContext(new Context() {
      @Override
      public void start() {
        try {
          selectColor(theStage);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void continueGame() {
        startGame(theStage, world);
      }

      @Override
      public void exit() {
        theStage.close();
      }
    });


    group.getStylesheets().add(getClass().getResource("healthBar.css").toExternalForm());
    Scene scene = new Scene(ctrl.getRoot());
    theStage.setScene(scene);
    theStage.show();
  }

  private void selectColor(Stage theStage) throws IOException {

    theStage.setTitle("Tanks - Colour Select");
    ColorSelectionController ctrl = ColorSelectionController.load();
    Scene scene = new Scene(ctrl.getRoot());
    theStage.setScene(scene);
    theStage.show();

    ctrl.setContext(new ColorSelectContext() {
      @Override
      public void start(World world) {
        startGame(theStage, world);
      }
    });
  }

  private void startGame(Stage theStage, World world) {
    theStage.setTitle("Tanks");
    this.world = world;

    Rectangle bg = new Rectangle(WIDTH, HEIGHT);
    bg.setFill(Color.DARKGREY.darker());
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    group.getChildren().addAll(bg, canvas);

    world.setWidth(WIDTH);
    world.setHeight(HEIGHT);

    BorderPane root = new BorderPane();
    root.setCenter(group);
    root.widthProperty().addListener((obs, oldV, newV) -> {
      canvas.setWidth(newV.doubleValue());
      bg.setWidth(newV.doubleValue());
      world.setWidth(newV.doubleValue());
    });
    root.heightProperty().addListener((obs, oldV, newV) -> {
      canvas.setHeight(newV.doubleValue());
      bg.setHeight(newV.doubleValue());
      world.setHeight(newV.doubleValue());
    });
    Scene theScene = new Scene(root);
    theStage.setScene(theScene);
    theStage.setMaximized(true);

    theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        if (!input.contains(code)) {
          input.add(code);
        }
      }
    });

    theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        input.remove(code);
      }
    });

    theScene.setOnKeyTyped(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCharacter().matches("q") || e.getCharacter().matches("")) { //  = esc
          try {
            pauseMenu = true;
            startMenu(theStage);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
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
      @Override
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
        for (Entity entity : world.getEntities()) {
          entity.update(elapsedTime);
        }

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setStroke(Color.CADETBLUE);
        gc.setLineWidth(1);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Tank player : world.getPlayers()) {
          player.render(gc);
        }
        for (Entity entity : world.getEntities()) {
          entity.render(gc);
        }

        for (Tank player : world.getPlayers()) {
          hp[player.getPlayerController(player.getController())] = player.getCracks();
        }

        if (setStats == false) {
          setInitialStats();
          setStats = true;
        }

        if (world.getPlayers().size() >= 1) {
          String statsPlayerOne =
              "Player 1 \n\nKills: " + world.getKillcount(0) + "\nDeaths: " + world.getDeathcount(0);
          gc.fillText(statsPlayerOne, 10, 25);
          gc.strokeText(statsPlayerOne, 10, 25);
          health0.setLayoutX(10);
          health0.setLayoutY(40);
          health0.setProgress(1 - hp[0] / 20);

        }
        if (world.getPlayers().size() >= 2) {
          String statsPlayerTwo =
              "Player 2 \n\nKills :" + world.getKillcount(1) + "\nDeaths: " + world.getDeathcount(1);
          gc.fillText(statsPlayerTwo, world.getWidth() - 120, 25);
          gc.strokeText(statsPlayerTwo, world.getWidth() - 120, 25);
          health1.setLayoutX(world.getWidth() - 120);
          health1.setLayoutY(40);
          health1.setProgress(1 - hp[1] / 20);
        }
        if (world.getPlayers().size() >= 3) {

          String statsPlayerThree =
              "Player 3 \n\nKills :" + world.getKillcount(2) + "\nDeaths: " + world.getDeathcount(2);
          gc.fillText(statsPlayerThree, 10, world.getHeight() - 110);
          gc.strokeText(statsPlayerThree, 10, world.getHeight() - 110);
          health2.setLayoutX(10);
          health2.setLayoutY(world.getHeight() - 95);
          health2.setProgress(1 - hp[2] / 20);
        }
        if (world.getPlayers().size() >= 4) {
          String statsPlayerFour =
              "Player 4 \n\nKills :" + world.getKillcount(3) + "\nDeaths: " + world.getDeathcount(3);
          gc.fillText(statsPlayerFour, world.getWidth() - 120, world.getHeight() - 110);
          gc.strokeText(statsPlayerFour, world.getWidth() - 120, world.getHeight() - 110);
          health3.setLayoutX(world.getWidth() - 120);
          health3.setLayoutY(world.getHeight() - 95);
          health3.setProgress(1 - hp[3] / 20);
        }
      }
    }.start();
    theStage.show();
  }

  private void setInitialStats() {
    group.getChildren().removeAll(health0, health1, health2, health3);

    if (world.getPlayers().size() >= 1) {
      group.getChildren().add(health0);
    }
    if (world.getPlayers().size() >= 2) {
      group.getChildren().add(health1);
    }
    if (world.getPlayers().size() >= 3) {
      group.getChildren().add(health2);
    }
    if (world.getPlayers().size() >= 4) {
      group.getChildren().add(health3);
    }
  }
}
