package ac.um.ds;

public class PlusOperator extends Operator
{
	public PlusOperator()
	{
		super("+", 1, Operator.Associativity.ASSOC_LEFT, false);
	}
	@Override
	public double evaluate(double v1, double v2)
	{
		return v1 + v2;
	}
}