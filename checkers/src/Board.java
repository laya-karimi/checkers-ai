package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class Board extends JPanel{

		public Checker[][] board;

		public int x_init,
		y_init,
		x,y,
		Width, Height;


		//Constructor
		public Board(Checker[][] c){
			board = c;
			x_init = 0;
			y_init = 0;
			x = x_init;
			y = y_init;
			Width = 75;
			Height = 75;
		}

		
		public void update(Checker[][] b){
			board = b;
			repaint();
		}
		

		
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			x = x_init;
			y = y_init;

			//prints the board based on current board state
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					if( board[i][j] != null){
						pickDraw(board[i][j].getColor(),g2);
						//prints the space number
						g2.setColor(Color.orange);
						g2.drawString(board[i][j].getBoard_pos_str(), x, y+10);
					}
					else
						g2 = pickDraw('b',g2);
					x+= Width;
				}
				y += Height;
				x = x_init;
			}
			
			

		}

		//draws whatever needs to be in the space
		public Graphics2D pickDraw(char c, Graphics2D d){

			switch(c)
			{
			case 'b':
				d.setColor(new Color(238,196,124));
				d.fillRect(x, y, Width, Height);
				return d;

			case 'p':
				d.setColor(new Color(156,111,31));
				d.fillRect(x, y, Width, Height);
				return d;
			//black piece
			case 'r':
				d.setColor(new Color(156,111,31));
				d.fillRect(x, y, Width, Height);
				d.setColor(Color.black);
				d.fillOval(x, y, Width, Height);
				return d;
			//red piece
			case 'w':
				d.setColor(new Color(156,111,31));
				d.fillRect(x, y, Width, Height);
				d.setColor(Color.red);
				d.fillOval(x, y, Width, Height);
				return d;
			//black king
			case 'R':
				d.setColor(new Color(156,111,31));
				d.fillRect(x, y, Width, Height);
				d.setColor(new Color(64,59,59));
				d.fillOval(x, y, Width, Height);
				return d;
			//red king
			case 'W':
			d.setColor(new Color(156,111,31));
			d.fillRect(x, y, Width, Height);
			d.setColor(new Color(117,11,11));
			d.fillOval(x, y, Width, Height);
				return d;
			default:

			}
			return d;
		}

	}