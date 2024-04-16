import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class fourdierolls {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int num_rolls = in.nextInt();

        ArrayList<Integer> rolls = new ArrayList<>();

        for(int i = 0; i < num_rolls; i++)
        {
            rolls.add(in.nextInt());
        }

        boolean hasDups = false;
        
        // check if duplicates already exist

        for(int i = 0; i < rolls.size() - 1; i++){
            for(int j = i + 1; j < rolls.size(); j++)
            {
                if(Objects.equals(rolls.get(i), rolls.get(j)))
                {
                    hasDups = true;

                }
            }
        }
    }
}
