package cclo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class PeakFeature {
	
	
	ArrayList<ArrayList<Integer>> peak = new ArrayList();
	String time ;
	Compare compare;
	double similarity;	//相似度
	int countRecord;
	FeatureType type;
	
	PeakFeature() {
		this.type = FeatureType.未辨識;
		this.countRecord = 0;
		this.similarity = 0;
	}

	PeakFeature(ArrayList<ArrayList<Integer>> peak, FeatureType type) {
		
		this.peak = peak;
		this.type = type;// 存擋用這個建構子
		
	}

	PeakFeature(ArrayList<ArrayList<Integer>> peak) {
		
		this.peak = peak;
		this.type = FeatureType.未辨識;		// 用這個建構子

	} 
	protected void analize(PeakFeature simpleFile){

		FeatureType resultType = FeatureType.未辨識;
		double lengthRatio = (double)simpleFile.getCountRecord()/(double)this.getCountRecord(); //長度比例
		System.out.println("simple length: "+simpleFile.getCountRecord());
		System.out.println("identification length: "+this.getCountRecord());

		if(lengthRatio > 1.2 || lengthRatio < 0.8) {	//暫時先不用
	//		System.out.println("長度相差過多");
		}
		
		int resultFromLCS = compare.lcs(simpleFile, this);
		System.out.println("LCS結果: "+resultFromLCS);
		System.out.println("樣本檔案總數: "+ simpleFile.getCountRecord());
		System.out.println("樣本檔案相似度: "+100*((double)resultFromLCS/(double)simpleFile.getCountRecord())+" %");
		System.out.println("辨識檔案總數: "+ this.getCountRecord());
		System.out.println("辨識檔案相似度: "+100*((double)resultFromLCS/(double)this.getCountRecord())+" %");
		
		
		
		
		setType(resultType);
	}
	public void recordingFeature(ArrayList<Integer> al) {
		peak.add(al);
	}
	public String gsonout() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	protected String getTime() {
		return time;
	}
	protected void setTime(String time) {
		this.time = time;
	}
	protected double getSimilarity() {
		return similarity;
	}
	protected void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	protected int getCountRecord() {
		return countRecord;
	}
	protected void setCountRecord(int countRecord) {
		this.countRecord = countRecord;
	}
	protected ArrayList<ArrayList<Integer>> getPeak() {

		return peak;

	}
	protected void setPeak(ArrayList<ArrayList<Integer>> peak) {

		this.peak = peak;

	}
	protected FeatureType getType() {

		return type;

	}
	protected void setType(FeatureType type) {
		this.type = type;
	}

	
 
}
