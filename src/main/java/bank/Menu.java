package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("welcome to bank of Java");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser(){
    System.out.println("enter your username");
    String username = scanner.next();
    System.out.println('enter your password');
    String password= scanner.next();

    Customer customer = null;
    try{
      customer = Authenticator.login(username, password);
    } catch(LoginException e) {
      System.out.println("error:" + e.getMessage());
    }
    return customer;    
  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.printlnln("===================");
      System.out.println("select an option");
      System.out.println('1: Deposit');
      System.out.println('2: Withdraw');
      System.out.println('3: Check balance');
      System.out.println('4: Exit');
      System.out.println("===================");
      
      selection = scanner.nextInt();
      double amount = 0;

      switch(selection) {
        case 1:
        System.out.println("how much would you like to deposit?");
        amount = scanner.nextDouble();
        account.deposit(amount);
        break;

        case 2:
        System.out.println("how much would you like to withdraw?");
        amount = scanner.nextDouble();
        account.withdraw(amount);
        break;

        case 3:
        System.out.println("Current balance:" + account.getBalance());        
        break;

        case 4:
        Authenticator.logout(customer);
        System.out.println("thanks for banking with java");
        break;

        default:
        System.out.println("Invalid selection");
        break;
      }

    }

  }
}
