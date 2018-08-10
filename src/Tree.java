
public class Tree {
	static boolean tree(boolean aa, boolean ab, boolean ac, boolean ba, boolean bb, boolean bc, boolean ca, boolean cb,
			boolean cc) {
		if (!(aa || ab || ac || ba || bb || bc || ca || cb || cc))
			return false; // not connected
		
		//test for cycles
		if (aa || bb || cc)
			return false;
		if ((ab && bc && ca) || (ac && cb && ba))
			return false;
		
		return true;
	}
	
	public static void main(String[] args){
		System.out.println(tree(false, false, false, false, false, false, false, false, false));
		System.out.println(tree(false, true, true, false, false, false, false, false, false));
		System.out.println(tree(false, true, false, false, false, true, false, false, false));
	}
}
