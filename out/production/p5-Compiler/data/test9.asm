# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

main:
addi $sp $sp 0
li $t0 -4
add $t0 $t0 $sp
li $t1 3
sw $t1 0($t0)
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 4
slt $t0 $t0 $t1
subi $t0 $t0 1
bne $t0 $zero datalabel1li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 4
slt $t0 $t1 $t0
subi $t0 $t0 1
bne $t0 $zero datalabel2li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 4
slt $t0 $t1 $t0
subi $t0 $t0 1
bne $t0 $zero datalabel3li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 3
slt $t0 $t1 $t0
bne $t0 $zero datalabel4li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 3
sub $t0 $t0 $t1
bne $t0 $zero datalabel5li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 4
slt $t0 $t0 $t1
bne $t0 $zero datalabel6addi $sp $sp 0
li $v0 10
syscall

# All memory structures are placed after the
# .data assembler directive
.data

newline: .asciiz "\n"
datalabel0: .asciiz  "This program prints [1..5] correct."
