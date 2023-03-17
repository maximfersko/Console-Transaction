public class User {
    private Integer Identifier;
    private String Name;
    private double Balance;
    private TransactionLinkedList list;

    public User( String name, double balance) {
        this.Balance = balance;
        this.Identifier = UsersIdsGenerator.getInstance().generateId();
        this.Name = name;
        list = new TransactionLinkedList();
    }

    public void printInfo() {
        System.out.println("Name: " + Name  + " ID: "  + Identifier + " Balance: " + Balance);
    }

    public Integer getIndetifier() {
        return this.Identifier;
    }

    public double getBalance() {
        return this.Balance;
    }

    public String getName() {
        return this.Name;
    }

    public void setIdentifier(Integer id) {
        this.Identifier = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setBalance(double balance) {
        this.Balance = balance;
    }

     public TransactionLinkedList getTransactionLinkedList() {
        return list;
     }
}