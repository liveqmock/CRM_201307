package com.oasis.tmsv5.common.enums.type;

public enum AssociateTable {
	ROLE_MENUITEM("gt_role_menuitem","role","menuitem"),
	ACCOUNT_ROLE("gt_account_role","account_id","role_id"),
	ACCOUNT_ORGANIZATION("gt_account_org_assoc","account_id","org_id"),
	ROLE_ACCOUNT("gt_account_role","role_id","account_id"),
	ORG_CUSTOMER("gt_org_customer_assoc","organization_id","customer_id"),
	ORG_NZONE("gt_org_nzone_assoc","organization_id","nzone_id"),
	ROLE_PROJECT("job_role_project","role_id","project_name"),
	TRUCK_ROUTE("wl_contract_route","truck_id","route_id");
	
	private String tableName;
	private String assocCol;
	private String inverseCol;
	
	AssociateTable(String tableName,String assocCol,String inverseCol){
		this.tableName = tableName;
		this.assocCol = assocCol;
		this.inverseCol = inverseCol;
	}
	
	public static AssociateTable[] getAllConstants(){
		return AssociateTable.class.getEnumConstants();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAssocCol() {
		return assocCol;
	}

	public void setAssocCol(String assocCol) {
		this.assocCol = assocCol;
	}

	public String getInverseCol() {
		return inverseCol;
	}

	public void setInverseCol(String inverseCol) {
		this.inverseCol = inverseCol;
	}
}
