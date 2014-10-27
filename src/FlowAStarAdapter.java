import gac.AStarAdapter;
import gac.ENextVariable;
import gac.GACState;
import gac.constraintNetwork.Constraint;
import gac.constraintNetwork.Variable;
import gac.instances.VI;

import java.util.ArrayList;
import java.util.List;

import astarframework.IState;


public class FlowAStarAdapter extends AStarAdapter
{
	private final FlowGrid	grid;
	
	
	public FlowAStarAdapter(List<Constraint> constraints, List<Variable> vars, ENextVariable next, FlowGrid grid)
	{
		super(constraints, vars, next);
		this.grid = grid;
	}
	
	
	@Override
	public List<IState> generateAllSuccessors(IState state)
	{
		if (super.chooseNextComplex == ENextVariable.COMPLEX4)
		{
			GACState gacState = (GACState) state;
			VI nextVar = null;
			int longestLine = 0;
			for (VI vi : gacState.getVis().values())
			{
				if (vi.getDomain().size() > 1)
				{
					int longestLineOfVi = findLengthOfLongestStraightLine(vi.getVarInCNET());
					if (longestLineOfVi > longestLine)
					{
						longestLine = longestLineOfVi;
						nextVar = vi;
					}
				}
			}
			System.out.println(longestLine);
			return super.generateSuccesorsOfVI(nextVar, gacState);
		} else
		{
			return super.generateAllSuccessors(state);
		}
	}
	
	
	@Override
	public boolean isApplicationSolution(GACState state)
	{
		// for all colors
		for (int i = 0; i < grid.getDomainSize(); i++)
		{
			// find start and end
			Position start = null;
			Position end = null;
			for (int j = 0; j < grid.getInitPositions().length; j++)
			{
				for (int k = 0; k < grid.getInitPositions()[0].length; k++)
				{
					if (grid.getInitPositions()[j][k] != null && grid.getInitPositions()[j][k].getIndex() == i)
					{
						if (start == null)
						{
							start = grid.getInitPositions()[j][k];
						} else
						{
							end = grid.getInitPositions()[j][k];
						}
					}
				}
			}
			List<Position> positions = new ArrayList<Position>();
			for (int j = 0; j < grid.getPositions().length; j++)
			{
				for (int k = 0; k < grid.getPositions()[0].length; k++)
				{
					if (grid.getPositions()[j][k].getIndex() == i)
					{
						positions.add(grid.getPositions()[j][k]);
					}
				}
			}
			
			// find a possible flow
			if (!findEnd(start, end, positions.size(), new ArrayList<Position>()))
			{
				return false;
			}
		}
		return true;
	}
	
	
	private boolean findEnd(Position start, Position end, int pathLength, List<Position> alreadyVisited)
	{
		if (alreadyVisited.size() == (pathLength - 1) && start.equals(end))
		{
			return true;
		} else
		{
			alreadyVisited.add(start);
			for (Position neighbour : findAndFilterNeighbours(start))
			{
				if (!alreadyVisited.contains(neighbour))
				{
					List<Position> newAlreadyVisited = new ArrayList<Position>(alreadyVisited);
					// Collections.copy(newAlreadyVisited, alreadyVisited);
					if (findEnd(neighbour, end, pathLength, newAlreadyVisited))
					{
						return true;
					}
				}
			}
			return false;
		}
	}
	
	
	private List<Position> findAndFilterNeighbours(Position p)
	{
		List<Position> neighbours = findNeighbours(p);
		int index = p.getIndex();
		List<Position> returnedNeighbours = new ArrayList<Position>();
		for (Position pos : neighbours)
		{
			if (pos.getIndex() == index)
			{
				returnedNeighbours.add(pos);
			}
		}
		return returnedNeighbours;
	}
	
	
	private List<Position> findNeighbours(Position p)
	{
		List<Position> neighbours = new ArrayList<Position>();
		int i = p.getX();
		int j = p.getY();
		
		if (i > 0)
		{
			if (grid.getPositions()[i - 1][j] != null)
			{
				neighbours.add(grid.getPositions()[i - 1][j]);
			}
		}
		if (i < grid.getGridSize() - 1)
		{
			if (grid.getPositions()[i + 1][j] != null)
			{
				neighbours.add(grid.getPositions()[i + 1][j]);
			}
		}
		if (j > 0)
		{
			if (grid.getPositions()[i][j - 1] != null)
			{
				neighbours.add(grid.getPositions()[i][j - 1]);
			}
		}
		if (j < grid.getGridSize() - 1)
		{
			if (grid.getPositions()[i][j + 1] != null)
			{
				neighbours.add(grid.getPositions()[i][j + 1]);
			}
		}
		return neighbours;
	}
	
	
	@Override
	public boolean isApplicationDeadEnd(GACState state)
	{
		return false;
	}
	
	
	@Override
	public int getHeuristic(IState state)
	{
		// List<IState> successors = super.generateAllSuccessors(state);
		Variable lastFocal = ((GACState) state).getLastGuessedVar();
		if (lastFocal != null)
		{
			List<Position> gridNeighbours = findNeighbours(varToPos(lastFocal));
			GACState gacState = (GACState) state;
			int lastGuessedDomain = gacState.getLastGuessed().getNumericalRepresentation();
			for (Position p : gridNeighbours)
			{
				if (p.getIndex() == lastGuessedDomain)
				{
					System.out.println("priorized");
					return 0;
				}
			}
		}
		return super.getHeuristic(state);
	}
	
	
	private int findLengthOfLongestStraightLine(Variable var)
	{
		Position toSearchForNeighbours = varToPos(var);
		int longestLine = 0;
		for (Position p : findNeighbours(toSearchForNeighbours))
		{
			int x_dir = p.getX() - toSearchForNeighbours.getX();
			int y_dir = p.getY() - toSearchForNeighbours.getY();
			int lineLength = 1;
			int lineOfP = longestLine(toSearchForNeighbours, p, x_dir, y_dir, p.getIndex(), lineLength);
			if (lineOfP > longestLine)
			{
				longestLine = lineOfP;
			}
		}
		return longestLine;
	}
	
	
	private int longestLine(Position toSearchForNeighbours, Position p, int x_dir, int y_dir, int index, int length)
	{
		if (p.getIndex() == index && (p.getX()) == (toSearchForNeighbours.getX() + (length * x_dir))
				&& (p.getY()) == (toSearchForNeighbours.getY() + (length * y_dir)))
		{
			int longestLine = length;
			for (Position newP : findNeighbours(p))
			{
				int longestLineOfNewP = longestLine(toSearchForNeighbours, newP, x_dir, y_dir, index, length + 1);
				if (longestLineOfNewP > longestLine)
				{
					longestLine = longestLineOfNewP;
				}
			}
			return longestLine;
		} else
		{
			return length - 1;
		}
	}
	
	
	private Position varToPos(Variable var)
	{
		String varName = var.getName();
		varName = varName.replace("pos", "");
		int x = Integer.parseInt(varName.split("a")[0]);
		int y = Integer.parseInt(varName.split("a")[1]);
		return new Position(x, y, 0);
	}
}
