package com.fmi110.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/21.
 */
public class Tree {
    private String id;
    private String text;
    private Boolean checked;
    private List<Tree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<Tree> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
