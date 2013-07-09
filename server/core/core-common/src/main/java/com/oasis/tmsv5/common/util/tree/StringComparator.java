package com.oasis.tmsv5.common.util.tree;

import java.util.Comparator;

public class StringComparator implements Comparator<TreeNode> {

	public int compare(TreeNode arg0, TreeNode arg1) {
		return arg0.getKey().compareToIgnoreCase(arg1.getKey());
	}

}
