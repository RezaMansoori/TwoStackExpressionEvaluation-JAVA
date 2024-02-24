package ac.um.ds;

public class Delimeter extends Token
{
	public final char mMark;

	public Delimeter(char mark)
	{
		super(Token.Type.DELIMETER);
		this.mMark = mark;
	}

	@Override
	public String toText()
	{
		String s = "";
		s = s + mMark;
		return s;
	}
}
