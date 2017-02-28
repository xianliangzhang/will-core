package top.kou.will.tree;

/**
 * Created by Administrator on 2017/2/28.
 */
public class BinaryTree {

    static class TreeNode {
        String label;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(String label) {
            this.label = label;
        }
    }

    static class ThreeFactory {
        /**
         *             A
         *            / \
         *           B   C
         *          / | / \
         *         D  E F  X
         *         |      /
         *         Y     Z
         *  前序：ABDYECFXZ
         *  中序：DYBEAFCZX
         *  后序：YDEBFZXCA
         */
        static TreeNode generateBinaryTree() {
            TreeNode a = new TreeNode("A");
            TreeNode b = new TreeNode("B");
            TreeNode c = new TreeNode("C");
            TreeNode d = new TreeNode("D");
            TreeNode e = new TreeNode("E");
            TreeNode f = new TreeNode("F");
            TreeNode x = new TreeNode("X");
            TreeNode y = new TreeNode("Y");
            TreeNode z = new TreeNode("Z");

            a.leftChild  = b;
            a.rightChild = c;

            b.leftChild  = d;
            b.rightChild = e;

            c.leftChild  = f;
            c.rightChild = x;

            d.rightChild = y;

            x.leftChild  = z;

            return a;
        }
    }


    static int getLevel(TreeNode tree) {
        if (tree == null) {
            return 0;
        }

        int left = 1;
        int right = 1;

        left += getLevel(tree.leftChild);
        right += getLevel(tree.rightChild);

        return left > right ? left : right;
    }

    static void preOrderIterator(TreeNode tree) {
        System.out.print(tree.label);
        if (tree.leftChild != null) {
            preOrderIterator(tree.leftChild);
        }
        if (tree.rightChild != null) {
            preOrderIterator(tree.rightChild);
        }
    }

    static void inOrderIterator(TreeNode tree) {
        if (tree.leftChild != null) {
            inOrderIterator(tree.leftChild);
        }
        System.out.print(tree.label);
        if (tree.rightChild != null) {
            inOrderIterator(tree.rightChild);
        }
    }

    static void postOrderIterator(TreeNode tree) {
        if (tree.leftChild != null) {
            postOrderIterator(tree.leftChild);
        }
        if (tree.rightChild != null) {
            postOrderIterator(tree.rightChild);
        }
        System.out.print(tree.label);
    }

    static void visit(TreeNode node) {
        System.out.print(node.label);
    }

    public static void main(String[] args) {
        TreeNode tree = ThreeFactory.generateBinaryTree();
        //preOrderIterator(tree);
        //inOrderIterator(tree);
        //postOrderIterator(tree);
    }
}
