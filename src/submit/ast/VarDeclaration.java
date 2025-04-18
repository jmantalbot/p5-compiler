/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edwajohn
 */
public class VarDeclaration implements Declaration, Node {

  private final VarType type;
  private final List<String> ids;
  private final List<Integer> arraySizes;
  private final boolean isStatic;

  public VarDeclaration(VarType type, List<String> ids, List<Integer> arraySizes, boolean isStatic) {
    this.type = type;
    this.ids = new ArrayList<>(ids);
    this.arraySizes = arraySizes;
    this.isStatic = isStatic;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append(prefix);
    if (isStatic) {
      builder.append("static ");
    }
    builder.append(type).append(" ");
    for (int i = 0; i < ids.size(); ++i) {
      final String id = ids.get(i);
      final int arraySize = arraySizes.get(i);
      if (arraySize >= 0) {
        builder.append(id).append("[").append(arraySize).append("]").append(", ");
      } else {
        builder.append(id).append(", ");
      }
    }
    builder.delete(builder.length() - 2, builder.length());
    builder.append(";\n");
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){
    for (int i = 0; i < ids.size(); ++i) {
      String id = ids.get(i);
      int arraySize = arraySizes.get(i);
      if (arraySize == -1) arraySize = 1;
      symbolTable.addSymbol(id, new SymbolInfo("id", type, false, arraySize), arraySize);
    }
    return MIPSResult.createVoidResult();
  }
}
