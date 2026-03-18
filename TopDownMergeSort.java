public class TopDownMergeSort {
    public static void sort(int[] a) {
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] aux, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        sort(a, aux, left, mid);
        sort(a, aux, mid + 1, right);
        merge(a, aux, left, mid, right);
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