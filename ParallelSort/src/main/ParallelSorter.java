package main;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelSorter<T extends Comparable<T>> extends RecursiveTask<T[]> {

	private static final long serialVersionUID = 1L;
	
	public static final int THRESHOLD = 20;
	private final T[] ts;
	
	public static <V extends Comparable<V>> void sort(V[] vs){
		ForkJoinTask<V[]> t = new ParallelSorter<>(vs);
		V[] out = new ForkJoinPool().invoke(t);
		System.arraycopy(out, 0, vs, 0, vs.length);
	}
	
	private ParallelSorter(T[] ts){
		this.ts = ts;
	}
	
	@SuppressWarnings("unchecked")
	public T[] compute() {
		if (ts.length <= THRESHOLD) {
			return insertionSort(ts);
		} else {
			T[] t1, t2;
			t1 = (T[]) new Comparable[ts.length / 2];
			t2 = (T[]) new Comparable[ts.length - ts.length / 2];

			System.arraycopy(ts, 0, t1, 0, t1.length);
			ParallelSorter<T> s1 = new ParallelSorter<T>(t1);
			s1.fork();
			
			System.arraycopy(ts, t1.length, t2, 0, t2.length);
			ParallelSorter<T> s2 = new ParallelSorter<T>(t2);

			return merge(s1.join(), s2.compute());
		}
	}

	@SuppressWarnings("unchecked")
	private T[] merge(T[] t1, T[] t2) {
		int t1Index = 0, t2Index = 0;
		T[] out = (T[]) new Comparable[t1.length + t2.length];
		for (int i = 0; i < out.length; i++) {
			if (t1Index == t1.length) {
				out[i] = t2[t2Index++];
				continue;
			}
			if (t2Index == t2.length) {
				out[i] = t1[t1Index++];
				continue;
			}
			if (t1[t1Index].compareTo(t2[t2Index]) <= 0) {
				out[i] = t1[t1Index++];
			} else {
				out[i] = t2[t2Index++];
			}
		}
		return out;
	}

	private T[] insertionSort(T[] ts) {
		for (int i = 1; i < ts.length; i++) {
			T x = ts[i];
			int j = i;
			while (j > 0 && ts[j - 1].compareTo(x) > 0) {
				ts[j] = ts[j - 1];
				j--;
			}
			ts[j] = x;
		}
		return ts;
	}
	
}
