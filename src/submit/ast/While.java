/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.*;

/**
 *
 * @author edwajohn
 */
public class While implements Statement {

  private final Expression expression;
  private final Statement statement;

  public While(Expression expression, Statement statement) {
    this.expression = expression;
    this.statement = statement;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("while (");
    expression.toCminus(builder, prefix);
    builder.append(")\n");
    if (statement instanceof CompoundStatement) {
      statement.toCminus(builder, prefix);
    } else {
      statement.toCminus(builder, prefix + " ");
    }
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String addr = StringLabelGenerator.getLabel();
    String addr2 = "";
    if (expression instanceof BinaryOperator) {
      code.append(String.format("%s:\n", addr));
      addr2 = expression.toMIPS(code, data, symbolTable, regAllocator).getAddress();
    }
    if (statement instanceof CompoundStatement) {
      symbolTable.addSymbol("0", new SymbolInfo("0", VarType.VOID, false));
    }
    statement.toMIPS(code, data, symbolTable, regAllocator);
    code.append(String.format("j %s\n", addr));
    code.append(String.format("%s:\n", addr2));
    return MIPSResult.createVoidResult();
  }
}
