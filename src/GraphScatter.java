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
  private String[] titles;
  private int gBndTopX;
  private int gBndTopY;
  private int gBndBotX;
  private int gBndBotY;

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
    this.titles = titles;
  }

  @Override
  public void process(){
    if(titles != null){
      gBndTopX = Letter.getHeight(' ') * 6;
      gBndTopY = Letter.getHeight(' ') * 3;
      gBndBotX = width - Letter.getHeight(' ');
      gBndBotY = height - (Letter.getHeight(' ') * 6);
    }else{
      gBndTopX = Letter.getHeight(' ') * 3;
      gBndTopY = Letter.getHeight(' ');
      gBndBotX = width - Letter.getHeight(' ');
      gBndBotY = height - (Letter.getHeight(' ') * 3);
    }
  }

  @Override
  public int getXY(int x, int y){
    if(titles != null){
      /* Display the title */
      if(y >= Letter.getHeight(' ') && y < (Letter.getHeight(' ') * 2)){
        return Letter.getXY(
          titles[0],
          x - ((width / 2) - (Letter.getWidth(titles[0], 1) / 2)),
          y - Letter.getHeight(' '),
          1
        ) ? foreground : background;
      }
      /* Display the X axis title */
      if(y >= height - (Letter.getHeight(' ') * 2) && y < height - Letter.getHeight(' ')){
        return Letter.getXY(
          titles[1],
          x - ((width / 2) - (Letter.getWidth(titles[1], 1) / 2)),
          y - (height - (Letter.getHeight(' ') * 2)),
          1
        ) ? foreground : background;
      }
      /* Display the Y axis title */
      if(x >= Letter.getHeight(' ') && x < (Letter.getHeight(' ') * 2)){
        return Letter.getXY(
          titles[2],
          Letter.getHeight(' ') - (y - (height / 2) - (Letter.getWidth(titles[2], 1) / 2)),
          x - Letter.getHeight(' '),
          1
        ) ? foreground : background;
      }
    }
    /* Display the borders */
    if(y >= gBndTopY && y <= gBndBotY && (x == gBndTopX || x == gBndBotX)){
      return foreground;
    }
    if(x >= gBndTopX && x <= gBndBotX && (y == gBndTopY || y == gBndBotY)){
      return foreground;
    }
    return background;
  }
}
