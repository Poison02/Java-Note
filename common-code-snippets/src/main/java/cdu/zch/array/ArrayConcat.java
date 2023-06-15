package cdu.zch.array;

import java.lang.module.ModuleDescriptor;
import java.util.Arrays;

/**
 * 数组串联合并，两个和n个
 * @author Zch
 * @data 2023/6/15
 **/
public class ArrayConcat {

    // 两个数组串联合并
    public static <T> T[] arrayConcat(T[] first, T[] second) {
        var result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    // n个数组串联合并
    public static <T> T[] nArrarConcat(T[] first, T[]... rest) {
        var totalLength = first.length;
        for (var array : rest) {
            totalLength += array.length;
        }
        var result = Arrays.copyOf(first, totalLength);
        var offset = first.length;
        for (var array : rest) {
            System.arraycopy(array, 0 , result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

}
