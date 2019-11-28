package tanks;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.libsdl.SDL;
import org.libsdl.SDL_Error;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.awt.Component;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

public class TanksApp extends Application {
  public static final int HEIGHT = 500;
  public static final int WIDTH = 600; 


  enum STATE{
	  MENU,
	  GAME
  };
  
  public static STATE state = STATE.MENU;
  
  public static void main(String[] args) {
    launch(args);
    SDL.SDL_SetHint("SDL_XINPUT_ENABLED", "1");
  }

  SDL2ControllerManager controllerManager = new SDL2ControllerManager();
  World world = new World();
  AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());
  ArrayList<String> input = new ArrayList<>();
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
        typed = e.getCharacter();
      }
    });

    GraphicsContext gc = canvas.getGraphicsContext2D();

    Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
    gc.setFont(theFont);
    gc.setStroke(Color.DARKGREY);
    gc.setLineWidth(1);

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

    if(state == STATE.GAME) {
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
	
	        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	        for (Tank player : world.getPlayers()) {
	          player.render(gc);
	        }
	        for (Entity entity : world.getEntities()) {
	          entity.render(gc);
	        }
	
	        if (world.getPlayers().size() >= 1) {
	          String deathcountOne = "Player 1 \nDeaths: " + world.getDeathcountPlI();
	          gc.fillText(deathcountOne, 10, 25);
	          gc.strokeText(deathcountOne, 10, 25);
	        }
	        if (world.getPlayers().size() >= 2) {
	          String deathcountTwo = "Player 2 \nDeaths: " + world.getDeathcountPlII();
	          gc.fillText(deathcountTwo, world.getWidth() - 110, 25);
	          gc.strokeText(deathcountTwo, world.getWidth() - 110, 25);
	        }
	        if (world.getPlayers().size() >= 3) {
	          String deathcountThree = "Player 3 \nDeaths: " + world.getDeathcountPlIII();
	          gc.fillText(deathcountThree, 10, world.getHeight() - 50);
	          gc.strokeText(deathcountThree, 10, world.getHeight() - 50);
	        }
	        if (world.getPlayers().size() >= 4) {
	          String deathcountFour = "Player 4 \nDeaths: " + world.getDeathcountPlIV();
	          gc.fillText(deathcountFour, world.getWidth() - 110, world.getHeight() - 50);
	          gc.strokeText(deathcountFour, world.getWidth() - 110, world.getHeight() - 50);
	        }
	      }
	    }.start();
    }
    if(state == STATE.MENU)
    {
    	Font font0 = Font.font("arial", FontWeight.BOLD, 60);
        gc.setFont(font0);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setLineWidth(1);
        gc.fillText("TANKS", WIDTH / 2, 100);
    	Font font1 = Font.font("arial", FontWeight.BOLD, 30);
        gc.setFont(font1);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setLineWidth(1);
        gc.fillText("Play", WIDTH / 2 , 200);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setLineWidth(1);
        gc.fillText("Exit", WIDTH / 2 , 300);

    }

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
