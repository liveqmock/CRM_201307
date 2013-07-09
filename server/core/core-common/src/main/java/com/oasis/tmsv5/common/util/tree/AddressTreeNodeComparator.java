package com.oasis.tmsv5.common.util.tree;

import java.util.Comparator;

/**
 * 地址节点排序时的大小对比类
 * @author bl00064
 *
 */
public class AddressTreeNodeComparator implements Comparator<TreeNode> {

	public int compare(TreeNode arg0, TreeNode arg1) {
		int type0 = getType(arg0), type1 = getType(arg1);
		int size0, size1;
		size0 = arg0.getChildren()==null? 0:arg0.getChildren().size();
		size1 = arg1.getChildren()==null? 0:arg1.getChildren().size();
		if(size0 <=0 && size1 > 0){
			return -1;
		}else if(size0 > 0 && size1 <=0){
			return 1;
		}
		if(type0 > type1){
			return 1;
		}else if(type0 < type1){
			return -1;
		}else{
			return 0;
		}
		
	}
	/**
	 * 获取节点的排序等级
	 * @param node
	 * 			要检查的树节点
	 * @return
	 */
	private int getType(TreeNode node){
		int result;
		String value = node.getValue();
		
		if(value.endsWith("市")){
			result = 1;
			return result;
		}else if(value.endsWith("省")){
			result = 2;
			return result;
		}else if(value.endsWith("自治区")){
			result = 4;
			return result;
		}else if(value.endsWith("特别行政区")){
			result = 5;
			return result;
		}else if(value.endsWith("地区")){
			result = 5;
			return result;
		}else if(value.endsWith("区")){
			result = -1;
			return result;
		}else if(value.endsWith("盟")){
			result = 6;
			return result;
		}else if(value.endsWith("自治县")){
			result = 9;
			return result;
		}else if(value.endsWith("县")){
			result = 8;
			return result;
		}else if(value.endsWith("街道")){
			result = 10;
			return result;
		}else if(value.endsWith("乡")){
			result = 11;
			return result;
		}else if(value.endsWith("镇")){
			result = 12;
			return result;
		}else{
			result = 20;
			return result;
		}
	}

}
