import java.util.ArrayList;
import java.util.List;



public class ExpressionCalculator {
String expression = null;

	public ExpressionCalculator(String expression) {
		this.expression = expression;
	}

	public static void main(String[] args) {
		ExpressionCalculator ep = new ExpressionCalculator(args[0]);
		double result = ep.evalExpr(ep.expression);
		System.out.println(result);
	}
	
	
	//Returns substring containing paren set dictated by offSet
	//use length of returned substring to find next offSet
	public String getParen(String expr)
	{
		int offSet = 0;
		int endOffset = 1;
		//until endOffset overflows
		while(endOffset <= this.expression.length())
		{
			if ((this.expression.indexOf('(', endOffset) != -1) && (this.expression.indexOf('(', endOffset))<(this.expression.indexOf(')', endOffset)))
			{
				endOffset = this.expression.indexOf(')', endOffset)+1;
				System.out.println(endOffset);
			}
			else
			{
				return expression.substring(offSet, this.expression.indexOf(")", endOffset)+1);
			}
		}
		return "None found";	
	}
	
	public List<String> mySplit(String expr, char splitOn)
	{
		//Implement own split function-------
		List<String> toAdd = new ArrayList<String>();
		int offSet = 0;
		int oldOffSet = -1;
		int leftCount = 0;
		int rightCount = 0;
		int j = 0;
		boolean added = false;
		int osAdd = 0;
		boolean unaryFlag = false;
				
		for (int i = 0; i < expr.length(); i++)
		{
			if (expr.charAt(offSet) == '(')
			{
				rightCount = 0;
				leftCount = 1;
				while (leftCount != rightCount)
				{
					offSet++;
					i++;
					if(expr.charAt(offSet) == '(')
					{
						leftCount++;
					}
					if(expr.charAt(offSet) == ')')
					{
						System.out.println("Found right, offS is " + offSet);
						rightCount++;
					}
				}
			}
			if ((expr.charAt(offSet)==splitOn))
			{
				added = true;
				
				//handle unary neg
				if ((expr.substring(oldOffSet+1, offSet).length()==0) && (splitOn == '-'))
				{
					System.out.println("Handling unary neg");
					toAdd.add("0");
					osAdd = 0;
					
				}
				else if (((expr.charAt(offSet-1) == '/') || (expr.charAt(offSet-1) == '*'))&&(splitOn=='-'))
				{
					System.out.println("Ignoring neg with preceeding mult/div");
					//toAdd.add("0");
					osAdd = -1;
					added=false;
					//Do nothing, number will be negated when parsed to double
				}
				else
				{
					toAdd.add(expr.substring(oldOffSet+1, offSet+osAdd));
					osAdd = 0;
				}
				
				System.out.println("old is: "+ oldOffSet+" offset is:" + offSet);
				System.out.println("putting " + expr.substring(oldOffSet+1, offSet+osAdd));
				System.out.println(expr.substring(oldOffSet+1, offSet).length());
				
				oldOffSet = offSet;
			}
			offSet++;
		}
				
		if (added)
		{
			
			//handle unary neg
			if ((expr.substring(oldOffSet+1, offSet).length()==0) && (splitOn == '-'))
			{
				System.out.println("Handling unary neg");
				toAdd.add("0");
				osAdd = 0;
				
			}
			else if ((expr.charAt(offSet-1) == '/') || (expr.charAt(offSet-1) == '*'))
			{
				System.out.println("Ignoring neg with preceeding mult/div");
				osAdd = -1;
				//Do nothing, number will be negated when parsed to double
			}
			else
			{
				toAdd.add(expr.substring(oldOffSet+1, offSet+osAdd));
				osAdd = 0;
			}
			System.out.println("In last iter");
			System.out.println("old is: "+ oldOffSet);
			System.out.println("putting " + expr.substring(oldOffSet+1, expr.length()+osAdd));
		}
		return toAdd;
		//-----------------------------
	}
	
	public double evalExpr(String expr)
	{
		
		//-----------------------Addition
		//String[] toAdd = expr.split("\\+(?![^(]*\\))");
		//String[] toAdd = expr.split("\\+");
		
		List<String> toAdd = mySplit(expr, '+');
		
		double totalSum = 0;
		if (toAdd.size() > 1)
		{
			for(int i =0; i < toAdd.size(); i++)
			{
				System.out.println("Adding string "+ (String) toAdd.get(i));
				System.out.println("Adding"+evalExpr((String) toAdd.get(i)));
				totalSum += evalExpr((String) toAdd.get(i));
			}
			return totalSum;
		}
		//----------------------------------
		
		//Subtract---------------------------
		//String[] toSubtr = expr.split("[-]");
		//String[] toSubtr = expr.split("\\-(?![^(]*\\))");
		
		List<String> toSubtr = mySplit(expr, '-');
		totalSum = 0;
		if (toSubtr.size() > 1)
		{
			for(int i =0; i < toSubtr.size(); i++)
			{
				System.out.println(evalExpr(toSubtr.get(i)));
				if ((i & 1) == 0)
				{
					totalSum+= evalExpr(toSubtr.get(i));
				}
				else
				{
					totalSum -= evalExpr(toSubtr.get(i));
				}
			}
			return totalSum;
		}
		//--------------------------------------
		
		//Exponent---------------------------------
		//String[] toMult = expr.split("\\*(?![^(]*\\))");
		/*
		List<String> toExp = mySplit(expr, '^');
		if (toExp.size() > 1)
		{
			double retVal = evalExpr(toExp.get(0));
			//double retVal = 1;
			for(int i =1; i < toExp.size(); i++)
			{
				//retVal *= Double.parseDouble(toAdd[i]);
				System.out.println(toExp.get(i));
				retVal = Math.pow(retVal, evalExpr(toExp.get(i)));
			}
			return retVal;
		}
		*/
		//-----------------------------------------
		
			try
			{
				System.out.println(expr);
				if (expr.indexOf("^")!= -1)
				{
					throw new NumberFormatException("Found exp");
				}
				else
				{
					double val = Double.parseDouble(expr);
					System.out.println("Parsed "+val);
					return val;
				}
			}
			catch (NumberFormatException e)
			{
				
				//Exponent---------------------------------
				//String[] toMult = expr.split("\\*(?![^(]*\\))");
				/*
				List<String> toExp = mySplit(expr, '^');
				if (toExp.size() > 1)
				{
					double retVal = evalExpr(toExp.get(0));
					//double retVal = 1;
					for(int i =1; i < toExp.size(); i++)
					{
						//retVal *= Double.parseDouble(toAdd[i]);
						System.out.println(toExp.get(i));
						retVal = Math.pow(retVal, evalExpr(toExp.get(i)));
					}
					return retVal;
				}
				*/
				//-----------------------------------------
				
				//Multiply---------------------------------
				//String[] toMult = expr.split("\\*(?![^(]*\\))");
				
				List<String> toMult = mySplit(expr, '*');
				if (toMult.size() > 1)
				{
					double retVal = 1;
					for(int i =0; i < toMult.size(); i++)
					{
						//retVal *= Double.parseDouble(toAdd[i]);
						retVal *= evalExpr(toMult.get(i));
					}
					return retVal;
				}
				//-----------------------------------------
				
				//Divide---------------------------------
				//String[] toDiv = expr.split("\\/(?![^(]*\\))");
				//String[] toDiv = expr.split("[^(]*/[^)]*");
				
				List<String> toDiv = mySplit(expr, '/');
				if (toDiv.size() > 1)
				{
					double retVal = 1;
					for(int i =0; i < toDiv.size(); i++)
					{
						//retVal *= Double.parseDouble(toAdd[i]);
						//retVal /= evalExpr(toDiv[i]);
						
						if (i== 0)
						{
							retVal = evalExpr(toDiv.get(i));
						}
						else
						{
							retVal /= evalExpr(toDiv.get(i));
						}
					}
					return retVal;
				}
				//-----------------------------------------
				
				//Exponent---------------------------------
				//String[] toMult = expr.split("\\*(?![^(]*\\))");
				
				List<String> toExp = mySplit(expr, '^');
				if (toExp.size() > 1)
				{
					double retVal = evalExpr(toExp.get(0));
					//double retVal = 1;
					for(int i =1; i < toExp.size(); i++)
					{
						//retVal *= Double.parseDouble(toAdd[i]);
						System.out.println(toExp.get(i));
						retVal = Math.pow(retVal, evalExpr(toExp.get(i)));
					}
					return retVal;
				}
				
				//-----------------------------------------
				
				//Parentheses----------------------------
				if (expr.indexOf("(") == 0)
				{
					System.out.println("Detected Parens");
					return evalExpr(expr.substring(1, expr.length()-1));
				}
				//---------------------------------------
			}
		return -666;
	}
}
