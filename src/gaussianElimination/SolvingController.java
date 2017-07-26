package gaussianElimination;

public class SolvingController {
	public static void main(String[] args){
		double[][] simultaneousEquationArray = {{1, -1, 0},{1, 1, 2}};

		Simultaneousness simultaneousness =
				new Simultaneousness(
						Simultaneousness.twoDimensionalArrayAdapter(simultaneousEquationArray)
						);

		simultaneousness.solve();
	}
}
