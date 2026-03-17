public class TopDownMergeSort {
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int[] auxiliary = new int[array.length];
        sort(array, auxiliary, 0, array.length - 1);
    }

    private void sort(int[] array, int[] auxiliary, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        sort(array, auxiliary, left, middle);
        sort(array, auxiliary, middle + 1, right);

        if (array[middle] <= array[middle + 1]) {
            return;
        }

        merge(array, auxiliary, left, middle, right);
    }

    private void merge(int[] array, int[] auxiliary, int left, int middle, int right) {
        System.arraycopy(array, left, auxiliary, left, right - left + 1);

        int leftPointer = left;
        int rightPointer = middle + 1;
        int current = left;

        while (leftPointer <= middle && rightPointer <= right) {
            if (auxiliary[leftPointer] <= auxiliary[rightPointer]) {
                array[current++] = auxiliary[leftPointer++];
            } else {
                array[current++] = auxiliary[rightPointer++];
            }
        }

        while (leftPointer <= middle) {
            array[current++] = auxiliary[leftPointer++];
        }

        while (rightPointer <= right) {
            array[current++] = auxiliary[rightPointer++];
        }
    }
}
