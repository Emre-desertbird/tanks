package moonlander;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import javafx.scene.canvas.GraphicsContext;

public class Tank extends Entity {
  private final World world;
  private final Controller controller;
  private final PlayerSkin skin;
  
  private Sprite body = new Sprite();
  private Sprite turret = new Sprite();
  private ImageAnimation anim = new ImageAnimation();
  private boolean engineActive = false;
  private double fx;
  private double fy;

  public Tank(World world, Controller controller, PlayerSkin skin) {
    this.world = world;
    this.controller = controller;
    this.skin = skin;
    controller.addListener(new ControllerAdapter() {
      
    });
    body.setImage(skin.getBodyImage());
    turret.setImage(skin.getTurretImage());
    turret.setPivot(14, 0);
    
    // anim.addImage(getClass().getResource("/graphics/moonlander2.png"));
    // anim.addImage(getClass().getResource("/graphics/moonlander3.png"));
    // anim.setNanoPausePerFrame(1000 * 1000 * 100);
  }
  
  public PlayerSkin getSkin() {
    return skin;
  }
  
  public boolean hasController(Controller ctrl) {
    return controller == ctrl;
  }

  public boolean isEngineActive() {
    return engineActive;
  }

  public void setEngineActive(boolean active) {
    if (active == this.engineActive) {
      return;
    }
    this.engineActive = active;
    if (this.engineActive) {
      body.setImageAnimation(anim);
    } else {
      body.setImageAnimation(null);
    }
  }

  public void setForce(double fx, double fy) {
    this.fx = fx;
    this.fy = fy;
  }

  public double getFx() {
    return fx;
  }

  public double getFy() {
    return fy;
  }

  public void addForce(double dfx, double dfy) {
    this.fx += dfx;
    this.fy += dfy;
  }

  public void update(double elapsedTime) {
    float xAxis = controller.getAxis(0);
    float yAxis = controller.getAxis(1);

    
    if (Math.sqrt(xAxis*xAxis+yAxis*yAxis)>0.2) {
      float factor = 150.0f;
      setVelocity(xAxis * factor, yAxis * factor);
      double rot = Math.toDegrees(Math.atan2(yAxis, xAxis));
      body.setRotation(rot);
    } else {
      setVelocity(0,0);
    }
    super.update(elapsedTime);
    
    float xTur = controller.getAxis(2);
    float yTur = controller.getAxis(3);
    if (Math.sqrt(xTur*xTur+yTur*yTur)>0.9) {
      double rot = Math.toDegrees(Math.atan2(yTur, xTur));
      turret.setRotation(rot);
    }

  }

  private float damp(float value) {
    if (0.15 > value && value > -0.15) {
      return 0f;
    }
    return value;
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.save();
    gc.translate(getPositionX(), getPositionY());
    body.render(gc);
    turret.render(gc);
    gc.restore();
  }

}
