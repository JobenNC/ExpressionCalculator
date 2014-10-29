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
				
		for (int i = 0; i < expr.length(); i++)
		{
			if (expr.charAt(offSet) == '(')
			{
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
						rightCount++;
					}
				}
			}
			if (expr.charAt(offSet)==splitOn)
			{
				added = true;
				System.out.println("old is: "+ oldOffSet+" offset is:" + offSet);
				System.out.println("putting " + expr.substring(oldOffSet+1, offSet));
				toAdd.add(expr.substring(oldOffSet+1, offSet));
				
				oldOffSet = offSet;
			}
			offSet++;
		}
				
		if (added)
		{
			System.out.println("old is: "+ oldOffSet);
			System.out.println("putting " + expr.substring(oldOffSet+1, expr.length()));
			toAdd.add(expr.substring(oldOffSet+1, expr.length()));
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
		
			try
			{
				double val = Double.parseDouble(expr);
				return val;
			}
			catch (NumberFormatException e)
			{
				
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
				
				//Parentheses--------------------
				if (expr.indexOf("(") == 0)
				{
					System.out.println("Detected Parens");
					return evalExpr(expr.substring(1, expr.length()-1));
					
					
					//Java regex can't find closing
					//parse
					/*
					int leftOffset = 0;
					int rightOffset = 0;
					
					if (expr.substring(leftOffset+1).indexOf("(") != -1)
					{
						while(expr.substring(leftOffset+1).indexOf("(") != -1)
						{
							//increemnt left offset once
							leftOffset = expr.substring(leftOffset+1).indexOf("(");
							
							//incrememnt right offset twice.
							rightOffset = expr.substring(rightOffset+1).indexOf(")");
							rightOffset = expr.substring(rightOffset+1).indexOf(")");
						}
					}
					else
					{
						rightOffset = expr.length();
					}
					
					
					
					System.out.println(expr);
					System.out.println(expr.substring(1, rightOffset-1));
					//return -666;
					return evalExpr(expr.substring(1, rightOffset-1));
				}
				//-------------------------------
			}
			
		//Shit didn't work
		return -666;
		*/
				}
			}
		return -666;
	}
}
