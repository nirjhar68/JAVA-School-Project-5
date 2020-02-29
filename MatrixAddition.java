import java.util.concurrent.*;

public class MatrixAddition {
	final static int size = 2000;
	
		public static void main(String[] args) {
			System.out.println("***Testing the execution time of two matrixes***");
			System.out.println("************************************************");

			double[][] firstMatrix = new double[size][size];
			double[][] secondMatrix = new double[size][size];

			for (int i = 0; i < firstMatrix.length; i++) {
				for (int j = 0; j < secondMatrix[i].length; j++) {
					//Randomize the elements
					firstMatrix[i][j] = Math.random()*2*size;
					secondMatrix[i][j] = Math.random()*2*size;
				}
			}

			long executionTime = System.currentTimeMillis();
			//Add two matrix parallels
			addParallely(firstMatrix, secondMatrix);
			System.out.println("The execution time of adding two matrixes parallely is: " + (System.currentTimeMillis() - executionTime) + " msec");

			executionTime = System.currentTimeMillis();

			addSequentially(firstMatrix, secondMatrix);

			System.out.println("The execution time of adding two matrixes sequentially is: " + (System.currentTimeMillis() - executionTime) + " msec");

		}
		
		//Define the method to add the parallel matrixes
		public static double[][] addParallely(double[][] one, double[][] two) {

			double[][] tempMatrix = new double[one.length][two.length];
			RecursiveAction mainTask = new addSequentially(one, two, tempMatrix, 0, one.length, 0, one.length);
			ForkJoinPool pool = new ForkJoinPool();
			pool.invoke(mainTask);
			return tempMatrix;
		}

		

		private static class addSequentially extends RecursiveAction {
			private static final long serialVersionUID = 1L;
			private final static int THRESHOLD = 100;
			private double[][] a;
			private double[][] b;
			private double[][] c;
			private int x1;
			private int x2;
			private int y1;
			private int y2;



			public addSequentially(double[][] a, double[][] b, double[][] c, int x1, int x2, int y1, int y2) {
				this.a = a;
				this.b = b;
				this.c = c;
				this.x1 = x1;
				this.x2 = x2;
				this.y1 = y1;
				this.y2 = y2;
			}


			@Override
			protected void compute() {
				if (((x2 - x1) < THRESHOLD) || ((y2 - y1) < THRESHOLD)) {
					for (int i = x1; i < x2; i++) {
						for (int j = y1; j < y2; j++) {
							c[i][j] = a[i][j] + b[i][j];
						}
					}
				} else {
					int midX = (x1 + x2) / 2;
					int midY = (y1 + y2) / 2;

					invokeAll(
							new addSequentially(a, b, c, x1, midX, y1, midY),
							new addSequentially(a, b, c, midX, x2, y1, midY),
							new addSequentially(a, b, c, x1, midX, midY, y2),
							new addSequentially(a, b, c, midX, x2, midY, y2));
				}
			}
		}

		public static double[][] addSequentially(double[][] a, double[][] b) {
			double[][] result = new double[a.length][a[0].length];
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[i].length; j++) {
					result[i][j] = a[i][j] + b[i][j];
				}
			}
			return result;
		}

}
