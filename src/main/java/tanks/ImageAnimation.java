package tanks;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.common.primitives.Ints;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageAnimation {
  private final List<Image> images = new ArrayList<>();
  private long nanoPausePerFrame = 1000 * 1000 * 1000;
  private boolean continuous = true;
  private boolean finished = false;
  private long nanoTimeStated;

  public ImageAnimation() {}

  public long getNanoPausePerFrame() {
    return nanoPausePerFrame;
  }

  public void setNanoPausePerFrame(long nanoPausePerFrame) {
    this.nanoPausePerFrame = nanoPausePerFrame;
  }

  public boolean isContinuous() {
    return continuous;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setContinuous(boolean continuous) {
    this.continuous = continuous;
  }

  public void addImage(Image image) {
    images.add(image);
  }

  public void addImage(URL filename) {
    addImage(new Image(filename.toExternalForm()));
  }

  public void addImage(URL filename, int width, int height) {
    Image i = new Image(filename.toExternalForm(), width, height, true, false);
    addImage(i);
  }

  public void render(long nanotime, double posX, double posY, GraphicsContext gc) {
    if (isContinuous()) {
      int len = images.size();
      int index = Ints.checkedCast((nanotime / nanoPausePerFrame) % len);
      Image image = images.get(index);
      gc.drawImage(image, posX, posY);
    } else if (isFinished()) {
      return;
    } else {
      if ( nanoTimeStated == 0) {
        nanoTimeStated = nanotime;
      }
      int len = images.size();
      int index = Ints.checkedCast(((nanotime-nanoTimeStated) / nanoPausePerFrame));
      if ( index < len) {
        Image image = images.get(index);
        gc.drawImage(image, posX, posY);
      } else {
        finished = true;
      }
    }
  }
}
