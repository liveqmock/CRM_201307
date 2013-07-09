package com.oasis.tmsv5.model.menuItem;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.common.enums.type.MenuType;
import com.oasis.tmsv5.model.BaseModel;

@Table(name = "GT_MENU_ITEM")
@SequenceGenerator(name = "GT_MENU_ITEM_SEQUENCE")
public class MenuItem extends BaseModel {

    private static final long serialVersionUID = 6440943617383720938L;

    private Long id;

    private String action;

    private Long sortIndex;

    private String location;

    private String name;

    private Long parentId;

    private String title;

    private MenuType type;
    
    private String titleen;
    

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Long sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(getId().toString());
    }

    @Override
    public boolean equals(Object obj) {

        return getId().equals(((MenuItem) obj).getId());
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

	public String getTitleen() {
		return titleen;
	}

	public void setTitleen(String titleen) {
		this.titleen = titleen;
	}

    
}
