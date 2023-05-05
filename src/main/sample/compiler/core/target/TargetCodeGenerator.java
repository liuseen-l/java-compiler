package sample.compiler.core.target;

import sample.compiler.core.code.analyser.FourItem;
import sample.compiler.core.code.analyser.FuncInfo;
import sample.compiler.core.target.component.Register;
import sample.compiler.core.target.component.TargetCode;
import sample.compiler.core.target.component.TargetInstruction;
import sample.compiler.util.FileReaderUtil;
import sun.net.RegisteredDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : TargetCodeGenerator
 * @date : 2022/5/21 13:49
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TargetCodeGenerator {

    private static String testFourItemData =
            "=, 1, , a\n" +
            "call, read, , T1\n" +
            "=, T1, , N\n" +
            "call, read, , T2\n" +
            "=, T2, , M\n" +
            ">=, M, N, T3\n" +
            "jnz, T3, , 12\n" +
            "jz, , , 10\n" +
            "=, N, , result\n" +
            "j, , , 13\n" +
            "=, M, , result\n" +
            "+, result, 100, T6\n" +
            "=, T6, , a\n" +
            "call, write, [a], T8\n" +
            "ret, , , 0\n";


    // 四元式
    private FourItem currentfourItem ;

    private List<FourItem> fourItemList = new ArrayList<>();

    // 函数表
    private Map<String, FuncInfo> funcsInfoMap ;

    // 目标代码列表
    private List<TargetCode> targetCodeList = new ArrayList<>();


    // 目标代码生成
    public String  generateTargetCode() throws IOException {

        for (FourItem fourItem : this.fourItemList) {
            System.out.println(fourItem);
        }

        // 中间代码生成目标代码
        this.interCodeToTargetCode();
        // 目标代码拼接转换为asm 文件
        String asmFile = this.createAsmFile();


        return asmFile;
    }


    // 输出 ASM 文件
    private String createAsmFile() throws IOException {

        // 最后的目标代码
        StringBuilder targetCode = new StringBuilder();

        // 读取文件
        List<String> mainASM = FileReaderUtil.readFileContentByLine(new FileInputStream("src/resource/asm/main.asm"));
        List<String> sysASM = FileReaderUtil.readFileContentByLine(new FileInputStream("src/resource/asm/sys.asm"));
        mainASM.forEach((line)->{
            System.out.println(line);
            targetCode.append(line).append("\n");
        });
        targetCode.append("\n");

        System.out.println("---------------------------------");
        this.targetCodeList.forEach((line)-> {
            System.out.println(line);
            targetCode.append(line).append("\n");
        });
        targetCode.append("\n");

        System.out.println("---------------------------------");
        sysASM.forEach((line)-> {
            System.out.println(line);
            targetCode.append(line).append("\n");
        });

        return targetCode.toString();
    }


    // 中间代码转换为目标代码
    public void interCodeToTargetCode(){

        for (FourItem fourItem : this.fourItemList) {
            // 获取四元式操作符
            String op = fourItem.op;
            switch (op){
                // 加法目标代码
                case "+":
                    this.plusTargetCode(fourItem);
                    break;

                // 减法目标代码
                case "-":
                    this.subTargetCode(fourItem);
                    break;

                // 乘法目标代码
                case "*":
                    this.mulTargetCode(fourItem);
                    break;

                // 除法目标代码
                case "/":
                    this.divTargetCode(fourItem);
                    break;

                // 取余目标代码
                case "%" :
                    this.modTargetCode(fourItem);
                    break;

                // 小于< 目标代码
                case "<":
                    this.lessTargetCode(fourItem);
                    break;

                // 大于等于>= 目标代码
                case ">=":
                    this.greateAndEqualsTargetCode(fourItem);
                    break;

                // 大于
                case ">":
                    this.greatTargetCode(fourItem);
                    break;

                // 小于等于
                case "<=":
                    this.lessAndEqualsTargetCode(fourItem);
                    break;

                // 等于
                case "==":
                    this.equalsTargetCode(fourItem);
                    break;

                // 不等于
                case "!=":
                    this.notEqualsTargetCode(fourItem);
                    break;

                // 条件与
                case "&&":
                    this.andTargetCode(fourItem);
                    break;

                // 条件或
                case "||":
                    this.orTargetCode(fourItem);
                    break;

                // 条件非
                case "!":
                    this.notTargetCode(fourItem);
                    break;

                // 无条件跳转
                case "j":
                    this.jumpTargetCode(fourItem);
                    break;

                // 条件为0 跳转
                case "jz":
                    this.jumpOnZeroTargetCode(fourItem);
                    break;

                // 条件非 0 跳转
                case "jnz":
                    this.jumpOnNotZeroTargetCode(fourItem);
                    break;

                // 函数调用
                case "call":
                    this.functionCallTargetCode(fourItem);
                    break;

                // 参数传递
                case "para":
                    this.parameterTargetCode(fourItem);
                    break;

                // 函数返回值
                case "ret":
                    this.returnTargetCode(fourItem);
                    break;

                // 函数定义
                case "fun":
                case "fund":
                    this.functionDefineTargetCode(fourItem);
                    break;

                // 系统退出
                case "sys":
                    //System.exit(0);
                    break;

                // 其他四元式
                default:

                    break;
            }
        }
        // 处理完全部四元式，得到对应的目标代码
        //this.targetCodeList.forEach(System.out::println);
        // 拼接头尾得到最后的 asm 文件

    }



    // 函数声明 目标代码
    private void functionDefineTargetCode(FourItem fourItem) {
        this.targetCodeList.add(new TargetCode(TargetInstruction.PUSH.getCmd(), Register.BP,null));
        this.targetCodeList.add(new TargetCode(TargetInstruction.MOV.getCmd(), Register.BP, Register.SP));
        this.targetCodeList.add(new TargetCode(TargetInstruction.SUB.getCmd(), Register.SP,null));

    }

    // 函数返回值 目标代码
    private void returnTargetCode(FourItem fourItem) {

        // 检查是否为 无条件返回，是否有返回值
        if (!TargetCodeGenerator.isBlankString(fourItem.getResult())){
            // 有返回值
            this.targetCodeList.add(
                    new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.getResult().trim()));
        }

        this.targetCodeList.add(new TargetCode(TargetInstruction.MOV.getCmd(), Register.SP,Register.BP));
        this.targetCodeList.add(new TargetCode(TargetInstruction.POP.getCmd(), Register.BP,null));
        this.targetCodeList.add(new TargetCode(TargetInstruction.RET.getCmd(),null,null));

    }


    // 函数参数传递
    private void parameterTargetCode(FourItem fourItem) {

        this.targetCodeList.add(new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.getA()));
        this.targetCodeList.add(new TargetCode(TargetInstruction.PUSH.getCmd(), Register.AX,null));
    }


    // 函数调用 目标代码
    // 需要处理 call 指令，和函数参数入栈 的指令
    private void functionCallTargetCode(FourItem fourItem) {
        // 提取出来函数参数,
        String params = fourItem.getArg2();

        if (!TargetCodeGenerator.isBlankString(params)){
            // 有函数参数,需要提取出来进行处理
            StringBuilder paramsSb = new StringBuilder(params);

            // 去除前后中括号
            paramsSb.deleteCharAt(0);
            paramsSb.deleteCharAt(paramsSb.length()-1);

            // 获取到每一个参数
            String[] paramItems = paramsSb.toString().split(",");

            // 对每一个 参数进行入栈
            for (String item : paramItems) {
                if (!TargetCodeGenerator.isBlankString(item)){
                    // 参数合法
                    this.targetCodeList.add(new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,item.trim()));
                    this.targetCodeList.add(new TargetCode(TargetInstruction.PUSH.getCmd(), Register.AX, null));
                }
            }
        }

        // 生成函数调用
        this.targetCodeList.add(new TargetCode(TargetInstruction.CALL.getCmd(), fourItem.getArg1(), null));

    }

    // 条件非0 跳转
    private void jumpOnNotZeroTargetCode(FourItem fourItem) {
        this.targetCodeList.add(new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.getA()));
        this.targetCodeList.add(new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        TargetCode ez = new TargetCode(TargetInstruction.JE.getCmd(), TargetCode.createTagByName("EZ", fourItem), null);
        this.targetCodeList.add(ez);

        this.targetCodeList.add(new TargetCode(TargetInstruction.JMP.getCmd(), "far ptr "+fourItem.getResult(),null));

        TargetCode targetCode = new TargetCode(TargetInstruction.NOP.getCmd(), null, null);
        targetCode.setTag(ez.getArg1());
        this.targetCodeList.add(targetCode);
    }


    // 条件为0 跳转 代码
    private void jumpOnZeroTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX , fourItem.getA()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 开始跳转，制作标签
        TargetCode neCode = new TargetCode(TargetInstruction.JNE.getCmd(), TargetCode.createTagByName("NE", fourItem), null);
        this.targetCodeList.add(neCode);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.JMP.getCmd(), "far ptr "+fourItem.getResult(), null));

        TargetCode targetCode = new TargetCode(TargetInstruction.NOP.getCmd(), null, null);
        targetCode.setTag(neCode.getArg1());

        this.targetCodeList.add(targetCode);
    }


    // 无条件跳转
    private void jumpTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.JMP.getCmd(), "far ptr "+fourItem.getResult(),null));

    }


    // 条件非
    private void notTargetCode(FourItem fourItem) {

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.getA()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 跳转开始
        TargetCode js = new TargetCode(TargetInstruction.JE.getCmd(), TargetCode.createTagByName("NOT", fourItem), null);
        this.targetCodeList.add(js);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"0"));

        TargetCode targetCode = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.getResult(), Register.DX);
        targetCode.setTag(js.getArg1());
        this.targetCodeList.add(targetCode);
    }


    // 条件或 代码
    private void orTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.getA()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 跳转开始
        TargetCode orStart = new TargetCode(TargetInstruction.JNE.getCmd(), TargetCode.createTagByName("OR", fourItem), null);
        this.targetCodeList.add(orStart);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.getB()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 表达式为真跳转
        TargetCode trueCode = new TargetCode(TargetInstruction.JNE.getCmd(), orStart.getArg1(), null);
        this.targetCodeList.add(trueCode);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"0"));

        // 赋值为 1
        TargetCode targetCode = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.getResult(), Register.DX);
        targetCode.setTag(orStart.getArg1());
        this.targetCodeList.add(targetCode);
    }


    // 条件与 代码
    private void andTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"0"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.getA()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 跳转开始
        TargetCode andStart = new TargetCode(TargetInstruction.JE.getCmd(), TargetCode.createTagByName("AND", fourItem), null);
        this.targetCodeList.add(andStart);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.getB()));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, "0"));

        // 表达式为真跳转
        TargetCode trueCode = new TargetCode(TargetInstruction.JE.getCmd(), andStart.getArg1(), null);
        this.targetCodeList.add(trueCode);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"1"));

        // 赋值为 1
        TargetCode targetCode = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.getResult(), Register.DX);
        targetCode.setTag(andStart.getArg1());
        this.targetCodeList.add(targetCode);
    }

    // 不等于 代码
    private void notEqualsTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JNE.getCmd(), TargetCode.createTagByName("NE", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);
    }

    // 等于 代码
    private void equalsTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JE.getCmd(), TargetCode.createTagByName("EQ", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);
    }


    // 小于等于 代码
    private void lessAndEqualsTargetCode(FourItem fourItem) {

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JNA.getCmd(), TargetCode.createTagByName("LE", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);
    }

    // 大于代码
    private void greatTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JA.getCmd(), TargetCode.createTagByName("GT", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);

    }

    // 大于等于 >=
    private void greateAndEqualsTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(), Register.AX, fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JNB.getCmd(), TargetCode.createTagByName("GE", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);
    }

    // 小于代码
    private void lessTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"1"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX,fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.CMP.getCmd(),Register.AX,fourItem.arg2));
        // 生产带标签的跳转，获取标签
        TargetCode jbt = new TargetCode(TargetInstruction.JB.getCmd(), TargetCode.createTagByName("LT", fourItem), null);
        this.targetCodeList.add(jbt);

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX,"0"));

        // 设置标签
        TargetCode jbTag = new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.DX);
        jbTag.setTag(jbt.getArg1());
        this.targetCodeList.add(jbTag);
    }

    // 取余数 代码
    private void modTargetCode(FourItem fourItem) {

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.DX, "0"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.BX,fourItem.arg2));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.DIV.getCmd(), Register.BX,null));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result,Register.DX));
    }

    // 除法代码
    private void divTargetCode(FourItem fourItem) {

        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.DX, "0"));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.BX, fourItem.arg2));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.DIV.getCmd(),Register.BX, null));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),fourItem.result, Register.AX));
    }

    // 乘法代码
    private void mulTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.BX, fourItem.arg2));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MUL.getCmd(),Register.BX, null));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),fourItem.result, Register.AX));
    }

    // 减法代码
    private void subTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),Register.AX, fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.SUB.getCmd(),Register.AX, fourItem.arg2));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(),fourItem.result, Register.AX));
    }

    // 生产 加法代码
    private void plusTargetCode(FourItem fourItem) {
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), Register.AX ,fourItem.arg1));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.ADD.getCmd(), Register.AX , fourItem.arg2));
        this.targetCodeList.add(
                new TargetCode(TargetInstruction.MOV.getCmd(), fourItem.result, Register.AX));
    }



    // 初始化封装数据
    public void initialize(){

        String[] strings = TargetCodeGenerator.testFourItemData.split("\n");
        for (String string : strings) {
            String[] items = string.split(",");

            // 根据每一个 items 产生四元式
            FourItem fourItem = new FourItem();

            fourItem.setOp(items[0].trim());
            fourItem.setArg1(items[1].trim());
            fourItem.setArg2(items[2].trim());
            fourItem.setResult(items[3].trim());
            this.fourItemList.add(fourItem);
        }


        for (FourItem fourItem : this.fourItemList) {
            System.out.println(fourItem);
        }

    }

    // 判断 string 字符串是否全是空白或 null 或 empty
    public static boolean isBlankString(String str){

        // 判空或没有内容
        if (str == null || str.length() <= 0){
            return true ;
        }

        // 判空百
        if (str.trim().length() <= 0){
            return true ;
        }

        return false ;
    }


    public static String getTestFourItemData() {
        return testFourItemData;
    }

    public static void setTestFourItemData(String testFourItemData) {
        TargetCodeGenerator.testFourItemData = testFourItemData;
    }

    public FourItem getCurrentfourItem() {
        return currentfourItem;
    }

    public void setCurrentfourItem(FourItem currentfourItem) {
        this.currentfourItem = currentfourItem;
    }

    public List<FourItem> getFourItemList() {
        return fourItemList;
    }

    public void setFourItemList(List<FourItem> fourItemList) {
        this.fourItemList = fourItemList;
    }

    public Map<String, FuncInfo> getFuncsInfoMap() {
        return funcsInfoMap;
    }

    public void setFuncsInfoMap(Map<String, FuncInfo> funcsInfoMap) {
        this.funcsInfoMap = funcsInfoMap;
    }

    public List<TargetCode> getTargetCodeList() {
        return targetCodeList;
    }

    public void setTargetCodeList(List<TargetCode> targetCodeList) {
        this.targetCodeList = targetCodeList;
    }

    // 运行测试
    public static void main(String[] args) throws IOException {

        TargetCodeGenerator generator = new TargetCodeGenerator();
        generator.initialize();
        System.out.println("-----------------------------------------");
        generator.generateTargetCode();
    }
}
