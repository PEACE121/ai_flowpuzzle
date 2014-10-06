import astarframework.IState;


public class Position implements IState
{
	private int	x;
	private int	y;
	
	
	/**
	 * @param x
	 * @param y
	 */
	public Position(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
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
}
