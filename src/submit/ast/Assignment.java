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
public class Assignment implements Expression, Node {

  private final Mutable mutable;
  private final AssignmentType type;
  private final Expression rhs;

  public Assignment(Mutable mutable, String assign, Expression rhs) {
    this.mutable = mutable;
    this.type = AssignmentType.fromString(assign);
    this.rhs = rhs;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    mutable.toCminus(builder, prefix);
    if (rhs != null) {
      builder.append(" ").append(type.toString()).append(" ");
      rhs.toCminus(builder, prefix);
    } else {
      builder.append(type.toString());
    }
  }
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    String reg1 = regAllocator.getAny();
    String reg2 = regAllocator.getAny();
    int offset = symbolTable.findOffset(mutable.toString());

    code.append(String.format("li %s %d\n", reg1, offset));
    code.append(String.format("add %s %s $sp\n",reg1, reg1));
    int value;
    if (rhs instanceof NumConstant){
      value = ((NumConstant) rhs).getValue();
      code.append(String.format("li %s %s\n", reg2, value));
    }
    else if (rhs instanceof BinaryOperator) {
      regAllocator.clear(reg2);
      reg2 = rhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    else if (rhs instanceof UnaryOperator) {
      regAllocator.clear(reg2);
      reg2 = rhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    code.append(String.format("sw %s %d(%s)\n", reg2, 0, reg1));
    regAllocator.clear(reg2);
    regAllocator.clear(reg1);
    return MIPSResult.createAddressResult(String.format("%d(%s)", 0, reg2), VarType.INT);
  }
}
