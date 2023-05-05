package sample.compiler.core.lexer.lexicalnalyzer;
import sample.compiler.core.lexer.constant.WordDefinition;
import sample.compiler.core.lexer.constant.WordPool;
import sample.compiler.core.lexer.exception.WordAnalyzerException;
import sample.compiler.core.lexer.handle.AnalyzerHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : HK意境
 * @ClassName : LexicalAnalyzer
 * @date : 2022/3/13 12:56
 * @description : 词法分析器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LexicalAnalyzer {

    // 目前处理的行
    private int currentRowIndex  = 1;
    // 目前处理的列
    private int currentColumnIndex ;
    // 当前所在位置
    private int currentCursor ;

    // 所有存储的单词 WordDefinition 节点
    private WordPool wordPool = new WordPool();

    // 源码表
    private String source ;

    // 异常信息表
    private List<WordAnalyzerException> wordAnalyzerExceptionList = new ArrayList<>();


    // 最终信息表
    private String resultText ;
    private String exceptionText ;
    private String wordText ;


    public String getExceptionText() {
        return exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    public void setCurrentRowIndex(int currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }

    public int getCurrentColumnIndex() {
        return currentColumnIndex;
    }

    public void setCurrentColumnIndex(int currentColumnIndex) {
        this.currentColumnIndex = currentColumnIndex;
    }

    public WordPool getWordPool() {
        return wordPool;
    }

    public void setWordPool(WordPool wordPool) {
        this.wordPool = wordPool;
    }

    public int getCurrentCursor() {
        return currentCursor;
    }

    public void setCurrentCursor(int currentCursor) {
        this.currentCursor = currentCursor;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<WordAnalyzerException> getWordAnalyzerExceptionList() {
        return wordAnalyzerExceptionList;
    }

    public void setWordAnalyzerExceptionList(List<WordAnalyzerException> wordAnalyzerExceptionList) {
        this.wordAnalyzerExceptionList = wordAnalyzerExceptionList;
    }

    public void cursorIncr() {

        ++this.currentCursor;

    }


    public StringBuilder postHandler(){

        StringBuilder sb = new StringBuilder();
        for (WordAnalyzerException wordAnalyzerException : this.getWordAnalyzerExceptionList()) {
            sb.append(wordAnalyzerException.toString()).append('\n');
        }
        this.exceptionText = sb.toString();
        StringBuilder wordSb = new StringBuilder();
        for (WordDefinition wordDefinition : this.getWordPool().getWordDefinitionList()) {
            sb.append(wordDefinition.getWord().toString()).append('\n');
            wordSb.append(wordDefinition.getWord().toString()).append('\n');
        }
        this.wordText = wordSb.toString();
        this.resultText = sb.toString();

        return sb;

    }


    // 根据已经传输进入的
    public StringBuilder start(String source){

        AnalyzerHandler.run(new StringBuilder(source),this);
        return postHandler();
    }

    // 清理缓存
    public void clear() {

        this.currentCursor = 0;
        this.currentColumnIndex = 0 ;
        this.currentRowIndex++ ;
        System.out.println(this.wordPool.getWordDefinitionList().size());
    }
}
