package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value) &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right);
        }

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    // Трудоемкость O(N) в худшем случае, O(logN) в среднем
    // Ресурсоемкость O(1)
    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> current = find(t);
        if (current != null && current.equals(root)) removePart(current, true);
        else removePart(current, false);
        size--;
        return true;
    }

    public void removePart(Node<T> current, boolean isRoot) {
        Node<T> parent = findParent(root, current.value);
        if (current.left == null && current.right == null) {
            if (isRoot) root = null;
            else replace(current, parent, null);
        } else if (current.left == null || current.right == null) {
            if (current.left == null) {
                if (isRoot) root = current.right;
                else replace(current, parent, current.right);
            } else {
                if (isRoot) root = current.left;
                else replace(current, parent, current.left);
            }
        } else {
            Node<T> node = leftNodeOfThis(current.right);
            transport(current, node);
        }
    }

    public void transport(Node<T> toRemove, Node<T> toReplace) {
        Node<T> parent = findParent(root, toReplace.value);
        if (!toRemove.equals(root)) {
            if (parent != null && !parent.equals(toRemove)) {
                parent.left = toReplace.right;
                parent = findParent(root, toRemove.value);
                toReplace.right = toRemove.right;
            } else {
                parent = findParent(root, toRemove.value);
            }
            toReplace.left = toRemove.left;
            replace(toRemove, parent, toReplace);
        } else {
            if (parent != null && !parent.equals(root)) {
                parent.left = toReplace.right;
                toReplace.right = root.right;
            }
            toReplace.left = root.left;
            root = toReplace;
        }
    }

    private Node<T> findParent(Node<T> start, T value) {
        Node<T> current = start;
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) return null;
            if (comparison < 0) {
                if (value.compareTo(current.left.value) == 0) return current;
                else current = current.left;
            } else {
                if (value.compareTo(current.right.value) == 0) return current;
                else current = current.right;
            }
        }
        return null;
    }

    public void replace(Node<T> current, Node<T> parent, Node<T> replacementNode) {
        if (parent.right != null && parent.right.equals(current)) parent.right = replacementNode;
        else parent.left = replacementNode;
    }

    public Node<T> leftNodeOfThis(Node<T> root) {
        Node<T> current = root;
        if (current.left == null) return current;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private Stack<Node<T>> stack = new Stack<>();
        private boolean isHasNextExecute = false;

        private BinaryTreeIterator() { }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        // Трудоемкость O(N) в худшем случае, O(logN) в среднем случае
        // Ресурсоемкость O(N)
        private Node<T> findNext() {
            if (root == null || (current != null && current.value == last())) return null;
            if (current == null) return getLeftNode(root);
            if (current.right == null) return current = stack.pop();
            return getLeftNode(current.right);
        }

        private Node<T> getLeftNode(Node<T> start) {
            current = start;
            while (current.left != null) {
                stack.push(current);
                current = current.left;
            }
            return current;
        }

        @Override
        public boolean hasNext() {
            isHasNextExecute = true;
            return findNext() != null;
        }

        @Override
        public T next() {
            if (isHasNextExecute) {
                isHasNextExecute = false;
                return current.value;
            }
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        // Трудоемкость O(N) в худшем случае, O(logN) в среднем
        // Ресурсоемкость O(1)
        @Override
        public void remove() {
            T previous = current.value;
            if (!hasNext()) {
                BinaryTree.this.remove(previous);
                current = find(last());
            } else {
                BinaryTree.this.remove(previous);
                next();
            }
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    // Трудоемкость O(N)
    // Ресурсоемкость O(N)
    // Знаю, что не совсем удоволетворяет контракту, если будет время - доделаю
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> set = new TreeSet<>();
        headCheck(toElement, root, set);
        return set;
    }

    public void headCheck(T toElement, Node<T> current, SortedSet<T> set) {
        int comparison = current.value.compareTo(toElement);
        if (comparison == 0) {
            if (current.left != null) addBranchToSet(current.left, set);
        }
        if (comparison < 0) {
            set.add(current.value);
            if (current.left != null) addBranchToSet(current.left, set);
            if (current.right != null) headCheck(toElement, current.right, set);
        }
        if (comparison > 0) {
            if (current.left != null) headCheck(toElement, current.left, set);
        }
    }

    public void addBranchToSet(Node<T> current, SortedSet<T> set) {
        set.add(current.value);
        if (current.left != null) addBranchToSet(current.left, set);
        if (current.right != null) addBranchToSet(current.right, set);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    // Трудоемкость O(N)
    // Ресурсоемкость O(N)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> set = new TreeSet<>();
        tailCheck(fromElement, root, set);
        return set;
    }

    public void tailCheck(T toElement, Node<T> current, SortedSet<T> set) {
        int comparison = current.value.compareTo(toElement);
        if (comparison >= 0) {
            set.add(current.value);
            if (current.right != null) addBranchToSet(current.right, set);
            if (current.left != null) tailCheck(toElement, current.left, set);
        }
        if (comparison < 0) {
            if (current.right != null) tailCheck(toElement, current.right, set);
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}