package threads;

public class ThreadSubClass extends Thread {
	
	@Override
	public void run() {
		System.out.println("Hello thread I am from another");
	}
}
