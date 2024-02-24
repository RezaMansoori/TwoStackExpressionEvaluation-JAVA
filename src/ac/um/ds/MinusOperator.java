package ac.um.ds;

public class MinusOperator extends Operator
{
	public MinusOperator()
	{
		super("-", 1, Operator.Associativity.ASSOC_LEFT, false);
	}
	@Override
	public double evaluate(double v1, double v2)
	{
		return v1 - v2;
	}
}