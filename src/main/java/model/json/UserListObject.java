package model.json;

import java.util.List;

public class UserListObject {
    private List<SharedUserObject> users;

    public UserListObject(List<SharedUserObject> users) {
        this.users = users;
    }

    public List<SharedUserObject> getUsers() {
        return users;
    }

    public void setUsers(List<SharedUserObject> users) {
        this.users = users;
    }
}
