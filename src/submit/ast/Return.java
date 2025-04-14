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
public class Return implements Statement {

  private final Expression expr;

  public Return(Expression expr) {
    this.expr = expr;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix);
    if (expr == null) {
      builder.append("return;\n");
    } else {
      builder.append("return ");
      expr.toCminus(builder, prefix);
      builder.append(";\n");
    }
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    regAllocator.clearAll();
    String reg = regAllocator.getAny();
    String reg2 = regAllocator.getAny();
    if (expr instanceof NumConstant){
      symbolTable.addSymbol(expr.toString(), new SymbolInfo(expr.toString(), VarType.INT, false));
      reg = expr.toMIPS(code, data, symbolTable, regAllocator).getRegister();
      code.append(String.format("sw %s -%d($sp)\n", reg, symbolTable.getSize()));
      regAllocator.clear(reg);
    } else if (expr instanceof Mutable){
      symbolTable.addSymbol(expr.toString(), new SymbolInfo(expr.toString(), VarType.INT, false));
      code.append(String.format("li %s %d\n", reg2, symbolTable.findOffset(expr.toString())));
      code.append(String.format("add %s %s $sp\n", reg2, reg2));
      code.append(String.format("lw %s 0(%s)\n", reg, reg2));
      symbolTable.setSize(symbolTable.getSize() + symbolTable.getTypeSize(VarType.INT));
      code.append(String.format("sw %s -%d($sp)\n", reg, symbolTable.getSize()));
      regAllocator.clear(reg);
      regAllocator.clear(reg2);
    } else if (expr instanceof BinaryOperator){
      regAllocator.clear(reg);
      regAllocator.clear(reg2);
      reg = expr.toMIPS(code, data, symbolTable, regAllocator).getRegister();
      if (symbolTable.getSize() != 0) symbolTable.setSize(symbolTable.getSize() + symbolTable.getTypeSize(VarType.INT));
      if (symbolTable.getSize() == 0) symbolTable.setSize(symbolTable.getParent().getSize() + symbolTable.getTypeSize(VarType.INT));
      code.append(String.format("sw %s %d($sp)\n", reg, -symbolTable.getSize()));
      regAllocator.clear(reg);
    } else if (expr instanceof Call){
      regAllocator.clear(reg);
      regAllocator.clear(reg2);
      expr.toMIPS(code, data, symbolTable, regAllocator);
    }
    regAllocator.clear(reg2);
    regAllocator.clear(reg);
    code.append("jr $ra\n");
    regAllocator.clearAll();
    return MIPSResult.createVoidResult();
  }
}
