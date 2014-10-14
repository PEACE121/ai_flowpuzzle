import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;


public class FlowGrid extends JPanel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2838947983271757774L;
	
	static final int				MAX_SIZE				= 600;
	private int						rectSize;
	
	private Position[][]			positions;
	
	private Position[][]			initPositions;
	
	private int						domainSize			= 0;
	
	
	public FlowGrid()
	{
	}
	
	
	// -------------------------------------------------------------------------
	// ------------------------------------ GUI --------------------------------
	// -------------------------------------------------------------------------
	
	public void refreshField()
	{
		this.repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g)
	{
		if (positions != null)
		{
			for (int i = 0; i < positions.length; i++)
			{
				for (int j = 0; j < positions[0].length; j++)
				{
					if (positions[i][j] != null)
					{
						g.setColor((positions[i][j].getColor().getColor()));
						g.fillRect(rectSize * i, (rectSize * (positions[0].length - 1)) - rectSize * j, rectSize, rectSize);
					}
				}
			}
		}
		
		// Display the window.
		setVisible(true);
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------------- Inpupt Processing ---------------------------
	// -------------------------------------------------------------------------
	private static float getFloatFromPair(String pair, int index)
	{
		return Float.parseFloat(getStringFromPair(pair, index));
	}
	
	
	private static int getIntFromPair(String pair, int index)
	{
		return Integer.parseInt(getStringFromPair(pair, index));
	}
	
	
	private static String getStringFromPair(String pair, int index)
	{
		pair = pair.replaceAll("\\(", "");
		pair = pair.replaceAll("\\)", "");
		String[] array = pair.split(" ");
		return array[index];
	}
	
	
	@SuppressWarnings("resource")
	public Position[][] readInput(String fileName)
	{
		try
		{
			File file = new File(fileName);
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String line;
			int lineNr = 0;
			while ((line = br.readLine()) != null)
			{
				switch (lineNr)
				{
					case 0:
						int size = getIntFromPair(line, 0);
						domainSize = getIntFromPair(line, 1);
						positions = new Position[size][size];
						initPositions = new Position[size][size];
						break;
					default:
						positions[getIntFromPair(line, 1)][getIntFromPair(line, 2)] = new Position(getIntFromPair(line, 1),
								getIntFromPair(line, 2), getIntFromPair(line, 0));
						positions[getIntFromPair(line, 3)][getIntFromPair(line, 4)] = new Position(getIntFromPair(line, 3),
								getIntFromPair(line, 4), getIntFromPair(line, 0));
				}
				lineNr++;
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < positions.length; i++)
		{
			for (int j = 0; j < positions[0].length; j++)
			{
				if (positions[i][j] != null)
				{
					initPositions[i][j] = positions[i][j];
				}
			}
		}
		rectSize = MAX_SIZE / positions.length;
		return positions;
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------------- Getter and Setter ---------------------------
	// -------------------------------------------------------------------------
	
	public int getGridSize()
	{
		return positions.length;
	}
	
	
	/**
	 * @return the domainSize
	 */
	public int getDomainSize()
	{
		return domainSize;
	}
	
	
	/**
	 * @return the positions
	 */
	public Position[][] getPositions()
	{
		return positions;
	}
	
	
	/**
	 * @param positions the positions to set
	 */
	public void setPositions(Position[][] positions)
	{
		this.positions = positions;
	}
	
	
	/**
	 * @return the initPositions
	 */
	public Position[][] getInitPositions()
	{
		return initPositions;
	}
	
	
	/**
	 * @param initPositions the initPositions to set
	 */
	public void setInitPositions(Position[][] initPositions)
	{
		this.initPositions = initPositions;
	}
	
	
}