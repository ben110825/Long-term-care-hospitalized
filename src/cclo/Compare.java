package cclo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class Compare {
	SendMail mail;
	int resultAcc = 0;
	String result = "";
	Compare(PeakFeature identificationFile){
		result = "";
		resultAcc = 0;
		mail = new SendMail();
		this.identificationFile = identificationFile;
		String compareFolder = "./sound_source/test";	//讀取sample路徑
		loadFilePath(compareFolder);
		compare();
		
	}
	public PeakFeature sampleFile;
	public PeakFeature identificationFile;
	String LoadFileName[];
	public static boolean compareFile(ArrayList<Integer> al1, ArrayList<Integer> al2) {
		int difference = 0;
		boolean similar;
		boolean[] marked = {true, true, true, true, true};
		int[] differenArray = new int[al1.size()];
		ArrayList<Integer> tempAl = new ArrayList<Integer>();
		for(int i=0;i<5;i++) {
			tempAl.add(0);
		}
		
		for(int i=0;i<al1.size();i++) {					//al1的前三個重要的與al2五個比
			int Most_similar_location = i;  	//與al1第i個最相似位置
			for(int j=0;j<al2.size();j++) {
				if(al1.equals(tempAl) || al2.equals(tempAl)) {
					return false;
				}
				
				if(Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i)) >= Math.abs((int)al2.get(j)-(int)al1.get(i))) {
					if(marked[j]) {
						
						Most_similar_location = j;
					}
				}
			}
//			System.out.println((int)al2.get(Most_similar_location)+" "+(int)al1.get(i)+" "+ Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i)));
			marked[Most_similar_location] = false;
			differenArray[i] = Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i));
//			difference += Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i));

		}
		Arrays.sort(differenArray);
		for(int i=0;i<3;i++) {
			difference += differenArray[i];
		}
//		System.out.println("difference: "+difference);
//		System.out.println("-------------------");

		
		if(al1.equals(tempAl) != true && al2.equals(tempAl) != true) {
//			System.out.println("AL1: "+al1);
//			System.out.println("AL2: "+al2);
//			System.out.println("Final difference: "+difference);

		}

		if(difference < 70) {		//相似
			similar = true;
		}
		else					//不相似
			similar  = false;
		return similar;
	}
	public static int lcs(PeakFeature sample, PeakFeature identification ) {  
		ArrayList<ArrayList<Integer>> al1 = sample.getPeak(); 
		ArrayList<ArrayList<Integer>> al2 = identification.getPeak(); 
		
		
	    int len1 = al1.size();
	    int len2 = al2.size();  
	    int c[][] = new int[len1+1][len2+1];  
	    for (int i = 0; i <= len1; i++) {
	    	
	        for( int j = 0; j <= len2; j++) {  
	        	
	        		
	            if(i == 0 || j == 0) {  
	                c[i][j] = 0;  
	            } else if (compareFile(al1.get(i-1), al2.get(j-1))) {  
	                c[i][j] = c[i-1][j-1] + 1;  
	            } else {  
	                c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);  
	            }  
	        }  
	    }  
	    return c[len1][len2];  
	} 
	public void loadFilePath(String compareFolder){	//讀取資料夾中的檔名
		LoadFileName = new String[1000];
		File folder = new File(compareFolder);
		int i = 0;
		for (File file : folder.listFiles()) { 
			if (!file.isDirectory()) {
				LoadFileName[i++] = file.getPath();
//				System.out.println(file.getPath());
			}
		}
		
	}
	public PeakFeature loadFile(String st) {	//讀取檔案轉為PeakFeature
		PeakFeature tempLoad = new PeakFeature();
			Gson gson = new Gson();
			try (Reader reader = new FileReader(st)) {
				// Convert JSON File to Java Object
				tempLoad = gson.fromJson(reader, PeakFeature.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return tempLoad;
	}
	public void compare() {		
		int i = 0;
		int maxSimilarity = -1; // 最高相似度
		boolean flag = false;
		String maxSimilarityLocation = "";
		while (LoadFileName[i] != null) {
			sampleFile = new PeakFeature();
			sampleFile = loadFile(LoadFileName[i]); // 讀入樣本檔
			double lengthRatio = (double) sampleFile.getCountRecord()
					/ (double) identificationFile.getCountRecord(); // 長度比例
//			System.out.println("lengthRatio:"+lengthRatio);
			if (lengthRatio > 1.5 || lengthRatio < 0.7) {
				writeCompareLog(identificationFile.getTime()
				, "長度相差過大"+"\r\n"
				+ "============================"+"\r\n");
				System.out.println("長度相差過大");
				System.out.println("============================");
				i++;
				continue;

			}

			int resultFromLCS = Compare.lcs(sampleFile, identificationFile);
			int acc = (int) (100 * ((double) resultFromLCS / (double) sampleFile.getCountRecord()));
			System.out.println("樣本名稱: "+sampleFile.getTime());
			System.out.println("樣本種類: " + sampleFile.getType());
			System.out.println("樣本檔案總數: " + sampleFile.getCountRecord());
			System.out.println("辨識種類: " + identificationFile.getType());
			System.out.println("樣本檔案相似度: " + acc + " %");
			System.out.println("============================");
			writeCompareLog(identificationFile.getTime(),
			 "樣本名稱: "+sampleFile.getTime()+"\r\n"
			+"樣本種類: " + sampleFile.getType()+"\r\n"
			+"樣本檔案總數: " + sampleFile.getCountRecord()+"\r\n"
			+"辨識種類: " + identificationFile.getType()+"\r\n"
			+"樣本檔案相似度: " + acc + " %"+"\r\n"
			+"============================"+"\r\n");
			if (acc > maxSimilarity) { // 紀錄最像的
				maxSimilarity = acc;
				maxSimilarityLocation = LoadFileName[i];
			}
			i++;
		}
		sampleFile = loadFile(maxSimilarityLocation);
		System.out.println(maxSimilarityLocation);
		int a = (int) (((double)sampleFile.zeroCount/(double)sampleFile.countRecord)*100);
		maxSimilarity += a;
		classify(maxSimilarity);
		if(maxSimilarity >= 60) {
			result = identificationFile.getType()+"";
			System.out.println("* 最相似的是:"+identificationFile.getType());
			System.out.println("* 相似度:"+maxSimilarity+"%");
			writeCompareLog(identificationFile.getTime(),
					 "* 最相似的是:"+identificationFile.getType()+"\r\n"
					+"* 相似度:"+maxSimilarity+"%"+"\r\n");
		}
		else {
			result = "X";
			System.out.println("* 最相似的是: Unidentified");
			System.out.println("* 相似度:"+maxSimilarity+"%");
			writeCompareLog(identificationFile.getTime(),
					 "* 最相似的是: X"+"\r\n"
					+"* 相似度:"+maxSimilarity+"%"+"\r\n");

		}
		if(identificationFile.getCountRecord() >= 60 && identificationFile.getCountRecord()<=400)
			storeResult();
		else
			System.out.println("過短");
		
		resultAcc = maxSimilarity;
		
		
	}
	public void classify(int maxSimilarity) {	//分類
		if(maxSimilarity >= 60) {
			
			identificationFile.setType(sampleFile.getType());
			mail.send("test", sampleFile.getType()+"警告");
		}
			
	}
	public void storeResult() {	//儲存結果
		try{
			String st = "sound_source/identification_"+identificationFile.type+"/"+getStoredFileName(identificationFile.getTime(), identificationFile.type);
			File dir_file = new File(st);
			dir_file.createNewFile();
			BufferedWriter buw = new BufferedWriter(new FileWriter(st));
			buw.write(identificationFile.gsonout());
			buw.close();
			System.out.println("儲存完畢");
			System.out.println("=================================================================================");
		}
		catch(IOException e) {
			
		}
		
	}
	public void writeCompareLog(String fileName, String content) {
		try{
			String st = fileName += ".log";
			String path = "./compare_log/";
			File dir_file = new File(path+st);
			dir_file.createNewFile();
			FileWriter buw = new FileWriter(path+st,true);
			buw.write(content);
			buw.close();
		}
		catch(IOException e) {
			
		}
	}
		
	public String getStoredFileName(String file, FeatureType Type) {
		 return file + "_" +Type +".json";
	}
}


