package com.tlcsdm.eclipse.methodsorter.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class MethodSorterTest {

	private String name;
	private static int counter = 0;
	private List<String> data = new ArrayList<>();

	static {
		System.out.println("Static block");
	}

	{
		System.out.println("Instance init block");
	}

	// 构造方法
	public MethodSorterTest(String name) {
		this.name = name;
	}

	public MethodSorterTest() {
		this("default");
	}

	// 静态方法
	public static void log(String msg) {
		System.out.println("LOG: " + msg);
	}

	private static int nextCount() {
		return ++counter;
	}

	// 公共方法
	public void process() {
		System.out.println("Processing " + name);
	}

	public void execute() {
		System.out.println("Executing");
		helperMethod();
	}

	// 私有方法
	private void helperMethod() {
		System.out.println("Helper running");
	}

	private int calculate(int a, int b) {
		return a + b;
	}

	// 重载方法
	public int calculate(int a) {
		return calculate(a, 10);
	}

	// 受保护方法
	protected void finalizeTask() {
		System.out.println("Finalize");
	}

	// 泛型方法
	public <T> T identity(T input) {
		return input;
	}

	// 带 lambda
	public void lambdaExample() {
		Function<String, String> f = s -> "Hello " + s;
		System.out.println(f.apply(name));
	}

	// 匿名内部类
	public void anonymousExample() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Anonymous running");
			}
		};
		r.run();
	}

	// 静态内部类
	public static class Inner {
		public void innerMethod() {
			System.out.println("Inner method");
		}

		private void innerHelper() {
			System.out.println("Inner helper");
		}
	}

	// 普通内部类
	class Local {
		public void localMethod() {
			System.out.println("Local class method");
		}
	}
}
