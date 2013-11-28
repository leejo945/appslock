package com.lj.appslock.util;

 

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private static final ThreadPool MANAGER= new ThreadPool();
	private ExecutorService service;
	
	private ThreadPool(){
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num*2);
	}
	
	
	
	public static ThreadPool getInstance(){
		return MANAGER;
	}
	
	public void addTask(Runnable runnable){
		service.execute(runnable);
	}
}
