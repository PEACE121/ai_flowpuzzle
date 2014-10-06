import gac.IDomainAttribute;

import java.awt.Color;


public class DomainColor implements IDomainAttribute
{
	
	private final Color	color;
	
	
	/**
	 * @param color
	 */
	public DomainColor(Color color)
	{
		super();
		this.color = color;
	}
	
	
	@Override
	public int getNumericalRepresentation()
	{
		return color.getRGB();
	}
	
	
	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}
	
	
}
