public class BottomUpMergeSort {
    public static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];

        for (int size = 1; size < n; size *= 2) {
            for (int left = 0; left < n - size; left += size * 2) {
                int mid = left + size - 1;
                int right = Math.min(left + size * 2 - 1, n - 1);
                merge(a, aux, left, mid, right);
            }
        }
    }

    private static void merge(int[] a, int[] aux, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            aux[i] = a[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (aux[i] <= aux[j]) {
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j++];
            }
        }

        while (i <= mid) {
            a[k++] = aux[i++];
        }

        while (j <= right) {
            a[k++] = aux[j++];
        }
    }
}