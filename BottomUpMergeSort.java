public class BottomUpMergeSort {
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int length = array.length;
        int[] auxiliary = new int[length];

        for (int size = 1; size < length; size *= 2) {
            for (int left = 0; left < length - size; left += size * 2) {
                int middle = left + size - 1;
                int right = Math.min(left + size * 2 - 1, length - 1);

                if (array[middle] <= array[middle + 1]) {
                    continue;
                }

                merge(array, auxiliary, left, middle, right);
            }
        }
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
