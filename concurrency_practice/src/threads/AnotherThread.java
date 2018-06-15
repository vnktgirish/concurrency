package threads;

public class AnotherThread extends Thread {
	public void run() {
		System.out.println("first line in another thread "+this.getName());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("Another theread woke me up");
			e.printStackTrace();
		}
		System.out.println("finally awake after 3 secs");
	}
}
