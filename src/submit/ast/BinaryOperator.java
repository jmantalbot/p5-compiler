/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

import java.util.Objects;

/**
 *
 * @author edwajohn
 */
public class BinaryOperator implements Expression {

  private final Expression lhs, rhs;
  private final BinaryOperatorType type;

  public BinaryOperator(Expression lhs, BinaryOperatorType type, Expression rhs) {
    this.lhs = lhs;
    this.type = type;
    this.rhs = rhs;
  }

  public BinaryOperator(Expression lhs, String type, Expression rhs) {
    this.lhs = lhs;
    this.type = BinaryOperatorType.fromString(type);
    this.rhs = rhs;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    lhs.toCminus(builder, prefix);
    builder.append(" ").append(type).append(" ");
    rhs.toCminus(builder, prefix);
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String reg1 = "";
    String reg2 = "";

    if (lhs instanceof NumConstant){
      reg1 = lhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }

    if (lhs instanceof Mutable){
      reg1 = lhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    if (lhs instanceof BinaryOperator){
      reg1 = lhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    if (rhs instanceof NumConstant){
      reg2 = rhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    if (rhs instanceof Mutable){
      reg2 = rhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }
    if (rhs instanceof BinaryOperator){
      reg1 = rhs.toMIPS(code, data, symbolTable, regAllocator).getRegister();
    }

    if (type.equals(BinaryOperatorType.PLUS)){
      code.append(String.format("add %s %s %s\n", reg1, reg1, reg2));
      regAllocator.clear(reg2);
    }
    else if (type.equals(BinaryOperatorType.MINUS)){
      code.append(String.format("sub %s %s %s\n",reg1, reg1, reg2));
      regAllocator.clear(reg2);
    }
    else if (type.equals(BinaryOperatorType.TIMES)){
      code.append(String.format("mult %s %s\nmflo %s\n", reg1, reg2, reg1));
      regAllocator.clear(reg2);
    }
    else if (type.equals(BinaryOperatorType.DIVIDE)){
      code.append(String.format("div %s %s\nmflo %s\n", reg1, reg2, reg1));
      regAllocator.clear(reg2);
    }
    return MIPSResult.createRegisterResult(reg1, VarType.INT);
  }

}
