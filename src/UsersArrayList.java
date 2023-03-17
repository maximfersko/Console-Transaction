class UserNotFoundExpection extends RuntimeException {
    public UserNotFoundExpection(String msg) {
        super(msg);
    }
}

public class UsersArrayList implements UsersList {
    User[] data;
    int countUsers;
    int size;

    public UsersArrayList() {
        data = new User[10];
        size = 10;
        countUsers = 0;
    }

    @Override
    public void addUser(User user) {
        if (size == countUsers) {
            User[] newData = data;
            data = new User[size * 2];
            for (int i = 0; i < size; ++i) {
                data[i] = newData[i];
            }
            size *= 2; 
        }
        data[countUsers++] = user;
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < countUsers; ++i) {
            if (data[i].getIndetifier() == id)
                return data[i];
        }
        throw new UserNotFoundExpection("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(Integer idx) {
        if (idx >= 0 && idx < countUsers)
            return data[idx];
        throw new UserNotFoundExpection("User with id " + idx + " not found");
    }

    @Override
    public Integer getUserCount() {
        return countUsers;
    }

    public void Print() {
        for (int i = 0; i < countUsers; ++i)
            data[i].printInfo();
    }
}
