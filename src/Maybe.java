package barray.graf;

/**
 * Maybe.java
 *
 * This class holds a value that may or may not have been set - information
 * which can be queried previous to use.
 **/
public class Maybe<E>{
  private boolean set;
  private E obj;

  /**
   * Maybe()
   *
   * Initialise the object if it exists.
   *
   * @param obj The object to be initialised with.
   **/
  public Maybe(E obj){
    this.set = true;
    this.obj = obj;
  }

  /**
   * Maybe()
   *
   * Initialise the object if it doesn't exist.
   **/
  public Maybe(){
    this.set = false;
    this.obj = null;
  }

  /**
   * isSet()
   *
   * Returns whether the object has been set.
   *
   * @return f the object is set, true, otherwise false.
   **/
  public boolean isSet(){
    return this.set;
  }

  /**
   * get()
   *
   * Gets the object - if not set an exception is thrown.
   *
   * @return The object stored.
   **/
  public E get(){
    if(this.set){
      return this.obj;
    }else{
      throw new RuntimeException("Object not set.");
    }
  }

  /**
   * set()
   *
   * Sets the object.
   *
   * @param obj The object to be set.
   **/
  public void set(E obj){
    this.set = true;
    this.obj = obj;
  }

  /**
   * clear()
   *
   * Clear the object from storage.
   *
   * @return Whether an object was removed.
   **/
  public boolean clear(){
    if(this.set){
      this.set = false;
      this.obj = null;
      return true;
    }else{
      this.set = false;
      return false;
    }
  }
}
