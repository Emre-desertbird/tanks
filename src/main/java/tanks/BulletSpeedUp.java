package tanks;

import javafx.scene.canvas.GraphicsContext;

public class BulletSpeedUp extends Entity {

  private final World world;
  private final Sprite sprite;

  public BulletSpeedUp(World world) {
    this.world = world;
    sprite = new Sprite();
    sprite.setImage(getClass().getResource("/graphics/bullet-speed-up.png"), 5 * 8, 6 * 8);
    setCollisionRadius((double) 3 * 8);
  }

  @Override
  public void update(double elapsedTime) {
    super.update(elapsedTime);
    if (getAge() > 20) {
      world.remove(this);
      return;
    }

    if (getAge() > 0.05) {
      for (Tank tank : world.getPlayers()) {
        if (tank.collides(this)) {
          tank.addBulletSpeed(this);
          world.remove(this);
          return;
        }
      }
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.save();
    gc.translate(getPositionX(), getPositionY());
    sprite.render(gc);
    gc.restore();
  }

}
