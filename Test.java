import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String q = (args.length == 0) ? "ALL" : args[0].trim();

        boolean ok = true;

        switch (q) {
            case "INT_TO_BIN":
                ok = testIntToBin();
                break;
            case "IS_PALINDROME":
                ok = testPalindrome();
                break;
            case "BINOMIAL":
                ok = testBinomial(false);
                break;
            case "MEMO_BINOMIAL":
                ok = testBinomial(true);
                break;
            case "IS_SORTED":
                ok = testIsSorted();
                break;
            case "ALL":
            default:
                ok = testIntToBin()
                        && testPalindrome()
                        && testBinomial(false)
                        && testBinomial(true)
                        && testIsSorted();
                break;
        }

        if (ok) {
            System.out.println("All test cases have passed");
        }
        // אם נכשל—לא מדפיסים כלום (כדי שהבודק יראה mismatch)
    }

    // ---------- Helpers (reflection) ----------
    private static Object callStatic(String className, String methodName, Class<?>[] sig, Object... args) {
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getDeclaredMethod(methodName, sig);
            m.setAccessible(true);
            return m.invoke(null, args);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean eq(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    // ---------- Tests ----------
    private static boolean testIntToBin() {
        // expects: IntToBin.toBinary(int) -> String
        int[] xs = {0, 1, 2, 3, 4, 5, 8, 10, 15, 16, 31};
        String[] expected = {"0","1","10","11","100","101","1000","1010","1111","10000","11111"};

        for (int i = 0; i < xs.length; i++) {
            Object out = callStatic("IntToBin", "toBinary", new Class<?>[]{int.class}, xs[i]);
            if (!(out instanceof String)) return false;
            if (!eq(out, expected[i])) return false;
        }
        return true;
    }

    private static boolean testPalindrome() {
        // expects: Palindrome.isPalindrome(String) -> boolean
        String[] yes = {"", "a", "aa", "aba", "abba", "racecar", "1221"};
        String[] no  = {"ab", "abc", "abca", "hello", "123"};

        for (String s : yes) {
            Object out = callStatic("Palindrome", "isPalindrome", new Class<?>[]{String.class}, s);
            if (!(out instanceof Boolean)) return false;
            if (!((Boolean) out)) return false;
        }
        for (String s : no) {
            Object out = callStatic("Palindrome", "isPalindrome", new Class<?>[]{String.class}, s);
            if (!(out instanceof Boolean)) return false;
            if (((Boolean) out)) return false;
        }
        return true;
    }

    private static boolean testBinomial(boolean memo) {
        // We try these options (in this order):
        // MemoBinomial.binomial(n,k) OR Binomial.memoBinomial(n,k) OR Binomial.binomial(n,k)
        String[] classes = memo ? new String[]{"MemoBinomial", "Binomial", "Binomial"} : new String[]{"Binomial"};
        String[] methods = memo ? new String[]{"binomial", "memoBinomial", "binomial"} : new String[]{"binomial"};

        int[][] cases = {
                {0,0,1},
                {1,0,1},{1,1,1},
                {5,0,1},{5,1,5},{5,2,10},{5,3,10},{5,4,5},{5,5,1},
                {6,2,15},
                {10,3,120}
        };

        for (int[] tc : cases) {
            int n = tc[0], k = tc[1], exp = tc[2];
            Long got = invokeBinomial(classes, methods, n, k);
            if (got == null) return false;
            if (got != exp) return false;
        }
        return true;
    }

    private static Long invokeBinomial(String[] classes, String[] methods, int n, int k) {
        for (int i = 0; i < classes.length; i++) {
            String cls = classes[i];
            String mth = methods[Math.min(i, methods.length - 1)];

            Object out = callStatic(cls, mth, new Class<?>[]{int.class, int.class}, n, k);
            if (out instanceof Integer) return ((Integer) out).longValue();
            if (out instanceof Long) return (Long) out;
        }
        return null;
    }

    private static boolean testIsSorted() {
        // expects: IsSorted.isSorted(int[]) -> boolean
        int[][] yes = {
                {},
                {1},
                {1,2,3},
                {1,1,2,2,3},
                {-5,-1,0,0,7}
        };
        int[][] no = {
                {2,1},
                {1,3,2},
                {1,1,0},
                {5,4,4}
        };

        for (int[] a : yes) {
            Object out = callStatic("IsSorted", "isSorted", new Class<?>[]{int[].class}, a);
            if (!(out instanceof Boolean)) return false;
            if (!((Boolean) out)) return false;
        }
        for (int[] a : no) {
            Object out = callStatic("IsSorted", "isSorted", new Class<?>[]{int[].class}, a);
            if (!(out instanceof Boolean)) return false;
            if (((Boolean) out)) return false;
        }
        return true;
    }
}
