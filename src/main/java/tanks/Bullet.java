package tanks;

import javafx.scene.canvas.GraphicsContext;

public class Bullet extends Entity {

  private final World world;
  private final Sprite sprite;
  private String origin;

  public Bullet(World world) {
    this.world = world;
    sprite = new Sprite();
    sprite.setImage(getClass().getResource("/graphics/bullet-1.png"), 3 * 4, 3 * 4);
    ImageAnimation anim = new ImageAnimation();
    anim.addImage(getClass().getResource("/graphics/bullet-1.png"), 3 * 4, 3 * 4);
    anim.addImage(getClass().getResource("/graphics/bullet-2.png"), 3 * 4, 3 * 4);
    anim.addImage(getClass().getResource("/graphics/bullet-3.png"), 3 * 4, 3 * 4);
    anim.addImage(getClass().getResource("/graphics/bullet-4.png"), 3 * 4, 3 * 4);
    anim.setNanoPausePerFrame(100 * 1000 * 1000);
    sprite.setImageAnimation(anim);
    setCollisionRadius((double) 3 * 4);
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
          world.addSmallExplosion(getPositionX(), getPositionY(), tank.getVelocityX(),
              tank.getVelocityY());
          System.out.println(this);
          world.remove(this);
          tank.hurt();
          return;
        }
      }
      for (Bullet bullet : world.getBullets()) {
        if (this != bullet && bullet.collides(this)) {
          world.addSmallExplosion(getPositionX(), getPositionY(), getVelocityX(), getVelocityY());
          world.remove(this);          
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
