public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    // 缺省容量
    private static final int DEFAULT_CAPACITY = 10;

    // 空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    // 最重要的内部数组， 通过无参创建对象时， 这旨一个空数组（没有元素）
    transient Object[] elementData;

    // 计数器
    private int size;

    // 修改次数
    protected transient int modCount = 0;

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 1 先确保容量够不够， 如果不够就扩容。
        elementData[size++] = e; // 重点语句
        return true;
    }
    private void ensureCapacityInternal(int minCapacity) {// 1
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity)); // 10
    }
    private static int calculateCapacity(Object[] elementData, int minCapacity) { //1
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) { // 第一次添加元素时进入
            return Math.max(DEFAULT_CAPACITY, minCapacity); // 10, 1
        }
        return minCapacity;
    }
    private void ensureExplicitCapacity(int minCapacity) { // 10
        modCount++; // 1
        if (minCapacity - elementData.length > 0) // 新容量值 - 老数组的长度
            grow(minCapacity);  // 10
    }
    private void grow(int minCapacity) { // 10 扩容
        // overflow-conscious code
        int oldCapacity = elementData.length; // 0
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩容1.5倍
        if (newCapacity - minCapacity < 0) // 0-10
            newCapacity = minCapacity; // 10
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity); // 复制数组
    }

    public E remove(int index) { // 3
        rangeCheck(index); // 检查下标合法性
        modCount++; // 删除元素也会导致modCount++
        E oldValue = elementData(index); // 获取要删除的老值
        int numMoved = size - index - 1; // 11 - 3 - 1 == 7
        if (numMoved > 0) {
            // 把要删除的位置的右侧所有元素都左移一位。 重点语句2
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        // 把之前的最后有效元素置空， 并调整计数器
        elementData[--size] = null; // clear to let GC do its work
        return oldValue;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }
    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        // 期望的修改次数
        int expectedModCount = modCount; // 在创建迭代器时用当前的修改次数赋值给属性 11
        Itr() {
        }
        public boolean hasNext() {
            return cursor != size;
        }
        public E next() {
            checkForComodification();
        }
        final void checkForComodification() {
            if (modCount != expectedModCount) // 如果期望的修改次数和实际的不一样， 抛出异常
                throw new ConcurrentModificationException();
        }
    }

}