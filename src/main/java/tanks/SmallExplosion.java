package tanks;

import javafx.scene.canvas.GraphicsContext;

public class SmallExplosion extends Entity {

  private final World world;
  private Sprite sprite = new Sprite();
  
  public SmallExplosion(World world) {
    this.world = world;
    sprite.setImage(getClass().getResource("/graphics/explosion-small-4.png"), 16*4, 16*4);
    ImageAnimation anim = new ImageAnimation();
    anim.addImage(getClass().getResource("/graphics/explosion-small-1.png"), 16*4, 16*4);
    anim.addImage(getClass().getResource("/graphics/explosion-small-2.png"), 16*4, 16*4);
    anim.addImage(getClass().getResource("/graphics/explosion-small-3.png"), 16*4, 16*4);
    anim.addImage(getClass().getResource("/graphics/explosion-small-4.png"), 16*4, 16*4);
    anim.setNanoPausePerFrame(100 * 1000 * 1000);
    anim.setContinuous(false);
    sprite.setImageAnimation(anim);
  }
  
  @Override
  public void update(double elapsedTime) {
    super.update(elapsedTime);
    if ( sprite.getImageAnimation().isFinished()) {
      world.remove(this);
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
