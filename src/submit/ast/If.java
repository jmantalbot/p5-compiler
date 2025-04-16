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
public class If implements Statement {

  private final Expression expression;
  private final Statement trueStatement;
  private final Statement falseStatement;

  public If(Expression expression, Statement trueStatement, Statement falseStatement) {
    this.expression = expression;
    this.trueStatement = trueStatement;
    this.falseStatement = falseStatement;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("if (");
    expression.toCminus(builder, prefix);
    builder.append(")\n");
    if (trueStatement instanceof CompoundStatement) {
      trueStatement.toCminus(builder, prefix);
    } else {
      trueStatement.toCminus(builder, prefix + " ");
    }
    if (falseStatement != null) {
      builder.append(prefix).append("else\n");
//      falseStatement.toCminus(builder, prefix);
      if (falseStatement instanceof CompoundStatement) {
        falseStatement.toCminus(builder, prefix);
      } else {
        falseStatement.toCminus(builder, prefix + " ");
      }
    }
//    builder.append(prefix).append("}");
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String addr = "";
    if (expression instanceof BinaryOperator) {
      addr = expression.toMIPS(code, data, symbolTable, regAllocator).getAddress();
    }
    String addr2 = StringLabelGenerator.getLabel();

    symbolTable.addSymbol("0", new SymbolInfo("0", VarType.VOID, false, 0), 0);
    trueStatement.toMIPS(code, data, symbolTable, regAllocator);
    code.append(String.format("j %s\n", addr2));
    code.append(String.format("%s:\n", addr));
    if (falseStatement != null) {
      falseStatement.toMIPS(code, data, symbolTable, regAllocator);
    }
    code.append(String.format("%s:\n", addr2));
    return MIPSResult.createVoidResult();
  }
}
