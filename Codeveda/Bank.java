// Bank...
import java.util.Scanner;

class BankAccount{
	
	private String owener;
	private double balance;
	
	public BankAccount(String owener){
		this.owener = owener;
		this.balance = balance;a
	
	
	public void deposit(double amount){
		if(amount <=0){
			System.out.println("Amount not accept nagative number, Please enter positive");
			return;
		}
		balance += amount;
		System.out.println("$" + amount + "deposit amount successful.");
	}
	
	public void withdraw(double amount){
		if(amount <=0){
			System.out.println("Amount not accept nagative number, Please enter positive");
			return;
		}else if(balance > amount){
			System.out.println("Insufficient balance. Please recheck.");
		}else{
			balance -= amount;
			System.out.println("$" + amount + "withdraw amount successful.");
		}
	}
	
	public void checkBalance(){
		System.out.println("Current balance are: ₹%.2f\n" + balance);
	}
	
	}
	
	public class Bank{
		public static void main(String[] args){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Welcome to our bank.");
			System.out.println("Enter your name to open an account: ");
			String name = sc.nextLine();
			
			BankAccount bank_ac = new BankAccount(); 
			
			while(true){
				System.out.println("\...n");
				System.out.println("1. Deposite amount.");
				System.out.println("2. Withdraw amount.");
				System.out.println("3. Check balance.");
				System.out.println("4. Exit.");
				System.out.println(" Choose the option (1-4): ");
				
				String choose = sc.nextLine();
				
				switch(choose){
					case "1":
						System.out.println("Enter amount to deposit: ");
						try{
							double amount = Double.parseDouble(sc.nextLine());
							bank_ac.deposit(amount);
						} catch(NumberFormatException e){
							System.out.println("Invalid input, please enter valid input.");
						}
						break;
					
					case "2":
						System.out.println("Enter amount to withdraw: ");
						try{
							double amount = Double.parseDouble(sc.nextLine());
							bank_ac.withdraw(amount);
						} catch(NumberFormatException e){
							System.out.println("Invalid input. please enter valid input.");
						}
						break;
						
					case "3":
						bank_ac.checkBalance();
						break;
						
					case "4":
						System.out.println("Thank you for using our banking")
						sc.close();
						return;
					
					default:
						System.out.println("Invalid choose, please enter valid input like (1-4). );
				}
			}
		}
	}
}