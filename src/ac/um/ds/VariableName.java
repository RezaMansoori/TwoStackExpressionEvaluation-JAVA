package ac.um.ds;

public class VariableName extends Token
{
	public final String mName;

	public VariableName(String name)
	{
		
		super(Token.Type.VARIABLE);
                this.mName = name;
	}

	@Override
	public String toText()
	{
		return mName;
	}

}