package main;

import java.util.Arrays;
import java.util.Random;

public class Tester {

	public static final int size = 1000000;
	public static final boolean parallel = true;

	public static void main(String[] args) {
		Integer[] data = genData(size);
		long start = System.nanoTime();
		if (parallel) {
			ParallelSorter.sort(data);
		} else {
			Arrays.sort(data);
		}
		System.out.println((System.nanoTime()-start)/1_000_000_000f);
	}

	private static final Random r = new Random();

	public static Integer[] genData(int length) {
		Integer[] data = new Integer[length];
		for (int i = 0; i < length; i++) {
			data[i] = r.nextInt();
		}
		return data;
	}

	public static <T extends Comparable<T>> boolean isSorted(T[] ts) {
		for (int i = 1; i < ts.length; i++) {
			if (ts[i - 1].compareTo(ts[i]) > 0) {
				return false;
			}
		}
		return true;
	}

	public static void print(Object... objects) {
		for (Object o : objects) {
			System.out.print(o);
			System.out.print(',');
		}
		System.out.println();
	}

	public static void printArray(Object[] objects) {
		System.out.print(Arrays.toString(objects));
	}

}
