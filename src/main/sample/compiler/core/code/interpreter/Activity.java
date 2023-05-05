package sample.compiler.core.code.interpreter;

// 活动记录
public class Activity {

	public String func;	// 函数名
	public int start;		// 变量栈中的变量起始位置
	public int now;		// 当前执行四元式的下标
	public boolean flag;	// 判断子程序是否执行
	
	public Activity(String func, int start, int now) {
		this.func = func;
		this.start = start;
		this.now = now;
	}
}
