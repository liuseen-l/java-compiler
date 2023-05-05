quit: mov ah,4ch
    INT 21H                               ; 程序返回操作系统控制，恢复寄存器等
code ends                                 ;代码段结束
end start                                 ;程序执行结束，对应于程序入口start