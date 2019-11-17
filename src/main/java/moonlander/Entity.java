package moonlander;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Cloneable {

  private double positionX;
  private double positionY;
  private double rotation;
  private double velocityX;
  private double velocityY;

  private Double collisionRadius = null;
  private Point2D collisionCenterOffset = null;

  public Entity() {
    positionX = 0;
    positionY = 0;
    velocityX = 0;
    velocityY = 0;
  }

  public Double getCollisionRadius() {
    return collisionRadius;
  }

  public void setCollisionRadius(Double collisionRadius) {
    this.collisionRadius = collisionRadius;
  }

  public Point2D getCollisionCenterOffset() {
    return collisionCenterOffset;
  }

  public void setCollisionCenterOffset(Point2D collisionCenterOffset) {
    this.collisionCenterOffset = collisionCenterOffset;
  }

  public double getPositionX() {
    return positionX;
  }

  public double getPositionY() {
    return positionY;
  }

  public void setRotation(double rotation) {
    this.rotation = rotation;
  }

  public double getRotation() {
    return rotation;
  }

  @Override
  public Entity clone() {
    try {
      return (Entity) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

  public void setPosition(double x, double y) {
    positionX = x;
    positionY = y;
  }

  public void setVelocity(double x, double y) {
    velocityX = x;
    velocityY = y;
  }

  public void addVelocity(double x, double y) {
    velocityX += x;
    velocityY += y;
  }

  public double getVelocityX() {
    return velocityX;
  }

  public void setVelocityX(double velocityX) {
    this.velocityX = velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public void setVelocityY(double velocityY) {
    this.velocityY = velocityY;
  }

  public void update(double time) {
    positionX += velocityX * time;
    positionY += velocityY * time;
  }

  public boolean collides(Entity other) {
    Double r1 = collisionRadius;
    Double r2 = other.getCollisionRadius();
    if ( r1 == null || r2==null) {
      return false;
    }

    double x1 = positionX + (collisionCenterOffset == null ? 0 : collisionCenterOffset.getX());
    double y1 = positionY + (collisionCenterOffset == null ? 0 : collisionCenterOffset.getY());
    double x2 = other.getPositionX()
        + (other.getCollisionCenterOffset() == null ? 0 : other.getCollisionCenterOffset().getX());
    double y2 = other.getPositionY()
        + (other.getCollisionCenterOffset() == null ? 0 : other.getCollisionCenterOffset().getY());

    double dx = x1 - x2;
    double dy = y1 - y2;
    double dist = Math.sqrt(dx * dx + dy * dy);
    return dist < r1 + r2;
  }

  public abstract void render(GraphicsContext gc);

}
