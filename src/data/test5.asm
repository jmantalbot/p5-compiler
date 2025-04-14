# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

foo:
addi $sp $sp 0
li $t0 7
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
addi $sp $sp 0
jr $ra

fum:
addi $sp $sp 0
li $t0 -4
add $t0 $t0 $sp
li $t1 9
sw $t1 0($t0)
li $t0 -8
add $t0 $t0 $sp
li $t1 12
sw $t1 0($t0)
li $t1 -8
add $t1 $t1 $sp
lw $t0 0($t1)
li $t2 -4
add $t2 $t2 $sp
lw $t1 0($t2)
sub $t0 $t0 $t1
li $t1 4
add $t0 $t0 $t1
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
move $t0 $ra
sw $t0 -12($sp)
add $sp $sp -12
jal foo
add $sp $sp 12
lw $t0 -12($sp)
move $ra $t0
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
add $sp $sp -4
jal foo
add $sp $sp 4
lw $t0 -4($sp)
move $ra $t0
move $t0 $ra
sw $t0 -4($sp)
add $sp $sp -4
jal fum
add $sp $sp 4
lw $t0 -4($sp)
move $ra $t0
addi $sp $sp 0
li $v0 10
syscall

# All memory structures are placed after the
# .data assembler directive
.data

newline: .asciiz "\n"
datalabel0: .asciiz  "This program prints 7 7 7"
