package tanks;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
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

  double speedFactor = 250;
  double bulletSpeedFactor = 550;

  private boolean fire = false;
  private double fireCooldown = 0;
  private double fireCooldownBoosted = 0.2;

  private int cracks = 0;

  public Tank(World world, Controller controller, PlayerSkin skin) {
    this.world = world;
    this.controller = controller;
    this.skin = skin;
    controller.addListener(new ControllerAdapter() {
      @Override
      public boolean buttonDown(Controller controller, int buttonIndex) {
        // System.out.println("buttonDown() buttonIndex=" + buttonIndex);
        if (buttonIndex == 10) {
          fire = true;
        }
        return false;
      }

      @Override
      public boolean buttonUp(Controller controller, int buttonIndex) {
        // System.out.println("buttonDown() buttonIndex=" + buttonIndex);
        if (buttonIndex == 10) {
          fire = false;
        }
        return false;
      }
    });

    // body.setPivot(-100, 0);
    // body.setPosition(-200, 0);
    body.setImage(skin.getBodyImage());
    bodyCracks.setImage(getClass().getResource("/graphics/cracks-lower.png"), 19 * 4, 21 * 4);
    body.getChildren().add(bodyCracks);

    turret.setImage(skin.getTurretImage());
    turretCracks.setImage(getClass().getResource("/graphics/cracks-upper.png"), 16 * 4, 9 * 4);
    turret.getChildren().add(turretCracks);

    body.getChildren().add(turret);

    turret.setPosition(-20, 0);
    turret.setPivot(25, 0);

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
      setVelocity(xAxis * speedFactor, yAxis * speedFactor);
      double rot = Math.toDegrees(Math.atan2(yAxis, xAxis));
      body.setRotation(rot);
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

      turret.setRotation(rot - body.getRotation());
    }

    if (fireCooldown > 0) {
      fireCooldown -= elapsedTime;
    }

    if (fire && fireCooldown <= 0) {
      fireCooldown = 0.2;
      if (fireCooldownBoosted < fireCooldown) {
        fireCooldown = fireCooldownBoosted;
      }
      double rot = body.getRotation() + turret.getRotation();
      double vx = Math.cos(Math.toRadians(rot));
      double vy = Math.sin(Math.toRadians(rot));

      Affine trans = new Affine();
      trans.appendTranslation(getPositionX(), getPositionY());
      trans.append(body.getAffine());
      trans.append(turret.getAffine());

      Point2D ptSrc = new Point2D(50, 0);
      Point2D ptDst = trans.transform(ptSrc);

      world.addBullet(this, ptDst.getX(), ptDst.getY(), vx * bulletSpeedFactor, vy * bulletSpeedFactor);
    }

    bodyCracks.setEnabled(cracks > 10);
    turretCracks.setEnabled(cracks > 5);
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.save();
    gc.translate(getPositionX(), getPositionY());
    body.render(gc);
    gc.restore();
  }

  public Controller getController() {
    return controller;
  }

  public int getCracks() {
    return cracks;
  }

  public void hurt(Bullet bullet) {
    rumble(0.6f);
    cracks++;
    if (cracks == 20) {
      world.addHealthPack(getPositionX(), getPositionY());
      world.remove(this);
      world.addLargeExplosion(getPositionX(), getPositionY(), getVelocityX(), getVelocityY());
      world.addTank(controller);
      world.setupKillcountAndDeathcount();
      world.setKillcount(bullet.getOrigin());
      world.setDeathcount(getPlayerController(controller));
    }
  }

  public Integer getPlayerController(Controller ctrl) {
    if (ctrl.toString().contains("instance:0")) {
      return 0;
    }
    if (ctrl.toString().contains("instance:1")) {
      return 1;
    }
    if (ctrl.toString().contains("instance:2")) {
      return 2;
    }
    if (ctrl.toString().contains("instance:3")) {
      return 3;
    }
    return null;
  }

  private void rumble(float intensity) {
    if (controller instanceof RumbleController) {
      ((RumbleController) controller).rumble(intensity, intensity, 300);
    }
  }

  public void addHealth(HealthPack healthPack) {
    cracks = 0;
    for (int i = 0; i < 10; ++i) {
      world.addStar(healthPack.getPositionX(), healthPack.getPositionY());
    }
  }

  public void addSpeed(SpeedUp speedUp) {
    speedFactor += 50;
    for (int i = 0; i < 10; ++i) {
      world.addStar(speedUp.getPositionX(), speedUp.getPositionY());
    }
  }

  public void addBulletSpeed(BulletSpeedUp bulletSpeedUp) {
    bulletSpeedFactor += 100;
    for (int i = 0; i < 10; ++i) {
      world.addStar(bulletSpeedUp.getPositionX(), bulletSpeedUp.getPositionY());
    }
  }

  public void addFireSpeed(FireSpeedUp fireSpeedUp) {
    fireCooldownBoosted *= 0.95;
    for (int i = 0; i < 10; ++i) {
      world.addStar(fireSpeedUp.getPositionX(), fireSpeedUp.getPositionY());
    }
  }

}
