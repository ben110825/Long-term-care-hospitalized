package cclo;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

class ProgressBar extends JFrame {
	Thread thread;// 宣告執行緒
	boolean flag = true;

	public ProgressBar() {
		Font font = new Font("標楷體",Font.BOLD,20);
		ImageIcon icon = new ImageIcon("./icon/icon.png");
		setIconImage(icon.getImage());
		setSize(200, 100);// 設定窗體寬高
		setVisible(true);// 窗體可見
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		final JProgressBar progressBar = new JProgressBar(); // 建立進度條
		progressBar.setFont(font);
		progressBar.setForeground(Color.white);
		getContentPane().add(progressBar, BorderLayout.CENTER);// 將進度條放置位置，可以手動修改
		progressBar.setStringPainted(true); // 設定進度條上顯示數字
		thread = new Thread() {// 使用匿名內部類方式建立執行緒物件
			long time1 = System.currentTimeMillis();
			@Override
			public void run() {
				while (getFlag()) {
					long time2 = System.currentTimeMillis();
					if(((time2 - time1)/1000)%2 == 0) {
						progressBar.setString("測試環境音中..");
					}
					else 
						progressBar.setString("測試環境音中...");

				}
				try {
                    progressBar.setString("測試環境音已完成");
                    Thread.sleep(1000);
                    dispose();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

			}

		};
		thread.start();
	}
	public boolean getFlag() {
		return flag;
		
	}
}