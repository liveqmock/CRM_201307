/**
 * 20110513--huhl@hundsun.com--
 */
Hs.BizframeUtil = {
	/**
	 * 获取数组
	 * 
	 * @param obj
	 */
	getArray : function(obj, separator) {
		separator = separator || ",";// 默认分隔符是','
		var arr;
		if (Ext.isArray(obj)) {
			arr = obj;
		} else if (typeof(obj) == "string") {
			arr = (obj ? obj.split(separator) : []);
		} else {
			arr = (obj ? [obj] : []);
		}
		return arr;
	},
	/**
	 * 获取数组map
	 * 
	 * @param array
	 * @param separator
	 */
	getArrayMap : function(array, separator) {
		array = Hs.BizframeUtil.getArray(array, separator);
		var arrayMap = {};
		for (var i = 0, len_i = array.length; i < len_i; i++) {
			arrayMap[array[i]] = true;
		}
		return arrayMap;
	},
	queryTree : function(tree, text) {
		var nodes = [];
		var startP = Hs.BizframeUtil.makePy(text);
		var allChildrens = tree.getAllChildren(), len = allChildrens.length;
		for (var i = 0; i < len; i++) {
			var child = allChildrens[i], childValue = child.text;
			if (childValue.indexOf(text) > -1
					|| Hs.BizframeUtil.makePy(childValue).indexOf(startP) > -1)
				nodes.push(child);
		}
		return nodes;
	}

};
/**
 * 
 * @param dateSet
 * @param grid
 * @return
 */
function setDateSetCurrentPage(dateSet, grid) {
	var $_start = grid.getStart();
	var $_limit = grid.getLimit();
	var param = {};
	Ext.apply(param, {
		start : $_start
	});
	Ext.apply(param, {
		limit : $_limit
	});
	dateSet.setParams(param);
}

/**
 * 
 * @param dateSet
 * @param grid
 * @return
 */
function setDateSetStartPage(dateSet, grid) {
	var $_start = 0;
	var $_limit = grid.pageSize;
	var param = {};
	Ext.apply(param, {
		start : $_start
	});
	Ext.apply(param, {
		limit : $_limit
	});
	dateSet.setParams(param);
}

/**
 * 
 * @param obj
 * @return
 */
function checkIsNull(obj) {
	return (undefined == obj || null == obj || '' == obj);
}

/**
 * 检测用户是否拥有角色的权限
 * 
 * @param roleId
 * @return
 */
function checkUserRole(roleId) {
	var _hasRight = false;
	if (top.userRightRoles) {
		for (var j = 0; j < top.userRightRoles.length; j++) {
			if (roleId == top.userRightRoles[j].role_code) {
				_hasRight = true;
				break;
			}
		}
	}
	return _hasRight;
}

/**
 * 
 */
function refreshTreeNode(tree, nodeId, rootId) {
	if (!tree)
		return;
	var selectNode = tree.getNodeById(nodeId);
	if (selectNode) {
		if (selectNode.attributes.leaf) {// 叶子节点
			if (selectNode.parentNode) {// 叶子节点存在父亲节点
				selectNode.parentNode.reload();
			} else {// 叶子节点不存在父亲节点
				tree.collapseAll();
				tree.getNodeById(rootId).reload();
			}
		} else {// 非叶子节点
			selectNode.reload();
		}
	}
}
/**
 * 
 */
function refreshGrid(grid, param) {
	if (grid) {
		grid.clear();
		grid.refresh();
	}
}
/**
 * 
 */
function checkNode(treePanl, node, isCheck) {
	if (isCheck) {
		var paths = node.getPath().split("/");
		for (var i = 0; i < paths.length; i++) {
			var $_id = paths[i];
			var $_node = treePanl.getNodeById($_id);
			if ($_node && "root" != $_id && node.id != $_id) {
				treePanl.setChecked($_node, true);
			}
		}
	} else {
		var $_pid = node.attributes.pid;
		var $_node = treePanl.getNodeById($_pid);
		while ("root" != $_pid && $_node) {
			var pnum = getCheckedChildNum(treePanl, $_node);
			var unCkeckNodeNum = getCheckedChildNum(treePanl, node);
			var checkedChild = (pnum - unCkeckNodeNum - 1) > 0;
			if (!checkedChild) {
				treePanl.setChecked($_node, false);
			}
			$_pid = $_node.attributes.pid;
			$_node = treePanl.getNodeById($_pid);
		}

	}
}

function getCheckedChildNum(treePanl, node) {
	var children = treePanl.getAllChildren(null, node);
	var size = 0;
	for (var i = 0; i < children.length; i++) {
		var child = children[i];
		if (child.attributes.checked) {
			size++;
		}
	}
	return size;
}

/**
 * 新增一个树节点 treePanel：树容器 pid ：父节点id treeNode ：当前新增树节点
 */
function addTreeNode(treePanel, pid, treeNode, rootId) {
	if (treePanel && pid && treeNode) {
		var id = treeNode.id;
		var selectNode = treePanel.getNodeById(pid);
		var path = selectNode.getPath();
		treePanel.getNodeById(rootId).reload();
		treePanel.expandPath(path, "", function() {
			if (treePanel.getNodeById(id)) {
				treePanel.getNodeById(id).select();
			}
		});
	}
}
/**
 * 移除一个树节点 treePanel：树容器 pid ：父节点id treeNode ：当前新移除节点
 */
function removeTreeNode(treePanel, pid, treeNode) {

}
