package barray.graf;

/**
 * Process.java
 *
 * Allows a graph to be processed pixel by pixel.
 **/
public interface Process{
  /**
   * process()
   *
   * Allows the image to be processed.
   **/
  public void process();

  /**
   * getXY()
   *
   * Gets the value of an ARGB pixel in the X and Y position, given the width
   * and height.
   *
   * @param x The X position of the pixel to be retrieved.
   * @param y The Y position of the pixel to be retrieved.
   * @return The ARGB pixel.
   **/
  public int getXY(int x, int y);
}
