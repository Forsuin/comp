import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

// solution to :
public class Default_Lambda implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Default_Lambda(), "whatever", 1 << 26).start();
    }

    FastScanner s;
    StringBuilder sb;

    /**
     * Intialize values shared among all tests.
     * (i.e., precomputation)
     */
    void init() {
        s = new FastScanner(System.in);
        sb = new StringBuilder();
    }


    class SingleTest {
        public SingleTest(int testNumber) {

        }
    }

    public void run() {
        init();
        int numTests = s.nextInt();
        for (int i = 1; i <= numTests; i++) {
            new SingleTest(i);
        }
        System.out.print(sb);
    }
    /*
     **********VVV  PERSONAL LIBRARY  VVV**********
     */

    /**
     * C stands for "Collection". This is my v
     * @param <T> : The type of item stored in the collection
     */
    interface C<T> extends Collection<T> {

    }

    class AL<E extends Comparable<? super E>> extends ArrayList<E> implements Comparable<AL<E>>{

        public AL() {
            super();
        }
        public AL(int size) {
            super(size);
        }
        <T extends Comparable<? super T>> AL<T> map(Function<E, T> function) {
            AL<T> newList = new AL<T>();
            for (int i = 0; i < this.size(); i++) {
                newList.add(function.apply(this.get(i)));
            }
            return newList;
        }
        <T extends Comparable<? super T>> AL<T> map(BiFunction<Integer, E, T> function) {
            AL<T> newList = new AL<T>();
            for (int i = 0; i < this.size(); i++) {
                newList.add(function.apply(i, this.get(i)));
            }
            return newList;

        }


        E reduce(BiFunction<E,E,E> func) {
            E reduction = this.get(0);
            for (int i = 1; i < size(); i++) {
                reduction = func.apply(reduction, get(i));
            }
            return reduction;
        }

        E min() {
            return reduce((a, b) -> a.compareTo(b) < 0 ? a : b);
        }
        E max() {
            return reduce((a, b) -> a.compareTo(b) > 0 ? a : b);
        }

        AL<E> filter(Function<E, Boolean> condition) {
            AL<E> filtered = new AL<>();
            for (int i = 0; i < size(); i++) {
                if (condition.apply(get(i)))
                    filtered.add(get(i));
            }
            return filtered;
        }

        AL<E> filter(BiFunction<Integer, E, Boolean> condition) {
            AL<E> filtered = new AL<>();
            for (int i = 0; i < size(); i++) {
                if (condition.apply(i, get(i)))
                    filtered.add(get(i));
            }
            return filtered;
        }

        public void forEach(BiConsumer<Integer, ? super E> func) {
            for (int i = 0; i < size(); i++) {
                func.accept(i, get(i));
            }
        }

        public AL concatenate(AL<E> other) {
            AL<E> clone = clone();
            clone.addAll(other);
            return clone;
        }

        AL<AL<E>> partition(int partitionSize) {
            AL<AL<E>> part = new AL<AL<E>>();
            for (int i = 0; i < size(); i += partitionSize) {
                AL<E> sublist = new AL<E>();
                for (int j = i; j < i + partitionSize && j < size(); j++) {
                    sublist.add(get(j));
                }
                part.add(sublist);
            }
            return part;
        }

        @Override
        public void forEach(Consumer<? super E> func) {
            for (int i = 0; i < size(); i++) {
                func.accept(get(i));
            }
        }

        @Override
        public AL<E> clone() {
            AL<E> newList = new AL<>();
            for (int i = 0; i < size(); i++) {
                newList.add(get(i));
            }
            return newList;
        }
        AL<E> sorted() {
            AL<E> newList = clone();
            Collections.sort(newList);
            return newList;
        }
        AL<E> sorted(Comparator<E> comp) {
            AL<E> newList = clone();
            Collections.sort(clone(), comp);
            return newList;
        }

        AL<E> reversed() {
            AL<E> newList = clone();
            Collections.reverse(clone());
            return newList;
        }

        void output() {
            for (int i = 0; i < this.size(); i++) {
                E t = this.get(i);
                sb.append(t.toString());
                if (i < this.size() - 1) {
                    sb.append(" ");
                }
            }
        }

        @Override
        public int compareTo(AL<E> o) {
            int minSize = Integer.min(size(), o.size());
            for (int i = 0; i < minSize; i++) {
                int res = get(i).compareTo(o.get(i));
                if (res != 0) {
                    return res;
                }
            }
            return Integer.compare(size(), o.size());
        }



        /**
         * Returns result of mapping this AL this to another AL result where:
         *  The first value in result is the same as the first value in this
         *  Each subsequent value in result is obtained by applying the operator on the previous result
         *  and the next value.
         *
         *  Somewhat like reduce, but while preserving memory of intermediate results
         */
        AL<E> chain(BiFunction<E, E, E> f) {
            AL<E> result = new AL<E>();
            if (this.size() == 0) {
                return result;
            }
            result.add(this.get(0));
            for (int i = 1; i < this.size(); i++) {
                result.add(f.apply(result.get(i - 1), this.get(i)));
            }
            return result;
        }

        E last() {
            return get(size() - 1);
        }
        E first() {
            return get(0);
        }

        /**
         * In O(log n) time, returns the index of the smallest element in the list
         * whose value is at least target.
         * If the target is larger than all values in the list, returns list.size().
         */
        int smallestCeil(E target) {
            if (last().compareTo(target) < 0) {
                return size();
            }
            int low = 0; //largest element so far < target
            int hi = size() - 1; // smallest element so far >= target
            while (hi - low > 1) {
                 int mid = (hi + low) / 2;
                 E midEl = get(mid);
                 if (target.compareTo(midEl) < 0) {
                     // found better low
                     low = mid;
                 }
                 else {
                     // found better high
                     hi = mid;
                 }
            }
            return hi;
        }


        <T extends Comparable<? super T>> ALP<E, T> zip(AL<T> other) {
            int size = Integer.min(size(), other.size());
            ALP<E, T> res = new ALP<>();
            for (int i = 0; i < size; i++) {
                res.add(new Pair<E, T>(this.get(i), other.get(i)));
            }
            return res;
        }

        AL<AL<E>> groupContiguousBy(BiFunction<E, E, Boolean> cond) {
            AL<AL<E>> groups = new AL<>();
            if (this.size() == 0) {
                return groups;
            }
            AL<E> curGroup = new AL<E>();
            curGroup.add(get(0));
            for (int i = 1; i < this.size(); i++) {
                if (!cond.apply(get(i - 1), get(i))) {
                    // Start new group
                    groups.add(curGroup);
                    curGroup = new AL<>();
                }
                curGroup.add(get(i));
            }
            groups.add(curGroup);
            return groups;
        }

        // TODO: Largest ceil, smallest/largest ceil / floor

        //TODO: zip() function -- like python
        // TODO: Binary search ceil / floor would be good, with a boolean condition
        // TODO: Methods to convert back and forth to treeset.

        //TODO : Defining a general interface (my equivalent of Collection) would be good.
        // TODO : Defining a wrapping type for an array that extends AL<T> would be good.
        //      Allows for more efficiency for fixed-size data structures.
    }

    AL<Long> countsList (AL<Long> items) {
        long max = max(items);
        AL<Long> counts = zerosList((int)(max + 2));
        for (int i = 0; i < items.size(); i++) {
            int item = items.get(i).intValue();
            counts.set(item, counts.get(item) + 1);
        }
        return counts;
    }

    // Specialized over AL, in that the elements must be pairs. Useful as inverse to zip.
    class ALP<X extends Comparable<? super X>,Y extends Comparable<? super Y>> extends AL<Pair<X,Y>> {


        @Override
        AL<Pair<X, Y>> sorted() {
            return super.sorted();
        }
    }
    <X extends Comparable<? super X>, Y extends Comparable<? super Y>> AL<X> unzipX(AL<Pair<X, Y>> pairs) {
        AL<X> al = new AL<X>();
        for (int i = 0; i < pairs.size(); i++) {
            al.add(pairs.get(i).x);
        }
        return al;
    }
    <X extends Comparable<? super X>, Y extends Comparable<? super Y>> AL<Y> unzipY(AL<Pair<X, Y>> pairs) {
        AL<Y> al = new AL<Y>();
        for (int i = 0; i < pairs.size(); i++) {
            al.add(pairs.get(i).y);
        }
        return al;
    }

    // TODO : consider making generic (on element types)
    class Pair<X extends Comparable<? super X>,Y extends Comparable<? super Y>> implements Comparable<Pair<X,Y>> {
        X x;
        Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }

        public Pair<Y,X> swapped() {
            return new Pair<Y,X>(y, x);
        }

        @Override
        public int compareTo(Pair<X, Y> o) {
            int res1 = this.x.compareTo(o.x);
            if (res1 == 0) {
                return this.y.compareTo(o.y);
            }
            return res1;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                throw new NullPointerException("Comparison to null");
            }
            if (getClass() != o.getClass()) {
                throw new IllegalArgumentException("Wrong argument type for equals()");
            }
            Pair<X, Y> other = (Pair<X,Y>)o;
            return x.equals(other.x) && y.equals(other.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    final IndexedPair INF_Pair = new IndexedPair(
            Integer.MAX_VALUE, new Pair(Long.MAX_VALUE / 2, Long.MAX_VALUE / 2));
    final IndexedPair NEG_INF_Pair = new IndexedPair(
            Integer.MIN_VALUE, new Pair(Long.MIN_VALUE / 2, Long.MIN_VALUE / 2));

    public class IndexedPair extends Pair {
        int index;

        public IndexedPair(int index, Pair p) {
            super(p.x, p.y);
            this.index = index;
        }

        @Override
        public String toString() {
            return "IndexedPair{" +
                    "x=" + x +
                    ", y=" + y +
                    ", index=" + index +
                    '}';
        }

        @Override
        public IndexedPair swapped() {
            return new IndexedPair(index, super.swapped());
        }
    }

    //TODO Update these to use generic pair
//    public Comparator<IndexedPair> comparatorByXYThenIndex() {
//        return (a, b) -> {
//            int res = a.compareTo(b);
//            if (res == 0) {
//                return Integer.compare(a.index, b.index);
//            }
//            return res;
//        };
//    }
//
//    public Comparator<IndexedPair> comparatorByIndexThenXY() {
//        return (a, b) -> {
//            int res = Integer.compare(a.index, b.index);
//
//            if (res == 0) {
//                return a.compareTo(b);
//            }
//            return res;
//        };
//    }
//
//    public static Comparator<Pair> ComparatorByXThenY() {
//        return (p, q) -> {
//            int res1 = Long.compare(p.x, q.x);
//            if (res1 == 0) {
//                return Long.compare(p.y, q.y);
//            }
//            return res1;
//        };
//    }
//
//    public static Comparator<Pair> ComparatorByYThenX() {
//        return (q, p) -> {
//            int res1 = Long.compare(p.x, q.x);
//            if (res1 == 0) {
//                return Long.compare(p.y, q.y);
//            }
//            return res1;
//        };
//    }

    <T> void output(T t) {
        sb.append(t.toString());
    }

    <T> void output(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            T t = arr[i];
            sb.append(t.toString());
            if (i < arr.length - 1) {
                sb.append(" ");
            }
        }
    }

    void newline() {
        sb.append('\n');
    }

    // Generic methods compatible with lambda expressions.
    @SuppressWarnings({"unchecked"})
    <T> T[] toArray(Collection<T> arg) {
        T[] arr = (T[]) new Object[arg.size()];
        Iterator<T> it = arg.iterator();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = it.next();
        }
        return arr;
    }

    <T> ArrayList<T> toList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

    ArrayList<Character> toList(String str) {
        ArrayList<Character> chars = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            chars.add(str.charAt(i));
        }
        return chars;
    }

    ArrayList<Integer> toList(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }
        return list;
    }

    ArrayList<Long> toList(long[] arr) {
        ArrayList<Long> list = new ArrayList<>();
        for (long i : arr) {
            list.add(i);
        }
        return list;
    }

    ArrayList<Double> toList(double[] arr) {
        ArrayList<Double> list = new ArrayList<>();
        for (double i : arr) {
            list.add(i);
        }
        return list;
    }

    <T> ArrayList<T> toList(Collection<T> col) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(col);
        return list;
    }

    <T extends Comparable<? super T>> ArrayList<T> sorted(ArrayList<T> list) {
        ArrayList<T> copy = toList(list);
        Collections.sort(copy, (p, q) -> p.compareTo(q));
        return copy;
    }

    <T> ArrayList<T> sorted(ArrayList<T> list, Comparator<? super T> comp) {
        ArrayList<T> copy = toList(list);
        Collections.sort(copy, comp);
        return copy;
    }

    @SuppressWarnings({"unchecked"})
    <T> T[] copy(T[] arr) {
        T[] clone = (T[]) new Object[arr.length];
        System.arraycopy(arr, 0, clone, 0, arr.length);
        return clone;
    }

    <T extends Comparable<T>> T max(Collection<T> col) {
        T max = null;
        for (T t : col) {
            if (col == null) {
                continue;
            } else if (max == null) {
                max = t;
            } else if (max.compareTo(t) < 0) {
                max = t;
            }
        }
        return max;
    }

    <T extends Comparable<? super T>> T min(T a, T b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    <T extends Comparable<? super T>> T max(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    <T extends Comparable<T>> T min(Collection<T> col) {
        T min = null;
        for (T t : col) {
            if (col == null) {
                continue;
            } else if (min == null) {
                min = t;
            } else if (min.compareTo(t) > 0) {
                min = t;
            }
        }
        return min;
    }

    <T extends Comparable<T>> T[] sorted(T[] arr) {
        T[] copy = copy(arr);
        Collections.sort(toList(copy));
        return copy;
    }

    <T> void accept(ArrayList<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    <T> void accept(ArrayList<T> list, BiConsumer<Integer, T> consumer) {
        for (int i = 0; i < list.size(); i++) {
            consumer.accept(i, list.get(i));
        }
    }

    <T, R> ArrayList<R> apply(ArrayList<T> list, Function<T, R> func) {
        ArrayList<R> newList = new ArrayList<>();
        accept(list, t -> newList.add(func.apply(t)));
        return newList;
    }

    <T> T reduce(ArrayList<T> list, BiFunction<T,T,T> func) {
        T cur = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T next = list.get(i);
            cur = func.apply(cur, next);
        }
        return cur;
    }


    <T, R> ArrayList<R> apply(ArrayList<T> list, BiFunction<Integer, T, R> func) {
        ArrayList<R> newList = new ArrayList<>();
        accept(list, (i, t) -> newList.add(func.apply(i, t)));
        return newList;
    }

    <T> ArrayList<T> reverse(ArrayList<T> list) {
        ArrayList<T> reversed = apply(list, t -> t);
        Collections.reverse(reversed);
        return reversed;
    }

    <T> void reverseAccept(ArrayList<T> list, BiConsumer<Integer, T> consumer) {
        for (int i = list.size() - 1; i >= 0; i--) {
            consumer.accept(i, list.get(i));
        }
    }

    <T> ArrayList<T> reverseApply(ArrayList<T> list, BiFunction<Integer, T, T> func) {
        ArrayList<T> newList = new ArrayList<>();
        reverseAccept(list, (i, t) -> newList.add(func.apply(i, t)));
        return newList;
    }

    <T> T[] reverse(T[] arr) {
        return toArray(reverse(toList(arr)));
    }

    void repeat(int count, Runnable r) {
        for (int i = 0; i < count; i++) {
            r.run();
        }
    }

    final long NEG_INF = Long.MIN_VALUE;
    final long INF = Long.MAX_VALUE;

    AL<Long> zerosList(int n) {
        return fill(n, 0L);
    }

    AL<Long> negInfList(int n) {
        return fill(n, -(Long.MIN_VALUE / 2));
    }

    AL<Long> posInfList(int n) {
        return fill(n, Long.MIN_VALUE / 2);
    }

    AL<Long> fill(int size, long item) {
        AL<Long> list = new AL<>(size);
        repeat(size, () -> list.add(item));
        return list;
    }

    public void yes() {
        sb.append("YES\n");
    }

    public void no() {
        sb.append("NO\n");
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }

        public FastScanner(InputStream in) {
            this(new InputStreamReader(in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public long[] nextArrLongs(int n) {
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = this.nextLong();
            }
            return arr;
        }

        public AL<Integer> nextListInts(int n) {
            AL<Integer> list = new AL<>();
            repeat(n, () -> list.add(s.nextInt()));
            return list;
        }

        public AL<Long> nextListLongs(int n) {
            AL<Long> list = new AL<>();
            repeat(n, () -> list.add(s.nextLong()));
            return list;
        }

        public AL<Double> nextListDoubles(int n) {
            AL<Double> list = new AL<>();
            repeat(n, () -> list.add(s.nextDouble()));
            return list;
        }

        public Pair nextPair() {
            return new Pair(s.nextLong(), s.nextLong());
        }

        public AL<Pair<Long, Long>> nextListLongPairs(int n) {
            var list = new AL<Pair<Long, Long>>();
            repeat(n, () -> list.add(nextPair()));
            return list;
        }

        //TODO fix after update to make pair generic
//        public AL<IndexedPair> readNextListIndexedPairs(int n) {
//                var list = new AL<Pair>();
//                repeat(n, () -> list.add(nextPair()));
//                var list2 = new AL<IndexedPair>();
//                accept(list, (i, p) -> list2.add(new IndexedPair(i, p)));
//                return list2;
//        }

        public AL<String> nextListStrings(int n) {
            AL<String> list = new AL<>();
            repeat(n, () -> list.add(s.next()));
            return list;
        }

        public String[] nextArrStrings(int n) {
            String[] arr = new String[n];
            for (int i = 0; i < n; i++) {
                arr[i] = this.next();
            }
            return arr;
        }

        public double[] nextArrDoubles(int n) {
            double[] arr = new double[n];
            for (int i = 0; i < n; i++) {
                arr[i] = this.nextDouble();
            }
            return arr;
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }
}