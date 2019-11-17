package moonlander;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.badlogic.gdx.controllers.Controller;

public class World {
  private Random random = new Random();
  private Set<PlayerSkin> availableSkins = EnumSet.allOf(PlayerSkin.class);
  private List<Tank> players = new ArrayList<>();
  private List<Bullet> bullets = new ArrayList<>();
  
  private Set<Entity> toRemove = new HashSet<>();
  
  private double width;
  private double height;

  public double getHeight() {
    return height;
  }
  
  public void setHeight(double height) {
    this.height = height;
  }
  
  public double getWidth() {
    return width;
  }
  
  public void setWidth(double width) {
    this.width = width;
  }
  
  public void update(double elapsedTime) {
    for (Entity entity : toRemove) {
      if ( entity instanceof Tank) {
        players.remove((Tank)entity);
        availableSkins.add(((Tank)entity).getSkin());
      } else if ( entity instanceof Bullet) {
        bullets.remove((Bullet)entity);
      }
    }
    toRemove.clear();
  }
  
  public void remove(Entity entity) {
    toRemove.add(entity);
  }

  public Collection<Tank> getPlayers() {
    return players;
  }

  public void addTank(Controller ctrl) {
    PlayerSkin skin = chooseSkin();
    if ( skin != null) {
      Tank tank = new Tank(this, ctrl, skin);
      tank.setPosition(100, 100);
      players.add(tank);
    }
  }

  private PlayerSkin chooseSkin() {
    int size = availableSkins.size();
    if ( size == 0) {
      return null;
    }
    int index = random.nextInt(size);
    PlayerSkin result = availableSkins.toArray(new PlayerSkin[size])[index];
    availableSkins.remove(result);
    return result;
  }

  public void addBullet(double positionX, double positionY, double vx, double vy) {
    Bullet bullet = new Bullet(this);
    bullet.setPosition(positionX, positionY);
    bullet.setVelocity(vx, vy);
    bullets.add(bullet);
  }

  public Collection<Bullet> getBullets() {
    return bullets;
  }

}
