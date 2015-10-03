import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Memory {
	
	int[] memory = new int [2000]; 
	private int instructionEnd = 0; 

    public static void main(String args[]) throws FileNotFoundException {
       
    	try {
			Memory mem = new Memory(); 
	        mem.run(args);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
    }
	
	public int read(int address){ 
		
		return memory[address];
		
	}
	
	public void write(int address, int data){
		
		memory[address] = data;
		
	}
	
	public void run(String[] args) throws FileNotFoundException {

		if (args.length > 0) {
			iniMemory(args[0]);
		}
		try{ 
			
			int x; 
			int i =0;
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("java CPU");
			OutputStream os = proc.getOutputStream();
			PrintWriter pw = new PrintWriter(proc.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));	
			
			while(i != instructionEnd){
				pw.printf(String.valueOf(read(i))+"\n");
				pw.flush();  
				i++;
			}
	 
			String line = "";
			
			
			while ((line = br.readLine() ) != null) {
				 if(line.contains("read")){
					sendToCPU(Integer.parseInt(line.substring(4)), pw);  
				 }
				 else if(line.contains("write"))
				 {
					 
				 }
//				 else
//					 System.out.println(line);
			}
	
			proc.waitFor();
			int exitVal = proc.exitValue();
			System.out.println("Process exited: " + exitVal);
			
		} 
		catch (IOException e){
			
		} 
		catch (InterruptedException e) { 
			e.printStackTrace();
		}
        
    
    }
	public void sendToCPU(int index, PrintWriter pw){ 
		System.out.println("SendToCPU: " + String.valueOf(read(index)));
		pw.println(String.valueOf(read(index))+"\n");
	}
    protected void iniMemory(String path) throws FileNotFoundException {
        File program = new File(path);
        if (!program.exists()) {
            error("Program does not exist. [" + path + "]");
        }
		Scanner scan = new Scanner(program);
		int i = 0;
		while (scan.hasNext()) {
			String line = scan.nextLine();
			int ignore = line.indexOf("/"); 
			if(ignore != -1){
				line = line.substring(0, ignore).trim();
			}
			else{
				line = line.trim();
			}
			if(line.equals("")){
				continue;
			}
		    write(i, Integer.parseInt(line)); 
		    i++;
		}
		scan.close();
		instructionEnd = i;
    }
    
    protected void error(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

	
}
