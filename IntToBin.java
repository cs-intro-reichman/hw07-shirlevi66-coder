/** Returns the binary representation of a given integer. */
public class IntToBin {

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        System.out.println("Binary representation of " + x + " is: " + toBinary(x));
    }

    /** Returns the binary representation of the given integer, as a string of 0's and 1's.  */
    public static String toBinary(int x) {
        if (x == 0){
            return "0";    
        }
        if (x == 1){
            return "1";   
        }
        int rightdigit = x%2;
        int nextdigit = x/2;
        return toBinary(nextdigit) + rightdigit;
    }    
 }
