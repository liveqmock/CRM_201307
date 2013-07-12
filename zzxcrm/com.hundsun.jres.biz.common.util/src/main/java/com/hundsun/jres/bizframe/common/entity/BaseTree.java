/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: BaseTree.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.common.entity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 功能说明: 树操作基础类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-7-26<br>
 * 审核人员:   <br>
 * 相关文档:   <br>
 * 修改记录:   <br>
 * 修改日期      修改人员                     修改说明  <br>
 * ========	   ======  ============================================  <br>
 *
 */

public abstract class BaseTree implements Serializable {

	private static final long serialVersionUID = 7612122703816489084L;

	private Object content = null;

	private BaseTree parentNode = null;

	private HashSet<BaseTree> childNodeSet = new HashSet<BaseTree>();

	/**
	 * 增加一个子节点
	 * 
	 * @param treeNode
	 */
	public void addChildNode(BaseTree treeNode) {
		this.childNodeSet.add(treeNode);
		treeNode.setParentNode(this);
	}

	/**
	 * 增加一组子节点
	 * 
	 * @param treeNodes
	 * @throws SQLException 
	 */
	public void addChildNodes(List<Object> treeNodes) throws Exception {
		if (treeNodes.size() > 0) {
			for (Object node : treeNodes) {
				this.addChildNode(this.initTreeNode(node));
			}
		}
	}

	/**
	 * 除去一个子节点
	 * 
	 * @param treeNode
	 */
	public void dltChildNode(BaseTree treeNode) {
		if (this.childNodeSet.contains(treeNode)) {
			this.childNodeSet.remove(treeNode);
			treeNode.setParentNode(null);
		}
	}

	/**
	 * 得到树的所有节点
	 * 
	 * @return
	 */
	public List<BaseTree> getAllNodes() {
		List<BaseTree> nodeList = new ArrayList<BaseTree>();

		this.traveTree(this, nodeList, new ArrayList<BaseTree>());

		return nodeList;
	}

	/**
	 * 得到所以叶子节点
	 * 
	 * @return
	 */
	public List<BaseTree> getAllLeafs() {
		List<BaseTree> leafList = new ArrayList<BaseTree>();
		this.traveTree(this, new ArrayList<BaseTree>(), leafList);
		return leafList;
	}

	/**
	 * 除去树上所有的叶子
	 * 
	 * @return 被除去的叶子列表
	 */
	public List<BaseTree> removeLeafs() {
		List<BaseTree> leafList = new ArrayList<BaseTree>();

		// BaseTree treeNode;
		List<BaseTree> leafs = this.getAllLeafs();
		for (BaseTree treeNode : leafs) {
			treeNode.getParentNode().dltChildNode(treeNode);
			leafList.add(treeNode);
		}

		return leafList;
	}

	/**
	 * 遍历整个树，将所有节点和叶子保存在列表里
	 * 
	 * @param rootNode
	 * @param nodeList
	 * @param leafList
	 */
	private void traveTree(BaseTree rootNode, List<BaseTree> nodeList,
			List<BaseTree> leafList) {

		if (rootNode.getContent() != null) {
			nodeList.add(rootNode);

			if (rootNode.getChildNodeSet().size() < 1)
				leafList.add(rootNode);
		}

		Iterator<BaseTree> childs = rootNode.getChildNodeSet().iterator();
		while (childs.hasNext()) {
			BaseTree child = (BaseTree) childs.next();
			traveTree(child, nodeList, leafList);
		}
	}

	/**
	 * 迭代建树
	 * 
	 * @param rootId
	 * @return
	 * @throws SQLException
	 */
	protected boolean buildTree(Object root) throws Exception {

		BaseTree node = this.initTreeNode(root);
		boolean result = false;

		if (node != null && node.getContent() != null) {

			this.buildTreeNode(this, node);
			result = true;
		} else {
			throw new SQLException("根结点" + root.toString() + "不存在");
		}

		return result;
	}

	/**
	 * 删除树
	 * 
	 * @return
	 */
	public boolean deleteTree() throws Exception {

		boolean result = true;
		List<BaseTree> leafs = new ArrayList<BaseTree>();

		// 从叶子节点开始删除树
		while (this.getChildNodeSet().size() > 0) {

			leafs.clear();
			leafs.addAll(this.removeLeafs());

			// 开始删除树节点里的资源层次和资源
			for (BaseTree node : leafs) {
				result = result && this.deleteTreeNode(node);
			}
		}

		return result;
	}

	/**
	 * 关联所有树节点
	 * 
	 * @param parentTreeNode
	 * @param currentTreeNode
	 * @throws SQLException
	 */
	protected void buildTreeNode(BaseTree parentTreeNode,
			BaseTree currentTreeNode) throws Exception {

		parentTreeNode.addChildNode(currentTreeNode);

		List<BaseTree> chiilds = this.getChilds(currentTreeNode);
		Iterator<BaseTree> resHieIte = chiilds.iterator();
		while (resHieIte.hasNext()) {

			BaseTree newResHie = (BaseTree) resHieIte.next();
			this.buildTreeNode(currentTreeNode, newResHie);
		}

	}

	/**
	 * 得到子节点动作
	 * 
	 * @param root
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public abstract List<BaseTree> getChilds(BaseTree root) throws Exception;

	/**
	 * 物理删除数据库里的树节点动作
	 * 
	 * @param treeNode
	 * @return
	 * @throws SQLException
	 */
	public abstract boolean deleteTreeNode(BaseTree treeNode)
			throws Exception;

	/**
	 * 初始化一个树节点
	 * 
	 * @param rootId
	 * @return
	 * @throws SQLException 
	 */
	public abstract BaseTree initTreeNode(Object obj) throws Exception;

	public HashSet<BaseTree> getChildNodeSet() {
		return childNodeSet;
	}

	public void setChildNodeSet(HashSet<BaseTree> childNodeSet) {
		this.childNodeSet = childNodeSet;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public BaseTree getParentNode() {
		return parentNode;
	}

	public void setParentNode(BaseTree parentNode) {
		this.parentNode = parentNode;
	}
}
