import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.regex.Pattern;

public class Problem {
	private int level;
	private int correct;
	private Random r = new Random();
	private String[] operators = {"+","-","*","/"};
	private String[] var = {"x","x^2","x^3"};
	private int xValue = r.nextInt(5) * 1;
	private double value = (r.nextDouble() * .06);
	private String deciValue = String.format("%." + 2 + "f", value);
	private int probType;
	private int count;
	//Type 1 : Fractions
	//Type 2 : Decimals
	//Type 3 : Polynomials

	public Problem(){
		level = 0;
		correct = 0;
		probType = 0;
	}
	public int num(){
		//decides the numbers for the variables
		if(probType == 1 || probType == 2)
			return (r.nextInt(10-1)+1);
		else
			return (r.nextInt(6-1)+1) ;
	}
	public String deciNum(){
		//decides the numbers for the variables
		value = (r.nextDouble() * (1-0) + 0);
		count = r.nextInt(3) + 1;
		deciValue = String.format("%." + count + "f", value);
		return deciValue;
	}
	public void setProb(int type){
		probType = type;
	}
	public String oper(){
		//pick a random operator
		if(level == 0)
			return operators[0];
		if(level == 1)
			return operators[r.nextInt(1-0+1)];
		if(level == 2)
			return operators[r.nextInt(2-0+1)];
		else
			return operators[r.nextInt(3-0+1)];
	}
	public String level(int n){

		//Type 1 Fractions
		if(probType == 1){
				return num() +"/"+ num() +" " + oper() +" "+ num() +"/"+ num() +  oper() +" "+ num() +"/"+ num();
		}
		//Type 2 Decimals 
		else if(probType == 2){
				return deciNum() +" " + oper() + " "+ deciNum()  + oper() + " "+ deciNum();
		}
		//Type 3 Polynomials 
		else{
				return num()+ (var[new Random().nextInt(var.length)]) +" "+ oper() +" "+ num() + (var[new Random().nextInt(var.length)]) + oper() +" "+ num();
		}
	}

	public String prob(int n){
		//generates the problem
		String p = level(n);
		return p + " where x = " + xValue;

	}
	public void setLevel(int lvl){
		level = lvl;
	}
	public String poly(String prob){

		if(prob.indexOf('*')>= 0)
			prob = prob.replace('*', '+');
		else if(prob.indexOf('+')>= 0)
			prob = prob.replace('+', '*');
		else if(prob.indexOf('-')>= 0)
			prob = prob.replace('-', '+');
		return prob;
	}
	public String fraction(int x, int x1, int y, int y1, String opera, String ep){
		//THIS IS TEST METHOD SO YOU HAVE TO MOVE BACK TO MAIN CLASS 
		//NOTE THE VARIABLES HAVE TO BE PUBLIC AFTER BEING MOVED

		//expressions for the faulty logic getting stored two different instances 
		//exp is for the expression 
		//exp1 is evaluated expression
		String exp, exp1 = null;
		int faultLogic1, faultLogic2, fault;
		if(opera.equals("+") || opera.equals("-")){
			//addition/subtraction logic fault
			System.out.println("Original Expression: "+ x +"/" + x1 +" + " + y +"/" + y1);
			exp = "(" +x + " "+opera+" "+ y + ") / " + "(" +x1 + " "+opera+" " + y1 + ")";
			System.out.println("Logic Fault (Addition): "+exp);

			//Logic being carried out 
			if(opera.equals("+")){
				faultLogic1 = x+y;
				faultLogic2 = x1+y1;
			}
			else{
				faultLogic1 = x-y;
				faultLogic2 = x1-y1;
			}	
			exp1 = faultLogic1 + " / " + faultLogic2;
		}
		/*if (opera.equals("+") || opera.equals("-")){
			//addition/subtraction logic fault
			exp = "(" +x + " "+opera+" "+ y + ") / " + "(" +x1 + " * " + y1 + ")";
			//Logic being carried out 
			if(opera.equals("+")){
				faultLogic1 = x+y;
				faultLogic2 = x1*y1;
			}
			else{
				faultLogic1 = x-y;
				faultLogic2 = x1*y1;
			}	
			exp1 = faultLogic1 + " / " + faultLogic2;
		}
		 */
		if(opera.equals("*") || opera.equals("/")){
			System.out.println("Original Expression: "+ x +"/" + x1 +" "+opera +" " + y +"/" + y1);
			fault = x1*y1;

			//Logic being carried out 
			if(opera.equals("*")){
				exp = "(" +x + " "+opera+" "+ y + ") / " + "(" +x1 + " "+opera+" " + y1 + ")";
				System.out.println("Logic Fault (Multiplication): "+exp);
				faultLogic1 = x*y1;
				faultLogic2 = x1*y;
			}
			else{
				exp = "(" +x + " * "+ y + ") / " + "(" +x1 + " * " + y1 + ")";
				System.out.println("Logic Fault (Division): "+exp);
				faultLogic1 = x*y;
				faultLogic2 = x1*y1;
			}	
			exp1 = faultLogic1 + " / " + faultLogic2;
		}
		return exp1 + ep;
	}
	public String decimal(BigDecimal x, BigDecimal y, String opera){
		//THIS IS TEST METHOD SO YOU HAVE TO MOVE BACK TO MAIN CLASS 
		//NOTE THE VARIABLES HAVE TO BE PUBLIC AFTER BEING MOVED

		//expressions for the faulty logic getting stored two different instances 
		//exp is for the expression 
		//exp1 is evaluated expression


		String exp, exp1 = null, deci, deci1;
		BigDecimal faultLogic1;
		BigDecimal fault = new BigDecimal(".1");
		int deciIndex1, deciIndex2;
		deci = x.toString();
		deci1 = y.toString();
		deciIndex1 = getNumberOfDecimalPlaces(x);
		deciIndex2 = getNumberOfDecimalPlaces(y);
		if(opera.equals("+") || opera.equals("-")){
			//addition/subtraction logic fault
			exp =  deci + " "+opera+" "+ deci1 ;
			System.out.println("Original Expression: " +exp);

			//Logic being carried out 
			if(opera.equals("+")){
				if(deciIndex1 < deciIndex2)
					x = x.multiply(fault);
				else
					y = y.multiply(fault);
				faultLogic1 = x.add(y);
			}
			else{
				if(deciIndex1 < deciIndex2)
					faultLogic1 = y.subtract(x);
				else

					faultLogic1 = x.subtract(y);
			}	
			exp1 = faultLogic1 + "";
		}

		if(opera.equals("*") || opera.equals("/")){
			exp =  deci + " "+opera+" "+ deci1 ;
			System.out.println("Original Expression: " + exp);

			//Logic for multiplication / Division
			if(opera.equals("*")){
				faultLogic1 = x.multiply(y);
				faultLogic1 = faultLogic1.movePointRight(1);
				/*if(deciIndex1 < deciIndex2)
					x.movePointRight(1);
				else
					y.movePointLeft(1);
				 */
			}
			else{
				faultLogic1 = x.divide(y, 2, RoundingMode.HALF_UP);
				faultLogic1 = faultLogic1.movePointLeft(1);
			}	
			exp1 = faultLogic1 + "";
		}
		return exp1;
	}

	public int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
		String string = bigDecimal.stripTrailingZeros().toPlainString();
		int index = string.indexOf(".");
		return index < 0 ? 0 : string.length() - index - 1;
	}


	public String expRecognition(String exp){
		//Goal use regular expressions to differentiate the expressions into the proper type and send to methods
		//Pattern p = Pattern.compile("(\d+(?! */))? *-? *(?:(\d+) */ *(\d+))?.*$");
		//matches but doesn't differentiate the steps
		System.out.println(exp);
		int p1, p2, p3, p4;
		BigDecimal p5, p6;
		String op;
		if(probType == 1 && exp.length()<12){
			p1 =  Character.getNumericValue(exp.charAt(0));
			p2 =  Character.getNumericValue(exp.charAt(2));
			p3 =  Character.getNumericValue(exp.charAt(4));
			p4 =  Character.getNumericValue(exp.charAt(6));
			op = Character.toString(exp.charAt(3));
			/*if(p2 == p4){
				op = Character.toString(exp.charAt(7));
				p1 = Character.getNumericValue(exp.charAt(8));
				p2 =  Character.getNumericValue(exp.charAt(10));
			}*/
			System.out.println("p1 is "+ p1 + "p2 is "+ p2 +"p3 is "+ p3 +"p4 is "+ p4);
			return fraction(p1,p2,p3,p4, op, exp.substring(7));
		}
		if(probType == 2){
			p1 =  Character.getNumericValue(exp.charAt(0));
			p2 =  Character.getNumericValue(exp.charAt(2));
			p3 =  Character.getNumericValue(exp.charAt(4));
			p4 =  Character.getNumericValue(exp.charAt(6));
			op = Character.toString(exp.charAt(3));
			return fraction(p1,p2,p3,p4, op, exp.substring(7));
		}
		if(probType == 3){
			p1 =  Character.getNumericValue(exp.charAt(0));
			p2 =  Character.getNumericValue(exp.charAt(2));
			p3 =  Character.getNumericValue(exp.charAt(4));
			p4 =  Character.getNumericValue(exp.charAt(6));
			op = Character.toString(exp.charAt(3));
			return fraction(p1,p2,p3,p4, op, exp.substring(7));
		}
		
			

		return "Invalid";
	}
	public static void main(String[] args) {
		Problem p = new Problem();
		//p.setProb(2);
		String x = p.fraction(1, 2, 3, 4, "+", "+3");
		System.out.println("Faulty Logic "+ x +"\n");
		String x1 = p.fraction(1, 2, 3, 4, "*", "+3");
		System.out.println("Faulty Logic "+ x1 +"\n");
		String x2 = p.fraction(1, 2, 3, 4, "/", "+3");
		System.out.println("Faulty Logic "+ x2 +"\n");
		BigDecimal u = new BigDecimal("0.3");
		BigDecimal u1 = new BigDecimal("0.005");
		String y = p.decimal(u, u1, "*");
		System.out.println("Faulty Logic (Multiplication): "+ y+"\n");
		String y3 = p.decimal(u, u1, "/");
		System.out.println("Faulty Logic (Division): "+ y3+"\n");
		String y1 = p.decimal(u, u1, "+");
		System.out.println("Faulty Logic (Addition) "+ y1+"\n");
		String y2 = p.decimal(u, u1, "-");
		System.out.println("Faulty Logic (Subtraction) "+ y2+"\n");
		
		String x5 = "5*1^2+1+5";
		
		p.level = 3;
		String t = p.oper();
		System.out.println("");
		System.out.println(t);
//		p.setProb(1);
//		String x = p.level(2);
//		System.out.println(x);

	}

}



