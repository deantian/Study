package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by TianWei on 2016/5/19.
 */


public class BIRCH {


    public static final int dimen = 4;

    LeafNode leafNodeHead = new LeafNode();

    int point_num = 0;        //point instance����


    //����ɨ�����ݿ⣬����B-��

    public TreeNode buildBTree(String filename) {

        //�Ƚ���һ��Ҷ�ӽڵ�

        LeafNode leaf = new LeafNode();

        TreeNode root = leaf;


        //��Ҷ�ӽڵ����洢Ҷ�ӽڵ��˫������

        leafNodeHead.setNext(leaf);

        leaf.setPre(leafNodeHead);

        //���ļ������ļ��ж�ȡԭʼ����

        File file = new File(filename);

        if (!file.exists()) {

            System.out.println("Data File Not Exists.");

            System.exit(2);

        }

        try {

            FileReader fr = new FileReader(file);

            BufferedReader br = new BufferedReader(fr);

            String line = null;

            while ((line = br.readLine()) != null && line.trim() != "") {

                point_num++;

                String[] cont = line.split("[,|\\s+]");

                //����point instance

                double[] data = new double[dimen];

                for (int i = 0; i < data.length; i++) {

                    data[i] = Double.parseDouble(cont[i]);

                }

                /**
                 * TianWei
                 * �޸ĽǱ�Խ�磬ԭֵ��cont[data.length]
                 */
                String mark = String.valueOf(point_num) + cont[data.length-1];

                //����һ��point instance����һ��MinCluster

                CF cf = new CF(data);

                MinCluster subCluster = new MinCluster();

                subCluster.setCf(cf);

                subCluster.getInst_marks().add(mark);

                //���µ���point instance��������

                root.absorbSubCluster(subCluster);

                //Ҫʼ�ձ�֤root�����ĸ��ڵ�

                while (root.getParent() != null) {

                    root = root.getParent();

                }

            }

            br.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return root;

    }


    //��ӡB-��������Ҷ�ӽڵ�

    public void printLeaf(LeafNode header) {

        //point_num��0

        point_num = 0;

        while (header.getNext() != null) {

            System.out.println("\nһ��Ҷ�ӽڵ�:");

            header = header.getNext();

            for (MinCluster cluster : header.getChildren()) {

                System.out.println("\nһ����С��:");

                for (String mark : cluster.getInst_marks()) {

                    point_num++;

                    System.out.print(mark + "\t");

                }

            }

        }

    }


    //��ӡָ�����ڵ������

    public void printTree(TreeNode root) {
        /**
         * TianWei
         * �޸������ж�������ԭֵ��!root.getClass().getName().equals("birch.LeafNode"))
         */

        if (!root.getClass().getSimpleName().equals("LeafNode")) {

            NonleafNode nonleaf = (NonleafNode) root;

            for (TreeNode child : nonleaf.getChildren()) {

                printTree(child);

            }

        } else {

            System.out.println("\nһ��Ҷ�ӽڵ�:");

            LeafNode leaf = (LeafNode) root;

            for (MinCluster cluster : leaf.getChildren()) {

                System.out.println("\nһ����С��:");

                for (String mark : cluster.getInst_marks()) {

                    System.out.print(mark + "\t");

                    point_num++;

                }

            }

        }

    }


    public static void main(String[] args) {

        BIRCH birch = new BIRCH();

        TreeNode root = birch.buildBTree("C:/Users/lmc/Desktop/Btest/iris.shuffled");

        birch.point_num = 0;

        birch.printTree(root);

        System.out.println();

        //birch.printLeaf(birch.leafNodeHead);

        //ȷ�ϱ������point instance��ɨ�����ݿ�ʱ¼���point instance�ĸ�����һ�µ�

        System.out.println(birch.point_num);

    }

}

