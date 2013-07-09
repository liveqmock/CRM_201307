package com.oasis.tmsv5.service.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.base.Constants;
import com.oasis.tmsv5.common.enums.status.CommonStatus;
import com.oasis.tmsv5.common.so.security.OrganizationSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.common.util.tree.FlatTreeNode;
import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.util.tree.TreeUtil;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;
import com.oasis.tmsv5.dao.organization.OrganizationDAO;
import com.oasis.tmsv5.model.organization.Organization;
import com.oasis.tmsv5.service.BaseComponent;
import com.oasis.tmsv5.service.helper.ErrorDispHelper;
import com.oasis.tmsv5.util.exception.ValidationException;

@Service
public class OrganizationComponent extends BaseComponent {

    private static final Log logger = LogFactory.getLog(OrganizationComponent.class);

    @Autowired
    private OrganizationDAO organizationDAO;

    public void createOrganization(OrganizationVO orgvo) {
        String error = validate(orgvo);
        if (error != null) {
            throw new ValidationException(error);
        }
        buildOrg(orgvo);
        Organization org = super.getDozer().convert(orgvo, Organization.class);
        organizationDAO.insert(org);
    }

    private void buildOrg(OrganizationVO vo) {
        Organization parent = organizationDAO.find(vo.getParentId());
        vo.setTreePath(parent.getTreePath() + Constants.VERTICAL_LINE + vo.getCode());
        vo.setNamePath(parent.getNamePath() + Constants.VERTICAL_LINE + vo.getName());
        vo.setStatus(CommonStatus.ACTIVE);
    }

    public PageList<OrganizationVO> getPageList(OrganizationSO so) {
        List<OrganizationVO> orgList = organizationDAO.getPaginatedList(so);
        List<OrganizationVO> list = super.getDozer().convertList(orgList, OrganizationVO.class);
        int cnt = organizationDAO.getPaginatedListCount(so);
        PageList<OrganizationVO> page = new PageList<OrganizationVO>();
        page.setList(list);
        page.setFullListSize(cnt);
        return page;
    }

    public OrganizationVO update(OrganizationVO orgvo) {
    	String error = editValidate(orgvo);
        if (error != null) {
            throw new ValidationException(error);
        }
        Organization org = super.getDozer().convert(orgvo, Organization.class);
        organizationDAO.update(org);
        return orgvo;
    }
    
    private String editValidate(OrganizationVO vo) {
    	Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("name", vo.getName());
        OrganizationVO orgs = organizationDAO.checkDuplication(searchMap);
        if (orgs != null) {
            String error = "";
            //新的name在数据库里存在，且不为旧的name,则不允许修改
            if (vo.getName().equals(orgs.getName()) && !vo.getOldName().equals(orgs.getName())) {
                error = ErrorDispHelper.getInstance().getValue("ORGANIZATION_NAME_ERROR");
                return error;
            }
        }
        
        Map<String, Object> searchMap2 = new HashMap<String, Object>();
        searchMap2.put("code", vo.getCode());
        OrganizationVO orgs2 = organizationDAO.checkDuplication(searchMap2);
        if (orgs2 != null) {
        	String error = "";
        	//新的code在数据库里存在，且不为旧的code,则不允许修改
	        if (vo.getCode().equals(orgs2.getCode()) && !vo.getOldCode().equals(orgs2.getCode())) {
	        	error = ErrorDispHelper.getInstance().getValue("ORGANIZATION_CODE_ERROR");
	        	return error;
	        }
        }
        return null;
    }
    
    private String validate(OrganizationVO vo) {
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("name", vo.getName());
        OrganizationVO orgs = organizationDAO.checkDuplication(searchMap);
        if (orgs != null) {
            String error = "";
            if (vo.getName().equals(orgs.getName())) {
            	error =  ErrorDispHelper.getInstance().getValue("ORGANIZATION_NAME_ERROR");
                return error;
            }
        }
        Map<String, Object> searchMap2 = new HashMap<String, Object>();
        searchMap2.put("code", vo.getCode());
        OrganizationVO orgs2 = organizationDAO.checkDuplication(searchMap2);
        if (orgs2 != null) {
        	String error = "";
	        if (vo.getCode().equals(orgs2.getCode())) {
	        	error = ErrorDispHelper.getInstance().getValue("ORGANIZATION_CODE_ERROR");
	        	return error;
	        }
        }
        return null;
    }

    public OrganizationVO view(Long id) {
        OrganizationVO orgvo = find(id);
        return orgvo;
    }

    // TODO
    public void remove(Long id) {
        organizationDAO.delete(id);
    }

    public TreeNode getOrgTree(String condition) {
        OrganizationVO root = organizationDAO.getRootOrganization();
        List<OrganizationVO> allOrg = organizationDAO.getAllOrganizations(condition);
        if (!condition.isEmpty()) {
            // 查找节点的的所有祖先节点
            allOrg = findLostNodes(allOrg);
        } else {
            if (!allOrg.contains(root)) {
                allOrg.add(root);
            }
        }
        return buildTree(root, allOrg);
    }
    
    private List<OrganizationVO> findLostNodes(List<OrganizationVO> child) {
        Set<OrganizationVO> allNodes = new HashSet<OrganizationVO>();
        Queue<OrganizationVO> childNodes = new LinkedList<OrganizationVO>(child);
        while (childNodes.size() > 0) {
            OrganizationVO org = childNodes.poll();
            allNodes.add(org);
            if (org.getId() != 60000) {
                OrganizationVO parent = (OrganizationVO)organizationDAO.findVO(org.getParentId());
                if(null != parent){
                    childNodes.offer(parent);
                }
            }
        }
        return new ArrayList<OrganizationVO>(allNodes);
    }

    public OrganizationVO find(Long id) {
        return getDozer().convert(organizationDAO.find(id), OrganizationVO.class);
    }

    private TreeNode buildTree(OrganizationVO root, List<OrganizationVO> organizations) {
        List<FlatTreeNode> flatTreeNodeList = new ArrayList<FlatTreeNode>(organizations.size() + 1);
        FlatTreeNode flatTreeNode;

        for (OrganizationVO organization : organizations) {
            flatTreeNode = new FlatTreeNode(organization.getId().toString(), organization.getName(), organization.getParentId()
                    + StringUtils.EMPTY, organization);
            flatTreeNodeList.add(flatTreeNode);
        }

        TreeNode menuItemTreeNodes = TreeUtil.buildTreeFromFlatTreeNodeList(root.getId().toString(), flatTreeNodeList, null);
        return menuItemTreeNodes;
    }
    
    public OrganizationVO findByTreepath(String treepath){
        return organizationDAO.findByTreepath(treepath);
    }
    
    public TreeNode getBelowOrgTree(OrganizationVO root){
    	List<OrganizationVO> allOrg = organizationDAO.getBelowOrganizations(root.getTreePath());
    	return buildTree(root, allOrg);
    }
    
    public OrganizationVO getOrgByAccount(Long accountId){
    	List<OrganizationVO> orgList = organizationDAO.getAsocOrgByAccount(accountId);
    	if(orgList != null && orgList.size() != 0){
    		return orgList.get(0);
    	} else {
    		return null;
    	}
	}
}
