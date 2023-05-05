package sample.compiler.core.target.component;

import sample.compiler.core.code.analyser.FourItem;
import sample.compiler.core.target.TargetCodeGenerator;

/**
 * @author : HK意境
 * @ClassName : TargetCode
 * @date : 2022/5/21 15:25
 * @description : 目标代码
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TargetCode {

    // 全局的标签计数器
    public static Integer tagCounter = 0;

    // tag 标签，用于跳转
    private String tag ;
    // 汇编指令
    private String instruction ;
    // 第一操作数
    private String arg1;
    // 第二操作数
    private String arg2 ;

    public TargetCode(String instruction, String arg1, String arg2) {
        this.instruction = instruction;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }


    public TargetCode() {
    }

    // 生产标签
    public static String createTagByName(String isTrue, FourItem item){
        TargetCode.tagCounter++;
        // 设置标志位
        StringBuilder sb = new StringBuilder();
        sb.append(TargetCode.tagCounter).append(":");
        if (TargetCodeGenerator.isBlankString(item.arg1)){
            sb.append("_");
        }else{
            sb.append(item.arg1).append("_");
        }

        if (TargetCodeGenerator.isBlankString(item.arg2)){
            sb.append("_");
        }else{
            sb.append(item.arg2).append("_");
        }

        sb.append(isTrue);

        return sb.toString();
    }


    // 是否保护标签
    public boolean hasTag(){
        return this.tag != null ;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }


    @Override
    public String toString() {

        String text = this.instruction + "  ";

        if (this.arg1 != null){
            text = text + this.arg1;
        }

        if (this.arg2 != null) {
            if (this.arg1 != null) {
                text = text + ", ";
            }
            text = text + this.arg2 ;
        }

        if (this.hasTag()){
            text = this.tag + "\t" + text ;
        }else{
            text = "\t" +text;
        }


        return text ;
    }
}
