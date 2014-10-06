

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import astarframework.IAreaOfApplication;
import astarframework.IState;
import astarframework.Node;


public class Field extends JPanel implements IAreaOfApplication
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2838947983271757774L;
	
	public static final int		MAX_SIZE				= 600;
	public static int				RECT_SIZE;
	
	private final int				columns;
	private final int				rows;
	private FieldType[][]		fieldInfo;
	
	private Position				start, end;
	
	
	public Field(FieldType[][] fieldInfo)
	{
		this.fieldInfo = fieldInfo;
		this.columns = fieldInfo.length;
		this.rows = fieldInfo[0].length;
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (fieldInfo[i][j] == FieldType.START)
				{
					start = new Position(i, j);
				}
				if (fieldInfo[i][j] == FieldType.END)
				{
					end = new Position(i, j);
				}
			}
		}
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------- IAreaOfApplication Overrides ----------------------
	// -------------------------------------------------------------------------
	@Override
	public int getHeuristic(IState pos)
	{
		Position p = (Position) pos;
		return cost(p, end);
	}
	
	
	@Override
	public IState getStart()
	{
		return start;
	}
	
	
	@Override
	public boolean isSolution(IState pos)
	{
		return pos.isTheSame(end);
	}
	
	
	@Override
	public List<IState> generateAllSuccessors(IState pos)
	{
		List<IState> successors = new LinkedList<IState>();
		if ((pos instanceof Position))
		{
			Position currentPos = (Position) pos;
			if (currentPos.getX() > 0)
			{
				FieldType left = fieldInfo[currentPos.getX() - 1][currentPos.getY()];
				if (left != FieldType.BORDER)
				{
					successors.add(new Position(currentPos.getX() - 1, currentPos.getY()));
				}
			}
			if (currentPos.getY() > 0)
			{
				FieldType lower = fieldInfo[currentPos.getX()][currentPos.getY() - 1];
				if (lower != FieldType.BORDER)
				{
					successors.add(new Position(currentPos.getX(), currentPos.getY() - 1));
				}
			}
			if (currentPos.getX() < fieldInfo.length - 1)
			{
				FieldType right = fieldInfo[currentPos.getX() + 1][currentPos.getY()];
				if (right != FieldType.BORDER)
				{
					successors.add(new Position(currentPos.getX() + 1, currentPos.getY()));
				}
			}
			if (currentPos.getY() < fieldInfo[0].length - 1)
			{
				FieldType upper = fieldInfo[currentPos.getX()][currentPos.getY() + 1];
				if (upper != FieldType.BORDER)
				{
					successors.add(new Position(currentPos.getX(), currentPos.getY() + 1));
				}
			}
		}
		return successors;
	}
	
	
	@Override
	public int cost(IState from, IState to)
	{
		Position fromPos = (Position) from;
		Position toPos = (Position) to;
		return Math.abs(fromPos.getX() - toPos.getX()) + Math.abs(fromPos.getY() - toPos.getY());
	}
	
	
	// -------------------------------------------------------------------------
	// ------------------------------------ GUI --------------------------------
	// -------------------------------------------------------------------------
	public void changeField(FieldType[][] fieldInfo)
	{
		this.fieldInfo = fieldInfo;
		this.repaint();
	}
	
	
	public void addPath(Node p)
	{
		// reset
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (fieldInfo[i][j] == FieldType.PATH)
				{
					fieldInfo[i][j] = FieldType.FIELD;
				}
			}
		}
		while (!p.getState().isTheSame(start))
		{
			Position pos = (Position) p.getState();
			if (fieldInfo[pos.getX()][pos.getY()] != FieldType.END)
			{
				fieldInfo[pos.getX()][pos.getY()] = FieldType.PATH;
			}
			p = p.getParent();
		}
	}
	
	
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
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				switch (fieldInfo[i][j])
				{
					case FIELD:
						g.setColor(Color.WHITE);
						break;
					case BORDER:
						g.setColor(Color.BLACK);
						break;
					case PATH:
						g.setColor(Color.RED);
						break;
					case START:
						g.setColor(Color.YELLOW);
						break;
					case END:
						g.setColor(Color.ORANGE);
						break;
				}
				g.fillRect(Field.RECT_SIZE * i, (Field.RECT_SIZE * (rows - 1)) - Field.RECT_SIZE * j, Field.RECT_SIZE,
						Field.RECT_SIZE);
			}
		}
		
		// Display the window.
		setVisible(true);
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------------- Inpupt Processing ---------------------------
	// -------------------------------------------------------------------------
	private static int getNumberFromPair(String pair, int index)
	{
		pair = pair.replaceAll("\\(", "");
		pair = pair.replaceAll("\\)", "");
		String[] array = pair.split(",");
		return Integer.parseInt(array[index]);
	}
	
	
	public static FieldType[][] randomFieldInput()
	{
		int rows = 300;
		int columns = 300;
		RECT_SIZE = 600 / Math.max(rows, columns);
		FieldType[][] fieldInfo = new FieldType[columns][rows];
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				fieldInfo[i][j] = FieldType.FIELD;
				if (Math.random() < 0.3)
				{
					fieldInfo[i][j] = FieldType.BORDER;
				}
			}
		}
		fieldInfo[0][0] = FieldType.START;
		fieldInfo[columns - 1][rows - 1] = FieldType.END;
		return fieldInfo;
	}
	
	
	@SuppressWarnings("resource")
	public static FieldType[][] readFieldInput(String fileName)
	{
		int rows;
		int columns;
		FieldType[][] fieldInfo = null;
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
						columns = getNumberFromPair(line, 0);
						rows = getNumberFromPair(line, 1);
						RECT_SIZE = 600 / Math.max(rows, columns);
						// create empty filed
						fieldInfo = new FieldType[columns][rows];
						for (int i = 0; i < columns; i++)
						{
							for (int j = 0; j < rows; j++)
							{
								fieldInfo[i][j] = FieldType.FIELD;
							}
						}
						break;
					case 1:
						String[] startAndEnd = line.split("\\s+");
						fieldInfo[getNumberFromPair(startAndEnd[0], 0)][getNumberFromPair(startAndEnd[0], 1)] = FieldType.START;
						fieldInfo[getNumberFromPair(startAndEnd[1], 0)][getNumberFromPair(startAndEnd[1], 1)] = FieldType.END;
						break;
					default:
						for (int i = getNumberFromPair(line, 0); i < getNumberFromPair(line, 0) + getNumberFromPair(line, 2); i++)
						{
							for (int j = getNumberFromPair(line, 1); j < getNumberFromPair(line, 1)
									+ getNumberFromPair(line, 3); j++)
							{
								fieldInfo[i][j] = FieldType.BORDER;
							}
							
						}
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
		return fieldInfo;
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------------- Getter and Setter ---------------------------
	// -------------------------------------------------------------------------
	/**
	 * @return the fieldInfo
	 */
	public FieldType[][] getFieldInfo()
	{
		return fieldInfo;
	}
	
}

enum FieldType
{
	FIELD,
	BORDER,
	PATH,
	START,
	END;
}
