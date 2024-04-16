import java.util.ArrayList;
import java.util.Scanner;

public class triplesevens {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean isBad = false;

        int n = in.nextInt();
        ArrayList<Integer> nums = new ArrayList<>();

        for(int i = 0; i < 3; i++)
        {
            nums.clear();

            for(int j = 0; j < n; j++)
            {
                nums.add(in.nextInt());
            }

            if(!nums.contains(7)) {
                isBad = true;
                break;
            }
        }

        if(!isBad)
        {
            System.out.println(777);
        }
        else {
            System.out.println(0);
        }
    }
}