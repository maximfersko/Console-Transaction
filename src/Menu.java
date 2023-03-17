import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionService facade;
    private Scanner scanner;
    
    enum Mode {
        Prod,
        Dev
    }

    public Menu() {
        facade = new TransactionService();
        scanner = new Scanner(System.in);
    }

    public void mainMenu(Mode mode) {
        for (;;) {
            if (mode == Mode.Dev) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. DEV - remove a transfer by ID ");
                System.out.println("6. DEV - check transfer validity");
                System.out.println("7. Finish execution");
                AnswerDev(getAnswer(7));
            } else {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. Finish execution");
                Answer(getAnswer(5));
            }
        }
    }

    private int getAnswer(int last) {
        int result = 0;
        String str = scanner.nextLine().trim();
        try {
            result = Integer.parseInt(str);
            if (result < 1 || result > last) {
                throw new RuntimeException("Invalid action. Enter valid number: ");
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            result = getAnswer(last);
        }
        return result;
    }

    private void Answer(int answer) {
        switch (answer) {
            case 1:
                addUser();
                break;
            case 2:
                getBalanceUser();
                break;
            case 3:
                transfer();
                break;
            case 4:
                printTransaction();
                break;
            case 5:
                System.exit(0);
        }
    }

    private void AnswerDev(int answer) {
        switch (answer) {
            case 1:
                addUser();
                break;
            case 2:
                getBalanceUser();
                break;
            case 3:
                transfer();
                break;
            case 4:
                printTransaction();
                break;
            case 5:
                removeTransactionById();
                break;
            case 6:
                checkTransfer();
                break;
            case 7:
                System.exit(0);
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String input = scanner.nextLine().trim();
        String[] strInput = input.split("\\s+");

        try {
            if (strInput.length < 2) {
                throw new RuntimeException("Error !");
            }
            User user = new User(strInput[0],  Integer.parseInt(strInput[1]));
            facade.addUser(user);
            System.out.println("User with id = " + user.getIndetifier() + " is added");
            System.out.println("------------------------------------------------------");
        } catch (RuntimeException e) {
            System.out.println(e);
            addUser();
        }
    }

    private void getBalanceUser() {
        System.out.println("Enter a user ID");
        String input = scanner.nextLine().trim();
        int id = Integer.parseInt(input);
        System.out.println(facade.getListUsers().getUserById(id).getName() + " - " + facade.getBalanceUser(id));
        System.out.println("------------------------------------------------------");
        try {

        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void transfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String input = scanner.nextLine().trim();
        String[] strInput = input.split("\\s+");
        try {
            if (strInput.length != 3) {
                throw new RuntimeException("Error !");
            }
            facade.transferTranscation(Integer.parseInt(strInput[0]), Integer.parseInt(strInput[1]), Integer.parseInt(strInput[2]));
            System.out.println("The transfer is completed");
            System.out.println("---------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void removeTransactionById() {
        System.out.println("Enter a user ID and a transfer ID");
        String input = scanner.nextLine().trim();
        String[] strInput = input.split("\\s+");
        try {
            if (strInput.length != 2) {
                throw new RuntimeException("Error !");
            }
            int userId = Integer.parseInt(strInput[0]);
            UUID transctionId = UUID.fromString(strInput[1]);
            Transaction transaction = new Transaction();
            Transaction[] transactionsListUser = facade.getTransactionsList(userId);
            if (transactionsListUser == null) {
                throw new TransactionNotFoundException("Transaction with id = " + transctionId + " not found.");
            }
            for (int i = 0; i < transactionsListUser.length; ++i) {
                if (transactionsListUser[i].getIdentifier().equals(transctionId)) {
                    transaction = transactionsListUser[i];
                }
            }
            if (transaction == null) {
                throw new TransactionNotFoundException("Transaction with id = " + transctionId + "not found. User id = " + userId);
            }
            facade.removeTransaction(userId, transctionId);
            String category = transaction.getTransferCategory() == Transaction.Category.DEBIT ? "From " : "To ";
            String name = transaction.getTransferCategory() == Transaction.Category.DEBIT ? transaction.getSender().getName() : transaction.getRecipient().getName();
            int id = transaction.getTransferCategory() == Transaction.Category.DEBIT ? transaction.getSender().getIndetifier() : transaction.getRecipient().getIndetifier();
            System.out.println("Transfer: " + category + " " + name);
            System.out.println("(Id = " + id + ")" + transaction.getTranferAmount() + " removed");
            System.out.println("---------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void checkTransfer() {
        System.out.println("Check results: ");
        Transaction[] unpaired = facade.getInvalidTransactions();
        if (unpaired != null) {
            User[] user = new User[200];
            for (int i = 0; i < unpaired.length; ++i) {
                UsersArrayList list = facade.getListUsers(); 
                for (int j = 0; j < list.getUserCount(); ++j) {
                    Transaction[] lTransactions = list.getUserById(i).getTransactionLinkedList().toArray();
                    for (int k = 0; k < lTransactions.length; ++k) {
                        if (lTransactions[k].getIdentifier().equals(unpaired[i].getIdentifier())) {
                            user[j] = list.getUserByIndex(j);
                        }
                    }
                }
                String name = unpaired[i].getTransferCategory() == Transaction.Category.DEBIT ? unpaired[i].getSender().getName() : unpaired[i].getRecipient().getName();
                int id = unpaired[i].getTransferCategory() == Transaction.Category.DEBIT ? unpaired[i].getSender().getIndetifier() : unpaired[i].getRecipient().getIndetifier();
                UUID transferId = unpaired[i].getIdentifier();
                double amount = unpaired[i].getTranferAmount();
                System.out.print(user[i].getName() + "(id = " + user[i].getIndetifier() + ")");
                String ct = unpaired[i].getTransferCategory() == Transaction.Category.DEBIT ? "from "  : "to ";
                System.out.print(" has an unacknowleged transfer id =" + transferId);
                System.out.println(" " + ct + name + "(id =" + id + ") for " + amount);
            }
        }
    }

    private void printTransaction() {
        System.out.println("Enter a user ID");
        String input = scanner.nextLine().trim();
        int id = Integer.parseInt(input);
        try {
            Transaction[] transactions = facade.getTransactionsList(id);
            if (transactions == null) {
                throw new TransactionNotFoundException("User with id = " + id + " hasn't transactions.");
            }
            for (int i = 0; i < transactions.length; ++i) {
                int userId = transactions[i].getTransferCategory() == Transaction.Category.DEBIT ? transactions[i].getSender().getIndetifier() : transactions[i].getRecipient().getIndetifier();
                String category = transactions[i].getTransferCategory() == Transaction.Category.DEBIT ? "From " : "To ";
                String name = transactions[i].getTransferCategory() == Transaction.Category.DEBIT ? transactions[i].getSender().getName() : transactions[i].getRecipient().getName();
                System.out.print(category + name + "(id = " + userId + ") ");
                System.out.print(transactions[i].getTranferAmount());
                System.out.println(" with id = " + transactions[i].getIdentifier());
                System.out.println("---------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
