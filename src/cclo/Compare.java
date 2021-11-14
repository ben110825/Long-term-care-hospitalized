package cclo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Compare {
	Compare(PeakFeature identificationFile){
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
		ArrayList<Integer> tempAl = new ArrayList();
		for(int i=0;i<5;i++) {
			tempAl.add(0);
		}
		for(int i=0;i<al1.size();i++) {					//al1的前三個重要的與al2五個比
			int Most_similar_location = i;  	//與al1第i個最相似位置
			for(int j=0;j<al2.size();j++) {
				if(Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i)) >= Math.abs((int)al2.get(j)-(int)al1.get(i))) {
					if(marked[j]) {
						
						Most_similar_location = j;
					}
				}
			}
			marked[Most_similar_location] = false;
			difference += Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i));

		}

		
		if(al1.equals(tempAl) != true && al2.equals(tempAl) != true) {
//			System.out.println("AL1: "+al1);
//			System.out.println("AL2: "+al2);
//			System.out.println("Final difference: "+difference);

		}
		
		if(difference < 180) {		//相似
			similar = true;
//			System.out.println("AL1: "+al1);
//			System.out.println("AL2: "+al2);
		//System.out.println("Final difference: "+difference);
		}
		else					//不相似
			similar  = false;
		return similar;
	}
	public static int lcs(PeakFeature sample, PeakFeature identification ) {  
		ArrayList<ArrayList<Integer>> al1 = sample.getPeak(); 
		ArrayList<ArrayList<Integer>> al2 = identification.getPeak(); 
		
	//	System.out.println(al1);
	//	System.out.println(al2);
		
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
				System.out.println(file.getPath());
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
		int maxSimilarity = 0; // 最高相似度
		boolean flag = false;
		String maxSimilarityLocation = "";
		while (LoadFileName[i] != null) {
			sampleFile = new PeakFeature();
			sampleFile = loadFile(LoadFileName[i]); // 讀入樣本檔
			double lengthRatio = (double) sampleFile.getCountRecord()
					/ (double) identificationFile.getCountRecord(); // 長度比例
			if (lengthRatio > 1.3 || lengthRatio < 0.7) {
				System.out.println("長度相差過大");
				System.out.println("============================");
				i++;
				if (LoadFileName[i] == null)
					continue;

			}

			int resultFromLCS = Compare.lcs(sampleFile, identificationFile);
			int acc = (int) (100 * ((double) resultFromLCS / (double) sampleFile.getCountRecord()));
			System.out.println("辨識檔案總數: " + identificationFile.getCountRecord());
			System.out.println("辨識檔案相似度: "
					+ 100 * ((double) resultFromLCS / (double) identificationFile.getCountRecord()) + " %");

			System.out.println("樣本種類: " + sampleFile.getType());
			System.out.println("樣本檔案總數: " + sampleFile.getCountRecord());
			System.out.println("辨識種類: " + identificationFile.getType());
			System.out.println("LCS結果: " + resultFromLCS);
			System.out.println("樣本檔案相似度: " + acc + " %");
			System.out.println("============================");
			if (acc > maxSimilarity) { // 紀錄最像的
				flag = true;
				maxSimilarity = acc;
				maxSimilarityLocation = LoadFileName[i];
			}
			i++;
		}
		classify(maxSimilarity);
		storeResult();
		
	}
	public void classify(int maxSimilarity) {	//分類
		if(maxSimilarity >= 60)
			identificationFile.setType(sampleFile.getType());
	}
	public void storeResult() {	//儲存結果
		try{
			String st = "sound_source/identification_"+identificationFile.type+"/"+setStoredFileName(identificationFile.getTime(), identificationFile.type);
			File dir_file = new File(st);
			dir_file.createNewFile();
			BufferedWriter buw = new BufferedWriter(new FileWriter(st));
			buw.write(identificationFile.gsonout());
			buw.close();
			System.out.println("儲存完畢 " + st);
		}
		catch(IOException e) {
			
		}
		
	}
	public String setStoredFileName(String file, FeatureType Type) {
		 return file + "_" +Type +".json";
	}
}


