package sample.compiler.core.target.component;

/**
 * @author : HK意境
 * @ClassName : TargetInstruction
 * @date : 2022/5/21 16:38
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum TargetInstruction {
    MOV("mov"),
    ADD("add"),SUB("sub"),MUL("mul"),DIV("div"),MOD("mod"),
    CMP("cmp"),JB("jb"),JNB("jnb"),JA("ja"),JNA("jna"),JE("je"),JNE("jne"),JMP("jmp"),
    NOP("nop"),
    PUSH("push"),CALL("call"),POP("pop"), RET("ret");


    // 目标指令
    protected String cmd ;


    TargetInstruction() {
    }

    TargetInstruction(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
