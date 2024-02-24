package ac.um.ds;


public class NumericConstant extends Token {
	public NumericConstant(double value)
	{
		
		super(Token.Type.NUMERIC_CONST);
                this.mValue = value;
	}
	public final double mValue;

	@Override
	public String toText()
	{
		String ss = "";
		ss = ss + mValue;
		return ss;
	}    
}
