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
  public final Expression index;

  public Mutable(String id, Expression index) {
    this.id = id;
    this.index = index;
  }
  public String toString() {
    return id;
  }
  public int getIndex(){
    if (index instanceof NumConstant){
      return ((NumConstant)index).getValue();
    }
    return -1;
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
    if (index == null) {
      code.append(String.format("li %s %d\n", reg3, symbolTable.findOffset(id)));
      code.append(String.format("add %s %s $sp\n", reg3, reg3));
      code.append(String.format("lw %s %d(%s)\n", reg2, 0, reg3));
    }
    if (index != null) {
      if (index instanceof NumConstant){
        String reg = regAllocator.getAny();
        String reg4 = regAllocator.getAny();
        code.append(String.format("li %s %d\n", reg3, symbolTable.findOffset(id)));
        code.append(String.format("add %s %s $sp\n", reg3, reg3));
        code.append(String.format("li %s %d\n", reg, ((NumConstant) index).getValue()));
        code.append(String.format("li %s %d\n", reg4, 4));
        code.append(String.format("mul %s %s %s\n", reg, reg, reg4));
        code.append(String.format("add %s %s %s\n", reg3, reg3, reg));
        code.append(String.format("lw %s %d(%s)\n", reg2, 0, reg3));
        regAllocator.clear(reg2);
        regAllocator.clear(reg3);
        regAllocator.clear(reg4);
        regAllocator.clear(reg);
      }
      if (index instanceof Mutable){
        String reg4 = regAllocator.getAny();
        String reg5 = regAllocator.getAny();
        code.append(String.format("li %s %d\n", reg4, symbolTable.findOffset(id)));
        code.append(String.format("add %s %s $sp\n", reg3, reg3));
        code.append(String.format("li %s %d\n", reg5, symbolTable.findOffset(String.valueOf(index))));
        code.append(String.format("add %s %s $sp\n", reg5, reg5));
        code.append(String.format("lw %s 0(%s)\n", reg4, reg5));
        code.append(String.format("li %s 4\n", reg4));
        code.append(String.format("mul %s %s %s\n", reg3, reg3, reg4));
        code.append(String.format("add %s %s %s\n", reg2, reg2, reg3));
        code.append(String.format("lw %s %d(%s)\n", reg2, 0, reg3));
        regAllocator.clear(reg2);
        regAllocator.clear(reg3);
        regAllocator.clear(reg4);
        regAllocator.clear(reg5);
      }


    }

    regAllocator.clear(reg3);
    return MIPSResult.createRegisterResult(reg2, VarType.INT);
  }
}
