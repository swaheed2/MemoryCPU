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
			 
			
//			while(i != instructionEnd){
//				System.out.println("sending instruction"); 
//				pw.println(String.valueOf(read(i)));
//				pw.flush();  
//				i++;
//			}

			String line; 
			while ( !(line = br.readLine() ).equals("end")) { 
				
				 if(line.contains("send") && i <= instructionEnd) {
					//System.out.println("sending instruction: " + String.valueOf(read(i)));
					 String[] part = line.split("(?<=\\D)(?=\\d)");  
					int index = Integer.parseInt(part[1]); 
					//System.out.println("index: " + index);
					//System.out.println("read at 0 : " + read(index));
					pw.println(read(index));
					pw.flush(); 
				 }
				 else if(line.contains("read")){ 
					//pw.println(String.valueOf(read(Integer.parseInt(line.substring(4))))); 
					sendToCPU(Integer.parseInt(line.substring(4)), pw);  
				 }
				 else if(line.contains("write")){
					 
					 int indexOfAdd = line.indexOf("address");
					 int value = Integer.parseInt(line.substring(5,indexOfAdd));
					 int address = Integer.parseInt(line.substring(indexOfAdd+7));
					 write(address,value);
					 
				 }
				 else if(line.contains("print")){
					 print(line.substring(5));
				 }
				 else if(line.contains("pstring")){
					 print(line.substring(7));
				 } 
				 else if(line.contains("pchar")){
					 System.out.println((char)Integer.parseInt(line.substring(5))); 
				 }
				 else if(line.contains("end")){
					 break;
				 }
				 else
					 System.out.println(line);
				 
				 
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
	public void print(String value){
		System.out.println(value);
	}
	public void sendToCPU(int index, PrintWriter pw){   
		pw.println(String.valueOf(read(index)));
		pw.flush();
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
			line = line.replaceAll("[^0-9]", "");
			//System.out.println(line);
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
