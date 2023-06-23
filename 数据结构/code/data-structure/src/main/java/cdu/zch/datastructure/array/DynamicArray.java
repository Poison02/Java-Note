package cdu.zch.datastructure.array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * 手动实现动态数组，即类似ArrayList
 *
 * @author Zch
 * @data 2023/6/22
 **/
public class DynamicArray implements Iterable<Integer> {

    private int size = 0; // 逻辑大小
    private int capacity = 8; // 容量
    private int[] array = {};

    public void addLast(int element) {
        add(size, element);
    }

    public void add(int index, int element) {
        // 扩容
        checkAndGrow();
        // 添加
        if (index >= 0 && index < size) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        array[index] = element;
        size++;
    }

    public void checkAndGrow() {
        // 容量检查
        if (size == 0) {
            array = new int[capacity];
        } else if (size == capacity) {
            // 扩容为原来的1.5倍
            capacity += capacity >> 1;
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    public int remove(int index) {
        int removed = array[index];
        if (index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
        size--;
        return removed;
    }

    public int get(int index) {
        return array[index];
    }

    public void foreach(Consumer<Integer> consumer) {
        /*
        使用 dynamicArray.foreach(element -> {xxx})
         */
        for (int i = 0; i < size; i++) {
            consumer.accept(array[i]);
        }
    }

    // 迭代器遍历
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i = 0;

            // 有没有下一个元素
            @Override
            public boolean hasNext() {
                return i < size;
            }

            // 返回当前元素，并移动到下一个元素
            @Override
            public Integer next() {
                return array[i++];
            }
        };
    }

    // 流遍历
    public IntStream stream() {
        /*
        使用：dynamicArray.stream().forEach(element -> {xxx})
         */
        return IntStream.of(Arrays.copyOfRange(array, 0, size));
    }
}
