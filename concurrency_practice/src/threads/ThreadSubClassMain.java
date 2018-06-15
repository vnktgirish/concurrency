package threads;

public class ThreadSubClassMain implements A{

	public static void main(String[] args) {
		A a = new ThreadSubClassMain();
		a.enter();
		a.how();
		A.staticM();
	}
	
	public void enter() {
		System.out.println("in enter class");
	}
	
	public static void staticM() {
		System.out.println("in class staticM");
	}
	
	@Override
	public void how() {
		System.out.println("how");
		
	}

}

interface A {
	void how();
	default public void enter() {
		System.out.println("default");
	}
	
	static void staticM() {
		System.out.println("staticM");
	}
}

class B implements A {

	@Override
	public void how() {
		// TODO Auto-generated method stub
		
	}
	
}

