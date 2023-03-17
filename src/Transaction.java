import java.util.UUID;
public class Transaction {

    private UUID Identifier;
    private User Recipient;
    private User Sender;
    private Category TransferCategory;
    private double TransferAmount;

    public enum Category {
        CREDIT, DEBIT
    }

    public Transaction(User sender, User recipient, Category transferCategory, double  transferAmount) {
        if (((sender.getBalance() < 0.0 || sender.getBalance() < transferAmount) && transferCategory == Category.DEBIT)
            || ((sender.getBalance() < -transferAmount /*|| sender.getBalance() > 0*/) && transferCategory == Category.CREDIT)) {
                System.err.println("ERROR TRANSFER !!!");
        } else {
            this.Sender = sender;
            this.Recipient = recipient;
            this.TransferCategory = transferCategory;
            this.TransferAmount = transferAmount;
            this.Identifier = UUID.randomUUID();
        }
    }

    Transaction() {}

    public UUID getIdentifier() {
        return Identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public User getSender() {
        return Sender;
    }

    public Category getTransferCategory() {
        return TransferCategory;
    }

    public double getTranferAmount() {
        return TransferAmount;
    }

    public void setIdentifier(UUID Id) {
        Identifier = Id;
    }

    public void setRecipient(User recipient) {
        Recipient = recipient;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public void setTransferCategory(Category transferCategory) {
        TransferCategory = transferCategory;
    }

    public void setTransferAmount(double amount) {
        TransferAmount = amount;
    }

    public void print () {
        System.out.println("==============================");
        System.out.println("Sender:" );
        Sender.printInfo();
        System.out.println("Recipient:" );
        Recipient.printInfo();
        System.out.println("ID Transaction: " + Identifier.toString());
        System.out.println("Type Operation: " + TransferCategory.toString());
        System.out.println("Amount: " + TransferAmount);
        System.out.println("==============================");
    }
}