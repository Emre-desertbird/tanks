package moonlander;

import javafx.scene.canvas.GraphicsContext;

public class Bullet extends Entity {

  private final World world;
  private final Sprite sprite;

  public Bullet(World world) {
    this.world = world;
    sprite = new Sprite();
    sprite.setImage(getClass().getResource("/graphics/bullet-1.png"), 3*4, 3*4);
    ImageAnimation anim = new ImageAnimation();
    anim.addImage(getClass().getResource("/graphics/bullet-1.png"), 3*4, 3*4);
    anim.addImage(getClass().getResource("/graphics/bullet-2.png"), 3*4, 3*4);
    anim.addImage(getClass().getResource("/graphics/bullet-3.png"), 3*4, 3*4);
    anim.addImage(getClass().getResource("/graphics/bullet-4.png"), 3*4, 3*4);
    anim.setNanoPausePerFrame(100*1000*1000);
    sprite.setImageAnimation(anim);
  }
  
  @Override
  public void update(double elapsedTime) {
    super.update(elapsedTime);
    double x = getPositionX();
    double y = getPositionY();
    if ( x < 10 || x > world.getWidth()+10 || y < 10 || y > world.getHeight()+10) {
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
