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
	int zeroCount;
	FeatureType type;
	
	PeakFeature() {
		this.type = FeatureType.Unidentified;
		this.countRecord = 0;
		this.similarity = 0;
		zeroCount = 0;
	}

	PeakFeature(ArrayList<ArrayList<Integer>> peak, FeatureType type) {
		
		this.peak = peak;
		this.type = type;// 存擋用這個建構子
		
	}

	PeakFeature(ArrayList<ArrayList<Integer>> peak) {
		
		this.peak = peak;
		this.type = FeatureType.Unidentified;		// 用這個建構子

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
