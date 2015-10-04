import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CPU {
	
	static int PC = 0;
	static int SP = 0;
	static int IR  = 0;
	static int AC = 0;
	static int x = 0;
	static int y = 0; 
 
    public static void main(String args[]) { 
    	Scanner sc = new Scanner(System.in);
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
 
		try { 
			
			String line = ""; 
			System.out.println("send" + PC); 
			while ((line = br.readLine() ) != null) { 
				//System.out.println("in the while loop CPU: " + line);
				int instruction = Integer.parseInt(line);
				
				if( hasOperand(instruction)){  
					PC++; 	
					System.out.println("send" + PC); 
					int op = Integer.parseInt(br.readLine());
					//printSToScreen("has operand: " + op);
					processInstruction(instruction,op,br);  
				}
				else{ 
					//printSToScreen("no operand: " + instruction);
					processInstruction(instruction,0,br); 
					 
				}
				PC++;
				System.out.println("send" + PC); 
					
				
			}
		} 
		catch (IOException e) { 
			e.printStackTrace();
		}
		
    	
    }
    
    public CPU() {

        
    }
	private static boolean hasOperand(int instruction){
		boolean hasOP = false;
		
		if( (instruction >= 1 && instruction <= 7) || (instruction == 9) || (instruction >= 20 && instruction <= 23) ){
			//printSToScreen("instruction == " + instruction);
			hasOP = true;
		}
		else{
			
		}
		
		
		return hasOP;
		
	}
	
    protected static int readFromMemory(int address, BufferedReader br) throws IOException {
    	System.out.println("read" + address); 
    	
    	String returnValue = br.readLine();
    	//printSToScreen("returnValue: " + returnValue);
		return Integer.parseInt(returnValue);
    	
    }
    
    protected static void writeToMemory(int value, int address)  {
    	System.out.println("write" + value + "address" + address); 
    }
    
    protected static void printToScreen(int value)  {
    	System.out.println("print" + value);    
    }
    
    protected static void printCToScreen(int value)  {
    	System.out.println("pchar" + value);    
    }
    
    protected static void printSToScreen(String value)  {
    	System.out.println("pstring" + value);   
    	
    }
    
	public static void processInstruction (int inst, int op, BufferedReader br) throws IOException {
		
		switch (inst){
			case 1:   
				AC = op;  // Load the value into the AC
				//printSToScreen("1. Load the value into the AC...." + "AC: " + AC + "  op: " + op);
				break;
			case 2: 
				AC = readFromMemory(op,br);   //Load the value at addr into the AC
				//printSToScreen("2. Load the value at addr into the AC...." + "AC: " + AC + "  riidFromMemory(op,br): " + readFromMemory(op,br));
				//printSToScreen("" + inst + ". ");
				break;
			case 3: 
				AC = readFromMemory(readFromMemory(op,br),br);    //Load the value from the address found in the given address into the AC (for example , if LoadInt 500, and 500 contains 100, then load from 100)
				//printSToScreen("" + inst + ". ");
				break; 
			case 4: 
				int address = op+x;
				AC = readFromMemory(address,br);     // Load the value at (address+X) into the AC
				//printSToScreen("");
				//printSToScreen("4. Load the value at address("+address+") + X("+x+") into the AC...." + " AC: " + AC + " ");
				//printSToScreen("" + inst + ". ");
				break;
			case 5:     
				AC = readFromMemory(op + y,br);  //Load the value at (address+Y) into the AC
				//printSToScreen("5. Load the value at (address+Y) into the AC...." + "AC: " + AC + "  op: " + op + " y: " + y);
				break;
			case 6: 
				AC = readFromMemory(SP + x,br); //Load from (Sp+X) into the AC
				//printSToScreen("" + inst + ". ");
				break;
			case 7:   
				writeToMemory(AC,op);     //Store the value in the AC into the address
				//printSToScreen("" + inst + ". ");
				break;
			case 8: // Gets a random int from 1 to 100 into the AC
				Random rand = new Random(); 
				int randN = rand.nextInt(100) + 1;
				AC = randN;
				//printSToScreen("Loading to AC: " + AC);
				//printSToScreen("" + inst + ". ");
				break;	
			case 9:  
				if(op == 1){  // If port=1, writes AC as an int to the screen
					//printSToScreen("op == 1, AC: " + AC);
					printToScreen(AC); 
				}
				else if(op == 2){ // If port=2, writes AC as a char to the screen 
					printCToScreen((char)AC);
				} 
				//printSToScreen("" + inst + ". ");
				break;
			case 10:  // Add the value in  to the AC
				AC += x;
				//printSToScreen("Add " + x + " in the X to AC: " + AC);
				//printSToScreen("" + inst + ". ");
				break;
			case 11:   //Add the value in Y to the AC
				AC += y;
				//printSToScreen("11. Add " + y + " in y to AC: " + AC);
				//printSToScreen("" + inst + ". ");
				break;
			case 12:   // Subtract the value in X from the AC
				AC -=  x;
				//printSToScreen("" + inst + ". ");
				break;
			case 13:// SubY
				AC -= y;
				//printSToScreen("" + inst + ". ");
				break;
			case 14:  // Copy the value in the AC to X	
				x = AC;
				//printSToScreen("14. Copy the value in the AC to X...." + "AC: " + AC + " x: " + x);
				//printSToScreen("" + inst + ". ");
				break;
			case 15:   //Copy the value in X to the AC
				AC = x;
				//printSToScreen("" + inst + ". ");
				break;
			case 16:  // Copy the value in the AC to Y
				y = AC;
				////printSToScreen("Copy " + AC + " in the AC to y: " + y);
				//printSToScreen("16. Copy the value in the AC to Y: AC: " + AC + " y: " + y);
				break;
			case 17:   //Copy the value in Y to the AC
				AC = y;
				//printSToScreen("" + inst + ". ");
				break;
			case 18:  //Copy the value in AC to the SP
				SP = AC;
				//printSToScreen("" + inst + ". ");
				break;
			case 19:    //Copy the value in SP to the AC  
				AC = SP;
				break;
			case 20: // Jump to the address
				PC = op-1;
				break;
			case 21:   // Jump to the address only if the value in the AC is zero
				if (AC == 0) {
                    PC = op-1;
                } 
				//printSToScreen("21. Jump to the address only if the value in the AC is zero...." + "AC: " + AC + " hex: "); 
                break;
			case 22:// JumpIfNotEqual addr
				if (AC != 0) {
                    PC = op-1;
                }
                break;
			case 23:    // Push return address onto stack, jump to the address
				writeToMemory(PC,SP);
				PC = PC-1;
				SP++;
				break;
			case 24:  // Pop return address from the stack, jump to the address
				int value  = readFromMemory(SP, br);
				SP--;
				PC = value-1;
				break;
			case 25:  // Increment the value in X 
				x++;
				//printSToScreen("25. incrementing x: " + x);
				break;
			case 26:  // Decrement the value in X 
				x--;
				break;
			case 27:  // Push AC onto stack
				writeToMemory(AC, SP);
				SP++;
				break;
			case 28:  // Pop from stack into AC
				AC  = readFromMemory(SP, br);
				SP--;
				break;
			case 29:  //Set system mode, switch stack, push SP and PC, set new SP and PC
				
			case 30:  // Restore registers, set user mode
				
			case 50:// End 
				System.out.println("end");   
				break;
		}
		//printSToScreen("");
	}
	

    
    
    
    protected void write(int address, int value) throws IOException {
        
    } 
    
    
	
}