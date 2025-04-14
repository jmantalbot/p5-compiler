/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.StringLabelGenerator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class StringConstant implements Expression {

  private final String value;

  public StringConstant(String value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append("\"").append(value).append("\"");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    String label = StringLabelGenerator.getLabel();
    return MIPSResult.createAddressResult(label, VarType.STRING);
  }
  public String getValue() {
    return value;
  }
}
