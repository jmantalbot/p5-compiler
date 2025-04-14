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
public class BoolConstant implements Expression {

  private final boolean value;

  public BoolConstant(boolean value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    if (value) {
      builder.append("true");
    } else {
      builder.append("false");
    }
  }
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    return MIPSResult.createVoidResult();
  }

}
