package toolbox.ll.com.toolbox.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class LiveMenuBean {
    private String id;
    private String title;
    private int imgId;
    private List<LiveMenuBean> children;
    private Object extension;

    public LiveMenuBean(String id, String title, int imgId) {
        this.id = id;
        this.title = title;
        this.imgId = imgId;
    }

    public LiveMenuBean(String id, String title, int imgId, List<LiveMenuBean> children,Object extension) {
        this.id = id;
        this.title = title;
        this.imgId = imgId;
        this.children = children;
        this.extension=extension;
    }

    public boolean hasChildren(){
        if(this.children==null||this.children.size()==0)
            return false;
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public List<LiveMenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<LiveMenuBean> children) {
        this.children = children;
    }

    public Object getExtension() {
        return extension;
    }

    public void setExtension(Object extension) {
        this.extension = extension;
    }
}
