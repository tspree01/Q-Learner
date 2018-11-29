import java.util.Random;

class Qlearning
{
	private double ε = 0.05;
	private double gamma = 0.97;
	private double learningRate;
	private double[][][] Q = new double[20][10][4];
	private int x;
	private int y;
	private int outOfBoundsX = 20;
	private int outOfBoundsY = 10;
	private int action;
	private double reward;
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
	}

	private void reset()
	{
		this.x = 0;
		this.y = 0;

	}

	private void QLearner(int numberOfGames)
	{
		for (int k = 0; k < 1000000; k++)
		{
			// Pick an action
			int currentStateX;
			int currentStateY;
			currentStateX = x;
			currentStateY = y;
			int action = 0;
			Random rand = new Random();
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


			Q[x][y][action] = (1 - learningRate) * Q[x][y][action] + learningRate * (board[nextX][nextY] + gamma * getMaxQValue(nextX, nextY));

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

	}

}