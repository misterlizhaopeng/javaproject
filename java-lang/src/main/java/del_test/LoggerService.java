package del_test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LoggerService {


	public static void main(String[] args) throws InterruptedException{
		LoggerService loggerService=new LoggerService(new StringWriter());
		loggerService.start();
		loggerService.log("error");


		// ...
		loggerService.stop();

	}

	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	private boolean isShutdown;
	private int reservations;

	public LoggerService(Writer writer) {
		this.queue = new LinkedBlockingQueue<>();
		this.loggerThread = new LoggerThread();
		this.writer = new PrintWriter(writer);
	}

	public void start() {
		loggerThread.start();
	}

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}

	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown) {
				throw new IllegalStateException();
			}
			++reservations;
		}
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					synchronized (LoggerService.this) {
						if (isShutdown && reservations == 0) {
							break;
						}
					}

					String msg = queue.take();
					synchronized (LoggerService.this) {
						--reservations;
					}
					
					writer.println(msg);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					writer.close();;
				}

			}

		}
	}

}