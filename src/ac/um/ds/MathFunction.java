package ac.um.ds;


public abstract class MathFunction extends Operator
{
	public MathFunction(String name)
	{
		super(name, 4,Operator.Associativity.ASSOC_LEFT, true, Token.Type.FUNCTION);//10 for funtion
	}
}