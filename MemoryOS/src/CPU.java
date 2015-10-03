import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    	//PrintWriter pw = new PrintWriter(System.out);
		String name = null;
 
		try { 
			String line = "";
			while ((line = br.readLine() ) != null) { 
				
				int instruction = Integer.parseInt(sc.nextLine());
				
				if( hasOperand(instruction)){
					int op = Integer.parseInt(sc.nextLine());
					processInstruction(instruction,op); 
				}
				else{
					processInstruction(instruction,0); 
				}
				
				
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
		
		if( (instruction >= 1 && instruction <= 6) || instruction == 7 || (instruction >= 20 && instruction <= 23)
		    || instruction == 9 ){
			hasOP = true;
		}		 
		
		
		return hasOP;
		
	}
	public static void processInstruction (int inst, int op) {
		
		switch (inst){
			case 1:   
				AC = op;  // Load the value into the AC
				break;
			case 2: 
				AC = op;   //Load the value at addr into the AC
				break;
			case 3: 
				    //Load the value from the address found in the given address into the AC (for example , if LoadInt 500, and 500 contains 100, then load from 100)
				break; 
				
			case 4: 
				AC = op + x;     // Load the value at (address+X) into the AC
				break;
			case 5:     
				AC = op + y;
				break;
			case 6: 
			case 7:   
			case 9:// Put port 
			case 10:// AddX
			case 11:// AddY
			case 12:// SubX
			case 13:// SubY
			case 14:// CopyToX
			case 15:// CopyFromX
			case 16:// CopyToY
			case 17:// CopyFromY
			case 18:// CopyToSp
			case 19:// CopyFromSp   
			case 20:// Jump addr
			case 21:// JumpIfEqual addr
			case 22:// JumpIfNotEqual addr
			case 23:// Call addr
			case 24:// Ret 
			case 25:// IncX 
			case 26:// DecX 
			case 27:// Push
			case 28:// Pop
			case 29:// Int 
			case 30:// IRet
			case 50:// End 
		}
	}
	
    protected static void readFromMemory(int address) throws IOException {
    	System.out.println("read" + address + "\n");  
    	
    }
    
    
    
    protected void write(int address, int value) throws IOException {
        
    } 
    
    
	
}
