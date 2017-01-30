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
  private int[][] pnts;
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
    /* Calculate boundaries */
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
    /* Calculate point positions */
    if(
      limits[0].isSet() &&
      limits[1].isSet() &&
      limits[2].isSet() &&
      limits[3].isSet()
    ){
      double xScl = (gBndBotX - gBndTopX) / (limits[1].get() - limits[0].get());
      double yScl = (gBndBotY - gBndTopY) / (limits[3].get() - limits[2].get());
      pnts = new int[2][];
      pnts[0] = new int[data[0].length];
      pnts[1] = new int[data[1].length];
      for(int z = 0; z < data[0].length; z++){
        pnts[0][z] = gBndTopX + (int)((Double.parseDouble(data[0][z]) - limits[0].get()) * xScl);
        pnts[1][z] = gBndTopY + (int)((Double.parseDouble(data[1][z]) - limits[2].get()) * yScl);
      }
    }else{
      /* TODO: Process String values. */
      Main.error("String values not supported.");
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
      /* Display the X axis markers */
      if(y >= height - (Letter.getHeight(' ') * 4) && y < height - (Letter.getHeight(' ') * 2)){
        if(x < width / 2){
          return Letter.getXY(
            Double.toString(limits[0].get()),
            x - gBndTopX,
            y - (height - (Letter.getHeight(' ') * 4)),
            1
          ) ? foreground : background;
        }else{
          return Letter.getXY(
            Double.toString(limits[1].get()),
            x - (gBndBotX - Letter.getWidth(Double.toString(limits[1].get()), 1)),
            y - (height - (Letter.getHeight(' ') * 4)),
            1
          ) ? foreground : background;
        }
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
    /* Process the graph */
    if((y > gBndTopY && y < gBndBotY) && (x > gBndTopX && x < gBndBotX)){
      for(int z = 0; z < pnts[0].length; z++){
        if(
          (x == pnts[0][z] - 1 && y == pnts[1][z] - 1) ||
          (x == pnts[0][z] + 0 && y == pnts[1][z] - 1) ||
          (x == pnts[0][z] + 1 && y == pnts[1][z] - 1) ||
          (x == pnts[0][z] - 1 && y == pnts[1][z] + 0) ||
          (x == pnts[0][z] + 1 && y == pnts[1][z] + 0) ||
          (x == pnts[0][z] - 1 && y == pnts[1][z] + 1) ||
          (x == pnts[0][z] + 0 && y == pnts[1][z] + 1) ||
          (x == pnts[0][z] + 1 && y == pnts[1][z] + 1)
        ){
          return foreground;
        }
      }
    }
    return background;
  }
}
