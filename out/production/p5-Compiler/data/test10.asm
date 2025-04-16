# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main

fib:
addi $sp $sp 0
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 0
sub $t0 $t0 $t1
bne $t0 $zero datalabel0
li $t2 1
sw $t2 -4($sp)
jr $ra
j datalabel1
datalabel0:
datalabel1:
li $t1 -4
add $t1 $t1 $sp
lw $t0 0($t1)
li $t1 1
sub $t0 $t0 $t1
bne $t0 $zero datalabel2
li $t2 1
sw $t2 -4($sp)
jr $ra
j datalabel3
datalabel2:
datalabel3:
add   

# All memory structures are placed after the
# .data assembler directive
.data

