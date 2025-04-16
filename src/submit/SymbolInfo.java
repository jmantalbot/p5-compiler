/*
 * Code formatter project
 * CS 4481
 */
package submit;

import submit.ast.VarType;

/**
 *
 * @author edwajohn
 */
public class SymbolInfo {

  private final String id;
  // In the case of a function, type is the return type
  private final VarType type;
  private final boolean function;
  private final int arraySize;

  public SymbolInfo(String id, VarType type, boolean function, int arraySize) {
    this.id = id;
    this.type = type;
    this.function = function;
    this.arraySize = arraySize;
  }


  @Override
  public String toString() {
    return "<" + id + ", " + type + '>';
  }

  public String getId() {
    return id;
  }

  public VarType getType() {
    return type;
  }
  public boolean isFunction() {
    return function;
  }
  public int arraySize() {
    return arraySize;
  }
}
