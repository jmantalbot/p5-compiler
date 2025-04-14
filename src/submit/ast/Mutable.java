/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class Mutable implements Expression, Node {

  private final String id;
  private final Expression index;

  public Mutable(String id, Expression index) {
    this.id = id;
    this.index = index;
  }
  public String toString() {
    return id;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(id);
    if (index != null) {
      builder.append("[");
      index.toCminus(builder, prefix);
      builder.append("]");
    }
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String reg2 = regAllocator.getAny();
    String reg3 = regAllocator.getAny();
    code.append(String.format("li %s %d\n", reg3, -symbolTable.findOffset(id)));
    code.append(String.format("add %s %s $sp\n", reg3, reg3));
    code.append(String.format("lw %s %d(%s)\n", reg2, 0, reg3));
    regAllocator.clear(reg3);
    return MIPSResult.createRegisterResult(reg2, VarType.INT);
  }
}
