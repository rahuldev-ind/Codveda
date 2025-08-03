// Calculator ...
import java.util.Scanner;
import java.util.InputMismatchException;

class Calculator{
	
	public double addition(double num_1, double num_2){
		return num_1 + num_2;
	}
	
	public double subtruction(double num_1, double num_2){
		return num_1 - num_2;
	}
	
	public double multiplication(double num_1, double num_2){
		return num_1 * num_2;
	}
	
	public double division(double num_1, double num_2){
		if(num_2 == 0){
			System.out.println("error:- Division by zero not a allow");
			return Double.NaN; //not a number.
		}
		return num_1 / num_2;
	}
}

public class Basic_Calculator{
	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		Calculator calculator = new Calculator();
		
		System.out.println("\nWelcome To Our Basic Calculator. ");
		
		
		while(true){
			System.out.println("\nPlease choose your operator: ");
			System.out.println("1. Addition(+)");
			System.out.println("2. Subtruction(-)");
			System.out.println("3. Multiplication(*)");
			System.out.println("4. Division(/)");
			System.out.println("5. Exit");
			System.out.println("Enter your choose (1-5) option: ");
			
			int choose;
			
			try{
				choose = input.nextInt();
			} catch(InputMismatchException e){
				System.out.println("Invalid input. Please enter valid input like (1-5): ");
				input.nextInt();
				continue;
			}
			
			if(choose == 5){
				System.out.println("Thank you for using our calculator, GoodBye!OK");
				break;
			}
			
			if(choose < 1 || choose > 4){
				System.out.println("Invalid input. Please enter valid input like (1- 5): ");
				continue;
			}
			
			double num_1, num_2;
			try{
				System.out.println("Enter the number num_1: ");
				num_1 = input.nextInt();
				System.out.println("Enter the number num_2: ");
				num_2 = input.nextInt();
			} catch(InputMismatchException e){
				System.out.println("Invalid input. Please enter valid number. ");
				continue;
			}
			
			double result;
			
			switch(choose){
				case 1:
					result = calculator.addition(num_1, num_2);
					System.out.println("Result: " + num_1 + " + " + num_2 + " = " + result);
					break;
			
				case 2:
					result = calculator.subtruction(num_1, num_2);
					System.out.println("Result: " + num_1 + " - " + num_2 + " = " + result);
					break;
				
				case 3:
					result = calculator.multiplication(num_1, num_2);
					System.out.println("Result: " + num_1 + " * " + num_2 + " = " + result);
					break;
				
				case 4:
					result = calculator.division(num_1, num_2);
					System.out.println("Result: " + num_1 + " / " + num_2 + " = " + result);
					break;
			}
		}
		input.close();
	}
}