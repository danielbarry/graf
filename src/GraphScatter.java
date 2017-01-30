package barray.graf;

/**
 * GraphScatter.java
 *
 * An implementation of a scatter graph.
 **/
public class GraphScatter implements Process{
  private static final int background = (0xFF << 16) | (0xFF << 8) | 0xFF;
  private static final int foreground = (0x00 << 16) | (0x00 << 8) | 0x00;

  private String[][] data;
  private Maybe<Double>[] limits;
  private int width;
  private int height;

  /**
   * GraphScatter()
   *
   * Initialise the object, read to get values.
   *
   * @param data Both sets of ordered data to be used.
   * @param limits An array of limits for the data if available.
   * @param width The width of the graph in pixels.
   * @param height The height of the graph in pixels.
   * @param titles The titles to be used.
   **/
  public GraphScatter(
    String[][] data,
    Maybe<Double>[] limits,
    int width,
    int height,
    String[] titles
  ){
    this.data = data;
    this.limits = limits;
    this.width = width;
    this.height = height;
  }

  @Override
  public void process(){
    /* TODO: Write this section. */
  }

  @Override
  public int getXY(int x, int y){
    /* TODO: Remove this section. */
    if(x >= 10 && x < 200 && y >= 10 && y < 20){
      return Letter.getXY("HELLO WORLD!", x - 10, y - 10, 2) ? foreground : background;
    }
    return background; // TODO: Remove me.
  }
}
