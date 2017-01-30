package barray.graf;

/**
 * Main.java
 *
 * The main entry point into the program.
 **/
public class Main{
  private String[] xVals;
  private String[] yVals;
  private String output;
  private String extension;
  private Mode mode;
  private int width;
  private int height;
  private String[] titles;

  /**
   * main()
   *
   * The main entry method into the program, responsible for putting the
   * program into an instance context.
   *
   * @param args The arguments to be passed to the program.
   **/
  public static void main(String[] args){
    new Main(args);
  }

  /**
   * Main()
   *
   * Create an instance of the main class, responsible for starting to the
   * process of passing the parameters.
   *
   * @param args The arguments to be passed by this program.
   **/
  public Main(String[] args){
    /* Set initial values */
    xVals = null;
    yVals = null;
    output = null;
    extension = null;
    mode = Mode.SCATTER;
    width = 640;
    height = 480;
    titles = null;
    /* Iterate over the command line parameters */
    for(int x = 0; x < args.length; x++){
      switch(args[x]){
        case "-h" :
        case "--help" :
          x += help(args, x);
          break;
        case "-m" :
        case "--mode" :
          x += mode(args, x);
          break;
        case "-t" :
        case "--title" :
          x += title(args, x);
          break;
        case "-w" :
        case "--width" :
          x += width(args, x);
          break;
        case "-x" :
        case "--xaxis" :
          x += xaxis(args, x);
          break;
        case "-y" :
        case "--yaxis" :
          x += yaxis(args, x);
          break;
        case "-z" :
        case "--height" :
          x += height(args, x);
          break;
        default :
          extension = isImage(args, x);
          if(extension != null){
            output = args[x];
          }else{
            error("Not a valid image file `" + args[x] + "`.");
          }
          break;
      }
    }
    /* Check whether we have what is required to run */
    if(xVals != null || yVals != null || output != null){
      if(xVals == null){
        error("X values not set.");
      }
      if(yVals == null){
        error("Y values not set.");
      }
      if(output == null){
        error("Output file not set.");
      }
      Graph graph = new Graph(
        xVals,
        yVals,
        output,
        extension,
        width,
        height,
        mode,
        titles
      );
      graph.generate();
      graph.save();
    }
  }

  /**
   * help()
   *
   * Display the help for this program.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int help(String[] args, int offset){
    System.out.println(
      "\n./graf.jar [OPT] [FILE]" +
      "\n" +
      "\n  OPTions" +
      "\n" +
      "\n    -h  --help      Displays this help" +
      "\n    -m  --mode      Set the graph mode" +
      "\n                      x    Scatter graph (default)" +
      "\n    -t  --title     Set the titles" +
      "\n                      <title> <x axis> <y axis>" +
      "\n    -w  --width     The width of the graph" +
      "\n    -x  --xaxis     The data for the X axis" +
      "\n    -y  --yaxis     The data for the Y axis" +
      "\n    -z  --height    The height of the graph" +
      "\n" +
      "\n  FILE" +
      "\n" +
      "\n    The file to be written to if the program is outputting a file." +
      "\n"
    );
    return 0;
  }

  /**
   * mode()
   *
   * Selects the mode to be used for displaying the graph.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int mode(String[] args, int offset){
    offset++;
    /* Check whether we have enough parameters */
    if(offset < args.length){
      /* Select the mode for the graph */
      switch(args[offset]){
        case "x" :
          mode = Mode.SCATTER;
          break;
        default :
          error("Graph mode `" + args[offset] + "` not supported.");
          break;
      }
    }else{
      Main.error("No parameters found.");
    }
    /* Allow the arguments iterator to skip the searched items */
    return 1;
  }

  /**
   * title()
   **/
  private int title(String[] args, int offset){
    titles = new String[3];
    /* Get the title */
    offset++;
    if(offset < args.length){
      titles[0] = args[offset];
    }else{
      Main.error("Title has not been given.");
    }
    /* Get the X axis */
    offset++;
    if(offset < args.length){
      titles[1] = args[offset];
    }else{
      Main.error("X axis title has not been given.");
    }
    /* Get the Y axis */
    offset++;
    if(offset < args.length){
      titles[2] = args[offset];
    }else{
      Main.error("Y axis title has not been given.");
    }
    /* Allow the arguments iterator to skip the searched items */
    return 3;
  }

  /**
   * xaxis()
   *
   * Record the values for the xaxis.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int xaxis(String[] args, int offset){
    offset++;
    /* Search the number of parameters to be loaded */
    int n = args.length - 1;
    for(int x = offset; x < args.length - 1; x++){
      if(args[x].charAt(0) == '-'){
        n = x;
        break;
      }
    }
    /* Copy our parameter array */
    xVals = new String[n - offset];
    for(int x = offset; x < n; x++){
      xVals[x - offset] = args[x];
    }
    /* Allow the arguments iterator to skip the searched items */
    return n - offset;
  }

  /**
   * yaxis()
   *
   * Record the values for the yaxis.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int yaxis(String[] args, int offset){
    offset++;
    /* Search the number of parameters to be loaded */
    int n = args.length - 1;
    for(int x = offset; x < args.length - 1; x++){
      if(args[x].charAt(0) == '-'){
        n = x;
        break;
      }
    }
    /* Copy our parameter array */
    yVals = new String[n - offset];
    for(int x = offset; x < n; x++){
      yVals[x - offset] = args[x];
    }
    /* Allow the arguments iterator to skip the searched items */
    return n - offset;
  }

  /**
   * width()
   *
   * Set the width of the graph to be output.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int width(String[] args, int offset){
    offset++;
    /* Make sure that there are enough parameters */
    if(offset < args.length){
      /* Try to convert the number */
      try{
        width = Integer.parseInt(args[offset]);
      }catch(NumberFormatException e){
        error("Invalid number set `" + args[offset] + "`.");
      }
      /* Make sure the number is valid */
      if(width < 0){
        error("Width cannot be minus.");
      }
    }else{
      error("No width parameter set.");
    }
    return 1;
  }

  /**
   * height()
   *
   * Set the height of the graph to be output.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The amount to offset the argument iterator by.
   **/
  private int height(String[] args, int offset){
    offset++;
    /* Make sure that there are enough parameters */
    if(offset < args.length){
      /* Try to convert the number */
      try{
        height = Integer.parseInt(args[offset]);
      }catch(NumberFormatException e){
        error("Invalid number set `" + args[offset] + "`.");
      }
      /* Make sure the number is valid */
      if(height < 0){
        error("Height cannot be minus.");
      }
    }else{
      error("No height parameter set.");
    }
    return 1;
  }

  /**
   * isImage()
   *
   * Check whether a String is a valid image.
   *
   * @param args A reference to the arguments if required.
   * @param offset The offset for the arguments if required.
   * @return The extension, otherwise NULL.
   **/
  private String isImage(String[] args, int offset){
    /* Check whether last parameter and picture */
    if(offset == args.length - 1){
      int i = args[offset].lastIndexOf(".") + 1;
      if(i > 0){
        switch(args[offset].substring(i)){
          case "bmp" :
            return "bmp";
          case "gif" :
            return "gif";
          case "jpeg" :
          case "jpg" :
            return "jpg";
          case "png" :
            return "png";
        }
      }
    }
    return null;
  }

  /**
   * error()
   *
   * Display an error message and stop execution of the program.
   *
   * @param msg The message to be displayed for the error.
   **/
  public static void error(String msg){
    System.err.println(msg);
    System.exit(0);
  }
}
