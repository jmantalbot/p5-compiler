package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author edwajohn
 */
public class Call implements Expression {

  private final String id;
  private final List<Expression> args;

  public Call(String id, List<Expression> args) {
    this.id = id;
    this.args = new ArrayList<>(args);
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(id).append("(");
    for (Expression arg : args) {
      arg.toCminus(builder, prefix);
      builder.append(", ");
    }
    if (!args.isEmpty()) {
      builder.setLength(builder.length() - 2);
    }
    builder.append(")");
  }
  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator){

    if (Objects.equals(id, "println")){
      if (data.indexOf("newline: .asciiz \"\\n\"") == -1){
        data.append( "newline: .asciiz \"\\n\"\n");
      }
      MIPSResult m = null;
      if(args.getFirst() instanceof StringConstant){
        m = args.getFirst().toMIPS(code, data, symbolTable, regAllocator);
        code.append(String.format("la $a0 %s\nli $v0 4\nsyscall\n", m.getAddress()));
        data.append(String.format("%s: .asciiz  %s\n", m.getAddress(), ((StringConstant) args.getFirst()).getValue()));
      } else {
        if (args.getFirst() instanceof NumConstant){
          String reg = args.getFirst().toMIPS(code, data, symbolTable, regAllocator).getRegister();
          code.append(String.format("move $a0 %s\nli $v0 1\nsyscall\n", reg));
          regAllocator.clear(reg);
        }
        if (args.getFirst() instanceof Mutable){
          String reg = args.getFirst().toMIPS(code, data, symbolTable, regAllocator).getRegister();
          code.append(String.format("move $a0 %s\nli $v0 1\nsyscall\n", reg));
          regAllocator.clear(reg);
        }
        if (args.getFirst() instanceof BinaryOperator){
          String reg = args.getFirst().toMIPS(code, data, symbolTable, regAllocator).getRegister();
          code.append(String.format("move $a0 %s\nli $v0 1\nsyscall\n", reg));
          regAllocator.clear(reg);
        }
        if (args.getFirst() instanceof Call){
          String reg = regAllocator.getAny();
          args.getFirst().toMIPS(code, data, symbolTable, regAllocator).getRegister();
          code.append(String.format("move $a0 %s\nli $v0 1\nsyscall\n", reg));
          regAllocator.clear(reg);
        }
      }
      code.append(String.format("la $a0 %s\nli $v0 4\nsyscall\n", "newline"));




    } else {
      String reg = regAllocator.getAny();
      code.append(String.format("move %s $ra\n", reg));
      code.append(String.format("sw %s %d($sp)\n", reg, -(symbolTable.getSize() + 4)));
      for (Expression arg : args) {
        String reg2 = regAllocator.getAny();
        if (arg instanceof NumConstant){
          symbolTable.addSymbol(arg.toString(), new SymbolInfo(arg.toString(), VarType.INT, false));
          regAllocator.clear(reg2);
          reg2 = arg.toMIPS(code, data, symbolTable, regAllocator).getRegister();
          code.append(String.format("sw %s -%d($sp)\n", reg2, symbolTable.getSize() + 4));
          regAllocator.clear(reg2);
        }
        else if (arg instanceof Mutable){
          symbolTable.addSymbol(arg.toString(), new SymbolInfo(arg.toString(), VarType.INT, false));
          reg2 = arg.toMIPS(code, data, symbolTable, regAllocator).getRegister();
          symbolTable.setSize(symbolTable.getSize() + symbolTable.getTypeSize(VarType.INT));
          code.append(String.format("sw %s -%d($sp)\n", reg2, symbolTable.getSize() + 4));
          regAllocator.clear(reg2);
        } else if (arg instanceof Call){
          regAllocator.clear(reg2);
          arg.toMIPS(code, data, symbolTable, regAllocator);
        }
      }
      regAllocator.clearAll();


      code.append(String.format("add $sp $sp -%s\n", symbolTable.getSize()));
      code.append(String.format("jal %s\n", id));
      code.append(String.format("add $sp $sp %s\n", symbolTable.getSize()));
      code.append(String.format("lw %s -%d($sp)\n", reg, symbolTable.getSize()));
      code.append(String.format("move $ra %s\n", reg));

      if (symbolTable.getThisOffset("return")){
        reg = regAllocator.getAny();
        code.append(String.format("lw %s -%d($sp)\n", reg, symbolTable.getSize() + args.size() * symbolTable.getTypeSize(VarType.INT)));
      }

      regAllocator.clear(reg);
    }
    return MIPSResult.createVoidResult();
  }
}
