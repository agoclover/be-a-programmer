public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {

    // 缺省的初始容量 ： 16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    // 最大容量， 所有容量用的都是2的n次方
    static final int MAXIMUM_CAPACITY = 1 << 30; // 010000000000

    // 缺省的加载因子， 3/4 是一个折衷数字， 表示数组的使用比率
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // 如果某个链表的元素大于此值，此处的链表就会转换为红黑树
    static final int TREEIFY_THRESHOLD = 8;

    // 如果红黑树中的元素小于此值，重新转换为链表
    static final int UNTREEIFY_THRESHOLD = 6;

    // 下标冲突导致的红黑树元素个数如果超过此值，强制扩容。
    static final int MIN_TREEIFY_CAPACITY = 64;

    // 嵌套类，描述的是条目， 每个条目都是有键对象又有值对象
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash; // 键对象的原始哈希码，不可变
        final K key; // 键对象， 不可变
        V value; // 值对象， 值可以变
        Node<K, V> next; // 用于做成链表的指针。

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // 最关键，最重要的数组， Node类型的数组
    transient Node<K,V>[] table; // 哈希表， 初始值为null

    // 计数器
    transient int size;

    // 控制同步的计数器
    transient int modCount;

    // 数组存储上限 = 数组的长度*加载因子
    int threshold;

    // 加载因子
    final float loadFactor;

    public HashMap() {
        // 当前的Map的加载因子是3/4, 此时没有创建数组对象
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    static final int hash(Object key) {
        int h;
        // 对象的哈希码要它无符号右移16位异或, 作用是提升散列性
        // 0000 0000 0000 0000 0000 0000 0000 1001 ^
        // 0000 0000 0000 0000 0000 0000 0000 0000 = 结点是高位的16比特移到低位
        // 0000 0000 0000 0000 0000 0000 0000 1001
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // hash是计数过的哈希码， key是键对象, value是值对象
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab; // 临时变量，指向哈希表。就是哈希表
        Node<K,V> p; // 数组元素类型的引用
        int n; // n是数组长度
        int i; // 目标下标
        tab = table;
        n = tab.length; // 是长度
        if (tab == null || n == 0) { // 第一次添加元素时一定进入
            tab = resize(); // 第一次扩容，创建长度为16的数组
            n = tab.length; // n保存的是数组长度
        }
        //if ((p = tab[i = (n - 1) & hash]) == null) { // 最最关键代码
        i = (n - 1) & hash; // 15 & 9, 效果就是 hash % n
        // 0001 0000 - 1 : 0000 1111
        // 0000 1001 &
        // 0000 1111 => 0000 1001 => 9
        p = tab[i]; // 获取目标位置处的老元素
        if (p == null) { // 如果老值为null, 说明这个位置没有元素， 这是最好的。
            tab[i] = newNode(hash, key, value, null); // 直接插入
        } else { // 出现了下标冲突， 要处理链表和红黑树
            Node<K,V> e; // 数组元素
            K k; // 键类型引用
            // 老元素的hash和新元素的hash是否相等
            k = p.key; // 老键对象， 下面是判断老键对象是否和新键 对象重复
            // 重复的标准就是两个键对象是否equals并且hash相等。
            if (p.hash == hash && (k == key || (key != null && key.equals(k)))) {
                e = p;
            } else if (p instanceof TreeNode) { // 判断此处是否已经就是红黑树
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value); // 红黑树插入
            } else { // 普通的链表插入
                // binCount 链表元素个数
                for (int binCount = 0; ; ++binCount) {
                    // e是p的下一个元素
                    e = p.next; // p是当前元素， e是它的下一个
                    if (e == null) { // 说明p就是尾结点。
                        p.next = newNode(hash, key, value, null); // 把新结点链入尾结点后面
                        if (binCount >= TREEIFY_THRESHOLD - 1) {// -1 for 1st  真的插入链表元素后， 判断元素个数是否到达上限
                            treeifyBin(tab, hash); // 把此位置的所有元素重新插入成一个红黑树
                        }
                        break; // 链表插入完成
                    }
                    k = e.key; // k是p后面的老键对象
                    // 判断p后面的元素是否和新键对象重复， 如果重复了，直接break;
                    if (e.hash == hash && (k == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = e; // p又指向它的下一个
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue; // 出现键重复时返回老值
            }
        }
        ++modCount; // 调整修改次数
        if (++size > threshold) { // 调整计数器， 并判断是否超过上限， 如果超过，扩容
            resize();
        }
        afterNodeInsertion(evict);
        return null; // 键对象没有重复！！
    }

    final Node<K,V>[] resize() { // 调整容量
        Node<K,V>[] oldTab = table; // 老表
        int oldCap = (oldTab == null) ? 0 : oldTab.length; // 老容量 ： 16
        int oldThr = threshold; // 老上限 ： 12
        int newCap; // 新容量
        int newThr = 0; // 新上限
        if (oldCap > 0) { // 第一次不进入，后面会进入
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) { // 会进入
                // 新容量也是*2
                newThr = oldThr << 1; // double threshold 新上限*2
            }
        } else if (oldThr > 0) { // initial capacity was placed in threshold
            newCap = oldThr;
        } else { // 第一次插入元素会进入              // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY; // 新容量16
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); // 新上限 12
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr; // 当前上限变成新上限
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; // 创建数组
        table = newTab; // 当前哈希表就创建为16个容量的数组
        if (oldTab != null) { // 如果老表不为空， 所有元素重新在新数组中散列， 进一步提升性能。
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
}