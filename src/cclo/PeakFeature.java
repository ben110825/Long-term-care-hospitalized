package cclo;

import java.io.IOException;
import java.util.ArrayList;

public abstract class PeakFeature {
	ArrayList<ArrayList> peak;
	FeatureType type;
	
	PeakFeature(ArrayList<ArrayList> peak,FeatureType type){
		
		this.peak = peak;
		this.type = type;
		//存擋用這個建構子
		
	}
	PeakFeature(ArrayList<ArrayList> peak){
		
		this.peak = peak;
		this.type = FeatureType.未辨識;
		//用這個建構子
		
	}
	protected void analize()
	{
		FeatureType resultType = FeatureType.未辨識;
		//......
		//......
		//......辨識完更改type
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
	protected void outPut()
	{	
		
	}
}
	/*public static boolean creatTxtFile(String name) throws IOException { 
		boolean flag = false; 
		filenameTemp = path + name + ".txt"; 
		File filename = new File(filenameTemp); 
		if (!filename.exists()) { 
		filename.createNewFile(); 
		flag = true; 
		} 
		return flag; 
		} 
	
	
	
	
	

}*/
	
