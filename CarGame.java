import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * CarGame
 */
public class CarGame  extends JFrame implements KeyListener, ActionListener{

    private int xpos = 300; //x position of the car
    private int ypos = 700; //y position of the car
    private ImageIcon car; //car image
    private Timer timer; //screen update timer
    Random random =  new Random(); 

    private int num1 = 400, num2 = 0, num3 = 0; //x position of obstacles
    private int tree1ypos = 400, tree2ypos = -200, tree3ypos = -500, tree4ypos = 100, tree5ypos = -300, tree6ypos = 500;
    private int roadMove = 0; // y position on the road
    private int carxpos[] = {100,200,300,400,500}; //x pos of car
    private int carypos[] = {-240,-480,-720, -960,-1200}; //y pos of the car

    private int cxpos1 = 0, cxpos2 =2, cxpos3 = 4;
    private int cypos1 = random.nextInt(5), cypos2 = random.nextInt(5), cypos3 = random.nextInt(5);
    int y1pos = carypos[cypos1];
    int y2pos = carypos[cypos2];
    int y3pos = carypos[cypos3];

    private ImageIcon car1, car2, car3;
    private int score = 0, delay = 100, speed = 90;
    private ImageIcon tree1, tree2, tree3;
    private boolean rightRotate = false, gameOver = false, paint = false; // boolean variables to control game logic and graphics


    public CarGame(String title) {
        super(title);
        setBounds(300, 10, 700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        setResizable(false);
    }
    //method to paint graphics on screen
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(0X82CD47));//Set color of grass
        g.fillRect(0, 0, 700, 700); //draw grass
        g.setColor(new Color(0X9F8772));//Set color of road
        g.fillRect(90, 0, 10, 700);//draw road
        g.fillRect(600, 0, 10, 700);
        g.fillRect(100, 0, 500, 700);

        //draw road lines
        if (roadMove == 0) {
            for (int i = 0; i <= 700; i+=100) {
                g.setColor(Color.white);
                g.fillRect(350, i, 10, 70);
        }
        roadMove = 1;
        }
        else if (roadMove==1) {
            //draw road lines again for the next line
            for (int i = 50; i <= 700; i+=100) {
                g.setColor(Color.white);
                g.fillRect(350, i, 10, 70);
        }
        roadMove = 0;
    }
      try {
        tree1 = new ImageIcon(ImageIO.read(getClass().getResource("tree.png"))); //load tree image
          tree2 = new ImageIcon(ImageIO.read(getClass().getResource("tree.png")));
          tree3 = new ImageIcon(ImageIO.read(getClass().getResource("tree.png")));
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

        tree1.paintIcon(this, g, 0, tree1ypos); //draw tree
        num1 = random.nextInt(500); 
        tree1ypos += 50;

        tree2.paintIcon(this, g, 0, tree2ypos);
        tree2ypos += 50;

        tree3.paintIcon(this, g, 0, tree3ypos);
        tree3ypos += 50;

        tree1.paintIcon(this, g, 600, tree4ypos);
        tree4ypos += 50;

        tree3.paintIcon(this, g, 600, tree5ypos);
        tree5ypos += 50;

        tree2.paintIcon(this, g, 600, tree6ypos);
        tree6ypos += 50;

        if (tree1ypos>700) {//if tree goes out of the screen reset the screen
            num1 = random.nextInt(500);
            tree1ypos = -num1;
        }
        if (tree2ypos>700) {
            num1 = random.nextInt(500);
            tree2ypos = -num1;
        }
        if (tree3ypos>700) {
            num1 = random.nextInt(500);
            tree3ypos = -num1;
        }
        if (tree4ypos>700) {
            num1 = random.nextInt(500);
            tree4ypos = -num1;
        }
        if (tree5ypos>700) {
            num1 = random.nextInt(500);
            tree5ypos = -num1;
        }
        if (tree6ypos>700) {
            num1 = random.nextInt(500);
            tree6ypos = -num1;
        }
        //load image for car
       // car.paintIcon(this, g, xpos, ypos); //draws car image on screen
        ypos -= 40;
       
        //load opp image for car

        try{
            car = new ImageIcon(ImageIO.read(getClass().getResource("gamecar1.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        car.paintIcon(this, g, xpos, ypos); //draw car image on screen

        ypos -= 40;
        if (ypos<500) {
            ypos = 500;
        }

        //load opponent image for car
        try {
            car1 = new ImageIcon(ImageIO.read(getClass().getResource("gamecar1.png")));
            car2 = new ImageIcon(ImageIO.read(getClass().getResource("gamecar2.png")));
            car3 = new ImageIcon(ImageIO.read(getClass().getResource("gamecar3.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        car1.paintIcon(this, g, carxpos[cxpos1], y1pos);//draw opponent car image on screen
        car2.paintIcon(this, g, carxpos[cxpos2], y2pos);
        car3.paintIcon(this, g, carxpos[cxpos3], y3pos);

        y1pos += 50;
        y2pos += 50;
        y3pos += 50;

        if (y1pos > 700) {
            cxpos1 = random.nextInt(5);
            cypos1 = random.nextInt(5);
            y1pos = carypos[cypos1]; //reset the y position of the car
        }
        if (y2pos>700) {
            //if the car goes out of the screen reset the screen
            cxpos2++;
            if(cxpos2>4) {
                cxpos2 = 0;
            }
            cypos2 = random.nextInt(5);
            cxpos2 = random.nextInt(5);
            y2pos = carypos[cypos2];
        }
        if(y3pos>700) {
            cxpos3++;
            if(cxpos3>4) {
                cxpos3 = 0;
            }
            cypos3 = random.nextInt(5);
            cxpos3 = random.nextInt(5);
            y3pos = carypos[cypos3];
        }
            if(cxpos1==cxpos2 && cypos1 > -100 && cypos2 > -100) {
                cxpos1-=1;
                if (cxpos1<0) {
                    cxpos1+=2;
                }
            }
            if(cxpos1==cxpos3 &&cypos1 > -100 && cypos3 > -100){
                cxpos3-=1;
                if (cxpos3<0) {
                    cxpos3+=2;
                }
            }
            if(cxpos2==cxpos3 &&cypos3 > -100 && cypos2 > -100){
                cxpos2-=1;
                if( cxpos2 <0){
                    cxpos2+=2;
                }
            }
            if (cxpos1<2 && cxpos2<2 && cxpos3<2) {
                if (cxpos1==0 && cxpos2==0 && cxpos3==1) {
                    cxpos2++;
                    cxpos3++;
                }
                else if (cxpos1==0 && cxpos2==1 && cxpos3==0) {
                    cxpos3++;
                    cxpos2++;
                }
                else if (cxpos1==1 && cxpos2==0 && cxpos3==0) {
                    cxpos1++;
                    cxpos2++;
                    
                }
            }
            //if the opponent car hits the player car then reset the game
            if (y1pos<ypos && y1pos+175>ypos && carxpos[cxpos1]==xpos) {
                gameOver = true;
            }
            if (y2pos<ypos && y2pos+175>ypos && carxpos[cxpos2]==xpos) {
                gameOver = true;
            }
            if (y3pos<ypos && y3pos+175>ypos && carxpos[cxpos3]==xpos) {
                gameOver = true;
            }
            if (ypos < y1pos && ypos+175 > y1pos && carxpos[cxpos1]==xpos) {
                gameOver=true;
            }
            if (ypos < y2pos && ypos+175 > y2pos && carxpos[cxpos2]==xpos) {
                gameOver=true;
            }
            if (ypos < y3pos && ypos+175 > y3pos && carxpos[cxpos3]==xpos) {
                gameOver=true;
            }
            //Score
            g.setColor(Color.red);
            g.fillRect(120,35,220,50);
            g.setColor(Color.black);
            g.fillRect(125, 40, 210, 40);
            g.setColor(Color.red);
            g.fillRect(385,35,180,50);
            g.setColor(Color.black);
            g.fillRect(390,40,170,40);
            g.setColor(Color.white);
            g.setFont(new Font("MV Boli", Font.BOLD, 30));
            g.drawString("Score:" + score, 130, 67);
            g.drawString(speed+"Km/h", 400, 67);
            score++;
            speed++;
            if (speed>140) {
                speed = 240- delay;
            }
            if (score%50==0) {
                delay-=10;
                if (delay<60) {
                    delay = 60;
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(delay
                ); //delay the game
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (y1pos < ypos && y1pos+175 > ypos && carxpos[cxpos1]==xpos) {
                gameOver=true;
                
            }
            if (y2pos < ypos && y2pos+175 > ypos && carxpos[cxpos2]==xpos) {
                gameOver=true;
                
            }
            if (y3pos < ypos && y3pos+175 > ypos && carxpos[cxpos3]==xpos) {
                gameOver=true;
                
            }
            if (gameOver) {
                g.setColor(Color.gray);
                g.fillRect(120,210,460,200);
                g.setColor(Color.darkGray);
                g.fillRect(130, 220, 440, 180);
                g.setFont(new Font("MV Boli", Font.BOLD, 50));
                g.setColor(Color.red);
                g.drawString("Game Over", 210, 270);
                g.setColor(Color.white);
                g.setFont(new Font("MV Boli", Font.BOLD, 30));
                g.drawString("Press enter to restart",190,340);
                if (!paint) {
                    repaint();
                    paint = true;
                }
            }
            else {
                repaint();
            }
        }
  
        public static void main(String[] args) {

            CarGame game = new CarGame("Car Racing Game");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_LEFT && !gameOver)
            { // if the left key is pressed then move the car to the left
                xpos-=100;
                if(xpos<100)
                {
                    xpos=100; // set the car to the left most position
                }
                
                
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT&&!gameOver)
            { 	// if the right key is pressed then move the car to the right
                xpos+=100;
                if(xpos>500)
                {
                    xpos=500; // if the car is at the right most position then don't move it
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_ENTER && gameOver)
            { // if the game is over and the enter key is pressed then restart the game
                gameOver=false; 	
                paint=false;
                cxpos1=0;
                cxpos2=2;
                cxpos3=4;
                cypos1=random.nextInt(5); // randomize the position of the opponent cars
                cypos2=random.nextInt(5); 
                cypos3=random.nextInt(5);
                y1pos=carypos[cypos1]; // set the position of the opponent cars
                y2pos=carypos[cypos2];
                y3pos=carypos[cypos3];
                speed=90; // set the speed to 90
                score=0; // set the score to 0
                delay=100; // set the delay to 100
                xpos=300; // set the position of the player car to the center
                ypos=700;	 // set the position of the player car to the bottom
            }
        }
        @Override
        public void keyReleased(KeyEvent arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar()=='a'&&!gameOver)
            { // if the key pressed is 'a' then move the car left
                xpos-=100; // decrement the xpos by 100
                
            }
            if(e.getKeyChar()=='s'&&!gameOver)
            { // if the key pressed is 's' then move the car right
                xpos+=100; // increment the xpos by 100
            }
            
            repaint();
            
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {}
    }
    
