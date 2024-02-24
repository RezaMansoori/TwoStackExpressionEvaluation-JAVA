
import ac.um.ds.ExpressionEvaluator;
import ac.um.ds.Token;
import java.util.HashMap;
import java.util.Vector;

public class TestTwoStackExpressionEvaluation {
	public static void main(String[] args) throws Exception {

        System.out.println("=============================================");
        System.out.println("Test 1. Postfix");
        System.out.println("=============================================");
		postfixTest();
        System.out.println("=============================================");
        System.out.println("Test 2. Evaluation");
        System.out.println("=============================================");
        evaluationTest();

	}
	
	public static void postfixTest() throws Exception{
		ExpressionEvaluator ee = new ExpressionEvaluator();
		Vector<Token> vtInfix = new Vector<Token>();
		Vector<Token> vtPostfix = new Vector<Token>();
		String tokenizedPostfixString;
		String[] testCases = {"-(-sin(-x))^(-10-x)","(z-x)-(y-(z-1)^(4-x))^(y-z)","sin(cos(x+exp(x-z))+ln(x))","(7-x-5)/z/y*z*x+7+8-9","7^z^y^2/z^x^3"};
		String[] tokenized = {" x _ sin _ _ 10.0 _ x - ^"," z x - y z 1.0 - 4.0 x - ^ - y z - ^ -"," x x z - exp + cos x ln + sin"," 7.0 x - 5.0 - z / y / z * x * 7.0 + 8.0 + 9.0 -"," 7.0 z y 2.0 ^ ^ ^ z x 3.0 ^ ^ /"};

		for (int i=0; i<5; i++) {
			ee.tokenizeString(testCases[i], vtInfix);
			ee.infix2Postfix(vtInfix, vtPostfix);
			tokenizedPostfixString = ee.tokenSeq2String(vtPostfix);

			System.out.println("Expression: " + testCases[i]);
			System.out.println("Evaluated Postfix: " + tokenizedPostfixString);
			System.out.println("Correct Answer: " + tokenized[i]);

			if (tokenizedPostfixString.equals(tokenized[i])) {
				System.out.println("True\n");
			} else {
				System.out.println("False\n");
				System.out.println();
                throw new RuntimeException("Incorrect postfix function.");
			}
			vtInfix.clear();
			vtPostfix.clear();
		}
	}

	public static void evaluationTest() throws Exception{
		ExpressionEvaluator ee = new ExpressionEvaluator();
		double evaluatedResult;
		HashMap<String, Double> vv = new HashMap<String, Double>();
		vv.put("x", (double) 1);
		vv.put("y", (double) 2);
		vv.put("z", (double) 3);
		vv.put("pi", 3.1415926535897932384626433832795);
		String[] testCases = {"-(-sin(-x))^(-10*x)","(z-x)-(y-(z-1)^(4-x))^(y-z)","sin(cos(x+exp(x-z))+ln(x))","(7-x-5)/z/y*z*x+7+8-9","7^z^y^2/6^z^4^x"};
		double[] result = {5.61835,2.16,0.40,6.50,264661};

		int res = 0;
		for (int i=0; i<5; i++) {
			evaluatedResult = ee.evaluateExpression(testCases[i], new HashMap(vv));

			System.out.println("Evaluated: " + evaluatedResult);
			System.out.println("Answer: " + result[i]);

			if (Math.abs(evaluatedResult - result[i]) < 0.01)
				res += 20;
			else{
                System.out.println();
                throw new RuntimeException("Incorrect evaluation function.");
			}
			System.out.println("Score: " + res);
			System.out.println();
		}

	}

}
