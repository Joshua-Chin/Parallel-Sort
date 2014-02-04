package main;

public class Sorter {

	public static final int THRESHOLD = 20;

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> T[] sort(T[] ts) {
		if (ts.length <= THRESHOLD) {
			return insertionSort(ts);
		} else {
			T[] t1, t2;
			t1 = (T[]) new Comparable[ts.length / 2];
			t2 = (T[]) new Comparable[ts.length - ts.length / 2];

			System.arraycopy(ts, 0, t1, 0, t1.length);
			sort(t1);

			System.arraycopy(ts, t1.length, t2, 0, t2.length);
			sort(t2);

			return merge(t1, t2);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] merge(T[] t1, T[] t2) {
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

	private static <T extends Comparable<T>> T[] insertionSort(T[] ts) {
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

	public static void print(Object... strings) {
		for (Object s : strings) {
			System.out.print(s);
			System.out.print(",");
		}
		System.out.println();
	}
}
