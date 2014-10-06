import gac.IDomainAttribute;
import gac.constraintNetwork.Constraint;
import gac.constraintNetwork.Variable;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;


public class FlowPuzzle
{
	
	/**
	 * 
	 */
	public FlowPuzzle(String fileName)
	{
		super();
		
		FlowGrid grid = new FlowGrid();
		grid.readInput(fileName);
		
		JFrame f = new JFrame();
		f.getContentPane().add(grid);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(FlowGrid.MAX_SIZE, FlowGrid.MAX_SIZE);
		f.setVisible(true);
		
		List<Constraint> constraints = new LinkedList<Constraint>();
		List<Variable> vars = new LinkedList<Variable>();
		
		List<IDomainAttribute> domains = Position.getListOfDomains(grid.getDomainSize());
		
		for (int i = 0; i < grid.getGridSize(); i++)
		{
			for (int j = 0; j < grid.getGridSize(); j++)
			{
				vars.add(new Variable("pos" + i + "-" + j, domains));
				// if()
				// constraints.add(new Constraint(", variables))
			}
		}
		
	}
}
