package com.edison.saas.bootstrap.common.business.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonTreeNode {

    private String id;
    private String name;
    private Boolean leaf;
    private String iconCls;
    private String type;
    private String parentId;
    private String objectId;

    private List<CommonTreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void addChild(CommonTreeNode node){
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(node);
        node.setParentId(this.getId());
        this.leaf = false;
    }

    public List<CommonTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<CommonTreeNode> children) {
        this.children = children;
        this.leaf = CollectionUtils.isEmpty(children);
    }
}
