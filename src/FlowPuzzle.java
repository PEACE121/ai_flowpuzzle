import gac.AStarAdapter;
import gac.ENextVariable;
import gac.GACState;
import gac.IDomainAttribute;
import gac.IGACObersvers;
import gac.constraintNetwork.Constraint;
import gac.constraintNetwork.Variable;
import gac.instances.VI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import algorithms.AStar;
import astarframework.IAStarObersvers;
import astarframework.Node;


public class FlowPuzzle implements IGACObersvers, IAStarObersvers
{
	private final FlowGrid	grid;
	
	
	/**
	 * 
	 */
	public FlowPuzzle(String fileName)
	{
		super();
		
		grid = new FlowGrid();
		grid.readInput(fileName);
		
		JFrame f = new JFrame();
		f.getContentPane().add(grid);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(FlowGrid.MAX_SIZE, FlowGrid.MAX_SIZE);
		f.setVisible(true);
		
		List<Constraint> constraints = new LinkedList<Constraint>();
		Map<String, Variable> vars = new HashMap<String, Variable>();
		
		List<IDomainAttribute> domains = Position.getListOfDomains(grid.getDomainSize());
		
		// fill variables
		for (int i = 0; i < grid.getGridSize(); i++)
		{
			for (int j = 0; j < grid.getGridSize(); j++)
			{
				String gridCell = createVariableName(i, j);
				if (grid.getPositions()[i][j] != null)
				{
					List<IDomainAttribute> domain = new ArrayList<IDomainAttribute>();
					domain.add(grid.getPositions()[i][j].getColor());
					vars.put(gridCell, new FlowVariable(gridCell, domain, i, j));
				} else
				{
					vars.put(gridCell, new FlowVariable(gridCell, domains, i, j));
				}
				
			}
		}
		
		// define neighbors and constraints
		for (int i = 0; i < grid.getGridSize(); i++)
		{
			for (int j = 0; j < grid.getGridSize(); j++)
			{
				List<String> neighbours = new ArrayList<String>();
				if (i > 0)
				{
					neighbours.add(createVariableName((i - 1), j));
				}
				if (i < grid.getGridSize() - 1)
				{
					neighbours.add(createVariableName((i + 1), j));
				}
				if (j > 0)
				{
					neighbours.add(createVariableName(i, (j - 1)));
				}
				if (j < grid.getGridSize() - 1)
				{
					neighbours.add(createVariableName(i, (j + 1)));
				}
				Map<String, Variable> variableOfConstraint = new HashMap<String, Variable>();
				String constraint = "";
				variableOfConstraint.put(createVariableName(i, j), vars.get(createVariableName(i, j)));
				
				for (int k = 0; k < neighbours.size(); k++)
				{
					String neighbour1 = neighbours.get(k);
					variableOfConstraint.put(neighbour1, vars.get(neighbour1));
					Position gridCell = grid.getPositions()[i][j];
					if (gridCell == null)
					{
						for (int k2 = k; k2 < neighbours.size(); k2++)
						{
							String neighbour2 = neighbours.get(k2);
							if (!neighbour1.equals(neighbour2))
							{
								constraint += "(" + createVariableName(i, j) + " == " + neighbour1 + " && " + neighbour1
										+ " == " + neighbour2 + ")" + " || ";
								
							}
						}
					} else
					{
						constraint += "(" + createVariableName(i, j) + " == " + neighbours.get(k) + ")" + " || ";
					}
				}
				
				constraint = constraint.substring(0, constraint.length() - 4);
				System.out.println(constraint);
				
				constraints.add(new Constraint(constraint, variableOfConstraint));
			}
		}
		List<Variable> variables = new ArrayList<Variable>(vars.values());
		AStarAdapter aStarGAC = new AStarAdapter(constraints, variables, ENextVariable.COMPLEX2);
		aStarGAC.register(this);
		aStarGAC.domainFilteringLoop();
		AStar aStarInstance = new AStar(aStarGAC);
		aStarInstance.register(this);
		aStarInstance.run();
	}
	
	
	private String createVariableName(int i, int j)
	{
		return "pos" + i + "a" + j;
	}
	
	
	@Override
	public void update(Node app, boolean force)
	{
		GACState gacState = (GACState) app.getState();
		update(gacState, force);
	}
	
	
	@Override
	public void update(GACState x, boolean force)
	{
		Position[][] positions = new Position[grid.getGridSize()][grid.getGridSize()];
		for (VI vi : x.getVis().values())
		{
			FlowVariable variable = (FlowVariable) vi.getVarInCNET();
			if (vi.getDomain().size() == 1)
			{
				positions[variable.getX()][variable.getY()] = new Position(variable.getX(), variable.getY(), vi.getDomain()
						.get(0).getNumericalRepresentation());
			} else if (vi.getDomain().size() == 0)
			{
				positions[variable.getX()][variable.getY()] = new Position(variable.getX(), variable.getY(), 7);
			}
		}
		grid.setPositions(positions);
		grid.refreshField();
		try
		{
			Thread.sleep(0);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
}
