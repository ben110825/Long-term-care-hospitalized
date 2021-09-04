package cclo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class PeakFeature {

	ArrayList<ArrayList> peak = new ArrayList();

	FeatureType type;

	PeakFeature() {
		this.type = FeatureType.未辨識;

	}

	PeakFeature(ArrayList<ArrayList> peak, FeatureType type) {
		
		this.peak = peak;
		this.type = type;// 存擋用這個建構子
		
	}

	PeakFeature(ArrayList<ArrayList> peak) {
		
		this.peak = peak;
		this.type = FeatureType.未辨識;		// 用這個建構子

	}
	protected void analize(PeakFeature simpleFile){

		FeatureType resultType = FeatureType.未辨識;

		
		
		setType(resultType);

	}

	protected ArrayList<ArrayList> getPeak() {

		return peak;

	}

	protected void setPeak(ArrayList<ArrayList> peak) {

		this.peak = peak;

	}

	protected FeatureType getType() {

		return type;

	}

	protected void setType(FeatureType type) {

		this.type = type;

	}

	public void recordingFeature(ArrayList al) {
		peak.add(al);
	}
	public String gsonout() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;	
	}
	public boolean isEmpty() {
		return peak.isEmpty();
	}
	
 

}
