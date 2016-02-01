import java.util.Random;

public class Problem {
	private int level;
	private int correct;
	private Random r = new Random();
	private String[] operators = {"+","-"};
	private String[] var = {"x","x^2","x^3"};
	public Problem(){
		level = 0;
		correct = 0;
	}
	public String num(){
		//decides the numbers for the variables
		return ""+(r.nextInt(8-1)+1) + (var[new Random().nextInt(var.length)]);
	}
	public String oper(){
		//pick a random operator
		return ""+operators[new Random().nextInt(operators.length)];
	}
	public String level(int n){
		//recursive iteration to create more values in the problem x + 2x + ... + ... based on the level
		if(n == 0)
			return num() +" "+ oper() +" "+ num();
		else
			return level(n-1) +" "+ oper() +" "+ num();
	}
	public String prob(int n){
		//generates the problem
		String p = level(n);
		return p + " where x =" + r.nextInt(5);
		
	}
	public void setLevel(int lvl){
		level = lvl;
	}
	
	
	
	 public static void main(String[] args) {
		 Problem p = new Problem();
		 System.out.println(p.prob(0));
		 
	 }

}



