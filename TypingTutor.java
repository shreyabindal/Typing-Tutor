package Game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener {
	JLabel lblTimer;
	JLabel lblScore;
	JLabel lblWord;
	TextArea txtWord;
	JButton btnStart;
	JButton btnStop;

	int score;
	int timeLeft;
	boolean isRunning;
	String[] data;
	Timer timer;

	public TypingTutor(String feeder) {
		data = feeder.split(" ");

		super.setTitle("TypingTutor");
		super.setSize(1000, 1000);
		super.setVisible(true);

		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		lblTimer = new JLabel("");
		this.addComponent(lblTimer);

		lblScore = new JLabel("");
		this.addComponent(lblScore);

		lblWord = new JLabel("");
		this.addComponent(lblWord);

		txtWord = new TextArea("");
		Font font = new Font("Comic Sans MS", 1, 100);
		txtWord.setFont(font);
		super.add(txtWord);

		btnStart = new JButton("Start");
		this.addComponent(btnStart);
		btnStart.addActionListener(this);

		btnStop = new JButton("Stop");
		this.addComponent(btnStop);
		btnStop.addActionListener(this);

		initGame();

	}

	private void addComponent(JComponent comp) {
		Font font = new Font("Comic Sans MS", 1, 100);
		comp.setFont(font);
		super.add(comp);
	}

	private void initGame() {
		isRunning = false;
		score = 0;
		timeLeft = 60;
		timer = new Timer(2000, this);

		lblTimer.setText("Timer: " + timeLeft);
		lblScore.setText("Score: " + score);
		lblWord.setText("");
		txtWord.setText("");
		txtWord.setFocusable(true);
		btnStart.setText("Start");
		btnStop.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			handleStart();
		} else if (e.getSource() == btnStop) {
			handleStop();
		} else if (e.getSource() == timer) {
			handleTimer();
		}
	}

	private void handleStart() {
		if (isRunning) {
			isRunning = false;
			timer.stop();

			btnStart.setText("Resume");
		} else {
			isRunning = true;
			score = -1;
			timer.start();

			btnStart.setText("Pause");
			btnStop.setEnabled(true);
		}

	}

	private void handleStop() {
		timer.stop();
		int choice = JOptionPane.showConfirmDialog(this, "Game Over. Restart?");
		if (choice == JOptionPane.YES_OPTION) {
			initGame();
		} else {
			super.dispose();
		}
	}

	private void handleTimer() {
		if (timeLeft > 0) {
			timeLeft--;
			lblTimer.setText("Timer :" + timeLeft);

			String expected = lblWord.getText();
			String actual = txtWord.getText();
			if (expected.equals(actual) && actual != "") {
				score++;
				lblScore.setText("Score :" + score);
			}

			int idx = (int) (data.length * Math.random());
			lblWord.setText(data[idx]);
			txtWord.setText("");
			txtWord.setFocusable(true);

		} else {
			handleStop();
		}
	}
}
