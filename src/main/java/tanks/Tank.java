package tanks;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import javafx.scene.canvas.GraphicsContext;
import uk.co.electronstudio.sdl2gdx.RumbleController;

public class Tank extends Entity {
  private final World world;
  private final Controller controller;
  private final PlayerSkin skin;


  private Sprite body = new Sprite();
  private Sprite bodyCracks = new Sprite();

  private Sprite turret = new Sprite();
  private Sprite turretCracks = new Sprite();

  private ImageAnimation anim = new ImageAnimation();
  private boolean engineActive = false;

  private boolean fire = false;
  private double fireCooldown = 0;

  private int cracks = 0;

  public Tank(World world, Controller controller, PlayerSkin skin) {
    this.world = world;
    this.controller = controller;
    this.skin = skin;
    controller.addListener(new ControllerAdapter() {
      @Override
      public boolean buttonDown(Controller controller, int buttonIndex) {
        // System.out.println("buttonDown() buttonIndex="+buttonIndex);
        if (buttonIndex == 10) {
          fire = true;
        }
        return false;
      }

      @Override
      public boolean buttonUp(Controller controller, int buttonIndex) {
        // System.out.println("buttonDown() buttonIndex="+buttonIndex);
        if (buttonIndex == 10) {
          fire = false;
        }
        return false;
      }
    });
    body.setImage(skin.getBodyImage());
    turret.setImage(skin.getTurretImage());
    turret.setPivot(14, 0);

    bodyCracks.setImage(getClass().getResource("/graphics/cracks-lower.png"), 19 * 4, 21 * 4);
    turretCracks.setImage(getClass().getResource("/graphics/cracks-upper.png"), 16 * 4, 9 * 4);
    turretCracks.setPivot(14, 0);

    setCollisionRadius((double) 8 * 4);
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
    if (active == engineActive) {
      return;
    }
    engineActive = active;
    if (engineActive) {
      body.setImageAnimation(anim);
    } else {
      body.setImageAnimation(null);
    }
  }

  @Override
  public void update(double elapsedTime) {
    float xAxis = controller.getAxis(0);
    float yAxis = controller.getAxis(1);

    if (Math.sqrt(xAxis * xAxis + yAxis * yAxis) > 0.2) {
      float factor = 250.0f;
      setVelocity(xAxis * factor, yAxis * factor);
      double rot = Math.toDegrees(Math.atan2(yAxis, xAxis));
      body.setRotation(rot);
      bodyCracks.setRotation(rot);
    } else {
      setVelocity(0, 0);
    }
    for (Tank tank : world.getPlayers()) {
      if (tank != this && tank.collides(this)) {
        double dx = getPositionX() - tank.getPositionX();
        double dy = getPositionY() - tank.getPositionY();
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        setVelocity(dx / magnitude * 100, dy / magnitude * 100);
        rumble(0.2f);
      }
    }
    super.update(elapsedTime);

    double x = getPositionX();
    if (x < 0) {
      setPositionX(0);
    } else if (x > world.getWidth()) {
      setPositionX(world.getWidth());
    }
    double y = getPositionY();
    if (y < 0) {
      setPositionY(0);
    } else if (y > world.getHeight()) {
      setPositionY(world.getHeight());
    }

    float xTur = controller.getAxis(2);
    float yTur = controller.getAxis(3);
    if (Math.sqrt(xTur * xTur + yTur * yTur) > 0.9) {
      double rot = Math.toDegrees(Math.atan2(yTur, xTur));
      turret.setRotation(rot);
      turretCracks.setRotation(rot);
    }

    if (fireCooldown > 0) {
      fireCooldown -= elapsedTime;
    }

    if (fire && fireCooldown <= 0) {
      fireCooldown = 0.15;
      double rot = turret.getRotation();
      double vx = Math.cos(Math.toRadians(rot));
      double vy = Math.sin(Math.toRadians(rot));
      double factor = 550;
      world.addBullet(getPositionX() + turret.getPositionX() + vx * 40,
          getPositionY() + turret.getPositionY() + vy * 40, vx * factor, vy * factor);
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.save();
    gc.translate(getPositionX(), getPositionY());
    body.render(gc);
    if (cracks > 10) {
      bodyCracks.render(gc);
    }
    turret.render(gc);
    if (cracks > 5) {
      turretCracks.render(gc);
    }

    gc.restore();
  }

  public void hurt() {
    rumble(0.6f);
    cracks++;
    if (cracks == 20) {
      world.remove(this);
      world.addLargeExplosion(getPositionX(), getPositionY(), getVelocityX(), getVelocityY());
      world.addTank(controller);
    }
  }

  private void rumble(float intensity) {
    if (controller instanceof RumbleController) {
      ((RumbleController) controller).rumble(intensity, intensity, 300);
      System.out.println(System.currentTimeMillis() + " Rumble");
    }
  }

}
