import gac.ENextVariable;


public class Start
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		FlowPuzzle flowPuzzle = null;
		ENextVariable next = ENextVariable.COMPLEX4;
		EConstraintType constraintType = EConstraintType.MAX_4;
		switch (args.length)
		{
			case 3:
				constraintType = EConstraintType.valueOf(args[2]);
			case 2:
				next = ENextVariable.valueOf(args[1]);
			case 1:
				System.out.println(args[0]);
				flowPuzzle = new FlowPuzzle(args[0], next, constraintType);
				break;
			case 0:
				System.out.println("flowspec-0.txt");
				flowPuzzle = new FlowPuzzle("flowspec-0.txt");
				flowPuzzle.run();
				System.out.println("flowspec-1.txt");
				flowPuzzle = new FlowPuzzle("flowspec-1.txt");
				flowPuzzle.run();
				System.out.println("flowspec-2.txt");
				flowPuzzle = new FlowPuzzle("flowspec-2.txt");
				flowPuzzle.run();
				System.out.println("flowspec-3.txt");
				flowPuzzle = new FlowPuzzle("flowspec-3.txt");
				flowPuzzle.run();
				System.out.println("flowspec-4.txt");
				flowPuzzle = new FlowPuzzle("flowspec-4.txt");
				flowPuzzle.run();
				System.out.println("flowspec-5.txt");
				flowPuzzle = new FlowPuzzle("flowspec-5.txt");
				flowPuzzle.run();
				System.out.println("flowspec-6.txt");
				flowPuzzle = new FlowPuzzle("flowspec-6.txt");
			default:
				System.out
						.println("Usage: Start <filename> <GAC heuristic> <constraint_type> \n Heuristics: COMPLEX1, COMPLEX2 for complex versions, SIMPLE for simple \n ConstraintTypes: MAX_4 or MAX_5");
				break;
		}
		if (flowPuzzle != null)
		{
			flowPuzzle.run();
		}
		
		// new FlowPuzzle("flowspec-0.txt");
		// new FlowPuzzle("flowspec-1.txt");
		// new FlowPuzzle("flowspec-2.txt");
		// new FlowPuzzle("flowspec-3.txt");
		// new FlowPuzzle("flowspec-4.txt");
		// new FlowPuzzle("flowspec-5.txt");
		// new FlowPuzzle("flowspec-6.txt");
	}
}
