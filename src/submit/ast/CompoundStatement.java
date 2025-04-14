/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author edwajohn
 */
public class CompoundStatement implements Statement {

  private final List<Statement> statements;
  private SymbolTable symbolTable;

  public CompoundStatement(List<Statement> statements) {
    this.statements = statements;
    this.symbolTable = new SymbolTable();
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("{\n");
    for (Statement s : statements) {
      s.toCminus(builder, prefix + "  ");
    }
    builder.append(prefix).append("}\n");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    this.symbolTable = new SymbolTable();
    this.symbolTable.setParent(symbolTable);
    int stackpointer = 0;
    if(symbolTable.find("return") != null){
      stackpointer -= symbolTable.findOffset("return");
    }
    if(symbolTable.find("return") == null){
      this.symbolTable.addSymbol("return", new SymbolInfo("return", null, true));
      this.symbolTable.setOffset("return", 0);
    }
    stackpointer += symbolTable.getSize();
    this.symbolTable.getParent().incrementOffsetAll(-stackpointer);
    code.append("addi $sp $sp ").append(-stackpointer).append("\n");

    MIPSResult m = MIPSResult.createVoidResult();
    for (Statement statement : statements) {
      m = statement.toMIPS(code, data, this.symbolTable, regAllocator);
    }

    code.append("addi $sp $sp ").append(stackpointer).append("\n");
    this.symbolTable.getParent().incrementOffsetAll(stackpointer);
    return m;
  }
}
