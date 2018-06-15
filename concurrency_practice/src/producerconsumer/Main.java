package producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static final String EOF = "EOF";

	public static void main(String[] args) {
		List<String> buffer = new ArrayList<>();
		ReentrantLock bufferLock = new ReentrantLock();
		MyProducer myProducer = new MyProducer(buffer, bufferLock);
		MyConsumer myConsumer1 = new MyConsumer(buffer, bufferLock);
		new Thread(myProducer).start();
		new Thread(myConsumer1).start();
		// new Thread(myConsumer2).start();
	}

}

class MyProducer implements Runnable {
	private List<String> buffer;
	private ReentrantLock bufferLock;

	public MyProducer(List<String> buffer, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.bufferLock = bufferLock;
	}

	@Override
	public void run() {
		Random random = new Random();
		String nums[] = { "1", "2", "3", "4", "5", "6", "7" };

		for (String num : nums) {
			try {
				System.out.println("Adding the num to buffer " + num);
				bufferLock.lock();
				try {
					buffer.add(num);
				} finally {
					bufferLock.unlock();
				}
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted.");
				e.printStackTrace();
			}
		}
		System.out.println("Adding EOF and Exiting");
		bufferLock.lock();
		try {
			buffer.add("EOF");
		} finally {
			bufferLock.unlock();
		}
	}

}

class MyConsumer implements Runnable {

	private List<String> buffer;
	private ReentrantLock bufferLock;

	public MyConsumer(List<String> buffer, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.bufferLock = bufferLock;
	}

	@Override
	public void run() {
		int counter = 0;
		while (true) {
			if (bufferLock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					System.out.println("The counter "+counter);
					counter = 0;
					if (buffer.get(0).equals("EOF")) {
						System.out.println("Exiting from consumer...");
						break;
					} else {
						System.out.println("Removed " + buffer.remove(0));
					}
				} finally {
					bufferLock.unlock();
				}
			} else {
				counter++;
			}
		}
	}

}
