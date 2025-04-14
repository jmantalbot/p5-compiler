# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

identity:
addi $sp $sp -0
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
sw $t0 -8($sp)
jr $ra
addi $sp $sp 0
jr $ra

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

main:
addi $sp $sp -0
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall
move $t0 $ra
sw $t0 -4($sp)
li $t1 7
sw $t1 -8($sp)
add $sp $sp -4
jal identity
add $sp $sp 4
lw $t0 -4($sp)
move $ra $t0
lw $t0 -8($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
move $t0 $ra
sw $t0 -8($sp)
li $t1 3
sw $t1 -12($sp)
li $t1 4
sw $t1 -16($sp)
add $sp $sp -12
jal add
add $sp $sp 12
lw $t0 -12($sp)
move $ra $t0
lw $t0 -20($sp)
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
datalabel0: .asciiz  "This program prints 7 7"
