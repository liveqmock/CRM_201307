package com.oasis.tmsv5.common.util.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<TreeNode> children;
	private String key;
	private String value;
	private boolean isLeaf = false;
	private int checkboxState = 2; //1: 选中并且不灰（所有子孙节点都被选中）； 2: 不选中； 3：选中但是灰掉（子孙节点中有没被选中的）
	private Object data; //用来记录该节点所代表的数据对象
	public TreeNode() {
	}
	
	public TreeNode(String key, String value, boolean isLeaf, int checkboxState) {
		super();
		this.key = key;
		this.value = value;
		this.isLeaf = isLeaf;
		this.checkboxState = checkboxState;
	}
	public TreeNode(String key, String value, boolean isLeaf, int checkboxState, Object data) {
		super();
		this.key = key;
		this.value = value;
		this.isLeaf = isLeaf;
		this.checkboxState = checkboxState;
		this.data = data;
	}
	public TreeNode(String key, String value, boolean isLeaf) {
		super();
		this.key = key;
		this.value = value;
		this.isLeaf = isLeaf;
	}
	public TreeNode(String key, String value, boolean isLeaf, Object data) {
		super();
		this.key = key;
		this.value = value;
		this.isLeaf = isLeaf;
		this.data = data;
	}
	public TreeNode(TreeNode node){
		this.key = node.key;
		this.value = node.value;
		this.isLeaf = node.isLeaf;
		this.checkboxState = node.checkboxState;
		this.data = node.data;
	}
	public TreeNode(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * 把node的所有值复制过来
	 * @param node
	 * 			要复制的节点
	 */
	public void copyValueOf(TreeNode node){
		this.key = node.key;
		this.value = node.value;
		this.isLeaf = node.isLeaf;
		this.checkboxState = node.checkboxState;
	}
	/**
	 * 添加子节点
	 * @param child
	 * @return
	 * 			成功返回true，失败返回false
	 */
	public boolean addChild(TreeNode child){
		if(children == null){
			children = new ArrayList<TreeNode>(); 
		}
		return children.add(child);
	}
	/**
	 * 删除子节点
	 * @param child
	 * @return
	 * 			成功返回true，失败返回false
	 */
	public boolean removeChild(TreeNode child){
		return children.remove(child);
	}
	/**
	 * visitor 设计模式
	 * 遍历(深度优先)所有子节点（包括自己）
	 * @param visitor
	 * 			遍历的处理类
	 */
	public void traverse(Visitor visitor){
		if(visitor.visit(this)){
			TreeNode child;
			if(children != null){
				for(int i=0; i<children.size(); i++){
					child = children.get(i);
					if(child != null){
						child.traverse(visitor);
					}
				}
			}
		}
	}
	/**
	 * visitor 设计模式
	 * 遍历(深度优先)所有子节点（包括自己）. 先访问所有子节点，再访问父节点
	 * @param visitor
	 * 			遍历的处理类
	 */
	public void childrenFirstTraverse(Visitor visitor){
		TreeNode child;
		if(children != null){
			for(int i=0; i<children.size(); i++){
				child = children.get(i);
				if(child != null){
					child.childrenFirstTraverse(visitor);
				}
			}
		}
		visitor.visit(this);
	}
	/**
	 * visitor 设计模式
	 * 遍历(宽度优先)所有子节点（包括自己）
	 * @param visitor
	 * 			遍历的处理类
	 */
	public void widthFirstTraverse(Visitor visitor){
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(this);
		TreeNode node;
		List<TreeNode> children;
		while(true){
			if(queue.size() == 0){
				break;
			}
			node = queue.poll();
			children = node.getChildren();
			if(visitor.visit(node)){
				if(children != null){
					for(int i=0; i<children.size(); i++){
						queue.offer(children.get(i));
					}
				}
			}
		}
	}
	public String toString(){
		return  key + "," + value;
	}
	public boolean equals(Object o){
		if(o instanceof TreeNode){
			TreeNode t = (TreeNode)o;
			if(this.key == null && t.getKey() == null){
				return true;
			}
			if(this.key.equals(t.getKey())){
				return true;
			}
		}
		return false;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCheckboxState() {
		return checkboxState;
	}
	public void setCheckboxState(int checkboxState) {
		this.checkboxState = checkboxState;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
