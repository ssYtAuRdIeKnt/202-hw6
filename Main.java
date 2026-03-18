import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 20000, 50000};
        Random random = new Random();

        System.out.println("Size\tTop-Down (ms)\tBottom-Up (ms)");

        for (int size : sizes) {
            int[] original = new int[size];
            for (int i = 0; i < size; i++) {
                original[i] = random.nextInt(100000);
            }

            int[] a1 = Arrays.copyOf(original, original.length);
            int[] a2 = Arrays.copyOf(original, original.length);

            long start1 = System.nanoTime();
            TopDownMergeSort.sort(a1);
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            BottomUpMergeSort.sort(a2);
            long end2 = System.nanoTime();

            double topDownTime = (end1 - start1) / 1_000_000.0;
            double bottomUpTime = (end2 - start2) / 1_000_000.0;

            System.out.printf("%d\t%.3f\t\t%.3f%n", size, topDownTime, bottomUpTime);
        }
    }
}