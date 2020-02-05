package tanks;

import javafx.scene.canvas.GraphicsContext;

public class Star extends Entity {

  private final World world;
  private final Sprite sprite;

  public Star(World world) {
    this.world = world;
    sprite = new Sprite();
    sprite.setImage(getClass().getResource("/graphics/star.png"), 16, 16);    
    setCollisionRadius((double) 4);
  }

  @Override
  public void update(double elapsedTime) {
    super.update(elapsedTime);
    if (getAge() > 0.5) {
      world.remove(this);
      return;
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
