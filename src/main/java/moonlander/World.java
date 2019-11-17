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

  public void remove(Tank tank) {
    players.remove(tank);
    availableSkins.add(tank.getSkin());
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

}
