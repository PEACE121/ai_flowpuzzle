import gac.ENextVariable;


public class Start
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		FlowPuzzle flowPuzzle = null;
		ENextVariable next = ENextVariable.COMPLEX2;
		switch (args.length)
		{
			case 2:
				next = ENextVariable.valueOf(args[1]);
			case 1:
				System.out.println(args[0]);
				flowPuzzle = new FlowPuzzle(args[0], next);
				break;
			default:
				System.out
						.println("Usage: Start <filename> <GAC heuristic> \n Heuristics: COMPLEX1, COMPLEX2 for complex versions, SIMPLE for simple");
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
