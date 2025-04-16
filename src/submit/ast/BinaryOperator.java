/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.*;

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
    if (lhs instanceof ParenExpression){
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
    else if (type.equals(BinaryOperatorType.LT)){
      String address = StringLabelGenerator.getLabel();
      code.append(String.format("slt %s %s %s\n", reg1, reg1, reg2));
      code.append(String.format("subi %s %s %d\n", reg1, reg1, 1));
      code.append(String.format("bne %s $zero %s\n", reg1, address));
      regAllocator.clear(reg2);
      regAllocator.clear(reg1);
      return MIPSResult.createAddressResult(address, VarType.VOID);
    }
    else if (type.equals(BinaryOperatorType.GT)){
      String address = StringLabelGenerator.getLabel();
      code.append(String.format("slt %s %s %s\n", reg1, reg2, reg1));
      code.append(String.format("subi %s %s %d\n", reg1, reg1, 1));
      code.append(String.format("bne %s $zero %s\n", reg1, address));
      regAllocator.clear(reg2);
      regAllocator.clear(reg1);
      return MIPSResult.createAddressResult(address, VarType.VOID);
    }
    else if (type.equals(BinaryOperatorType.LE)){
      String address = StringLabelGenerator.getLabel();
      code.append(String.format("slt %s %s %s\n", reg1, reg2, reg1));
      code.append(String.format("bne %s $zero %s\n", reg1, address));
      regAllocator.clear(reg2);
      regAllocator.clear(reg1);
      return MIPSResult.createAddressResult(address, VarType.VOID);
    }

    else if (type.equals(BinaryOperatorType.EQ)){
      String address = StringLabelGenerator.getLabel();
      code.append(String.format("sub %s %s %s\n", reg1, reg1, reg2));
      code.append(String.format("bne %s $zero %s\n", reg1, address));
      regAllocator.clear(reg2);
      regAllocator.clear(reg1);
      return MIPSResult.createAddressResult(address, VarType.VOID);
    }
    else if (type.equals(BinaryOperatorType.GE)){
      String address = StringLabelGenerator.getLabel();
      code.append(String.format("slt %s %s %s\n", reg1, reg1, reg2));
      code.append(String.format("bne %s $zero %s\n", reg1, address));
      regAllocator.clear(reg2);
      regAllocator.clear(reg1);
      return MIPSResult.createAddressResult(address, VarType.VOID);
    }


    return MIPSResult.createRegisterResult(reg1, VarType.INT);
  }

}
