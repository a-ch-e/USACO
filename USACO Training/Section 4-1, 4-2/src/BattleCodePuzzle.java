
public class BattleCodePuzzle {

	public static void main(String[] args) {
		
		String[] s = new String[]{"5DB777", "A34D45", "1B330E", "877A2B"};
		for(int shift = (int)'A' - (int)'0'; shift <= (int)'Z' - (int)'E'; shift++){
			for(String str : s){
				for(char c : str.toCharArray()){
					System.out.print((char)((int)c + shift));
				}
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------");
		}
		
	}
	
}
