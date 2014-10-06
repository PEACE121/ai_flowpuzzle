import gac.IDomainAttribute;
import gac.constraintNetwork.Variable;

import java.util.List;


public class FlowVariable extends Variable
{
	private final int	x;
	private final int	y;
	
	
	public FlowVariable(String name, List<IDomainAttribute> fullDomain, int x, int y)
	{
		super(name, fullDomain);
		this.x = x;
		this.y = y;
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	
	
	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}
	
	
}
