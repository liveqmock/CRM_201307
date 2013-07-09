package com.oasis.tmsv5.util.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TreePathHelper {
    public static final String TREE_PATH_SEPERATOR = "|";

    private static final Log log = LogFactory.getLog(TreePathHelper.class);

    static public List<String> getAllSectionInTreePath(String treePath) {
        String[] nodes = treePath.split("\\" + TREE_PATH_SEPERATOR);
        List<String> nodeList = new ArrayList<String>();

        for (String node : nodes) {
            nodeList.add(node);
        }

        return nodeList;
    }

    /*
     * parse the given tree path into a list of parent tree pathes i.e. a tree
     * path like "1001|1002|1003" will return "1001", "1001|1002"
     */
    static public List<String> getAllParentTreePathes(String treePath) {
        List<String> pathList = new ArrayList<String>();

        if (treePath.length() > 0) {
            int lastIndex = treePath.lastIndexOf(TREE_PATH_SEPERATOR);

            if (lastIndex > 0) {
                int curIndex = treePath.indexOf(TREE_PATH_SEPERATOR);

                while (curIndex <= lastIndex && curIndex > 0) {
                    String parentPath = treePath.substring(0, curIndex);
                    pathList.add(parentPath);

                    curIndex = treePath.indexOf(TREE_PATH_SEPERATOR, curIndex + 1);
                }

            } else {
                pathList.add(treePath);
            }
        }

        return pathList;
    }

    /**
     * 检查parentTreePath是否是childTreePath的父亲, 若是，返回true;否则返回false；
     * 当parentTreePath与childTreepath相同时，返回false
     * 
     * @param parentTreePath
     * @param childTreePath
     * @return
     */
    static public boolean isDescendant(String parentTreePath, String childTreePath) {
        if (parentTreePath == null || childTreePath == null) {
            throw new IllegalArgumentException();
        }

        if (parentTreePath.length() == 0 || childTreePath.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (parentTreePath.length() >= childTreePath.length()) {
            return false;
        }

        if (childTreePath.indexOf(parentTreePath) >= 0) {
            return true;
        }

        return false;
    }

    static class StringLengthComparator implements Comparator<String> {

        public int compare(String o1, String o2) {
            int ret = 0;
            if (o1.length() < o2.length()) {
                ret = -1;
            } else if (o1.length() == o2.length()) {
                ret = 0;
            } else {
                ret = 1;
            }

            return ret;
        }

    }

    static public List<String> mergeTreePathes(List<String> treePathList) {
        List<String> mergedTreePathList = new ArrayList<String>();

        // sort the given tree path list again tree path length
        Comparator<String> comparator = new TreePathHelper.StringLengthComparator();

        Collections.sort(treePathList, comparator);

        // add all tree pathes into the merged list
        mergedTreePathList.addAll(treePathList);

        mergedTreePathList = CollectionHelper.removeDuplicatedItems(mergedTreePathList);

        for (String treePath : treePathList) {
            for (String innerTreePath : treePathList) {
                if (treePath == innerTreePath) {
                    continue;
                }

                if (!mergedTreePathList.contains(innerTreePath)) {
                    continue;
                }

                if (isDescendant(treePath, innerTreePath)) {
                    mergedTreePathList.remove(innerTreePath);
                }
            }
        }

        return mergedTreePathList;

    }

    static public String getAllChildrenTreePath(String treePath) {
        String ret = "";
        if (treePath != null && treePath.length() != 0) {
            ret += treePath + TREE_PATH_SEPERATOR;
        }

        ret += "%";

        return ret;
    }

    static public String getSelfAndAllChildrenTreePath(String treePath) {
        String ret = "";
        if (treePath != null && treePath.length() != 0) {
            ret += treePath;
        }

        ret += "%";

        return ret;
    }

    static public boolean hasCommonPart(List<String> treePathList1, List<String> treePathList2) {
        boolean ret = false;

        treePathList1 = CollectionHelper.removeDuplicatedItems(treePathList1);
        treePathList1 = mergeTreePathes(treePathList1);

        treePathList2 = CollectionHelper.removeDuplicatedItems(treePathList2);
        treePathList2 = mergeTreePathes(treePathList2);

        for (String treePath1 : treePathList1) {
            for (String treePath2 : treePathList2) {
                if (hasCommonPart(treePath1, treePath2)) {
                    ret = true;
                    break;

                }
            }
        }

        return ret;
    }

    static public boolean hasCommonPart(String treePath1, String treePath2) {
        boolean ret = false;

        if (treePath1 == null || treePath2 == null) {
            throw new IllegalArgumentException("tree path should not be null");
        }

        if (treePath1.length() == 0 || treePath2.length() == 0) {
            throw new IllegalArgumentException("tree path length should not be null");
        }

        if (treePath1.equals(treePath2)) {
            ret = true;
        } else if (isDescendant(treePath1, treePath2)) {
            ret = true;
        } else if (isDescendant(treePath2, treePath1)) {
            ret = true;
        }

        return ret;
    }

    /**
     * 检查coveredList中所有TreePath是否完全被coveringList中的treepath所覆盖
     * 
     * @param coveringList
     * @param coveredList
     * @return
     */
    static public boolean isFullyCovered(List<String> coveringList, List<String> coveredList) {
        coveringList = mergeTreePathes(coveringList);

        List<String> allTreePathList = new ArrayList<String>();
        allTreePathList.addAll(coveringList);
        allTreePathList.addAll(coveredList);

        allTreePathList = mergeTreePathes(allTreePathList);

        boolean ret = false;

        if (CollectionHelper.isListIdentical(coveringList, allTreePathList)) {
            ret = true;
        }

        return ret;

    }

    static public boolean isFullyCovered(List<String> coveringList, String covered) {
        coveringList = mergeTreePathes(coveringList);

        List<String> allTreePathList = new ArrayList<String>();
        allTreePathList.addAll(coveringList);
        allTreePathList.add(covered);

        allTreePathList = mergeTreePathes(allTreePathList);

        boolean ret = false;

        if (CollectionHelper.isListIdentical(coveringList, allTreePathList)) {
            ret = true;
        }

        return ret;

    }

    /*
     * find how many tree pathes in the "coveredList" is covered by covering
     * list
     */

    static public int getCoveredCount(List<String> coveringList, List<String> coveredList) {
        coveringList = mergeTreePathes(coveringList);
        coveredList = mergeTreePathes(coveredList);

        List<String> allTreePathList = new ArrayList<String>();
        allTreePathList.addAll(coveringList);
        allTreePathList.addAll(coveredList);

        allTreePathList = mergeTreePathes(allTreePathList);

        int ret = coveredList.size();

        for (String treePath : coveredList) {
            if (allTreePathList.contains(treePath) && !coveringList.contains(treePath)) {
                ret -= 1;
            }
        }

        log.debug("getCoveredCount: input : coveringList: " + convertTreePathListToString(coveringList) + " coveredList: "
                + convertTreePathListToString(coveredList) + " ret: " + ret);

        return ret;
    }

    static String convertTreePathListToString(List<String> treePathList) {
        String ret = "";
        for (String treePath : treePathList) {
            ret += treePath + ",";
        }

        return ret;
    }

    static public int getTreePathLevel(String treePath) {
        int ret = -1;
        if (treePath != null) {
            String[] level = treePath.split(TreePathHelper.TREE_PATH_SEPERATOR);
            ret = level.length;
        }
        return ret;
    }

    /**
     * 取treePath1、treePath2的公共treePath;若没有公共treePath,则返回null
     * 
     * @param treePath1
     * @param treePath2
     * @return
     */
    static public String getCommonTreePath(String treePath1, String treePath2) {

        if (StringUtils.isEmpty(treePath1) || StringUtils.isEmpty(treePath2)) {
            return null;
        }

        if (treePath1.equals(treePath2)) {
            return treePath1;
        }

        String[] treePath1Nodes = treePath1.split("\\" + TREE_PATH_SEPERATOR);
        String[] treePath2Nodes = treePath2.split("\\" + TREE_PATH_SEPERATOR);

        int size = treePath1Nodes.length < treePath2Nodes.length ? treePath1Nodes.length : treePath2Nodes.length;
        int commonLength = 0;
        for (int i = 0; i < size; i++) {
            if (treePath1Nodes[i].equals(treePath2Nodes[i])) {
                commonLength++;
            } else {
                break;
            }
        }

        if (commonLength > 0) {
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < commonLength; i++) {
                ret.append(treePath1Nodes[i]);
                if (i < commonLength - 1) {
                    ret.append(TREE_PATH_SEPERATOR);
                }
            }
            return ret.toString();
        }

        return null;
    }
}
