package cclo;

import java.util.ArrayList;

public class Compare {
	public static boolean compareFile(ArrayList<Integer> al1, ArrayList<Integer> al2) {
		int difference = 0;
		boolean similar;
		boolean[] marked = {true, true, true, true, true};
		ArrayList<Integer> tempAl = new ArrayList();
		for(int i=0;i<5;i++) {
			tempAl.add(0);
		}
		for(int i=0;i<3;i++) {					//al1的前三個重要的與al2五個比
			int Most_similar_location = 0;  	//與al1第i個最相似位置
			for(int j=0;j<al2.size();j++) {
				if(Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i)) >= Math.abs((int)al2.get(j)-(int)al1.get(i))) {
					if(marked[j])
						Most_similar_location = j;
				}
			}
			marked[Most_similar_location] = false;
			difference += Math.abs((int)al2.get(Most_similar_location)-(int)al1.get(i));								
		}
		
		if(al1.equals(tempAl) != true && al2.equals(tempAl) != true) {
	//		System.out.println("AL1: "+al1);
	//		System.out.println("AL2: "+al2);
	//		System.out.println("Final difference: "+difference);

		}
		
		if(difference < 20)		//相似
			similar = true;
		else					//不相似
			similar  = false;
		return similar;
	}
	public static int lcs(PeakFeature simple, PeakFeature identification ) {  
		ArrayList<ArrayList<Integer>> al1 = simple.getPeak(); 
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
}

