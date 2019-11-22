package tanks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import com.badlogic.gdx.controllers.Controller;

public class World {
  private Random random = new Random();
  private Set<PlayerSkin> availableSkins = EnumSet.allOf(PlayerSkin.class);
  private List<Tank> players = new ArrayList<>();
  private List<Entity> entities = new ArrayList<>();

  private Set<Entity> toRemove = new HashSet<>();
  private List<Entity> toAdd = new ArrayList<>();


  private int playercount;
  private int deathcount[] = new int[2];
  private int x = 0;

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

  public Collection<Entity> getEntities() {
    return entities;
  }

  public void update(double elapsedTime) {
    for (Entity entity : toRemove) {
      if (entity instanceof Tank) {
        players.remove(entity);
        availableSkins.add(((Tank) entity).getSkin());
      } else if (entity instanceof Bullet) {
        entities.remove(entity);
      }
    }
    toRemove.clear();

    for (Entity entity : toAdd) {
      entities.add(entity);
    }
    toAdd.clear();
  }

  public void remove(Entity entity) {
    toRemove.add(entity);
  }

  public Collection<Tank> getPlayers() {
    return players;
  }

  public void addTank(Controller ctrl) {
    PlayerSkin skin = chooseSkin();
    if (skin != null) {
      Tank tank = new Tank(this, ctrl, skin);
      tank.setPosition(skin.getX() * width, skin.getY() * height);
      players.add(tank);
    }
  }

  private PlayerSkin chooseSkin() {
    int size = availableSkins.size();
    if (size == 0) {
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
    toAdd.add(bullet);
  }

  public void addLargeExplosion(double x, double y, double vx, double vy) {
    LargeExplosion explosion = new LargeExplosion(this);
    explosion.setPosition(x, y);
    explosion.setVelocity(vx, vy);
    toAdd.add(explosion);
  }

  public void addSmallExplosion(double x, double y, double vx, double vy) {
    SmallExplosion explosion = new SmallExplosion(this);
    explosion.setPosition(x, y);
    explosion.setVelocity(vx, vy);
    toAdd.add(explosion);
  }

  public Collection<Bullet> getBullets() {
    return entities.stream().filter(Bullet.class::isInstance).map(Bullet.class::cast).collect(Collectors.toList());
  }

  public void deathcount() {
	  playercount = getPlayers().size() - 1;
	  if(x == 0) {
	      x++;
	      deathcount = new int[playercount];
	      for(int y=0; y<playercount; y++) {
	    	  deathcount[y] = 0;
	      }
	  }
	  deathcount[0] += 1;
  }
  
  public void showDeathcount() {
	  String getDeathcount = Arrays.toString(deathcount);
	  System.out.println(getDeathcount);
  }
}
