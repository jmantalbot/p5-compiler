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
      int stack = symbolTable.getSize();
      String reg = regAllocator.getAny();
      code.append(String.format("move %s $ra\n", reg));
      stack += 4;
      code.append(String.format("sw %s %d($sp)\n", reg, -(stack)));
      for (Expression arg : args) {
        String reg2 = "";
        if (arg instanceof NumConstant){
          reg2 = arg.toMIPS(code, data, symbolTable, regAllocator).getRegister();
          stack +=4;
          code.append(String.format("sw %s -%d($sp)\n", reg2, stack));
          regAllocator.clear(reg2);
        }
        else if (arg instanceof Mutable){
          reg2 = arg.toMIPS(code, data, symbolTable, regAllocator).getRegister();
          stack += 4;
          code.append(String.format("sw %s -%d($sp)\n", reg2, stack));
          regAllocator.clear(reg2);
        } else if (arg instanceof Call){
          arg.toMIPS(code, data, symbolTable, regAllocator);
        }
      }
      regAllocator.clearAll();


      code.append(String.format("add $sp $sp -%s\n", stack - args.size() * symbolTable.getTypeSize(VarType.INT)));
      code.append(String.format("jal %s\n", id));
      code.append(String.format("add $sp $sp %s\n", stack - args.size() * symbolTable.getTypeSize(VarType.INT)));
      code.append(String.format("lw %s -%d($sp)\n", reg, stack - args.size() * symbolTable.getTypeSize(VarType.INT)));
      code.append(String.format("move $ra %s\n", reg));
      regAllocator.clearAll();

      VarType type = symbolTable.find(id).getType();
      if (type != VarType.VOID && type != null){
         reg = regAllocator.getAny();
        code.append(String.format("lw %s -%d($sp)\n", reg, stack));
        return MIPSResult.createRegisterResult(reg, type);
      }
    }
    return MIPSResult.createVoidResult();
  }
}
