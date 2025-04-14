# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

# code for add
add:
addi $sp $sp -0
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t2 -8
add $t2 $t2 $sp
lw $t1 0($t2)
add $t0 $t0 $t1
sw $t0 -12($sp)
jr $ra
addi $sp $sp 0
jr $ra

# code for add2
add2:
addi $sp $sp -0
move $t0 $ra
sw $t0 -16($sp)
move $t1 $ra
sw $t0 -16($sp)
sw $t1 -20($sp)
li $t3 -4
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -24($sp)
li $t3 -8
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -28($sp)
add $sp $sp -20
jal add
add $sp $sp 20
lw $t0 -16($sp)
lw $t1 -20($sp)
move $ra $t1
lw $t1 -32($sp)
sw $t1 -20($sp)
li $t1 1
sw $t1 -24($sp)
add $sp $sp -16
jal add
add $sp $sp 16
lw $t0 -16($sp)
move $ra $t0
lw $t0 -28($sp)
sw $t0 -12($sp)
jr $ra
addi $sp $sp 0
jr $ra

# code for main
main:
# Entering a new scope.
# Symbols in symbol table:
#  println
#  return
# Update the stack pointer.
addi $sp $sp -0
# println
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall
# println
# Calling function add2
# Save $ra to a register
move $t0 $ra
# Save $t0-9 registers
sw $t0 -4($sp)
# Evaluate parameters and save to stack
li $t1 2
sw $t1 -8($sp)
li $t1 4
sw $t1 -12($sp)
# Update the stack pointer
add $sp $sp -4
# Call the function
jal add2
# Restore the stack pointer
add $sp $sp 4
# Restore $t0-9 registers
lw $t0 -4($sp)
# Restore $ra
move $ra $t0
# Get return value off stack
lw $t0 -16($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
# Exiting scope.
addi $sp $sp 0
li $v0 10
syscall

# All memory structures are placed after the
# .data assembler directive
.data

newline:	.asciiz	"\n"
datalabel0:	.asciiz	"This program prints 7"
