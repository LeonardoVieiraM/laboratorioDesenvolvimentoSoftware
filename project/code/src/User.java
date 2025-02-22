public abstract class User {
    protected int id;
    protected String password;

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public abstract void login(int id, String password);
}