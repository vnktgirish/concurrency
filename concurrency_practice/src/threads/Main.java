package threads;

public class Main {

	public static void main(String[] args) {
		final Thread anotherThread = new AnotherThread();
		anotherThread.setName("VG");
		anotherThread.start();
		
		Thread runnableThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Hello I am anonymous class runnable");
				try {
					anotherThread.join(2000);
					System.out.println("Hello I am after join");
				} catch (InterruptedException e) {
					System.out.println("runnableThread interrupted.");
					e.printStackTrace();
				}
			}
		});
		
		runnableThread.start();
		//runnableThread.interrupt();
		System.out.println("inside main leaving byee");
	}

}
