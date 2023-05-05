assume cs:code,ds:data,ss:stack,es:extended      ;程序开始，初始化4个段地址
extended segment                           ;初始化扩展段中的信息
    db 1024 dup (0)
extended ends
stack segment                              ;初始化堆栈段中的信息
    db 1024 dup (0)
stack ends
data segment                               ;初始化数据段中的信息
    t_buff_p db 256 dup (24h)
    t_buff_s db 256 dup (0)
data ends
code segment                              ;代码段开始
start: mov ax,extended:                   ;定义程序入口
    mov es,ax                             ;接下来的mov指令为各个段寄存器赋值
    mov ax,stack
    mov ss,ax
    mov sp,1024
    mov bp,sp
    mov ax,data
    mov ds,ax