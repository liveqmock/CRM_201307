package com.oasis.tmsv5.common.util.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeUtil {
	/**
	 * 从一棵树里面删除某个节点（包括其所有子节点）
	 * @param root
	 * 			树的根节点
	 * @param removingNodeKey
	 * 			要删除的节点的key
	 * @return
	 * 			成功返回删除的节点，失败返回null
	 */
	public static TreeNode removeNodeFromTree(TreeNode root, String removingNodeKey){
		final TreeNode removingNode = new TreeNode(removingNodeKey, "");
		class DeleteTreeNodeVisitor implements Visitor{
			public TreeNode result = null;
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				for(TreeNode child : node.getChildren()){
					if(child.equals(removingNode)){
						result = child;
						node.removeChild(removingNode);
						return false;
					}
					
				}
				return true;
			}
		}
		DeleteTreeNodeVisitor visitor = new DeleteTreeNodeVisitor();
		root.traverse(visitor);
		return visitor.result;
	}
	/**
	 * 给一棵树添加新的节点
	 * @param root
	 * 			树的根节点
	 * @param newNode
	 * 			要添加的新节点
	 * @param parentKey
	 * 			要添加的新节点的父节点的key
	 * @return
	 * 			成功返回true，失败返回false
	 */
	public static boolean addNewNodeToTree(TreeNode root, TreeNode newNode, String parentKey){
		final TreeNode tNewNode = newNode;
		final String tParentKey = parentKey;
		class AddNewNodeVisitor implements Visitor{
			public boolean result = false;
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(tParentKey != null && tParentKey.equals(node.getKey())){
					node.addChild(tNewNode);
					node.setIsLeaf(false);
					result = true;
					return false;
				}
				return true;
			}
		}
		AddNewNodeVisitor visitor = new AddNewNodeVisitor();
		root.traverse(visitor);
		return visitor.result;
	}
	/**
	 * 更新一棵树的某个节点
	 * @param root
	 * 			要更新的树的根节点
	 * @param newNode
	 * 			更新的新节点对象
	 * @return
	 * 			成功返回true，失败返回false
	 */
	public static boolean updateTreeNode(TreeNode root, TreeNode newNode){
		final TreeNode tNewNode = newNode;
		class UpdateTreeNodeVisitor implements Visitor{
			public boolean result = false;
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(tNewNode.getKey() != null && tNewNode.getKey().equals(node.getKey())){
					node.setValue(tNewNode.getValue());
					//node.setIsLeaf(tNewNode.getIsLeaf());
					result = true;
					return false;
				}
				return true;
			}
		}
		UpdateTreeNodeVisitor visitor = new UpdateTreeNodeVisitor();
		root.traverse(visitor);
		return visitor.result;
	}
	/**
	 * 修改一棵树的某个节点的父节点(移动到另一个节点下面)
	 * @param root
	 * 			树的根节点
	 * @param changingNodeKey
	 * 			要修改的节点的key
	 * @return
	 */
	public static boolean changeTreeNodeParent(TreeNode root, String changingNodeKey, String newParentKey){
		TreeNode changingNode = removeNodeFromTree(root, changingNodeKey);
		return addNewNodeToTree(root, changingNode, newParentKey);
	}
	/**
	 * 把匹配的节点标记出来
	 * @param root
	 * 			要搜索的树的根节点
	 * @param keyword
	 * 			搜索关键字
	 * @return
	 * 			如果根节点匹配就返回true，不匹配就返回false
	 */
	private static boolean markMatchedNode(TreeNodeWrapper root, String keyword){
		boolean result = false;
		if(root.getValue().indexOf(keyword) != -1){
			root.setStatus(2);//访问过且匹配上了
			result = true;
		}else{
			root.setStatus(1);//访问过但没有匹配上
			result = false;
		}
		List<TreeNode> children = root.getChildren();
		List<TreeNode> childrenWrapper = new ArrayList<TreeNode>(children.size());
		root.setChildren(childrenWrapper);
		TreeNodeWrapper child, parent;
		for(int i=0; i<children.size(); i++){
			child = new TreeNodeWrapper(children.get(i));
			child.setChildren(children.get(i).getChildren());
			child.setParent(root);
			childrenWrapper.add(child);
			if(markMatchedNode(child, keyword)){
				parent = child.getParent();
				while(parent != null){
					parent.setStatus(3); //自己没匹配上，但子节点匹配上了（一人得道，祖先升天）
					parent = parent.getParent();
				}
			}
		}
		return result;
	}
	/**
	 * 在树中搜索value中包含keyword的节点
	 * @param root
	 * 			要搜索的树的根节点
	 * @param keyword
	 * 			搜索关键字
	 * @param type
	 * 			1: 只返回包含关键字的节点
	 * 			2: 返回包含关键字的节点，同时其兄弟节点也返回
	 * @return
	 * 			返回的树直包含匹配的节点和匹配的节点的所有祖先节点
	 */
	public static TreeNode search(TreeNode root, String keyword, int type){
		TreeNode result = new TreeNode();
		final TreeNodeWrapper rootWrapper = new TreeNodeWrapper(root);
		rootWrapper.setChildren(root.getChildren());
		markMatchedNode(rootWrapper, keyword);

		final int tType = type;
		class MatchedNodeTreeBuilderVisitor implements Visitor{

			public boolean visit(Object o) {
				if(o != null){
					TreeNodeWrapper node = (TreeNodeWrapper)o;
					if(node.getStatus() <= 1){
						if(node.getParent() != null){
							if(tType == 1 || (tType == 2 && node.getParent().getStatus() == 1)){
								List<TreeNode> children = node.getParent().getChildren();
								int index = children.indexOf(node);
								children.set(index, null);
							}
						}
					}
				}
				return true;
			}
			
		}
		rootWrapper.traverse(new MatchedNodeTreeBuilderVisitor());
		
		result = (TreeNode)rootWrapper;
		return result;
	}
	/**
	 * 把匹配的节点标记出来
	 * @param root
	 * 			要搜索的树的根节点
	 * @param includeKeys
	 * 			要显示的树结点key list
	 * @return
	 * 			如果根节点匹配就返回true，不匹配就返回false
	 */
	private static boolean markMatchedNode(TreeNodeWrapper root, List<String> includeKeys){
		boolean result = false;
		if(includeKeys.contains(root.getKey())){
			root.setStatus(2);//访问过且匹配上了
			result = true;
		}else{
			root.setStatus(1);//访问过但没有匹配上
			result = false;
		}
		List<TreeNode> children = root.getChildren();
		List<TreeNode> childrenWrapper = new ArrayList<TreeNode>(children.size());
		root.setChildren(childrenWrapper);
		TreeNodeWrapper child, parent;
		for(int i=0; i<children.size(); i++){
			child = new TreeNodeWrapper(children.get(i));
			child.setChildren(children.get(i).getChildren());
			child.setParent(root);
			childrenWrapper.add(child);
			if(markMatchedNode(child, includeKeys)){
				parent = child.getParent();
				while(parent != null){
					parent.setStatus(3); //自己没匹配上，但子节点匹配上了（一人得道，祖先升天）
					parent = parent.getParent();
				}
			}
		}
		return result;
	}
	/**
	 * remark those nodes whose ancestor's status is 2
	 * @param root
	 * 			the root node
	 */
	private static void remarkMatchedNodeByParent(TreeNodeWrapper root){
		class MatchedNodeTreeBuilderVisitor implements Visitor{

			public boolean visit(Object o) {
				if(o != null){
					TreeNodeWrapper node = (TreeNodeWrapper)o;
					if(node.getStatus() == 2){
						for(TreeNode child : node.getChildren()){
							TreeNodeWrapper child2 = (TreeNodeWrapper)child;
							child2.setStatus(2);
						}
					}
				}
				return true;
			}
			
		}
		root.traverse(new MatchedNodeTreeBuilderVisitor());
	}
	/**
	 * 在树中搜索，判断条件如下：
	 * 			1. 如果节点的key 在includeKeys中，则在结果树中显示该节点;
	 * 			2. 如果节点的祖先节点在includeKeys中，则在结果树中该节点也显示
	 * value中包含keyword的节点
	 * @param root
	 * 			要搜索的树的根节点
	 * @param includeKeys
	 * 			要显示的树结点key list
	 * @param type
	 * 			1: 只返回包含关键字的节点
	 * 			2: 返回包含关键字的节点，同时其兄弟节点也返回
	 * @return
	 * 			返回的树直包含匹配的节点和匹配的节点的所有祖先节点
	 */
	public static TreeNode search(TreeNode root, List<String> includeKeys, int type){
		TreeNode result = new TreeNode();
		final TreeNodeWrapper rootWrapper = new TreeNodeWrapper(root);
		rootWrapper.setChildren(root.getChildren());
		markMatchedNode(rootWrapper, includeKeys);
		remarkMatchedNodeByParent(rootWrapper);
		
		final int tType = type;
		class MatchedNodeTreeBuilderVisitor implements Visitor{

			public boolean visit(Object o) {
				if(o != null){
					TreeNodeWrapper node = (TreeNodeWrapper)o;
					if(node.getStatus() != 2){
						if(node.getParent() != null){
							if(tType == 1 || (tType == 2 && node.getParent().getStatus() == 1)){
								if(node.getStatus() <= 1){
									List<TreeNode> children = node.getParent().getChildren();
									int index = children.indexOf(node);
									children.set(index, null);
								}
							}
						}
					}
				}
				return true;
			}
			
		}
		rootWrapper.traverse(new MatchedNodeTreeBuilderVisitor());
		
		result = (TreeNode)rootWrapper;
		return result;
	}
	/**
	 * 找到所有被选中的节点的key列表
	 * 选中但是灰掉（子孙节点中有没被选中的）的节点的key前面会加上：^_^
	 * @param root
	 * 			要找的树的根节点
	 * @param checkedNodeKeys
	 * 			选中的节点的key列表
	 * @return
	 * 			返回所有选中的节点的key列表，包含其祖先节点
	 */
	public static List<String> findAllCheckedNode(TreeNode root, List<String> checkedNodeKeys){
		return findAllCheckedNode(root, checkedNodeKeys, true);
	}
	/**
     * 找到所有被选中的节点的key列表
     * 选中但是灰掉（子孙节点中有没被选中的）的节点的key前面会加上：^_^
     * @param root
     *          要找的树的根节点
     * @param checkedNodeKeys
     *          选中的节点的key列表
     * @param isRecursive
     *          父子节点是否联动，
     *          如果isRecursive == true，那么如果一个节点的所有子节点被选中的话，这个节点就被选中；
     *          如果isRecursive == false，那么如果一个节点的所有子节点被选中的话，这个节点仍然是灰选的.
     * @return
     *          返回所有选中的节点的key列表，包含其祖先节点
     */
	public static List<String> findAllCheckedNode(TreeNode root, List<String> checkedNodeKeys, 
	                                                boolean isRecursive){
        List<String> result = new ArrayList<String>();
        if(checkedNodeKeys != null){
            
            TreeNodeWrapper rootWrapper = new TreeNodeWrapper(root);
            rootWrapper.setChildren(root.getChildren());
            findAllCheckedNode(rootWrapper, checkedNodeKeys, isRecursive, result);
        }
        return result;
    }
	/**
	 * 
	 * @param root
	 * @param checkedNodeKeys
	 * @param resultCheckedNodeKeys
	 * @param isRecursive
	 * @return
	 * 			1: 选中并且不灰（所有子孙节点都被选中）； 2: 不选中； 3：选中但是灰掉（子孙节点中有没被选中的）; 4: 暂时选中
	 */
	private static int findAllCheckedNode(TreeNodeWrapper root, List<String> checkedNodeKeys, boolean isRecursive, 
	                                    List<String> resultCheckedNodeKeys){
		int result = 2;
		
		root.setChildren(root.getChildren());
		
		List<TreeNode> children = root.getChildren();
		int size = 0;
		if(children != null){
			size = children.size();
		}
		List<TreeNode> childrenWrapper = new ArrayList<TreeNode>(size);
		root.setChildren(childrenWrapper);
		TreeNodeWrapper child;
		int[] childrenCheckboxStateArr = new int[size];
		boolean needTraverseChildren = true;
		if(checkedNodeKeys.contains(root.getKey())){
		    result = 1;
		    needTraverseChildren = false;
		}else if(checkedNodeKeys.contains("^_^" + root.getKey())){
		    result = 3;
		    needTraverseChildren = false;
		}
		if(isRecursive){
		    needTraverseChildren = true;
		}
		if(children != null && children.size() > 0 && needTraverseChildren){
    		for(int i=0; i<children.size(); i++){
    			child = new TreeNodeWrapper(children.get(i));
    			child.setChildren(children.get(i).getChildren());
    			child.setParent(root);
    			childrenWrapper.add(child);
    			if(checkedNodeKeys.contains(child.getKey())){
    				childrenCheckboxStateArr[i] = 1;
    				findAllCheckedNode(child, checkedNodeKeys, isRecursive, resultCheckedNodeKeys);
    			}else{
    				if(checkedNodeKeys.contains(child.getParent().getKey())){
    					childrenCheckboxStateArr[i] = 4;
    					findAllCheckedNode(child, checkedNodeKeys, isRecursive, resultCheckedNodeKeys);
    				}else{
    			        childrenCheckboxStateArr[i] = findAllCheckedNode(child, checkedNodeKeys, isRecursive, resultCheckedNodeKeys);
    				}
    			}
    		}
    		
    		int t = 0, p = 0;
    		for(int i=0; i<childrenCheckboxStateArr.length; i++){
                int childrenCheckboxState = childrenCheckboxStateArr[i];
                if(childrenCheckboxState == 1 || childrenCheckboxState == 3 || childrenCheckboxState == 4){
                    p = 3;
                }
                if((childrenCheckboxState == 4 || childrenCheckboxState == 1)&& (t == 1 || i == 0)){
                    t = 1;
                }else{
                    t = 2;
                }
            }
    		if(t == 0 && p == 0){
    			
    		}else{
    			if(t == 1){ //子节点全部选中
    			    if(isRecursive){
    			        result = 1;
    			    }else{ //如果isRecursive == false，那么如果一个节点的所有子节点被选中的话，这个节点仍然是灰选的.
    			        result = 3;
    			    }
    			}else{
    				if(p == 3){//子节点部分选中
    					result = 3;
    				}else{//子节点全部没选中
    					result = 2;
    				}
    			}
    		}
		}
		if(result == 1){
			resultCheckedNodeKeys.add(root.getKey());
		}else if(result == 3){
			resultCheckedNodeKeys.add("^_^" + root.getKey());
		}
		return result;
	}
	/**
	 * 把checkedNodeKeys所包含的树的所有节点复制成另外一棵树, 剩下的节点删掉
	 * @param root
	 * 			要复制的树的根节点
	 * @param checkedNodeKeys
	 * 			选中的节点的key列表
	 * @return
	 * 			把checkedNodeKeys所包含的树的所有节点复制成另外一棵树的根节点
	 */
	public static TreeNode buildTreeWithCheckedNodeKyes(TreeNode root, List<String> checkedNodeKeys){
		TreeNode result = new TreeNode(root);
		Queue<TreeNode> queue = new LinkedList<TreeNode>(), queue2 = new LinkedList<TreeNode>();;
		queue.offer(root);
		queue2.offer(result);
		TreeNode node, parent = result, child, child2;
		List<TreeNode> children;
		while(true){
			if(queue.size() == 0){
				break;
			}
			node = queue.poll();
			parent = queue2.poll();
			children = node.getChildren();
			if(checkedNodeKeys.contains("^_^" + parent.getKey())){
				for(int i=0; i<children.size(); i++){
					child = children.get(i);
					child2 = new TreeNode(child);
					parent.addChild(child2);
					queue.offer(child);
					queue2.offer(child2);
				}
			}
		}
		return result;
	}
	/**
	 * 把一个所有元素皆为Long类型的list转化为所有元素皆为String类型的list
	 * @param list
	 * 			要转化的list
	 * @return
	 * 			所有元素皆为String类型的list
	 */
	public static List<String> convertLongListToStringList(List<Long> list){
		List<String> result = null;
		if(list != null){
			result = new ArrayList<String>();
			for(Long item : list){
				result.add("" + item);
			}
		}
		return result;
	}
	/**
	 * 把一个所有元素皆为String类型的list转化为所有元素皆为Long类型的list
	 * @param list
	 * 			要转化的list
	 * @return
	 * 			所有元素皆为String类型的list
	 */
	public static List<Long> convertStringListToLongList(List<String> list){
		List<Long> result = null;
		if(list != null){
			result = new ArrayList<Long>();
			for(String item : list){
				result.add(Long.parseLong(item));
			}
		}
		return result;
	}
	/**
	 * 把一个乱序的FlatTreeNode list转化为一颗树
	 * @param rootKey
	 * 			根节点的key
	 * @param flatTreeNodeList
	 * 			乱序的FlatTreeNode list
	 * @param comparator
	 * 			子节点排序的Comparator, 不排序的话可以传null
	 * @return
	 * 			返回创建好的树的根节点
	 */
	public static TreeNode buildTreeFromFlatTreeNodeList(String rootKey, 
			List<FlatTreeNode> flatTreeNodeList, Comparator<TreeNode> comparator){
		TreeNode result = null;

		List<TreeNode> parents = new ArrayList<TreeNode>();
		parents.add(result);
		FlatTreeNode flatTreeNode;
		String parentKey = null;
		TreeNode parent = null;
		HashMap<String, TreeNode> hash = new HashMap<String, TreeNode>();
		hash.put(rootKey, result);
		
		for (int i = 0; i < flatTreeNodeList.size(); i++) {
			flatTreeNode = flatTreeNodeList.get(i);
			hash.put(flatTreeNode.getKey(), new TreeNode(flatTreeNode.getKey(), flatTreeNode.getValue(), true, 2, flatTreeNode.getData()));
		}
		for (int i = 0; i < flatTreeNodeList.size(); i++) {
			flatTreeNode = flatTreeNodeList.get(i);
			parentKey = flatTreeNode.getParentKey();
			if(parentKey != null){
				parent = hash.get(parentKey);
			}
			if(parent != null){
				parent.addChild(hash.get(flatTreeNode.getKey()));
				if(comparator != null){
					Collections.sort(parent.getChildren(), comparator);
				}
				parent.setIsLeaf(false);
			}
			
			flatTreeNodeList.remove(i);
			i--;
		}
		result = hash.get(rootKey);
		
		return result;
	}
	/**
	 * 获取以树root中parentKey为父节点的子节点列表（子节点不包含children list）
	 * @param root
	 * 			树的根节点
	 * @param parentKey
	 * 			要获取的父节点的key
	 * @return
	 * 			树root中parentKey为父节点的子节点列表（子节点不包含children list）
	 */
	public static List<TreeNode> getChildNodeList(TreeNode root, String parentKey){
		List<TreeNode> result = new ArrayList<TreeNode>();
		
		final List<TreeNode> tResult = new ArrayList<TreeNode>();
		final String tParentKey = parentKey; 
		class GetChildNodeListVisitor implements Visitor{
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(node.getKey().equals(tParentKey)){
					for(TreeNode child : node.getChildren()){
						if(child != null){
							tResult.add(new TreeNode(child));
						}
					}
					return false;
				}
				return true;
			}
			
		}
		root.traverse(new GetChildNodeListVisitor());
		result = tResult;
		
		return result;
	}
	/**
	 * get all descendant keys of the node with it's key in parentKeys list
	 * @param root
	 * 			the tree root node
	 * @param parentKeys
	 * 			parent node key list
	 * @return
	 * 			all descendant keys list including itself
	 */
	public static List<String> getAllDescendantKeys(TreeNode root, List<String> parentKeys){
		List<String> result ;
		
		final List<String> tResult = new ArrayList<String>();
		tResult.addAll(parentKeys);
		
		class GetAllDecendantKeysVisitor implements Visitor{
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(tResult.contains(node.getKey())){
					if(node.getChildren() != null){
						for(TreeNode child : node.getChildren()){
							if(child != null){
								tResult.add(child.getKey());
							}
						}
					}
				}
				return true;
			}
			
		}
		root.traverse(new GetAllDecendantKeysVisitor());
		result = tResult;
		return result;
	}
	/**
	 * get all decendant keys of the node with it's key in childKeys list
	 * @param root
	 * 			the tree root node
	 * @param childKeys
	 * 			child node key list
	 * @return
	 * 			all ancestor keys list including itself
	 */
	public static List<String> getAllAncestorKeys(TreeNode root, List<String> childKeys){
		List<String> result ;
		
		final List<String> tResult = new ArrayList<String>();
		tResult.addAll(childKeys);
		
		class GetAllAncestorKeysVisitor implements Visitor{
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				for(TreeNode child : node.getChildren()){
					if(child != null){
						if(tResult.contains(node.getKey())){
							if(!tResult.contains(node.getKey())){
								tResult.add(node.getKey());
							}
						}
						
					}
				}
				return true;
			}
			
		}
		root.childrenFirstTraverse(new GetAllAncestorKeysVisitor());
		result = tResult;
		return result;
	}
	/**
	 * get all children keys of the node with it's key in parenKeys list
	 * @param root
	 * 			the tree root node
	 * @param parenKeys
	 * 			parent node key list
	 * @return
	 * 			all children keys list
	 */
	public static List<String> getAllChildrenKeys(TreeNode root, List<String> parenKeys){
		List<String> result ;
		
		final List<String> tResult = new ArrayList<String>();
		final List<String> tParenKeys = new ArrayList<String>();
		tParenKeys.addAll(parenKeys);
		
		class GetAllChildrenKeysVisitor implements Visitor{
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(tParenKeys.contains(node.getKey())){
					for(TreeNode child : node.getChildren()){
						if(child != null){
							if(!tResult.contains(child.getKey())){
								tResult.add(child.getKey());
							}
						}
					}
				}
				return true;
			}
			
		}
		root.traverse(new GetAllChildrenKeysVisitor());
		result = tResult;
		return result;
	}
	/**
	 * 计算一个ajax树提交选中的checkbox列表
	 * 因为有可能一个节点是灰选的，但是他的子节点没有展开，这样提交的时候他的选中的子节点就没有提交上来，这就需要根据旧的选中节点列表来计算。
	 * 1. post上来的节点如果是灰选但没有初始化子节点，他的checkbox的value会加上disabled_uninitialized_前缀
	 * 2. post上来的节点如果是灰选且初始化了子节点，他的checkbox的value会加上disabled_前缀
	 * @param root
	 * @param postedCheckedNodeKeys
	 * @param oldCheckedNodeKeys
	 * @return
	 */
	public static List<String> computeAjaxCheckedNodeKeysFromPostData(TreeNode root, List<String> postedCheckedNodeKeys, List<String> oldCheckedNodeKeys){
		List<String> result = new ArrayList<String>();
		
		if(oldCheckedNodeKeys != null && postedCheckedNodeKeys != null){
			String key, value;
			List<String> parentKeys = new ArrayList<String>(1);
			for(int i=0; i<postedCheckedNodeKeys.size(); i++){
				value = postedCheckedNodeKeys.get(i);
				if(value.indexOf("disabled_uninitialized_") != -1){
					key = value.substring("disabled_uninitialized_".length());
					parentKeys.add(key);
					value = "disabled_" + key;
					postedCheckedNodeKeys.set(i, value);
				}
			}
			List<String> allDescendantKeys = TreeUtil.getAllDescendantKeys(root, parentKeys);
			for(String oldCheckedKey : oldCheckedNodeKeys){
				if(allDescendantKeys.contains(oldCheckedKey)){
					result.add(oldCheckedKey);
				}
			}
		}
		if(postedCheckedNodeKeys != null){
			result.addAll(postedCheckedNodeKeys);
		}
		
		return result;
	}
	/**
	 * remove all key who starts with "disabled_"
	 * @param keys
	 * 			key list
	 * @return
	 * 			key list with all key which starts with "disabled_" removed
	 */
	public static List<String> removeAllDisabledKeys(List<String> keys){
		List<String> result = new ArrayList<String>();
		if(keys != null){
			for(String key : keys){
				if(key.indexOf("disabled_") != -1){
					result.add(key);
				}
			}
			keys.removeAll(result);
		}
		
		return result;
	}
	/**
	 * remove all key who starts with "disabled_"
	 * @param keys
	 * 			key list
	 * @return
	 * 			key list with all key which starts with "disabled_" removed
	 */
	public static List<String> removeAllSmileKeys(List<String> keys){
		List<String> result = new ArrayList<String>();
		if(keys != null){
			for(String key : keys){
				if(key.startsWith("^_^")){
					result.add(key);
				}
			}
			keys.removeAll(result);
		}
		
		return result;
	}
	/**
	 * 重新计算选中的节点列表：
	 * 	  1. 如果A节点的所有子节点都在checkedNodeKeys列表中,则把所有子节点都删掉，加入A节点(如果A节点不在checkedNodeKeys列表中)
	 *    2. 如果A节点的key在removedNodeKeys对应的所有节点的祖先列表中且A节点的所有子节点都不在checkedNodeKeys列表中，则把A节点删掉
	 *    3. 如果A节点的key在addedNodeKeys列表中且不是A节点的所有兄弟节点都在checkedNodeKeys列表中，则把A节点的所有祖先节点都删掉
	 * @param root
	 * @param checkedNodeKeys
	 * @param addedNodeKeys
	 * @return
	 * 		返回规整的选中的节点列表
	 */
	public static List<String> recomputeCheckedNodeKeys(TreeNode root, List<String> checkedNodeKeys, List<String> removedNodeKeys){
		List<String> result = new ArrayList<String>();
		
		final List<String> tResult = new ArrayList<String>();
		tResult.addAll(checkedNodeKeys);
		
		final List<String> tRemovedNodeKeys = new ArrayList<String>(removedNodeKeys.size());
		tRemovedNodeKeys.addAll(removedNodeKeys);
		
		final List<String> tRemovedNodeAncestorKeys = new ArrayList<String>();
		
		class RecomputeCheckedNodeKeysVisitor implements Visitor{
			private boolean isAllChildrenIn(TreeNode node, List<String> checkedNodeKeys){
				boolean result = true;
				if(node.getChildren() == null || node.getChildren().size() <= 0){
					result = false;
				}
				if(node.getChildren() != null){
					for(TreeNode child : node.getChildren()){
						if(!checkedNodeKeys.contains(child.getKey())){
							result = false;
							break;
						}
					}
				}
				return result;
			}
			private boolean isHaveChildrenIn(TreeNode node, List<String> checkedNodeKeys){
				boolean result = false;
				if(node.getChildren() != null){
					for(TreeNode child : node.getChildren()){
						if(checkedNodeKeys.contains(child.getKey())){
							result = true;
							break;
						}
					}
				}
				return result;
			}
			private boolean isPartOfChildrenIn(TreeNodeWrapper node, List<String> checkedNodeKeys){
				boolean result = false;
				if(node.getChildren() == null || node.getChildren().size() <= 0){
					result = false;
				}else{
					int childrenInCount = 0;
					for(TreeNode child : node.getChildren()){
						if(checkedNodeKeys.contains(child.getKey())){
							childrenInCount++;
						}
					}
					if(childrenInCount > 0 && childrenInCount < node.getChildren().size()){
						result = true;
					}
				}
				return result;
			}
			private void removeAllChildrenKeys(TreeNode node, List<String> checkedNodeKeys){
				if(node.getChildren() != null){
					for(TreeNode child : node.getChildren()){
						checkedNodeKeys.remove(child.getKey());
					}
				}
			}
			private boolean replaceAllChildrenKeysByParentKey(TreeNodeWrapper node, List<String> checkedNodeKeys){
				boolean result = false;
				if(isAllChildrenIn(node, checkedNodeKeys)){
					if(!tResult.contains(node.getKey())){
						tResult.add(node.getKey());
					}
					removeAllChildrenKeys(node, tResult);
					result = true;
					if(node.getParent() != null){
						replaceAllChildrenKeysByParentKey(node.getParent(), checkedNodeKeys);
					}
				}
				return result;
			}
			private boolean removeParentIfAllChildrenIsNotIn(TreeNodeWrapper node, List<String> checkedNodeKeys){
				boolean result = false;
				if(!isHaveChildrenIn(node, checkedNodeKeys)){
					if(tResult.contains(node.getKey())){
						tResult.remove(node.getKey());
					
						result = true;
						if(node.getParent() != null){
							removeParentIfAllChildrenIsNotIn(node.getParent(), checkedNodeKeys);
						}
					}
				}
				return result;
			}
			private boolean removeParentIfOnlyPartOfChildrenIsIn(TreeNodeWrapper node, List<String> checkedNodeKeys){
				boolean result = false;
				if(isPartOfChildrenIn(node, checkedNodeKeys)){
					if(tResult.contains(node.getKey())){
						tResult.remove(node.getKey());
					
						result = true;
						if(node.getParent() != null){
							removeParentIfOnlyPartOfChildrenIsIn(node.getParent(), checkedNodeKeys);
						}
					}
				}
				return result;
			}
			public boolean visit(Object o) {
				TreeNodeWrapper node = (TreeNodeWrapper)o;
				replaceAllChildrenKeysByParentKey(node, tResult);
				if(tRemovedNodeKeys.contains(node.getKey())){
					List<TreeNodeWrapper> ancestors = getAllAncestor(node);
					for(TreeNodeWrapper ancestor : ancestors){
						if(!tRemovedNodeAncestorKeys.contains(ancestor.getKey())){
							tRemovedNodeAncestorKeys.add(ancestor.getKey());
						}
					}
				}
				if(tRemovedNodeAncestorKeys.contains(node.getKey())){
					removeParentIfAllChildrenIsNotIn(node, tResult);
				}
				
				removeParentIfOnlyPartOfChildrenIsIn(node, tResult);
				return true;
			}
			
		}
		TreeNodeWrapper newRoot = buildTreeWithParentReference(root);
		newRoot.childrenFirstTraverse(new RecomputeCheckedNodeKeysVisitor());
		result = tResult;
		return result;
	}
	/**
	 * get node's all ancestor
	 * @param node
	 * @return
	 * 		node's all ancestor list
	 */
	public static List<TreeNodeWrapper> getAllAncestor(TreeNodeWrapper node){
		List<TreeNodeWrapper> result = new ArrayList<TreeNodeWrapper>();
		TreeNodeWrapper parent = node.getParent();
		while(parent != null){
			result.add(parent);
			parent = parent.getParent();
		}
		return result;
	}
	/**
	 * build a new tree with every node keep a reference to it's parent node
	 * @param root
	 * 			the root node of the tree
	 * @return
	 * 			the root node of the new tree
	 */
	public static TreeNodeWrapper buildTreeWithParentReference(TreeNode root){
		TreeNodeWrapper result = new TreeNodeWrapper(root);
		result.setChildren(root.getChildren());
		
		class MyVisitor implements Visitor{

			public boolean visit(Object o) {
				TreeNodeWrapper node = (TreeNodeWrapper)o;
				TreeNodeWrapper nodeWrapper;
				List<TreeNode> children = new ArrayList<TreeNode>();
				if(node.getChildren() != null){
					for(TreeNode child : node.getChildren()){
						if(child != null){
							nodeWrapper = new TreeNodeWrapper(child);
							nodeWrapper.setParent(node);
							nodeWrapper.setChildren(child.getChildren());
							children.add(nodeWrapper);
						}
					}
					node.setChildren(children);
				}
				return true;
			}
			
		}
		result.traverse(new MyVisitor());
		return result;
	}
	/**
	 * prune a tree to limit it's node number less than leftTreeNodeNum
	 * @param root
	 * 			the root node the the tree
	 * @param leftTreeNodeNum
	 * 			the left tree node number
	 * @return
	 * 			the new pruned tree
	 */
	public static TreeNode pruneTree(TreeNode root, int leftTreeNodeNum){
		TreeNode result;
		
		final TreeNode tResult = new TreeNode(root);
		final int[] count = new int[2];
		count[0] = leftTreeNodeNum;
		count[1] = 1;
		
		class PruneTreeVisitor implements Visitor{

			public boolean visit(Object o) {
				
				TreeNode node = (TreeNode)o;
				if(node.getChildren() != null){
					for(TreeNode child : node.getChildren()){
						if(count[1] < count[0]){
							if(child != null){
								addTreeNode(tResult, node, new TreeNode(child));
								count[1]++;
							}
						}
					}
				}
				if(count[1] < count[0]){
					return true;
				}else{
					return false;
				}
			}
		}
		root.traverse(new PruneTreeVisitor());
		result = tResult;
		return result;
	}
	/**
	 * add a new node to a tree
	 * @param root
	 * 			the root node of the tree
	 * @param parent
	 * 			the new node's parent node
	 * @param node
	 * 			new node
	 */
	public static void addTreeNode(TreeNode root, TreeNode parent, TreeNode node){
		
		final TreeNode tParent = parent;
		final TreeNode tNode = node;
		
		class AddTreeNodeVisitor implements Visitor{

			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				if(node.equals(tParent)){
					node.addChild(tNode);
				}
				return true;
			}
			
		}
		
		root.traverse(new AddTreeNodeVisitor());
	}
	/**
	 * count the total number of the node of the tree
	 * @param root
	 * 			the root node of the tree
	 * @return
	 * 			total number of the node of the tree
	 * 			
	 */
	public static int countTotalTreeNodeNum(TreeNode root){
		int result = 0;
		final int[] tResult = {0};
		class CountTotalTreeNodeNum implements Visitor{

			public boolean visit(Object o) {
				tResult[0]++;
				return true;
			}
			
		}
		root.traverse(new CountTotalTreeNodeNum());
		result = tResult[0];
		
		return result;
	}
	/**
	 * 自动生成一颗树
	 * @param root
	 * 			树的根节点
	 * @param depth
	 * 			树的深度
	 * @param width
	 * 			树的宽度
	 * 
	 */
	public static void generateTree(TreeNode root, int depth, int width){
		TreeNode node;
		for(int i=0; i<width; i++){
			String key = root.getKey() + " ^ " + depth + "-" + i;
			String value = "节点 - " + depth + "-" + i;
			node = new TreeNode(key, value);
			if(depth == 0){
				node.setIsLeaf(true);
			}
			root.addChild(node);
			if(depth > 0){
				generateTree(node, depth - 1, width);
			}
		}
	}
	/**
	 * 根据checkedNodeKeys列表中所含数据，计算生成一个选中的checkbox列表的树
	 * @param root
	 * @param checkedNodeKeys
	 * @return
	 */
	public static void markCheckedNode(TreeNode root, List<String> checkedNodeKeys) {
		/**
		 * STATE_SCHRODINGER : 部分子项选中 <br />
		 * STATE_CHECKED : 全部子项选中 <br />
		 * STATE_UNCHECKED : 全部子项未选中 <br />
		 */
		final int STATE_SCHRODINGER = 3;
		final int STATE_CHECKED = 1;
		final int STATE_UNCHECKED = 2;
		final List<String> tmpcheckedNodeKeys = checkedNodeKeys;

		class FindChildrenVisitor implements Visitor {

			public boolean visit(Object o) {
				TreeNode node = (TreeNode) o;

				if(tmpcheckedNodeKeys.contains(node.getKey())){
					node.setCheckboxState(STATE_CHECKED);
				}else if(tmpcheckedNodeKeys.contains("^_^" + node.getKey())){
					node.setCheckboxState(STATE_SCHRODINGER);
				}else{
					node.setCheckboxState(STATE_UNCHECKED);
				}
				return true;
			}
		}
		root.widthFirstTraverse(new FindChildrenVisitor());
	}
	/**
	 * 判断一个节点是否有子孙节点被选中 
	 * @param node
	 * 
	 */		
	public static Boolean isNodeHasDescendantChecked(TreeNode node){	
		class HasDescendantCheckedVisitor implements Visitor{
			public Boolean result = false;  
			public boolean visit(Object o){
				TreeNode node = (TreeNode)o;
				if(node.getCheckboxState() == 1){
					result = true;
					return false;
				}else{
					return true;
				}
			}
		}
		HasDescendantCheckedVisitor visitor = new HasDescendantCheckedVisitor();				
    	node.widthFirstTraverse(visitor);    	
    	return visitor.result;
	}
	/**
	 * 判断一个节点是否有子孙节点没有被选中 
	 * @param node
	 * 
	 */		
	public static Boolean isNodeHasNotDescendantChecked(TreeNode node){	
		class HasNotDescendantCheckedVisitor implements Visitor{
			public Boolean result = false;  
			public boolean visit(Object o){
				TreeNode node = (TreeNode)o;
				if(node.getCheckboxState() == 2){
					result = true;
					return false;
				}else{
					return true;
				}
			}
		}
		HasNotDescendantCheckedVisitor visitor = new HasNotDescendantCheckedVisitor();				
    	node.widthFirstTraverse(visitor);    	
    	return visitor.result;
	}
	/**
	 * 判断一个节点是否全部子孙节点被选中，如果不是，则将该节点从checkedNodeKeys中去掉
	 * @param checkedNodeKeys	
	 * 
	 */	
	public static List<String> redefineCheckedNodeKeys(TreeNode root, List<String> checkedNodeKeys){
		//标记整棵树中存在的权限
		TreeUtil.markCheckedNode(root, checkedNodeKeys);
		
		final List<String> removedCheckedNodeKeys = new ArrayList<String>();
		class DeleteNodeVisitor implements Visitor{
			public boolean visit(Object o) {
				TreeNode node = (TreeNode)o;
				
				if(node.getChildren() != null){
					if(TreeUtil.isNodeHasNotDescendantChecked(node)){
						removedCheckedNodeKeys.add(node.getKey());
					}
				}
				return true;
			}
			
		}
		root.widthFirstTraverse(new DeleteNodeVisitor());
		checkedNodeKeys.removeAll(removedCheckedNodeKeys);
		return checkedNodeKeys;
	}
	
}

