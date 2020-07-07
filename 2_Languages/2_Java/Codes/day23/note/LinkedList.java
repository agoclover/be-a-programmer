public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

    private static class Node<E> {
        E item; // 数据域
        Node<E> next; // 下一个指针
        Node<E> prev; // 上一个指针
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    // 计数器
    transient int size = 0;
    // 头引用， 缺省值为null
    transient Node<E> first;
    // 尾引用， 缺省值为null
    transient Node<E> last;
    // 控制同步变量
    protected transient int modCount = 0;

    public LinkedList() {
    }

    public boolean add(E var1) {
        this.linkLast(var1);
        return true;
    }

    // 尾部链入
    void linkLast(E var1) { // "yy"
        LinkedList.Node var2 = this.last; // var2指向当前尾， 第一次为null
        // var3指向新结点对象， 新结点的上一个指向当前尾
        LinkedList.Node var3 = new LinkedList.Node(var2, var1, (LinkedList.Node)null); // 新结点的上一个指针指当前尾， 第3个参数是下一个指针
        this.last = var3; // 把老尾引用刷成新结点的地点
        if (var2 == null) { // var2老尾如果是null, 表明链表为空
            this.first = var3; // 头引用指向新结点
        } else {
            var2.next = var3; // var2是老尾， 让老尾的下一个指针 指向新结点
        }
        ++this.size; // 调整计数器
        ++this.modCount; // 调整修改次数（添加或删除或插入都是修改）
    }

    public boolean remove(Object var1) { // 要删除的元素值
        LinkedList.Node var2; // 临时变量
        if (var1 == null) {
            for(var2 = this.first; var2 != null; var2 = var2.next) {
                if (var2.item == null) {
                    this.unlink(var2);
                    return true;
                }
            }
        } else { // 删除的元素值不为空
            // var2初始值是头引用
            for(var2 = this.first; var2 != null; var2 = var2.next) {
                if (var1.equals(var2.item)) {
                    this.unlink(var2);
                    return true;
                }
            }
        }

        return false;
    }

    E unlink(LinkedList.Node<E> var1) { // 516
        Object var2 = var1.item; // 删除元素的值保存一下
        // var3是删除目标的下一个 519
        LinkedList.Node var3 = var1.next;
        // var4是删除目标的上一个 513
        LinkedList.Node var4 = var1.prev;
        if (var4 == null) { // var4为null, 说明var1是头
            this.first = var3; // 让头刷新为下一个结点
        } else { // 不是删除头
            var4.next = var3; // 让上一个结点的下一个指针指向 目标的下一个
            var1.prev = null; // 删除目标节点的prev置空
        }

        if (var3 == null) {// var3为null, 说明var1是尾
            this.last = var4; // 让尾引用指向删除的前一个结点。
        } else { // 不是删除尾
            var3.prev = var4; // 让下一个结点的上一个指针指向 目标的上一个
            var1.next = null; // 删除目标节点的next置空
        }

        var1.item = null; // 让删除目标节点的数据域也置空，
        --this.size; // 调整计数器
        ++this.modCount; // 调整修改次数
        return var2;
    }

}
