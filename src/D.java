import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class D {
    FastScanner s = new FastScanner(System.in);
    StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {
        new D();
    }

    HashSet<Integer> set = new HashSet<Integer>();

    {
        for(int i = 1; i < 32; i++) {
            set.add(Integer.parseInt(Integer.toBinaryString(i)));
        }

        HashSet<Integer> newSet = new HashSet<>(set);

        do {
            newSet = new HashSet<>(set);
            for(int i : set) {
                for(int j : set) {
                    newSet.add(i * j);
                }
            }

            set = new HashSet<>(newSet);
        } while(set.size() != newSet.size());

        for(int i : set) {
            System.out.println(i);
        }
    }

    {
        // Handle multiple tests. If only 1 test, set numTests = 1
        int numTests = s.nextInt();
        for (int i = 0; i < numTests; i++) {
            new TestCase();
        }
    }
    {
        // Output all the results at once -- more efficient.
        // When solving a test case, use sb to collect output.
        System.out.print(sb.toString());
    }



    class TestCase {
        // Solve a single test case
        {
            int in = s.nextInt();

            if(set.contains(in)) {
                sb.append("YES\n");
            }
            else {
                sb.append("NO\n");

            }
        }
    }


    // Faster implementation of basic Scanner methods, using Buffered Reader.
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

        public int nextInt() {
            return Integer.parseInt(next());
        }
        public long nextLong() {
            return Long.parseLong(next());
        }
        public double nextDouble() { return Double.parseDouble(next());}

    }
}