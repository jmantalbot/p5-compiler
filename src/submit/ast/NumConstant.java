/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class NumConstant implements Expression, Node {

  private final int value;

  public NumConstant(int value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append(Integer.toString(value));
  }

  public String toString() {
    return Integer.toString(value);
  }
  public int getValue() {
    return value;
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String reg = regAllocator.getAny();
    code.append(String.format("li %s %d\n", reg, value));
    return MIPSResult.createRegisterResult(reg, VarType.INT);
  }
}
