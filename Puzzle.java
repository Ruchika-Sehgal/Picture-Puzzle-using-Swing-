import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Puzzle extends JFrame implements ActionListener {
	ArrayList<Icon> images = new ArrayList<Icon>();
	ArrayList<JButton> Buttons = new ArrayList<JButton>();
	JPanel jp;
	Boolean firstClick = false;
	JButton firstButton, secondButton;

	public Puzzle() {
		super("Picture Puzzle");
		setSize(500, 500);
		setResizable(false);
		arrange();
		init();
	}
	
	public void init() {
		jp = new JPanel();
		// jp.setLayout(new GridLayout());
		jp.setLayout(new GridLayout(3, 3, 1, 1));
		getContentPane().add(jp, BorderLayout.CENTER);
		// pack();
		// Pack method used to set the Jframe at or above their required sizes
		JButton reset = new JButton("Reset Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Buttons.clear();
				getContentPane().removeAll();
				init();
				System.out.println("Reset done");
				
			}
		});
		// getContentPane().
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createButtons();
		getContentPane().add(reset, BorderLayout.SOUTH);
		setVisible(true);
		

	}

	public void arrange() {
		for (int i = 1; i < 10; i++) {
			Icon ic = new ImageIcon("./Puzzle_Java/" + i + ".jpg");
			images.add(ic);
		}
	}

	public void createButtons() {
		for (int i = 0; i < 9; i++) {
			JButton b = new JButton(resizeImage(images.get(i)));
			((ImageIcon) b.getIcon()).setDescription(i + "");
		
			b.setPreferredSize(new Dimension(175, 175));
			b.addActionListener(this);
			Buttons.add(b);
		}
		Collections.shuffle(Buttons);
		for (int i = 0; i < 9; i++) {
			jp.add(Buttons.get(i));
		}
	}

	public Icon resizeImage(Icon icon) {
		ImageIcon imcon = new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(155, 155, DO_NOTHING_ON_CLOSE));
		return imcon;
	}

	//The above method takes an Icon, converts this icon into ImageIcon, extract its image and then set irts size. Necessary for preventing the image from stretching.

	public void actionPerformed (ActionEvent ae) {
		
		if (firstClick == false) {
			firstClick = true;
			firstButton = (JButton) ae.getSource();

		} else {
			firstClick = false;
			secondButton = (JButton) ae.getSource();
			if (firstButton != secondButton) {
				swap();
			}
		}

	}
	
	public void swap() {
		Icon i1 = firstButton.getIcon();
		Icon i2 = secondButton.getIcon();
		firstButton.setIcon(i2);
		secondButton.setIcon(i1);
		won();

	}
	
	public Boolean won (){
		Boolean iswin=true;
		for(int i=0; i<9; i++){
			if (!((ImageIcon) Buttons.get(i).getIcon()).getDescription().equals(i + "")) {
				iswin = false;
				System.out.println("Out from won");
				break;
			}
			
		}
		if (iswin == true) {
			JOptionPane.showMessageDialog(this, "Yay! You won!", "Congrats", JOptionPane.PLAIN_MESSAGE);	
			// System.out.println("You won");
		}
		return iswin;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Puzzle p = new Puzzle();

	}

}

