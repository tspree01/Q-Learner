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
	static private Random rand;
	private double[][] board = {
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, - 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
	};

	public Qlearning()
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
			int currentStateX;
			int currentStateY;
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
				action = 0;
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


			Q[currentStateX][currentStateY][action] = (1 - learningRate) * Q[currentStateX][currentStateY][action] + learningRate * (board[nextX][nextY] + gamma * getMaxQValue(nextX, nextY));
			if (board[nextX][nextY] == 100)
			{
				reset();
			}
		}
	}

	private double getMaxQValue(int nextX, int nextY)
	{
		double maxQValue = 0;
		int action = 0;
		for (int candidate = 0; candidate < 4; candidate++)
		{

			if (Q[x][y][candidate] > Q[x][y][action])
			{ maxQValue = Q[x][y][candidate]; }
		}
		return maxQValue;
	}

	private void doAction(int action)
	{
		// go left
		if (action == 1)
		{
			if (! isOutOfBounds(x, y))
			{
				x--;
			}
		}
		//go right
		else if (action == 2)
		{
			if (! isOutOfBounds(x, y))
			{
				x++;
			}
		}
		// go down
		else if (action == 3)
		{
			if (! isOutOfBounds(x, y))
			{
				y--;
			}
		}
		// go up
		else if (action == 4)
		{
			if (! isOutOfBounds(x, y))
			{
				y++;
			}
		}
	}


	private boolean isOutOfBounds(int x, int y)
	{
		//check to see if the agent is out of bounds and if it hit a wall
		if (x < outOfBoundsX && x >= 0 && y < outOfBoundsY && y >= 0)
		{
			return false;
		}
		return board[x][y] == - 25;
	}

	public static void main(String[] args)
	{
		Qlearning learn = new Qlearning();

	}

}