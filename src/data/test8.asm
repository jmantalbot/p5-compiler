# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

add:
addi $sp $sp 0
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

add2:
addi $sp $sp 0
move $t0 $ra
sw $t0 -4($sp)
move $t1 $ra
sw $t1 -4($sp)
li $t3 -4
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -8($sp)
li $t3 -8
add $t3 $t3 $sp
lw $t2 0($t3)
sw $t2 -12($sp)
add $sp $sp -4
jal add
add $sp $sp 4
lw $t1 -4($sp)
move $ra $t1
lw $t0 -16($sp)
li $t1 1
sw $t1 -8($sp)
add $sp $sp -0
jal add
add $sp $sp 0
lw $t0 -0($sp)
move $ra $t0
lw $t0 -12($sp)
jr $ra
addi $sp $sp 0
jr $ra

main:
addi $sp $sp 0
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
addi $sp $sp 0
li $v0 10
syscall

# All memory structures are placed after the
# .data assembler directive
.data

newline: .asciiz "\n"
datalabel0: .asciiz  "This program prints 7"
