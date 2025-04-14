# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

main:
addi $sp $sp 0
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall
li $t0 -4
add $t0 $t0 $sp
li $t1 3
sw $t1 0($t0)
li $t0 -8
add $t0 $t0 $sp
li $t1 2
sw $t1 0($t0)
addi $sp $sp 8
li $t0 -4
add $t0 $t0 $sp
li $t1 5
sw $t1 0($t0)
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t2 -16
add $t2 $t2 $sp
lw $t1 0($t2)
add $t0 $t0 $t1
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
addi $sp $sp -4
li $t0 -4
add $t0 $t0 $sp
li $t1 9
sw $t1 0($t0)
li $t0 0
add $t0 $t0 $sp
li $t2 2
sub $t2 $zero $t2
sw $t2 0($t0)
li $t2 0
add $t2 $t2 $sp
lw $t0 0($t2)
li $t3 -4
add $t3 $t3 $sp
lw $t2 0($t3)
add $t0 $t0 $t2
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
addi $sp $sp 4
li $t0 -16
add $t0 $t0 $sp
li $t1 4
sw $t1 0($t0)
addi $sp $sp -8
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t2 -8
add $t2 $t2 $sp
lw $t1 0($t2)
add $t0 $t0 $t1
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
datalabel0: .asciiz  "This program prints 7 7 7"
