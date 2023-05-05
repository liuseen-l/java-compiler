package sample.compiler.core.target.component;

/**
 * @author : HK意境
 * @ClassName : FourItemOperator
 * @date : 2022/5/21 16:13
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum FourItemOperator {
    // 运算符
    PLUS("PLUS"),SUB("SUB"),MUL("MUL"),DIV("DIV"),MOD("MOD"),
    // 关系符号


    ;

    private String op ;

    FourItemOperator(String op) {
        this.op = op;
    }

    FourItemOperator() {
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
