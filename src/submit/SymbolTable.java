package submit;

import submit.ast.VarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable {

  private final HashMap<String, SymbolInfo> table;
  private SymbolTable parent;
  private final List<SymbolTable> children;
  private int size;
  private final HashMap<String, Integer> offset;

  public SymbolTable() {
    table = new HashMap<>();
    parent = null;
    children = new ArrayList<>();
    table.put("println", new SymbolInfo("println", VarType.VOID, true));
    size = 0;
    offset = new HashMap<>();
  }

  public void addSymbol(String id, SymbolInfo symbol) {
    if (!table.containsKey(id)) {
      table.put(id, symbol);
      if (!symbol.isFunction()) {
        size += getTypeSize(symbol.getType());
      }
      if (!symbol.isFunction()){
        offset.put(id, size);
      }
    }
  }
  public int getTypeSize(VarType type) {
    if (type == null){
      return 0;
    }
    switch (type) {
      case INT:
        return 4;
      case BOOL:
        return 1;
      case CHAR:
        return 1;
      case VOID:
      default:
        return 0;
    }
  }

  /**
   * Returns null if no symbol with that id is in this symbol table or an
   * ancestor table.
   *
   * @param id
   * @return
   */
  public SymbolInfo find(String id) {
    if (table.containsKey(id)) {
      return table.get(id);
    }
    if (parent != null) {
      return parent.find(id);
    }
    return null;
  }

  public boolean removeSymbol(String id) {
    if (table.containsKey(id) && offset.containsKey(id)) {
      table.remove(id);
      offset.remove(id);
      return true;
    }
    return false;
  }

  public HashMap<String, SymbolInfo> getSymbolTable() {
    return table;
  }

  /**
   * Returns the new child.
   *
   * @return
   */
  public SymbolTable createChild() {
    SymbolTable child = new SymbolTable();
    children.add(child);
    child.parent = this;
    return child;
  }

  public SymbolTable getParent() {
    return parent;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setParent(SymbolTable parentSymbolTable) {
    parent = parentSymbolTable;
  }

  public int getSize() {
    return this.size;
  }

  public HashMap<String, Integer> getOffset() {
    return offset;
  }

  public void setOffset(String id, Integer offset) {
    this.offset.remove(id);
    this.offset.put(id, offset);
  }

  public Integer getTotalSize(){
    Integer totalSize = 0;
    totalSize += getSize();
    if (parent != null) {
      totalSize += parent.getTotalSize();
    }
    return totalSize;
  }


  public Integer findOffset(String id) {
    Integer t_offset = -1;
    if (offset.containsKey(id)) {
      return -offset.get(id);
    }
    else {
      if (parent != null) {
        t_offset = parent.findOffset(id);
      }
    }
    if (t_offset == -1) return 0;
    return t_offset;
  }

  public void incrementOffset(String id, Integer decrement) {
      offset.compute(id, (k, curr_offset) -> curr_offset + decrement);
  }

  public void incrementOffsetAll(Integer increment) {
    for (String s : offset.keySet()) {
      incrementOffset(s, increment);
    }
  }

  public boolean getThisOffset(String id) {
    return offset.containsKey(id);
  }
}
