package rx.com.wyn.rxjavademo.model;

import java.io.Serializable;

/**
 * Created by nancy on 17-10-15.
 */

public class People implements Serializable{
    protected Image avatars;
    protected String name;
    protected String id;

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

    public Image getAvatars() {
        return avatars;
    }

    public void setAvatars(Image avatars) {
        this.avatars = avatars;
    }
}
