import java.util.UUID;

public interface TransactionList {
    void addTransaction(Transaction transcation);
    void removeTransactionById(UUID id);
    Transaction[] toArray();
}
