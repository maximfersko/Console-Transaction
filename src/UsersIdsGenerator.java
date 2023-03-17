public class UsersIdsGenerator {
    private static Integer Id = 0;
    private static UsersIdsGenerator instance;

    public static UsersIdsGenerator getInstance() {
        if (instance == null)
            instance = new UsersIdsGenerator();
        return instance;
    }

    public static Integer getId() {
        return Id;
    }

    public Integer generateId() {
        return ++Id;
    }

}
