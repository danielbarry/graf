package barray.graf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Graph.java
 *
 * This class if responsible for generating a graph given valid data.
 **/
public class Graph{
  private BufferedImage img;
  private Process process;
  private String output;
  private String ext;
  private int width;
  private int height;

  /**
   * Graph()
   *
   * This method is responsible for testing the appropriate parameters for the
   * purpose of graph generation.
   *
   * @param xVals An array of values for the X axis to be computed.
   * @param yVals An array of values for the Y axis to be computed.
   * @param output The output filename for the graph being generated.
   * @param ext The extension for the filename.
   * @param width The width of the image to be produced.
   * @param height The height of the image to be produced.
   * @param mode The mode to output the graph in.
   * @param titles The titles that have been set.
   **/
  public Graph(
    String[] xVals,
    String[] yVals,
    String output,
    String ext,
    int width,
    int height,
    Mode mode,
    String[] titles
  ){
    /* Store important data */
    this.output = output;
    this.ext = ext;
    this.width = width;
    this.height = height;
    /* Initialise the image */
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    /* Check the type of the data */
    boolean xIsNum = isNumber(xVals);
    boolean yIsNum = isNumber(yVals);
    /* Order the data by X, failing that by Y */
    String[][] data = orderData(xVals, yVals, xIsNum, yIsNum);
    /* Process the limits for the graph */
    Maybe<Double>[] limits = dataLimits(data, xIsNum, yIsNum);
    /* Process the graph for the mode */
    switch(mode){
      case SCATTER :
        process = new GraphScatter(data, limits, width, height, titles);
        break;
      default :
        Main.error("`" + mode.toString() + "` mode not set.");
        break;
    }
  }

  /**
   * generate()
   *
   * Here the graph image is generated in a buffer, ready to be saved in the
   * desired format.
   **/
  public void generate(){
    process.process();
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        img.setRGB(x, y, process.getXY(x, y));
      }
    }
  }

  /**
   * save()
   *
   * The save method is responsible for saving the buffered image to disk in
   * the desired format.
   **/
  public void save(){
    File file = new File(output);
    try{
      ImageIO.write(img, ext, file);
    }catch(IOException e){
      Main.error("Failed to write the image to disk.");
    }
  }

  /**
   * isNumber()
   *
   * Check whether the value array contains all numbers or should be treated as
   * a String. This is done by checking whether the values can be cast to
   * Double values.
   *
   * @param array The array to be checked.
   * @return Whether the array contains all numbers, true, or Strings, false.
   **/
  private boolean isNumber(String[] array){
    for(int x = 0; x < array.length; x++){
      try{
        Double.parseDouble(array[x]);
      }catch(NumberFormatException e){
        return false;
      }
    }
    return true;
  }

  /**
   * orderData()
   *
   * Order the X and Y array depending on whether it contains data that can be
   * ordered.
   *
   * @param xData The X array data.
   * @param yData The Y array data.
   * @param xIsNum Whether the X data array is all numbers.
   * @param yIsNum Whether the Y data array is all numbers.
   * @return The ordered data.
   **/
  private String[][] orderData(
    String[] xData,
    String[] yData,
    boolean xIsNum,
    boolean yIsNum
  ){
    return new String[][]{xData, yData}; // TODO: Remove me.
  }

  /**
   * dataLimits()
   *
   * Find the limits for the data to be displayed.
   *
   * @param data Both sets of data.
   * @param xIsNum Whether the X data array is all numbers.
   * @param yIsNum Whether the Y data array is all numbers.
   * @return An array of limits: X min, X max, Y min and Y max.
   **/
  private Maybe<Double>[] dataLimits(
    String[][] data,
    boolean xIsNum,
    boolean yIsNum
  ){
    return new Maybe[]{
      new Maybe<Double>(),
      new Maybe<Double>(),
      new Maybe<Double>(),
      new Maybe<Double>()
    }; // TODO: Remove me.
  }
}
