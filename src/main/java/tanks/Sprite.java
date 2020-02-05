package tanks;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.geom.transform.Affine2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Sprite implements Cloneable {
  private Image image;
  private ImageAnimation imageAnimation;
  
  private double width;
  private double height;

  private double positionX;
  private double positionY;

  private double rotation;

  private double pivotX;
  private double pivotY;
  
  private boolean mirrorX = false;
  private boolean mirrorY = false;

  private List<Sprite> children = new ArrayList<>();
  
  private boolean enabled = true;
  
  public Sprite() {
    positionX = 0;
    positionY = 0;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public List<Sprite> getChildren() {
    return children;
  }
  
  public ImageAnimation getImageAnimation() {
    return imageAnimation;
  }

  public void setImageAnimation(ImageAnimation imageAnimation) {
    this.imageAnimation = imageAnimation;
  }

  public double getPositionX() {
    return positionX;
  }

  public double getPositionY() {
    return positionY;
  }
  
  public double getPivotX() {
    return pivotX;
  }
  
  public double getPivotY() {
    return pivotY;
  }
  
  public void setRotation(double rotation) {
    this.rotation = rotation;
  }
  
  public double getRotation() {
    return rotation;
  }

  public boolean isMirrorX() {
    return mirrorX;
  }

  public void setMirrorX(boolean mirrorX) {
    this.mirrorX = mirrorX;
  }

  public boolean isMirrorY() {
    return mirrorY;
  }

  public void setMirrorY(boolean mirrorY) {
    this.mirrorY = mirrorY;
  }

  @Override
  public Sprite clone() {
    try {
      return (Sprite) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

  public void setImage(Image i) {
    image = i;
    width = i.getWidth();
    height = i.getHeight();
  }

  public void setImage(URL filename) {
    Image i = new Image(filename.toExternalForm());
    setImage(i);
  }

  public void setImage(URL filename, int width, int height) {
    Image i = new Image(filename.toExternalForm(), width, height, true, false);
    setImage(i);
  }

  public void setPosition(double x, double y) {
    positionX = x;
    positionY = y;
  }
  
  public void setPivot(double x, double y) {
    pivotX = x;
    pivotY = y;
  }
  
  public Affine getAffine() {
    Affine result = new Affine();
    result.appendTranslation(positionX, positionY);
    result.appendRotation(rotation);
    result.appendTranslation(pivotX, pivotY);
    //result.appendTranslation(-width/2D,-height/2D);
    return result;
  }

  public void render(GraphicsContext gc) {
    if (!isEnabled()) {
      return;
    }
    gc.save();

    gc.translate(positionX, positionY);
    gc.rotate(rotation);
    gc.translate(pivotX, pivotY);

    gc.save();
    gc.translate(-width/2D,-height/2D);

    // Debug bounds
    //gc.setLineWidth(2);
    //gc.setStroke(Color.WHITE.deriveColor(1, 1, 1, 0.5));
    //gc.strokeRect(0, 0, width, height);
    
    if (imageAnimation != null) {
      imageAnimation.render(System.nanoTime(), 0, 0, gc);
    } else {
      gc.drawImage(image, 0, 0);
    }
    gc.restore();
    
    children.forEach(child->child.render(gc));
    
    gc.restore();
  }

  public Rectangle2D getBoundary() {
    return new Rectangle2D(positionX - width / 2, positionY - height / 2, width, height);
  }

  public boolean intersects(Sprite s) {
    return s.getBoundary().intersects(this.getBoundary());
  }

}
