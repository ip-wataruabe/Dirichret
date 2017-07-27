package gaussianElimination;

public class SolvingController {
	public static void main(String[] args){
		//x-y=0,x+y=2
		double[][] simultaneousEquationArray = {{1, -1, 0},{1, 1, 2}};

		Simultaneousness simultaneousness =
				new Simultaneousness(
						Simultaneousness.twoDimensionalArrayAdapter(simultaneousEquationArray)
						);

		simultaneousness.solve();

		//何も考えずに消去法で解くと計算機が計算ミスをする例･･･のはずだった。
		//double型は浮動小数点型として優秀すぎて誤差が大きくなってくれない。4桁で計算すると誤差が大きくなるらしい。
		double[][] stewart = {{0.001, 2, 3, 5.001},{-1, 3.712, 4.623, 7.335},{-2, 1.072, 5.643, 4.715}};

		Simultaneousness stewartSimultaneousness =
				new Simultaneousness(
						Simultaneousness.twoDimensionalArrayAdapter(stewart)
						);

		stewartSimultaneousness.solve();
	}
}
