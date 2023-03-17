import java.util.UUID;

public class TransactionService {
    private UsersList usersList = new UsersArrayList();
    private TransactionLinkedList invLinkedList = new TransactionLinkedList();
    private TransactionLinkedList transactionsList = new TransactionLinkedList();

    public TransactionService() {}

    public void addUser(User user) {
        usersList.addUser(user);
    }

    Transaction[] getTransactionsList(int id) {
        return usersList.getUserById(id).getTransactionLinkedList().toArray();
    }
    
    public double getBalanceUser(int id) {
        return usersList.getUserById(id).getBalance();
    }

    public User getUser(int id) {
        return usersList.getUserById(id);
    }

    public UsersArrayList getListUsers() {
        UsersArrayList list = new UsersArrayList();
        for (int i = 0; i < usersList.getUserCount(); ++i) {
            list.addUser(usersList.getUserByIndex(i));
        }
        return list;
    }

    public void PrintAllUsers() {
        for (int i = 1; i <= usersList.getUserCount(); ++i) {
            User user = usersList.getUserById(i);
            user.printInfo();
        }
    }

    public void transferTranscation(Integer idSender, Integer idRecipient, double amount) {
        User sender = usersList.getUserById(idSender);
        User recipient = usersList.getUserById(idRecipient);
        // TODO rework expection here
        if (sender == recipient) 
            System.out.println(" E");
        Transaction transcationDebit = new Transaction(sender, recipient, Transaction.Category.DEBIT, amount);
        Transaction transactionCredit = new Transaction(sender, recipient, Transaction.Category.CREDIT, amount);

        transcationDebit.setIdentifier(transactionCredit.getIdentifier());

        recipient.getTransactionLinkedList().addTransaction(transcationDebit);
        sender.getTransactionLinkedList().addTransaction(transactionCredit);
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] geTransactionsUser(int id) {
        return usersList.getUserById(id).getTransactionLinkedList().toArray();
    }

    public void removeTransaction(int id, UUID idTranscation) {
        usersList.getUserById(id).getTransactionLinkedList().removeTransactionById(idTranscation);
    }

    public Transaction[] getInvalidTransactions() {
        TransactionLinkedList invalidPairTransaction = new TransactionLinkedList();
        TransactionLinkedList allTranscationsUsers = new TransactionLinkedList();
        int count = 0;
        for (int id = 1; id <= usersList.getUserCount(); ++id) {
            User user = usersList.getUserById(id);
            for (int j = 0; j < user.getTransactionLinkedList().getSize(); ++j) {
                allTranscationsUsers.addTransaction(user.getTransactionLinkedList().toArray()[j]);
            }
        }
        Transaction[] allUsersTransactions = allTranscationsUsers.toArray();
        for (int i = 0; i < allUsersTransactions.length; ++i) {
            for (int j = 0; j < allUsersTransactions.length; ++j) {
                if (allUsersTransactions[i].getIdentifier().equals(allUsersTransactions[j].getIdentifier())) {
                    count++;
                }
            }
            if (count % 2 != 0) {
                invalidPairTransaction.addTransaction(allUsersTransactions[i]);
            }
        }
        return invalidPairTransaction.toArray();
    }
}
