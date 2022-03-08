import java.util.*; 

public class Oblig3{
    static int hNr = 100000;
    static int size = 100000;
    static Random random = new Random();

    public static void main(String[] args) {
        int[] ar = new int[size];

        for(int index = 0; index < size; index++){
            ar[index] = random.nextInt(hNr);
        }

        int[] a1 = ar.clone();

        //fra random
        long t1 = System.nanoTime();
        Arrays.sort(a1);
        double tid = (System.nanoTime() - t1) / 1000000.0;
        System.out.println("Sortering med sort() fra random\nTid: " + tid);

        //fra sortert 
        t1 = System.nanoTime();
        Arrays.sort(a1);
        tid = (System.nanoTime() - t1) / 1000000.0;
        System.out.println("Sortering med sort() fra sortert\nTid: " + tid);

        //klone a1 til a2 og reverse den 
        int[] a2 = a1.clone();
        rev(a1);

        int[] s2 = a2.clone();
        //fra reversert
        t1 = System.nanoTime();
        Arrays.sort(s2);
        tid = (System.nanoTime() - t1) / 1000000.0;
        System.out.println("Sortering med sort() fra reversert\nTid: " + tid);

        int[] a3 = ar.clone();

        int[] b1 = a1.clone();
        int[] b2 = a2.clone();
        int[] b3 = ar.clone();
        int[] c1 = a1.clone();
        int[] c2 = a2.clone();
        int[] c3 = ar.clone();
        int[] d1 = a1.clone();
        int[] d2 = a2.clone();
        int[] d3 = ar.clone();
        int[] e1 = a1.clone();
        int[] e2 = a2.clone();
        int[] e3 = ar.clone();


        testSelectionSort(a1, a2, a3);
        testInsertionSort(b1, b2, b3);
        testQuickSort(c1, c2, c3);
        testHeapSort(e1, e2, e3);
        testBucketSort(d1, d2, d3);

    }

    private static void rev(int[] a){
        for(int i = 0; i < a.length / 2; i++){
            int temp = a[i];
            a[i] = a[a.length - i - 1];
            a[a.length - i - 1] = temp;
        }
    }

    private static void print(int[] a){
        System.out.println();
        for(int i : a){
            System.out.println(i + " ");
        }
    }


    private static void selectionSort(int[] array){
        int aLength = array.length;

        for(int i = 0; i < aLength -1; i++){
            int index = i;

            for(int j = i +1; j < aLength; j++){
                if(array[j] < array[index]){
                    index = j;
                }
            }
        int tmp = array[index];
        array[index] = array[i];
        array[i] = tmp;
        }
    }

    private static void insertionSort(int[] array){
        int aLength = array.length;
        for(int i = 1; i < aLength; i++){
            int j = i-1;
            int key = array[i];
            while(j >= 0 && array[j] > key){
                array[j+1] = array[j];
                j = j-1;
            }
        array[j+1] = key;
        }
    }

    private static void quickSort(int[] array, int first, int last){
        if(first >= last){return;}

        int par = inPlacePartition(array, first, last);
        quickSort(array, first, par-1);
        quickSort(array, par +1, last);
    }

    private static int inPlacePartition(int[] array, int first, int last){
        int pivot = random.nextInt(last + 1 - first) + first;
        int tmp = array[pivot];
        array[pivot] = array[last];
        array[last] = tmp;
        int p = array[last];
        int l = first;

        pivot = last -1;
        while(l <= pivot){
            while(l <= pivot && array[l] <= p){
                l = l+1;
            }
            while(pivot >= l && array[pivot] >= p){
                pivot = pivot -1;
            }
            if(l < pivot){
                tmp = array[l];
                array[l] = array[pivot];
                array[pivot] = tmp;
            }
        }
        tmp = array[l];
        array[l] = array[last];
        array[last] = tmp;
        return l; 
    }

    private static void heapSort(int[] a1){
        int n = a1.length;

        //Bygger heap
        for(int i = n/2-1; i >= 0; i--){
            heapify(a1, n, i);
        }

        //tar ut ett og ett element fra heapen
        for(int i = n-1; i>=0; i--){
            int temp = a1[0];
            a1[0] = a1[i];
            a1[i] = temp;

            heapify(a1, i, 0);
        }
    }

    private static void heapify(int[] a1, int n, int i){
        int largest = i; 
        int left = 2*i +1;
        int right = 2*i +2;

        if(left < n && a1[left] > a1[largest]){
            largest = left;
        }

        if (right < n && a1[right] > a1[largest]) {
            largest = right;
        }

        if(largest != i){
            int swap = a1[i];
            a1[i] = a1[largest];
            a1[largest] = swap;

            heapify(a1, n, largest);
        }
    }

    private static void bucketSort(int[] array, int max){
        int[] bucket = new int[max + 1];

        for(int i = 0; i < bucket.length; i++){
            bucket[i] = 0;
        }

        for(int i = 0; i < array.length; i++){
            bucket[array[i]]++;
        }

        int index = 0;
        for(int i = 0; i < bucket.length; i++){
            for(int j = 0; j < bucket[i]; j++){
                array[index++] = i;
            }
        }
    }

    

    private static void testSelectionSort(int[] a1, int[] a2, int[] a3){
        System.out.println("\n----------selectionSort: ");

        long t1 = System.nanoTime();
        selectionSort(a1);
        double tid = (System.nanoTime() - t1)/1000000.0;
        System.out.println("\nTid: " + tid);

        long t2 = System.nanoTime();
        selectionSort(a2);
        tid = (System.nanoTime() - t2) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t3 = System.nanoTime();
        selectionSort(a1);
        tid = (System.nanoTime() - t3) / 1000000.0;
        System.out.println("\nTid: " + tid);
    }

    private static void testInsertionSort(int[] a1, int[] a2, int[] a3){
        System.out.println("\n----------insertionSort: ");
        
        long t1 = System.nanoTime();
        insertionSort(a1);
        double tid = (System.nanoTime() - t1) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t2 = System.nanoTime();
        insertionSort(a2);
        tid = (System.nanoTime() - t2) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t3 = System.nanoTime();
        insertionSort(a1);
        tid = (System.nanoTime() - t3) / 1000000.0;
        System.out.println("\nTid: " + tid);
    }

    private static void testQuickSort(int[] a1, int[] a2, int[] a3){
        System.out.println("\n----------quickSort: ");

        long t1 = System.nanoTime();
        quickSort(a1, 0, a1.length-1);
        double tid = (System.nanoTime() - t1)/1000000.0;
        System.out.println("\nTid: " + tid);

        long t2 = System.nanoTime();
        quickSort(a2, 0, a2.length - 1);
        tid = (System.nanoTime() - t2) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t3 = System.nanoTime();
        quickSort(a3, 0, a3.length - 1);
        tid = (System.nanoTime() - t3) / 1000000.0;
        System.out.println("\nTid: " + tid);
    }

    private static void testHeapSort(int[] a1, int[] a2, int[] a3){
        System.out.println("\n----------heapSort: ");

        long t1 = System.nanoTime();
        heapSort(a1);
        double tid = (System.nanoTime()-t1)/1000000.0;
        System.out.println("\nTid: " + tid);

        long t2 = System.nanoTime();
        heapSort(a2);
        tid = (System.nanoTime() - t2) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t3 = System.nanoTime();
        heapSort(a3);
        tid = (System.nanoTime() - t3) / 1000000.0;
        System.out.println("\nTid: " + tid);

    }

    private static void testBucketSort(int[] a1, int[] a2, int[] a3){
        System.out.println("\n----------bucketSort: ");

        long t1 = System.nanoTime();
        bucketSort(a1, hNr);
        double tid = (System.nanoTime()-t1)/1000000.0;
        System.out.println("\nTid: " + tid);

        long t2 = System.nanoTime();
        bucketSort(a2, hNr);
        tid = (System.nanoTime() - t2) / 1000000.0;
        System.out.println("\nTid: " + tid);

        long t3 = System.nanoTime();
        bucketSort(a3, hNr);
        tid = (System.nanoTime() - t3) / 1000000.0;
        System.out.println("\nTid: " + tid);


    }
}