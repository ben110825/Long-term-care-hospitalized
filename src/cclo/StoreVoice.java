package cclo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class StoreVoice extends Thread{
	public String fileName = "";
	public String filePath = "";
	public String codePath = "./py/record.py";
	public String compiler = "python";
	StoreVoice(String fileName, String filePath){
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public void run() {
		Process proc;
		try {
			String temp = compiler+" "+codePath+" "+filePath+" "+fileName;
			System.out.println("temp "+temp);
			proc = Runtime.getRuntime().exec(temp);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
            String line = null;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
            in.close();  
            proc.waitFor();  
		}
		catch(IOException e) {
			e.printStackTrace();  
		}
		catch(InterruptedException e) {
			e.printStackTrace();  
		}
	}
	public void main(String[] args)  {
		// TODO Auto-generated method stub
		
	}

}
