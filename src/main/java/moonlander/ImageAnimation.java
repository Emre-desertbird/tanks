package moonlander;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.common.primitives.Ints;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageAnimation {
  private final List<Image> images = new ArrayList<>();
  private long nanoPausePerFrame = 1000 * 1000 * 1000;

  public ImageAnimation() {}

  public long getNanoPausePerFrame() {
    return nanoPausePerFrame;
  }
  
  public void setNanoPausePerFrame(long nanoPausePerFrame) {
    this.nanoPausePerFrame = nanoPausePerFrame;
  }
  
  public void addImage(Image image) {
    images.add(image);
  }

  public void addImage(URL filename) {
    addImage(new Image(filename.toExternalForm()));
  }

  public void render(long nanotime, double posX, double posY, GraphicsContext gc) {
    int len = images.size();
    int index = Ints.checkedCast((nanotime / nanoPausePerFrame) % len);
    Image image = images.get(index);
    gc.drawImage(image, posX, posY);
  }
}
