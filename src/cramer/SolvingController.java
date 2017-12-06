package cramer;

public class SolvingController {
	public static void main(String[] args){
		double[][] matrixElements = {{4,2,0,-1},{-1,1,-1,3},{0,1,-1,0},{3,-4,2,2}};

		SquareMatrix squareMatrix = new SquareMatrix(matrixElements);

		System.out.println(squareMatrix.getDeterminant());

		// 去年のアドベントカレンダー
		// http://interprism.hatenablog.com/entry/dirichlet_problem_by_java
		// のディリクレ問題
		// ガウスの消去法ならば解くのに1/100秒もかからないが、クラメルの公式では2秒程度かかる
		double[][] heatPropagation = {
				{4, -1, 0, -1, 0, 0, 0, 0, 0},
				{-1, 4, -1, 0, -1, 0, 0, 0, 0},
				{0, -1, 4, 0, 0, -1, 0, 0, 0},
				{-1, 0, 0, 4, -1, 0, -1, 0, 0},
				{0, -1, 0, -1, 4, -1, 0, -1, 0},
				{0, 0, -1, 0, -1, 4, 0, 0, -1},
				{0, 0, 0, -1, 0, 0, 4, -1, 0},
				{0, 0, 0, 0, -1, 0, -1, 4, -1},
				{0, 0, 0, 0, 0, -1, 0, -1, 4}
				};

		double[] heatImport = {5,5,5,0,0,0,0,0,0};

		Equation stickHeat = new Equation(heatPropagation, heatImport);

		long heatStart = System.nanoTime();
		double[] heatDistribution = stickHeat.solveBySimpleCramersRule();
		long heatEnd = System.nanoTime();

		System.out.println(heatDistribution[3]);

		System.out.println("-------time-------");
		System.out.println(heatEnd-heatStart);
	}
}
