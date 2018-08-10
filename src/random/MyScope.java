package random;

public class MyScope {
	int a = 2;
	static int b = 3;
	public static void main(String[] args) {
		MyScope myInstance = new MyScope();
		MyScope myOtherInstance = new MyScope();
		double a = 3.14;
		double b = 2.7;
		System.out.println(MyScope.b); // 3
		System.out.println(a); // 3.14
		System.out.println(myInstance.b); // 3
		myInstance.b = 7;
		System.out.println(myInstance.b); // 7
		System.out.println(myOtherInstance.b);
		myInstance.a = 88;
		System.out.println(myInstance.a);
		System.out.println(myOtherInstance.a);
		System.out.println(b);
	}
}
