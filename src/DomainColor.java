import gac.IDomainAttribute;

import java.awt.Color;


public class DomainColor implements IDomainAttribute
{
	
	private final Color	color;
	
	private final int		index;
	
	
	/**
	 * @param color
	 */
	public DomainColor(Color color, int index)
	{
		super();
		this.index = index;
		this.color = color;
	}
	
	
	@Override
	public int getNumericalRepresentation()
	{
		return index;
	}
	
	
	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}
	
	
}
