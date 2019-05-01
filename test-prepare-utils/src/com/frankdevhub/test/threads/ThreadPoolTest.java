package com.frankdevhub.test.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ArrayList<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
		ArrayList<Callable<Boolean>> callableList = new ArrayList<Callable<Boolean>>();

		BlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 50, TimeUnit.MILLISECONDS, bq);
		for (int i = 0; i <= 100; i++) {
			int index = i;
			Callable<Boolean> callable = new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					System.out
							.println(String.format("Thread is running:%s,%s", Thread.currentThread().getName(), index));
					if ((index + 2) % 2 == 0) {
						// TimeUnit.SECONDS.sleep(1);
					}

					return Boolean.TRUE;
				}
			};

			FutureTask<Boolean> futureTask = new FutureTask<Boolean>(callable);
			taskList.add(futureTask);
			callableList.add(callable);
			threadPoolExecutor.execute(futureTask);
			System.out.println("is Done:" + futureTask.isDone());
			System.out.println("Result:" + futureTask.get());
		}
		System.out.println("========================");

		for (FutureTask<Boolean> future : taskList) {
			System.out.println(future.isDone());
		}

		threadPoolExecutor.shutdown();
	}
}
