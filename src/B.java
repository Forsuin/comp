import java.io.*;
import java.util.StringTokenizer;

public class B {
    FastScanner s = new FastScanner(System.in);
    StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {
        new B();
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
            int n = s.nextInt();

            for(int i = 0; i < n; i++) {
                String str = "";

                for(int j = 0; j < n; j++) {
                    if(i % 2 == 0) {
                        if(j % 2 == 0) {
                            str += "##";
                        }
                        else {
                            str += "..";
                        }
                    }
                    else {
                        if(j % 2 == 0) {
                            str += "..";
                        }
                        else {
                            str += "##";
                        }
                    }
                }
                sb.append(str).append("\n").append(str).append("\n");
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