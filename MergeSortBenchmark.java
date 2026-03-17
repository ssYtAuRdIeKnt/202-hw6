import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class MergeSortBenchmark {
    private static final int[] INPUT_SIZES = {10000, 20000, 50000, 100000, 200000, 400000, 800000, 1200000};
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 5;
    private static final long RANDOM_SEED = 20260317L;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);

        TopDownMergeSort topDownMergeSort = new TopDownMergeSort();
        BottomUpMergeSort bottomUpMergeSort = new BottomUpMergeSort();
        Random random = new Random(RANDOM_SEED);

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("size,top_down_ms,bottom_up_ms\n");

        System.out.printf("%-12s %-18s %-18s %-12s%n", "Size", "Top-down (ms)", "Bottom-up (ms)", "Faster");
        System.out.println("----------------------------------------------------------------");

        for (int size : INPUT_SIZES) {
            warmUp(topDownMergeSort, bottomUpMergeSort, random, size);

            double topDownTotal = 0.0;
            double bottomUpTotal = 0.0;

            for (int run = 0; run < MEASURED_RUNS; run++) {
                int[] data = createRandomArray(random, size);
                int[] topDownInput = Arrays.copyOf(data, data.length);
                int[] bottomUpInput = Arrays.copyOf(data, data.length);

                topDownTotal += measure(topDownMergeSort, topDownInput);
                bottomUpTotal += measure(bottomUpMergeSort, bottomUpInput);
            }

            double topDownAverage = topDownTotal / MEASURED_RUNS;
            double bottomUpAverage = bottomUpTotal / MEASURED_RUNS;
            String faster = topDownAverage <= bottomUpAverage ? "Top-down" : "Bottom-up";

            System.out.printf("%-12d %-18.3f %-18.3f %-12s%n", size, topDownAverage, bottomUpAverage, faster);
            csvBuilder.append(size)
                    .append(',')
                    .append(String.format(Locale.US, "%.3f", topDownAverage))
                    .append(',')
                    .append(String.format(Locale.US, "%.3f", bottomUpAverage))
                    .append('\n');
        }

        Path outputPath = Path.of("benchmark_results.csv");
        Files.writeString(outputPath, csvBuilder.toString());
        System.out.println("\nResults were also written to: " + outputPath.toAbsolutePath());
    }

    private static void warmUp(TopDownMergeSort topDownMergeSort, BottomUpMergeSort bottomUpMergeSort, Random random, int size) {
        for (int run = 0; run < WARMUP_RUNS; run++) {
            int[] data = createRandomArray(random, size);
            int[] topDownInput = Arrays.copyOf(data, data.length);
            int[] bottomUpInput = Arrays.copyOf(data, data.length);
            topDownMergeSort.sort(topDownInput);
            bottomUpMergeSort.sort(bottomUpInput);
            verifySorted(topDownInput);
            verifySorted(bottomUpInput);
        }
    }

    private static double measure(TopDownMergeSort sorter, int[] input) {
        long start = System.nanoTime();
        sorter.sort(input);
        long end = System.nanoTime();
        verifySorted(input);
        return (end - start) / 1_000_000.0;
    }

    private static double measure(BottomUpMergeSort sorter, int[] input) {
        long start = System.nanoTime();
        sorter.sort(input);
        long end = System.nanoTime();
        verifySorted(input);
        return (end - start) / 1_000_000.0;
    }

    private static int[] createRandomArray(Random random, int size) {
        int[] array = new int[size];
        for (int index = 0; index < size; index++) {
            array[index] = random.nextInt();
        }
        return array;
    }

    private static void verifySorted(int[] array) {
        for (int index = 1; index < array.length; index++) {
            if (array[index - 1] > array[index]) {
                throw new IllegalStateException("The array is not sorted correctly.");
            }
        }
    }
}
