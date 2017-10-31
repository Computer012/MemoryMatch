package com.sangmin.memorymatch;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class MemorizeGame {

	public static void main(String[] args) {
		new MemorizeGame();
	}

	static int flag = 0;
	static int index = 0;
	MyButton [] btnList;
	
	public MemorizeGame() {

		JFrame frame = new JFrame();
		frame.setLocation(100, 100);
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setTitle("Memorize Game");

		Container contentPane = frame.getContentPane();
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(4, 4));

		int[] arr = getNums();
		btnList = new MyButton[16];

		for (int i = 0; i < 16; i++) {
			MyButton newBtn = new MyButton(i, arr[i]);
			newBtn.setBackground(Color.lightGray);
			newBtn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
			btnList[i] = newBtn;
			
			newBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!newBtn.getFlag()) {
						if (flag == 0) {
							newBtn.setFlag(true);
							newBtn.setText(String.valueOf(newBtn.getNum()));
							flag = 1;
							index = newBtn.getIndex();
						}
						else if (flag == 1) {
							newBtn.setText(String.valueOf(newBtn.getNum()));
							flag = 2;
							
							if (!compare(newBtn)) {
								sleep(newBtn);
							} else {
								newBtn.removeActionListener(this);
								btnList[index].removeActionListener(this);
								flag = 0;
								index = 0;
							}
						}
					}
				}
			});
			
			btnPanel.add(newBtn);
		}

		contentPane.add(btnPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private int[] getNums() {
		ArrayList<String> numbers = new ArrayList<String>();
		Random rd = new Random();
		int[] arr = new int[16];

		for (int i = 0; i < 2; i++)
			for (int j = 1; j <= 8; j++)
				numbers.add(String.valueOf(j));

		int i = 0;
		while (!numbers.isEmpty())
			arr[i++] = Integer.valueOf(numbers.remove(rd.nextInt(numbers.size())));

		return arr;
	}
	
	private boolean compare(MyButton btn) {
		return btnList[index].getNum() == btn.getNum() ? true : false;
	}
	
	private void sleep(MyButton newBtn) {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					newBtn.setText("");
					newBtn.setFlag(false);
					btnList[index].setText("");
					btnList[index].setFlag(false);
					flag = 0;
					index = 0;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		thread.start();
	}
}

@SuppressWarnings("serial")
class MyButton extends JButton {
	private int index;
	private int num;
	private boolean flag = false;

	public MyButton(int index, int num) {
		this.index = index;
		this.num = num;
	}

	public int getIndex() {
		return index;
	}

	public int getNum() {
		return num;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}