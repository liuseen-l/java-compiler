package sample.compiler.core.lexer.strategy;


import sample.compiler.core.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : ResolveStrategy
 * @date : 2022/3/13 20:33
 * @description : 不同状态单词的解析策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public abstract class ResolveStrategy {

    // 解析方法
    public abstract Object resolveWord(int status, LexicalAnalyzer analyzer);




}
