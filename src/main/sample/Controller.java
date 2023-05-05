package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sample.compiler.core.code.analyser.Analyser;
import sample.compiler.core.code.analyser.FourItem;
import sample.compiler.core.code.analyser.FuncInfo;
import sample.compiler.core.code.analyser.Generator;
import sample.compiler.core.code.client.ProxyCompileClient;
import sample.compiler.core.code.parser.Parser;
import sample.compiler.core.code.tokenizer.Token;
import sample.compiler.core.code.tokenizer.Tokenizer;
import sample.compiler.core.run.NewInterpreter;
import sample.compiler.core.target.TargetCodeGenerator;
import sample.compiler.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane borderPane ;

    // 左侧数据
    @FXML
    private TextArea metaData ;

    @FXML
    private TextArea sourceEditArea ;

    @FXML
    private TextArea resultArea;

    @FXML
    private TextArea codeArea;

    @FXML
    private MenuItem OpenFileButton ;

    private File sourceFile ;

    private ProxyCompileClient compileClient = new ProxyCompileClient();

    public void allEventRegister(){
        // 打开源文件
        this.OpenFileButton.setOnAction((event)->{
            // 选择源文件
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("src/resource"));
            fileChooser.setTitle("Open Source File");

            // 过滤器
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("文本文件","*.txt"),
                    new FileChooser.ExtensionFilter("C++源程序", "*.cpp"),
                    new FileChooser.ExtensionFilter("C源程序", "*.c"),
                    new FileChooser.ExtensionFilter("Java源程序", "*.java")

            );

            // 打开的文件file
            this.sourceFile = fileChooser.showOpenDialog(new ContextMenu());

            if (this.sourceFile != null) {
                // 交给本地 代码编辑区域
                this.sourceEditAreaFill(this.sourceFile);
            }
        });
    }

    // 代码编辑区域内容填充
    private void sourceEditAreaFill(File sourceFile) {
        // 获取文件内容
        String sourceFileContent = FileUtil.getFileContent(sourceFile);
        // 填充
        this.sourceEditArea.setText(sourceFileContent);
    }

    // 词法分析
    public void lexAnalyze(){
        // 获取源代码
        String text = this.sourceEditArea.getText();
        String[] lines = text.split("\\r?\\n");
        // do 词法分析结果
        //CompilerClient.doLexicalAnalysis(lines);
        System.out.println(this.sourceEditArea.getText());
        // 首先通过getTokenizer()获取token的存储器，然后再调用analysis去解析当前ui传入的代码
        compileClient.getTokenizer().analysis(this.sourceEditArea.getText());
        // 分析完拿到tokens
        List<Token> tokens = compileClient.getTokenizer().getTokens();
        for (int i = 0; i < tokens.size() - 1; i++) {
            // 输出token
            Token token = tokens.get(i);
            this.resultArea.appendText(token.getValue()+"\t\t"+token.getType().print()+"\n");
        }
    }

    // 语法分析
    public void syntaxAnalyze(){
        // 语法分析
        // 词法分析阶段检查
        // 获取token
        Tokenizer tokenizer = this.compileClient.getTokenizer();
        if (tokenizer.getTokens() == null || tokenizer.getTokens().size() == 1) {
            resultArea.setText("未输入字符或未进行词法分析\n");
            return;
        } else if (resultArea.getText().isEmpty() && resultArea.getText().contains("无语法错误")) {
            resultArea.setText("存在错误 无法进行语法分析\n");
            return;
        }
        // 进行语法分析
        // 获取parser实例，调用实例上的parse方法解析token
        this.compileClient.getParser().parse(tokenizer);
        List<Error> errors = this.compileClient.getParser().getErrors();
        for (Error error : errors) {
            resultArea.appendText(error + "\n");
        }
        if (errors.size() == 0) {
            resultArea.appendText("语法检查完成 无语法错误\n");
            Parser parser = this.compileClient.getParser();
            parser.dump(0,this.resultArea);
        }
    }

    // 中间代码生成
    public void itermidiateCode(){
        // 语义分析和中间代码生成

        // 环境准备
        this.codeArea.setText("");
        Parser parser = this.compileClient.getParser();

        // 错误检查
        if (parser.getRoot() == null) {
            resultArea.setText("未进行语法分析\n");
            return;
        }

        String text = resultArea.getText();
        if (text.isEmpty() || !text.contains("无语法错误")) {
            resultArea.setText("存在错误 无法进行语义分析\n");
            return;
        }

        // 开始语义分析
        Analyser analyser = this.compileClient.getAnalyser();
        analyser.analyse(parser.getRoot());

        // 错误处理
        List<Error> errors = analyser.getErrors();
        for (Error error : errors) {
            codeArea.appendText(error + "\n");
        }
        if (errors.size() == 0) {
            System.out.println("语法分析没有错误，开始进行语义分析");
            Generator generator = this.compileClient.getGenerator();

            // 设置 开始函数
            System.out.println("函数表");
            Map<String, FuncInfo> funcsInfoMap = analyser.getFuncsInfoMap();
            for (String s : funcsInfoMap.keySet()) {
                FuncInfo funcInfo = funcsInfoMap.get(s);

                String data = s + "\t" + "\n"+ "\t"+funcInfo.toString();
                System.out.println(data);
                this.metaData.appendText(data+"\n");
            }

            // 根据函数表和语法分析树生成四元式
            generator.generate(analyser.getFuncsInfoMap(), analyser.getRoot());
            List<FourItem> fourItems = generator.getFourItemList();
            for (int i = 0; i < fourItems.size(); i++) {
                FourItem item = fourItems.get(i);
                this.codeArea.appendText(String.format("%-3d:", i + 1));
                this.codeArea.appendText(item + "\n");
            }

        }

    }


    // 解释执行
    public void interpreterRun(){
        System.out.println("------------开始解释执行------------");
        this.resultArea.clear();
        // 初始化解释器
        this.compileClient.setInterpreter(new NewInterpreter(this.resultArea));
        // 拿到解释器
        NewInterpreter interpreter = this.compileClient.getInterpreter();

        // 开始解释执行
        interpreter.interpreter(this.compileClient.getAnalyser().getFuncsInfoMap(),
                this.compileClient.getGenerator().getFourItemList());

    }

    // LR 分析
    public void lrSyntax(){

    }

    // 编译运行
    public void compilerRun() throws IOException {

        this.codeArea.clear();
        TargetCodeGenerator generator = this.compileClient.getTargetCodeGenerator();
        generator.setFourItemList(this.compileClient.getGenerator().getFourItemList());
        String targetCode = generator.generateTargetCode();
        this.codeArea.setText(targetCode);
    }

    // UI 程序初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 注册全局事件
        this.allEventRegister();

        // 默认填充
        File file = new File("src/resource/test/前10组测试用例及其各阶段的结果样例/test9.txt");
        this.sourceEditAreaFill(file);

    }
}
