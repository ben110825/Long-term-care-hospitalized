package cclo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class EngineeCore {

	String filePath = "";
	AudioFormat audioFormat;
	TargetDataLine targetDataLine;
	boolean flag = true;
	EngineeCore(String filePath){
		this.filePath = filePath;
	}
	public void stopRecognize() {
		flag = false;
		targetDataLine.stop();
		targetDataLine.close();
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 8;
		// 8,16
		int channels = 2;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}// end getAudioFormat

	public void startRecognize() {
		try {
			// 獲得指定的音訊格式
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

			// Create a thread to capture the microphone
			// data into an audio file and start the
			// thread running. It will run until the
			// Stop button is clicked. This method
			// will return after starting the thread.
			flag = true;
			new CaptureThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch
	}// end captureAudio method

	class CaptureThread extends Thread {
		public void run() {
			AudioFileFormat.Type fileType = null;
		    File audioFile = new File(filePath);

		    fileType = AudioFileFormat.Type.WAVE;
		    ByteArrayInputStream bais = null;
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    AudioInputStream ais = null;
		    try {
		    	
//		    	targetDataLine.open(audioFormat);
//		        targetDataLine.start();
		        byte[] fragment = new byte[1024];
		        while(flag) {
		        	targetDataLine.read(fragment, 0, fragment.length);
		        	baos.write(fragment);
		        }
		        //取得錄音輸入流
		        audioFormat = getAudioFormat();
		        System.out.println(audioFormat);
		        byte audioData[] = baos.toByteArray();
		        bais = new ByteArrayInputStream(audioData);
		        ais = new AudioInputStream(bais,audioFormat,audioData.length / audioFormat.getFrameSize());
		        //定義最終儲存的檔名
		        System.out.println("開始寫入錄音檔案");
		        AudioSystem.write(ais,AudioFileFormat.Type.WAVE,audioFile);
		        System.out.println("錄音完成");
		        stopRecognize();
		    }
		    catch(Exception e) {
		    	e.printStackTrace();
		    }
		    finally {
		    	 try {
		             ais.close();
		             bais.close();
		             baos.reset();
		           } catch (IOException e) {
		             e.printStackTrace();
		           }
		    }
		    	
		}
	}
}// end inner class CaptureThread