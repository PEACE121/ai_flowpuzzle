import gac.IDomainAttribute;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import astarframework.IState;


public class Position implements IState
{
	private int				x;
	private int				y;
	
	private final int		index;
	
	private DomainColor	color;
	
	
	/**
	 * @param x
	 * @param y
	 */
	public Position(int x, int y, int index)
	{
		super();
		this.index = index;
		this.color = getColor(index);
		this.x = x;
		this.y = y;
	}
	
	
	private static DomainColor getColor(int index)
	{
		
		switch (index)
		{
			case 0:
				return new DomainColor(Color.red, index);
			case 1:
				return new DomainColor(Color.blue, index);
			case 2:
				return new DomainColor(Color.green, index);
			case 3:
				return new DomainColor(Color.yellow, index);
			case 4:
				return new DomainColor(Color.pink, index);
			case 5:
				return new DomainColor(Color.orange, index);
			case 6:
				return new DomainColor(Color.cyan, index);
			case 7:
				return new DomainColor(new Color(153, 0, 76), index);
			case 8:
				return new DomainColor(Color.magenta, index);
			case 9:
				return new DomainColor(new Color(188, 143, 143), index);
			case 10:
				return new DomainColor(new Color(160, 32, 240), index);
			case 11:
				return new DomainColor(new Color(34, 139, 34), index);
			default:
				return new DomainColor(Color.gray, index);
		}
	}
	
	
	public static List<IDomainAttribute> getListOfDomains(int amount)
	{
		List<IDomainAttribute> colors = new ArrayList<IDomainAttribute>();
		for (int i = 0; i < amount; i++)
		{
			colors.add(getColor(i));
		}
		return colors;
	}
	
	
	/**
	 * @return the color
	 */
	public DomainColor getColor()
	{
		return color;
	}
	
	
	/**
	 * @param color the color to set
	 */
	public void setColor(DomainColor color)
	{
		this.color = color;
	}
	
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	
	
	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	
	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}
	
	
	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	
	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}
	
	
	@Override
	public boolean isTheSame(IState object)
	{
		if (object instanceof Position)
		{
			Position p = (Position) object;
			return this.x == p.x && this.y == p.y;
		}
		System.out.println("Warning: Comparing Position with something else");
		return false;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Position [x=" + x + ", y=" + y + ", index=" + index + "]";
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (index != other.index)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
