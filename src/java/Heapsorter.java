/*
 *    Ecotype Simulation models the sequence diversity within a bacterial
 *    clade as the evolutionary result of net ecotype formation and periodic
 *    selection, yielding a certain number of ecotypes.
 *
 *    Copyright (C) 2009       Andrew Warner, Wesleyan University
 *    Copyright (C) 2009-2019  Jason M. Wood, Montana State University
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package ecosim;

import java.util.List;
import java.util.Comparator;

/**
 *  Runs the heapsort on a given set of data.
 *
 *  @author Andrew Warner
 *  @author Jason M. Wood
 *  @copyright GNU General Public License
 */
public class Heapsorter<T extends Comparable<T>> {

    /**
     *  The default Heapsorter constructor.
     */
    public Heapsorter () {
        c = new DefaultComparator<T> ();
    }

    /**
     *  Heapsorter constructor when using a custom Comparator.
     *
     *  @param c The Comparator to use for sorting.
     */
    public Heapsorter (Comparator<T> c) {
        this.c = c;
    }

    /**
     *  A static sorting method.
     *  Note that we construct a min heap originally so that, once each
     *  element is popped off a list of the elements in descending order
     *  remains.
     *
     *  @pre list is an unsorted array of objects that implement Comparable.
     *  @post list is sorted using heapsort and returned in descending order.
     *  @param list The list of values to be sorted.
     */
    public void sort (List<T> list) {
        for (int index = 0; index < list.size (); index ++) {
            // Restore the heap property as each element is added.
            restoreHeap (list, index);
        }
        // Pop the max value each time and place it in the given list, then
        // switch the last value for the first and pushdown.
        int end = list.size () - 1;
        for (int i = 0; i < list.size () - 1; i ++) {
            // Swap the first and last values.
            swap (list, 0, end);
            // Push down until the heap property is restored.
            pushdown (list, 0, end);
            end --;
        }
    }

    /**
     *  A static sorting method.
     *  Note that we construct a min heap originally so that, once each
     *  element is popped off a list of the elements in descending order
     *  remains.
     *
     *  @pre list is an unsorted array of objects that implement Comparable.
     *  @post list is sorted using heapsort and returned in descending order.
     *  @param list The list of values to be sorted.
     *  @param c The Comparator to use for sorting.
     */
    public void sort (List<T> list, Comparator<T> c) {
        this.c = c;
        sort (list);
    }

    /**
     *  @pre list is a min-heap except for the root.
     *  @post list is a min-heap.
     *  @param list The list of values.
     *  @param root The root of the subtree.
     *  @param end The index of the end of the tree, ie the index of the
     *  first value no longer in the tree (but still in the array).
     */
    private void pushdown (List<T> list, int root, int end) {
        // If root has no left node, it is a leaf, and therefore we are done.
        if (2 * root + 1 > (end - 1)) {
            return;
        }
        int left = 2 * root + 1;
        if (2 * root + 2 > (end - 1)) {
            // If root has no right node, check if it is greater than its
            // left node. If it is, swap it, and then break.
            if (c.compare (list.get (root), list.get (left)) > 0) {
                swap (list, root, left);
            }
            return;
        }
        int right = 2 * root + 2;
        if (c.compare (list.get (left), list.get (right)) < 0) {
            if (c.compare (list.get (root), list.get (left)) > 0) {
                swap (list, root, left);
                pushdown (list, left, end);
            }
        }
        else if (c.compare (list.get (root), list.get (right)) > 0) {
            swap (list, root, right);
            pushdown (list, right, end);
        }
    }

    /**
     *  @pre list is a list of comparable with one and two being indexes
     *  within that list.
     *  @post the values at index one and two are swapped.
     *  @param list The list of values.
     *  @param one The first index.
     *  @param two The second index.
     */
    private void swap (List<T> list, int one, int two) {
        T storage = list.get (one);
        list.set (one, list.get (two));
        list.set (two, storage);
    }

    /**
     *  @pre list is a max heap except for the value at index, which is the
     *  rightmost child.
     *  @post list is a max heap.
     *  @param list The list of values.
     *  @param index The root of the subtree that needs to be restored to a
     *  heap.
     */
    private void restoreHeap (List<T> list, int index) {
        // If index is the root, we are done.
        // Note that the left child of a node at index n is at index 2n + 1
        // and the right child of a node is at index 2n + 2, therefore if a
        // node's index n % 2 = 1 then it is a left child, otherwise it is a
        // right child.
        int parent;
        if (index == 0) {
            return;
        }
        else if (index % 2 == 1) {
            parent = (index - 1) / 2;
        }
        else {
            parent = (index - 2) / 2;
        }
        // If we already have a min heap property - IE the parent is less
        // than the child, then we are done.
        if (c.compare(list.get (parent), list.get (index)) <= 0) {
            return;
        }
        // Otherwise switch the parent and the child and then recursively
        // restore the heap.
        swap (list, parent, index);
        restoreHeap (list, parent);
    }

    private Comparator<T> c;

    private class DefaultComparator<T extends Comparable<T>> implements Comparator<T> {
        @Override
        public int compare (T a, T b) {
            return a.compareTo (b);
        }
    }

}
