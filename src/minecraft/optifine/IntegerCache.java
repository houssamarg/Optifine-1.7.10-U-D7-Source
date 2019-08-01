package optifine;

public class IntegerCache
{
    private static final int CACHE_SIZE = 65535;
    private static final Integer[] cache = makeCache(65535);

    private static Integer[] makeCache(int size)
    {
        Integer[] arr = new Integer[size];

        for (int i = 0; i < size; ++i)
        {
            arr[i] = new Integer(i);
        }

        return arr;
    }

    public static Integer valueOf(int value)
    {
        return value >= 0 && value < 65535 ? cache[value] : new Integer(value);
    }
}
