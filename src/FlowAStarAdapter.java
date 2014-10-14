import gac.AStarAdapter;
import gac.ENextVariable;
import gac.GACState;
import gac.constraintNetwork.Constraint;
import gac.constraintNetwork.Variable;

import java.util.ArrayList;
import java.util.List;


public class FlowAStarAdapter extends AStarAdapter
{
	private final FlowGrid	grid;
	
	
	public FlowAStarAdapter(List<Constraint> constraints, List<Variable> vars, ENextVariable next, FlowGrid grid)
	{
		super(constraints, vars, next);
		this.grid = grid;
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
			for (Position neighbour : findNeighbours(start))
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
	
	
	private List<Position> findNeighbours(Position p)
	{
		List<Position> neighbours = new ArrayList<Position>();
		int i = p.getX();
		int j = p.getY();
		int index = p.getIndex();
		if (i > 0)
		{
			if (grid.getPositions()[i - 1][j] != null && grid.getPositions()[i - 1][j].getIndex() == index)
			{
				neighbours.add(grid.getPositions()[i - 1][j]);
			}
		}
		if (i < grid.getGridSize() - 1)
		{
			if (grid.getPositions()[i + 1][j] != null && grid.getPositions()[i + 1][j].getIndex() == index)
			{
				neighbours.add(grid.getPositions()[i + 1][j]);
			}
		}
		if (j > 0)
		{
			if (grid.getPositions()[i][j - 1] != null && grid.getPositions()[i][j - 1].getIndex() == index)
			{
				neighbours.add(grid.getPositions()[i][j - 1]);
			}
		}
		if (j < grid.getGridSize() - 1)
		{
			if (grid.getPositions()[i][j + 1] != null && grid.getPositions()[i][j + 1].getIndex() == index)
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
}
