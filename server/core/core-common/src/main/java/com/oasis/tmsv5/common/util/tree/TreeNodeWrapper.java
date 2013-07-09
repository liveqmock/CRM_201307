package com.oasis.tmsv5.common.util.tree;


public class TreeNodeWrapper extends TreeNode {
    
    private static final long serialVersionUID = 1L;
    
    private TreeNodeWrapper parent = null;
	private int status;
	public TreeNodeWrapper getParent() {
		return parent;
	}

	public void setParent(TreeNodeWrapper parent) {
		this.parent = parent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public TreeNodeWrapper(TreeNode node) {
		super(node);
	}
	
}
