package messages;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Message message = new Message();
		Writer writer = new Writer(message);
		new Thread(writer).start();
		Reader reader = new Reader(message);
		new Thread(reader).start();
	}

}

class Message {
	private String message;
	private boolean empty = true;

	public synchronized String read() {
		while (empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = true;
		notifyAll();
		return message;
	}

	public synchronized void write(String message) {
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = false;
		this.message = message;
		notifyAll();
	}
}

class Writer implements Runnable {
	private Message message;

	public Writer(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		String messages[] = { "Venkata", "Girish", "Humpty", "Dumpty", "Wonder", "Walter" };
		Random random = new Random();

		for (String string : messages) {
			message.write(string);

			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		message.write("finished");
	}
}

class Reader implements Runnable {
	private Message message;

	public Reader(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		Random random = new Random();

		for (String latestmessage = message.read(); !latestmessage.equals("finished");latestmessage = message.read()) {
			System.out.println(latestmessage);

			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}