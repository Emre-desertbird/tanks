package tanks;

import java.util.ArrayList;
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
  private EnumSet<PlayerSkin> availableSkins = EnumSet.allOf(PlayerSkin.class);
  private List<Tank> players = new ArrayList<>();
  private List<Entity> entities = new ArrayList<>();

  private Set<Entity> toRemove = new HashSet<>();
  private List<Entity> toAdd = new ArrayList<>();

  public int playercount;
  public boolean playercountSet = false;
  private int deathcount[];
  private int killcount[];

  private double width;
  private double height;

  public int colorPlayer1;
  public int colorPlayer2;
  public int colorPlayer3;
  public int colorPlayer4;

  public int getColorPlayer1() {
    return colorPlayer1;
  }

  public void setColorPlayer1(int colorPlayer1) {
    this.colorPlayer1 = colorPlayer1;
  }

  public int getColorPlayer2() {
    return colorPlayer2;
  }

  public void setColorPlayer2(int colorPlayer2) {
    this.colorPlayer2 = colorPlayer2;
  }

  public int getColorPlayer3() {
    return colorPlayer3;
  }

  public void setColorPlayer3(int colorPlayer3) {
    this.colorPlayer3 = colorPlayer3;
  }

  public int getColorPlayer4() {
    return colorPlayer4;
  }

  public void setColorPlayer4(int colorPlayer4) {
    this.colorPlayer4 = colorPlayer4;
  }



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
      } else {
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
    PlayerSkin skin = chooseSkin(ctrl);
    if (skin != null) {
      Tank tank = new Tank(this, ctrl, skin);
      tank.setPosition(skin.getX() * width, skin.getY() * height);
      players.add(tank);
    }
  }

  private PlayerSkin chooseSkin(Controller ctrl) {
    int size = availableSkins.size();
    if (size == 0) {
      return null;
    }
    Integer colorIndex = setPlayerControllerColor(ctrl);
    if (colorIndex == null) {
      return null;
    }
    switch (colorIndex.intValue()) {
      case 0:
        return PlayerSkin.BLUE;
      case 1:
        return PlayerSkin.RED;
      case 2:
        return PlayerSkin.YELLOW;
      case 3:
        return PlayerSkin.GREEN;
      default:
        throw new IllegalStateException();
    }
  }

  private Integer setPlayerControllerColor(Controller ctrl) {
    Integer controller = getPlayerController(ctrl);
    if (controller == 0) {
      return getColorPlayer1();
    } else if (controller == 1) {
      return getColorPlayer2();
    } else if (controller == 2) {
      return getColorPlayer3();
    } else if (controller == 3) {
      return getColorPlayer4();
    }
    return null;
  }

  private Integer getPlayerController(Controller ctrl) {
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

  public void addBullet(Tank tank, double positionX, double positionY, double vx, double vy) {

    Bullet bullet = new Bullet(this);
    bullet.setOrigin(tank.getPlayerController(tank.getController()));
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

  public void setKillcount(int player) {
    killcount[player] += 1;
  }

  public void setDeathcount(int player) {
    deathcount[player] += 1;
  }

  public void setupKillcountAndDeathcount() {
    playercount = getPlayers().size() - 1;
    if (playercountSet == false) {
      deathcount = new int[playercount];
      killcount = new int[playercount];
      for (int y = 0; y < playercount; y++) {
        deathcount[y] = 0;
        killcount[y] = 0;
      }
      playercountSet = true;
    }
  }

  public String getKillcount(int player) {
    try {
      return Integer.toString(killcount[player]);
    } catch (Exception e) {
      return "0";
    }
  }

  public String getDeathcount(int player) {
    try {
      return Integer.toString(deathcount[player]);
    } catch (Exception e) {
      return "0";
    }
  }



  public void addHealthPack(double x, double y) {
    HealthPack pack = new HealthPack(this);
    pack.setPosition(x, y);
    toAdd.add(pack);
  }

  public void addSpeedUp(double x, double y) {
    SpeedUp speedUp = new SpeedUp(this);
    speedUp.setPosition(x, y);
    toAdd.add(speedUp);
  }

  public void addBulletSpeedUp(double x, double y) {
    BulletSpeedUp bulletSpeedUp = new BulletSpeedUp(this);
    bulletSpeedUp.setPosition(x, y);
    toAdd.add(bulletSpeedUp);
  }

  public void addFireSpeedUp(double x, double y) {
    FireSpeedUp fireSpeedUp = new FireSpeedUp(this);
    fireSpeedUp.setPosition(x, y);
    toAdd.add(fireSpeedUp);
  }

  public void addStar(double x, double y) {
    Star star = new Star(this);
    star.setPosition(x, y);
    double factor = 1000;
    double vx = (random.nextDouble() - 0.5) * factor;
    double vy = (random.nextDouble() - 0.5) * factor;
    star.setVelocity(vx, vy);
    toAdd.add(star);
  }
}
