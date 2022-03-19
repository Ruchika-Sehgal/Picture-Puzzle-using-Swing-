import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Puzzle extends JFrame implements ActionListener {
	ArrayList<Icon> images = new ArrayList<Icon>();
	ArrayList<JButton> Buttons = new ArrayList<JButton>();
	JPanel jp;
	Boolean firstClick = false;
	JButton firstButton, secondButton;
	public Puzzle() {
		super("Picture Puzzle");
		setSize(500, 500);
		// ImageIcon i= new ImageIcon().getImage().getScaledInstance(30, 30, DO_NOTHING_ON_CLOSE));

		Image icon = Toolkit.getDefaultToolkit().getImage("E:/Ruchika/Swing/Images/SquareImages/Icon.jpg");
		
		setIconImage(icon);// setResizable(false);
		store();
		init();
		
	}
	
	//Initialtes the entire game, adds panels and menus
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
		JButton newGame = new JButton(new ImageIcon(new ImageIcon("E:/Ruchika/Swing/Images/SquareImages/newGame.png").getImage().getScaledInstance(30, 30, DO_NOTHING_ON_CLOSE)));
		newGame.setMaximumSize(new Dimension(30, 30));
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Buttons.clear();
				images.clear();
				getContentPane().removeAll();
				store();
				init();
				System.out.println("New Game created");
			}
		});
		JToolBar Bar = new JToolBar();
		// Bar.setSize(BorderLayout., );
		Bar.add(newGame);
		// JMenuBar menu = new JMenuBar();
		// menu.add(file);
		createButtons();
		getContentPane().add(reset, BorderLayout.SOUTH);
		getContentPane().add(Bar, BorderLayout.NORTH);
		setVisible(true);

	}
	

	//it stores those buttons in their respective partitions, internally calls the partition method...
	// for making partions within the image and scaling these partitions accurately.
	public void store() {
		int rand = (int) (((Math.random() + Math.random())*10)%6);
		System.out.println(rand+"");
		for (int i = 0; i < 9; i++) {
			// Icon ic = new ImageIcon("E:/Ruchika/Swing/Puzzle_Java/" + i + ".jpg");
		
			BufferedImage[] finaliImages = createPartitions(rand);
			// BufferedImage piece= [i];

			images.add(new ImageIcon(finaliImages[i]));
		}
	}

	BufferedImage[] createPartitions(int rand) {
		System.setProperty("http.agent", "Chrome");
		File image = new File("E:/Ruchika/Swing/Images/SquareImages/" + rand + ".jpg");

		try {
			BufferedImage srcImg = ImageIO.read(image);
			int rows = 3;
			int columns = 3;
			int srcImgWidth = srcImg.getWidth();
			int srcImgHeight = srcImg.getHeight();
			BufferedImage[] pieces = new BufferedImage[9];
			int piecesWidth = srcImgWidth / columns;
			int piecesHeight = srcImgHeight / rows;
			int currentImg = 0;

			for(int i=0; i<3; ++i){
				for (int j = 0; j < 3; j++) {
					pieces[currentImg] = new BufferedImage(piecesWidth, piecesHeight, srcImg.getType());
					Graphics2D piece = pieces[currentImg].createGraphics();

					int src_first_x = j * piecesWidth;
					//It gives the two diagonal points with 0,0 set at top left corner. 
					//Adjust the width with the no of columns as the columns divide the width into equal parts.
					//Similarly adjust the height with the rows. 

					int src_first_y = i * piecesHeight;

					int corner_x = src_first_x + piecesWidth;
					int corner_y = src_first_y + piecesHeight;

					piece.drawImage(srcImg, 0, 0, piecesWidth, piecesHeight, src_first_x, src_first_y, corner_x,
							corner_y, null);
					currentImg++;

				}
			}
			return pieces;
		} catch (IOException e) {
			System.out.println("Falied to load image due to exception" + e);
		}
		return null;
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
		ImageIcon imcon = new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(172, 172, DO_NOTHING_ON_CLOSE));
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


