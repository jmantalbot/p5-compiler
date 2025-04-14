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
public class UnaryOperator implements Expression {

  private final UnaryOperatorType type;
  private final Expression expression;

  public UnaryOperator(String type, Expression expression) {
    this.type = UnaryOperatorType.fromString(type);
    this.expression = expression;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(type);
    expression.toCminus(builder, prefix);
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String reg = "";
    if (type == UnaryOperatorType.NEG && expression instanceof NumConstant) {
      reg = regAllocator.getAny();
      code.append(String.format("li %s %d\n", reg, ((NumConstant) expression).getValue()));
      code.append(String.format("sub %s $zero %s\n", reg, reg));
    }

    return MIPSResult.createRegisterResult(reg, VarType.INT);
  }
}
