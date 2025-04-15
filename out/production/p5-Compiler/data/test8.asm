# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

add:
addi $sp $sp -8
li $t1 4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t2 0
add $t2 $t2 $sp
lw $t1 0($t2)
add $t0 $t0 $t1
sw $t0 -12($sp)
jr $ra
addi $sp $sp 8
jr $ra

add2:
addi $sp $sp -8
move $t0 $ra
sw $t0 -12($sp)
move $t1 $ra
sw $t1 -12($sp)
li $t3 4
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -16($sp)
li $t3 0
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -20($sp)
add $sp $sp -12
jal add
add $sp $sp 12
lw $t1 -12($sp)
move $ra $t1
lw $t0 -24($sp)
li $t1 1
sw $t1 -16($sp)
add $sp $sp -8
jal add
add $sp $sp 8
lw $t0 -8($sp)
move $ra $t0
lw $t0 -20($sp)
jr $ra
addi $sp $sp 8
jr $ra

main:
addi $sp $sp -8
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall
move $t0 $ra
sw $t0 -4($sp)
li $t1 2
sw $t1 -8($sp)
li $t1 4
sw $t1 -12($sp)
add $sp $sp -4
jal add2
add $sp $sp 4
lw $t0 -4($sp)
move $ra $t0
lw $t0 -16($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
addi $sp $sp 8
li $v0 10
syscall

# All memory structures are placed after the
# .data assembler directive
.data

newline: .asciiz "\n"
datalabel0: .asciiz  "This program prints 7"
