package toolbox.ll.com.toolbox.bean;

/**
 * Created by ll on 2018/3/25.
 */

public class MainMenu {
    public static final int TYPE_NORMAL=0;
    public static final int TYPE_PADDING=1;
    private String id;
    private String name;
    private int type=TYPE_NORMAL;
    private int icon;
    private String value;
    private boolean showMoreIcon;

    public MainMenu(int type) {
        this.type = type;
    }
    public MainMenu(String id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }
    public MainMenu(String id, String name, int type, int icon, String value, boolean showMoreIcon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.value = value;
        this.showMoreIcon = showMoreIcon;
    }

    public static int getTypeNormal() {
        return TYPE_NORMAL;
    }

    public static int getTypePadding() {
        return TYPE_PADDING;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isShowMoreIcon() {
        return showMoreIcon;
    }

    public void setShowMoreIcon(boolean showMoreIcon) {
        this.showMoreIcon = showMoreIcon;
    }
}
