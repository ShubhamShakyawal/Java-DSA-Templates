// Range update with Lazy propagation
public class SegmentTree2 {
    static class Node {
        long sum;
        int a, d;

        Node() {
            sum = 0L;
            a = d = 0;
        }
    }

    public static class SegmentTree {
        Node[] seg;
        int[] arr;
        int n;

        public SegmentTree(int[] nums) {
            arr = nums;
            n = nums.length;
            seg = new Node[4 * nums.length + 1];
            for (int i = 0; i < seg.length; i++)
                seg[i] = new Node();

            build(0, 0, n - 1, arr);
        }

        // can change merge logic depending upon need
        Node merge(Node a, Node b) {
            Node mergeNode = new Node();
            mergeNode.sum = a.sum + b.sum;
            return mergeNode;
        }

        void build(int ind, int low, int high, int[] nums) {
            if (low == high) {
                seg[ind].sum = nums[low];
                return;
            }
            int mid = (low + high) / 2;
            build(2 * ind + 1, low, mid, nums);
            build(2 * ind + 2, mid + 1, high, nums);
            seg[ind] = merge(seg[2 * ind + 1], seg[2 * ind + 2]); // don't change
        }

        // reflect lazy effect to child nodes.
        void pushDown(int ind, int low, int high, int a, int d) {
            int mid = (low + high) / 2;

            // Update the left child
            seg[2 * ind + 1].a += a;
            seg[2 * ind + 1].d += d;

            // Update the right child
            seg[2 * ind + 2].a += a + (mid - low + 1) * d;
            seg[2 * ind + 2].d += d;
        }

        // apply lazy propagation on current node and change values of current node
        // with changing child nodes
        void push(int ind, int low, int high) { // acc. to need
            if (seg[ind].d == 0) return;
            int n = high - low + 1;
            int a = seg[ind].a, d = seg[ind].d;

            // Update the current node's sum
            long sm = n * (2L *a + (long) (n - 1) * d) / 2L;
            seg[ind].sum += sm;
            // Push the updates to child nodes
            if (low != high) {
                pushDown(ind, low, high, a, d);
            }
            seg[ind].a = 0;
            seg[ind].d = 0;
        }

        Node query(int ind, int low, int high, int l, int r) {
            push(ind, low, high); // Apply lazy updates

            if (low > r || high < l) return new Node(); // No overlap
            if (low >= l && high <= r) {
                return seg[ind]; // Complete overlap
            }

            // Partial overlap
            int mid = (low + high) / 2;
            Node left = query(2 * ind + 1, low, mid, l, r);
            Node right = query(2 * ind + 2, mid + 1, high, l, r);

            return merge(left,right);
        }

        public void update(int l, int r, int a, int d) {
            updateTree(0, 0, n - 1, l, r, a, d);
        }

        void updateTree(int ind, int low, int high, int l, int r, int a, int d) {
            push(ind, low, high); // apply lazy

            if (high < l ||low > r) return; // No overlap
            if (low >= l && high <= r) { // Complete overlap
                seg[ind].a += a + (low - l);
                seg[ind].d += d;
                push(ind, low, high); // Apply immediately
                return;
            }

            // Partial overlap
            int mid = (low + high) / 2;
            updateTree(2 * ind + 1, low, mid, l, r, a, d);
            updateTree(2 * ind + 2, mid + 1, high, l, r, a, d);

            // Recalculate sum for current node
            seg[ind] = merge(seg[2 * ind + 1], seg[2 * ind + 2]);
        }

        public long sumRange(int l, int r) {
            return query(0, 0, n - 1, l, r).sum;
        }
    }
}
