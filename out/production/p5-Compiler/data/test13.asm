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
li $t0 -40
add $t0 $t0 $sp
li $t1 0
li $t2 4
mul $t1 $t1 $t2
add $t0 $t0 $t1
li $t1 0
sw $t1 0($t0)
li $t1 -40
add $t1 $t1 $sp
li $t2 0
li $t3 4
mul $t2 $t2 $t3
add $t1 $t1 $t2
lw $t0 0($t1)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
li $t0 -40
add $t0 $t0 $sp
li $t1 2
li $t2 4
mul $t1 $t1 $t2
add $t0 $t0 $t1
li $t1 2
sw $t1 0($t0)
li $t1 -40
add $t1 $t1 $sp
li $t2 2
li $t3 4
mul $t2 $t2 $t3
add $t1 $t1 $t2
lw $t0 0($t1)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
li $t0 -44
add $t0 $t0 $sp
li $t1 2
sw $t1 0($t0)
li $t0 -40
add $t0 $t0 $sp
li $t2 -44
add $t2 $t2 $sp
lw $t1 0($t2)
li $t2 4
mul $t1 $t1 $t2
add $t0 $t0 $t1
li $t2 -44
add $t2 $t2 $sp
lw $t1 0($t2)
sw $t1 0($t0)
li $t1 -40
add $t1 $t1 $sp
li $t3 -44
add $t3 $t3 $sp
lw $t2 0($t3)
li $t3 4
mul $t1 $t1 $t2
add $t0 $t0 $t1
lw $t0 0($t1)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
li $t0 -44
add $t0 $t0 $sp
li $t1 0
sw $t1 0($t0)
datalabel1:
li $t1 -44
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 10
slt $t0 $t0 $t1
subi $t0 $t0 1
bne $t0 $zero datalabel2
addi $sp $sp -44
li $t0 4
add $t0 $t0 $sp
li $t2 0
add $t2 $t2 $sp
lw $t1 0($t2)
li $t2 4
mul $t1 $t1 $t2
add $t0 $t0 $t1
li $t2 0
add $t2 $t2 $sp
lw $t1 0($t2)
sw $t1 0($t0)
li $t0 0
add $t0 $t0 $sp
li $t2 0
add $t2 $t2 $sp
lw $t1 0($t2)
li $t2 1
add $t1 $t1 $t2
sw $t1 0($t0)
addi $sp $sp 44
j datalabel1
datalabel2:
li $t1 -40
add $t1 $t1 $sp
li $t2 3
li $t3 4
mul $t2 $t2 $t3
add $t1 $t1 $t2
lw $t0 0($t1)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
li $t1 -40
add $t1 $t1 $sp
li $t2 6
li $t3 4
mul $t2 $t2 $t3
add $t1 $t1 $t2
lw $t0 0($t1)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall
li $t1 -40
add $t1 $t1 $sp
li $t2 6
li $t3 4
mul $t2 $t2 $t3
add $t1 $t1 $t2
lw $t0 0($t1)
li $t1 6
mult $t0 $t1
mflo $t0
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
datalabel0: .asciiz  "This should print 0, 2, 2, 3, 6 and 36"
