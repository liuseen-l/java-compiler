package sample.compiler.core.code.analyser;

// 四元式
public class FourItem {

	public String op;
	public String arg1;
	public String arg2;
	public String result;

	public FourItem(String op, String arg1, String arg2, String result) {
		super();
		this.op = op;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.result = result;
	}

	public FourItem() {
	}

	@Override
	public String toString() {
		return "(" + op + ", " + arg1 + ", " + arg2 + ", " + result + ")";
	}


	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getA(){
		return this.arg1;
	}

	public String getB(){

		return this.arg2;

	}




}
