
public class InterfaceTest {
	public static void main(String[] args){
		InterfaceTester obj = new sub();
		((sub) obj).A();
		obj.B();
	}
}


class sup {
	public void C(){}
	public void B(){
		System.out.println("super");
	}
}

class sub extends sup implements InterfaceTester {
	
	
	public void A(){}
	
	
}
