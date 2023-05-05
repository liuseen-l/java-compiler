package sample.compiler.core.code.client;

import sample.compiler.core.code.analyser.Analyser;
import sample.compiler.core.code.analyser.Generator;
import sample.compiler.core.code.interpreter.Interpreter;
import sample.compiler.core.code.parser.Parser;
import sample.compiler.core.code.tokenizer.Tokenizer;
import sample.compiler.core.run.NewInterpreter;
import sample.compiler.core.target.TargetCodeGenerator;

import java.io.File;

public class ProxyCompileClient {
    // 编译
    private Tokenizer tokenizer;
    private Parser parser;
    private Analyser analyser;
    private Generator generator;
    private NewInterpreter interpreter;
    private TargetCodeGenerator targetCodeGenerator = new TargetCodeGenerator();

    public ProxyCompileClient(Tokenizer tokenizer, Parser parser, Analyser analyser, Generator generator, NewInterpreter interpreter) {
        this.tokenizer = tokenizer;
        this.parser = parser;
        this.analyser = analyser;
        this.generator = generator;
        this.interpreter = interpreter;
    }

    public ProxyCompileClient() {
        tokenizer = new Tokenizer();
        // 加载语法文件
        parser = new Parser(new File("src/resource/grammar_new.txt"));
        analyser = new Analyser();
        generator = new Generator();
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public Analyser getAnalyser() {
        return analyser;
    }

    public void setAnalyser(Analyser analyser) {
        this.analyser = analyser;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public NewInterpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(NewInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public TargetCodeGenerator getTargetCodeGenerator() {
        return targetCodeGenerator;
    }

    public void setTargetCodeGenerator(TargetCodeGenerator targetCodeGenerator) {
        this.targetCodeGenerator = targetCodeGenerator;
    }
}
