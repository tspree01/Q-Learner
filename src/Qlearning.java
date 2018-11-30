import java.util.Random;

class Qlearning
{
	private double ε;
	private double gamma;
	private double learningRate;
	private double[][][] Q = new double[20][10][4];
	private int x;
	private int y;
	private int outOfBoundsX;
	private int outOfBoundsY;
	int currentStateX;
	int currentStateY;
	private String action;
	static private Random rand;
	private double[][] board = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, - 3, 0, 0, 0, 0, 0, 0, 0, 0, 10},
	};

	private Qlearning()
	{
		this.ε = 0.05;
		this.gamma = 0.97;
		this.learningRate = 0.1;
		reset();
		this.outOfBoundsX = 20;
		this.outOfBoundsY = 10;
		rand = new Random();
	}

	private void reset()
	{
		this.x = 0;
		this.y = 0;

	}

	private void QLearner(int numberOfIterations)
	{
		for (int k = 0; k < numberOfIterations; k++)
		{
			// Pick an action
			currentStateX = x;
			currentStateY = y;
			int action = 0;
			if (rand.nextDouble() < ε)
			{
				// Explore (pick a random action)
				action = rand.nextInt(4);
			}
			else
			{
				// Exploit (pick the best action)

				for (int candidate = 0; candidate < 4; candidate++)
				{
					if (Q[x][y][candidate] > Q[x][y][action])
					{ action = candidate; }
				}
				if (Q[x][y][action] == 0.0)
				{ action = rand.nextInt(4); }
			}
			doAction(action);
			int nextX = x;
			int nextY = y;


			Q[currentStateX][currentStateY][action] = (1 - learningRate) * Q[currentStateX][currentStateY][action] + learningRate * (board[nextY][nextX] + gamma * getMaxQValueForJ(nextX, nextY));
			if (board[currentStateY][currentStateX] == 10)
			{
				reset();
			}

			if (k * 10 % 10000 == 0)
			{

				for (int i = 0; i < board.length; i++)
				{
					for (int j = 0; j < 20; j++)
					{
						if (board[i][j] == 0)
						{
							int actions = getMaxQValue(j, i);
							if (actions == 0)
							{
								System.out.print("<");
							}
							else if (actions == 1)
							{
								System.out.print(">");
							}
							else if (actions == 2)
							{
								System.out.print("v");
							}
							else if (actions == 3)
							{
								System.out.print("^");
							}
						}
						else if (board[i][j] == - 3)
						{
							System.out.print("#");
						}
						else if (board[i][j] == 10)
						{
							System.out.print("G");
						}
					}
					System.out.println();
				}
				System.out.println();
			}

		}
	}

	private double getMaxQValueForJ(int nextX, int nextY)
	{
		double maxQValue = Q[nextX][nextY][0];
		int action = 0;
		int actions = 0;
		for (int candidate = 1; candidate < 4; candidate++)
		{

			if (Q[nextX][nextY][candidate] > maxQValue)
			{
				maxQValue = Q[nextX][nextY][candidate];
			}
		}
		return maxQValue;
	}

	private int getMaxQValue(int nextX, int nextY)
	{
		double maxQValue = Q[nextX][nextY][0];
		int action = 0;
		int actions = 0;
		for (int candidate = 1; candidate < 4; candidate++)
		{

			if (Q[nextX][nextY][candidate] > maxQValue)
			{
				maxQValue = Q[nextX][nextY][candidate];
				actions = candidate;

			}
		}
		return actions;
	}

	private void doAction(int action)
	{
		// go left
		if (action == 0)
		{
			if (x - 1 > 0 && x - 1 < outOfBoundsX)
			{
				x--;
			}
		}
		//go right
		else if (action == 1)
		{
			if (x + 1 >= 0 && x + 1 < outOfBoundsX)
			{
				x++;
			}
		}
		// go up
		else if (action == 2)
		{
			if (y + 1 >= 0 && y + 1 < outOfBoundsY)
			{
				y++;
			}
		}
		// go down
		else if (action == 3)
		{
			if (y - 1 >= 0 && y - 1 < outOfBoundsY)
			{
				y--;
			}
		}

	}


	private boolean isOutOfBounds(int x, int y)
	{
		//check to see if the agent is out of bounds and if it hit a wall
		return board[y][x] == - 25;
	}

	public static void main(String[] args)
	{
		Qlearning learn = new Qlearning();
		learn.QLearner(10000000);
		System.out.println();
	}

}