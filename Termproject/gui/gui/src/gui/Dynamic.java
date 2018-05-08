package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class Dynamic extends JFrame//�׷��� ��� ���α׷� ���
{
	private Image screenImage;//������۸�
	private Graphics screenGraphic;//������۸�
	
	
	// â �����ư ����
	private ImageIcon exitbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/exitbuttonentered.png"));
	private ImageIcon exitbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/exitbuttonbasic.png"));
	// Ȩ ���͹�ư ����
	private ImageIcon homebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/homebuttonentered.png"));
	private ImageIcon homebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/homebuttonbasic.png"));
	// Ŀ�� �ֹ���ư ����
	private ImageIcon coffeebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/coffeebuttonentered.png"));
	private ImageIcon coffeebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/coffeebuttonbasic.jpg"));
	// ����ũ �ֹ���ư ����
	private ImageIcon cakebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/cakebuttonentered.png"));
	private ImageIcon cakebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/cakebuttonbasic.jpg"));
	// �ֹ� ������ư ����
	private ImageIcon resultbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/resultbuttonentered.jpg"));
	private ImageIcon resultbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/resultbuttonbasic.jpg"));
	
	// ���� �߰���ư ����
	private ImageIcon upbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/upbuttonentered.png"));
	private ImageIcon upbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/upbuttonbasic.png"));
	// ���� ���ҹ�ư ����
	private ImageIcon downbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/downbuttonentered.png"));
	private ImageIcon downbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/downbuttonbasic.png"));
	
	private Image selectedImage = new ImageIcon(Main.class.getResource("../images/bighero.jpg")).getImage();
	private Image background = new ImageIcon(Main.class.getResource("../images/introbackground.png")).getImage();//�̹��������� ���ͼ� �̹��� �ν��Ͻ��� �̹��� ������ �ʱ�ȭ
	private JLabel  menubar  = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.png")));
	private JButton exitbutton = new JButton(exitbuttonbasicImage);
	private JButton homebutton = new JButton(homebuttonbasicImage);
	private JButton coffeebutton = new JButton(coffeebuttonbasicImage);
	private JButton cakebutton = new JButton(cakebuttonbasicImage);
	private JButton resultbutton = new JButton(resultbuttonbasicImage);
	private JButton upbutton = new JButton(upbuttonbasicImage);
	private JButton downbutton = new JButton(downbuttonbasicImage);
	
	
	private int mouseX, mouseY;
	
	
	
	public int CoffCnt = 0, CakeCnt = 0, obj_sec = 0;
	// Ŀ��, ����ũ ī��Ʈ �ʱ�ȭ, obj_sec-> Coff or Cake ����
	private JButton coffcntbutton = new JButton(" " + CoffCnt + "��" );//Ŀ�� ����
	private JButton cakecntbutton = new JButton(" " + CakeCnt + "��");//����ũ ����
	
	
	private Font font3 = new Font("Selif", Font.PLAIN, 50);
	
    ///////////////////��ǲ ��ĳ�� ///////////////////////////////////////////////////////////
	Scanner input = new Scanner(System.in);
	//System.out.println("client 1");
	Socket CS = null;

	int Pow(int num, int p) {
		
		int _num = num;
		
		for(int i=1; i<p; i++) {
			_num *= num;
		}
		
		return _num;
	}
	//////////////////////////////////////////////////////////////////////////////////////
	
	public Dynamic() //������
	{
    ////////////////////Ŭ���̾�Ʈ ���� ����/////////////////////////////////////////////////////
		CS = new Socket();
		 
		System.out.print("Login? (enter num): ");
		int temp = input.nextInt();

		try {
			CS.connect(new InetSocketAddress("127.0.0.1", 9999));
		} catch (IOException e1) {

			System.out.println(e1);
		}
		
		System.out.println("SUCCESS");
		
	//////////////////////////////////////////////////////////////////////////////////////
		
		setUndecorated(true);
		setTitle("game name");
		setSize(Main.SCREEN_WIDTH,Main.SCREEN_HEIGH);
		setResizable(false);//���Ƿ� â���� �Ұ���
		setLocationRelativeTo(null);//���߾�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����â ����� ���α׷� ��ü����
		setVisible(true);//���� ��� 
		setBackground(new Color(0,0,0,0));//��� ���
		setLayout(null);
	
	/////////////coffcntbutton����//////////////////////
		coffcntbutton.setVisible(false);
	    coffcntbutton.setBounds(400,120,500,500);//��ġ ��ġ ���� ����  ��ǥ�� ũ�� ����
	    coffcntbutton.setBorderPainted(false);
	    coffcntbutton.setContentAreaFilled(false);
	    coffcntbutton.setFocusPainted(false);
	    coffcntbutton.setFont(font3);// ��Ʈ ����
	    coffcntbutton.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseEntered(MouseEvent e) {
	            //cofcntbutton.setIcon(upbuttonenteredImage);//���콺�� �ö����� ��ư ����
	            coffcntbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	            
	         }
	         @Override
	         public void mouseExited(MouseEvent e) {
	            coffcntbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	            
	         }
	         @Override
	         public void mousePressed(MouseEvent e) {        
	         }   
	      });
	   
	      add(coffcntbutton); //Ŀ�� ī��Ʈ��ư ����->Ŀ�Ǽ��� ��¹� ����
	      
	      
	/////////////cakecntbutton����///////////////////////	
	      	cakecntbutton.setVisible(false);
	      	cakecntbutton.setBounds(400,120,500,500);//��ġ ��ġ ���� ����
	      	cakecntbutton.setBorderPainted(false);
	      	cakecntbutton.setContentAreaFilled(false);
	      	cakecntbutton.setFocusPainted(false);
	      	cakecntbutton.setFont(font3);
	      	cakecntbutton.addMouseListener(new MouseAdapter() {
		         @Override
		         public void mouseEntered(MouseEvent e) {
		            
		        	 cakecntbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		            
		         }
		         @Override
		         public void mouseExited(MouseEvent e) {
		        	 cakecntbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		            
		         }
		         @Override
		         public void mousePressed(MouseEvent e) {        
		         }   
		      });
		   
		      add(cakecntbutton);//����ũ ī��Ʈ��ư ����->����ũ���� ��¹� ����
		      
	//////////////exitbutton����//////////////////////////	
		exitbutton.setBounds(1245,0,30,30);
		exitbutton.setBorderPainted(false);
		exitbutton.setContentAreaFilled(false);
		exitbutton.setFocusPainted(false);
		exitbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitbutton.setIcon(exitbuttonenteredImage);//���콺�� �ö����� ��ư ����
				exitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitbutton.setIcon(exitbuttonbasicImage);//���콺�� ��ư�� ������� �ʱ��ư���� ����
				exitbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
				//���콺 Ŭ���� ����
			
				
			}
		});
	
		add(exitbutton);//�����ư �߰�
	
	//////////////homebutton����////////////////////////////
		homebutton.setBounds(1175,30,100,100);
		homebutton.setBorderPainted(false);
		homebutton.setContentAreaFilled(false);
		homebutton.setFocusPainted(false);
		homebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				homebutton.setIcon(homebuttonenteredImage);//���콺�� �ö����� ��ư ����
				homebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				homebutton.setIcon(homebuttonbasicImage);//���콺�� ��ư�� ������� �ʱ��ư���� ����
				homebutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				coffeebutton.setVisible(true);//Ŀ���ֹ���ư ����
				cakebutton.setVisible(true);//����ũ�ֹ���ư ����
				upbutton.setVisible(false);//����������ư ����
				downbutton.setVisible(false);//�������ҹ�ư ����
				background = new ImageIcon(Main.class.getResource("../images/introbackground.png")).getImage();//�⺻ ����ȭ�� �ε�
				coffcntbutton.setVisible(false);//Ŀ�Ǽ�����ư ����
				cakecntbutton.setVisible(false);//����ũ������ư ����
				
			}
		});
	
		add(homebutton);//Ȩ��ư ����
		
	/////////////coffeebutton����////////////////////////////

		coffeebutton.setBounds(80,250,220,176);
		coffeebutton.setBorderPainted(false);
		coffeebutton.setContentAreaFilled(false);
		coffeebutton.setFocusPainted(false);
		coffeebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				coffeebutton.setIcon(coffeebuttonenteredImage);//���콺�� �ö����� ��ư ����
				coffeebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				coffeebutton.setIcon(coffeebuttonbasicImage);
				coffeebutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				coffeebutton.setVisible(false);
				cakebutton.setVisible(false);
				upbutton.setVisible(true);
				downbutton.setVisible(true);
				background = new ImageIcon(Main.class.getResource("../images/coffeemainbackground.jpg")).getImage();
				coffcntbutton.setVisible(true);
				cakecntbutton.setVisible(false);
				
				obj_sec = 0;
			}
		});
	
		add(coffeebutton);
		
	/////////////cakebutton����////////////////////////////	
		cakebutton.setBounds(80,480,220,176);//��ġ ��ġ ���� ����
		cakebutton.setBorderPainted(false);
		cakebutton.setContentAreaFilled(false);
		cakebutton.setFocusPainted(false);
		cakebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cakebutton.setIcon(cakebuttonenteredImage);//���콺�� �ö����� ��ư ����
				cakebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cakebutton.setIcon(cakebuttonbasicImage);
				cakebutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//System.exit(0);
				coffeebutton.setVisible(false);
				cakebutton.setVisible(false);
				upbutton.setVisible(true);
				downbutton.setVisible(true);
				background = new ImageIcon(Main.class.getResource("../images/cakemainbackground.jpg")).getImage();
				coffcntbutton.setVisible(false);
				cakecntbutton.setVisible(true);
				
				obj_sec = 1;
			}
		});
	
		add(cakebutton);
		
	/////////////resultbutton����////////////////////////////	
		resultbutton.setBounds(1000,480,220,176);
		resultbutton.setBorderPainted(false);
		resultbutton.setContentAreaFilled(false);
		resultbutton.setFocusPainted(false);
		resultbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				resultbutton.setIcon(resultbuttonenteredImage);//���콺�� �ö����� ��ư ����
				resultbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				resultbutton.setIcon(resultbuttonbasicImage);
				resultbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//////////////////////////////////////////////////////////////////
				//String message = "S01,1,02,!00@01#!01@03#,E";//input.nextLine();
				//String message = input.nextLine();
				String message = "S01,1,";
				//CoffCnt = 0, CakeCnt = 0, obj_sec = 0;
				int obj_cnt = 0;
				if(CoffCnt != 0)	obj_cnt++;
				if(CakeCnt != 0)	obj_cnt++;
				
				message += String.format("%02d,", 2);
				
				//for(int i=0; i<2; i++) {
				message += String.format("!%02d@%02d#", 0, CoffCnt);
				message += String.format("!%02d@%02d#", 1, CakeCnt);
				//}
					
				message += String.format(",E");
				
				if(obj_cnt == 0)	return;
				
				try {
					if(message.length() != 0) {
						byte[] as = message.getBytes("UTF-8");
							OutputStream OS = CS.getOutputStream();
							OS.write(as);
							
						InputStream IS = CS.getInputStream();
						byte[] bt = new byte[256];
			            int size = IS.read(bt);
			            
			            if(bt[0] == 'O') {
			            	int money = 0;
			            	int cnt = 1;
			            	
			            	for(int i=(size-2); i>=0; i--) {
			            		money += (bt[cnt] - 48) * Pow(10, i);
			            		cnt++;
			            	}
			            	
			            	System.out.println("�ֹ��� ���� �Ǿ����ϴ�[" + money + "]");
			            	JOptionPane.showMessageDialog(null, "�ֹ��� ���� �Ǿ����ϴ�[" + money + "]");
			            }
			            //�������� �����κ��� �޾ƿ� n�� ���Ź޾� �������޽��� ���
			            else if(bt[0] == 'N') {
			            	System.out.println("��� �����մϴ�.");
			            	JOptionPane.showMessageDialog(null, "��� �����մϴ�.");
			            }
					}
				} catch (Exception e1) {
					System.out.println(e1);				
				}
				///////////////////////////////
				

				coffeebutton.setVisible(true);
				cakebutton.setVisible(true);
				upbutton.setVisible(false);
				downbutton.setVisible(false);
				background = new ImageIcon(Main.class.getResource("../images/introbackground.png")).getImage();
				coffcntbutton.setVisible(false);
				cakecntbutton.setVisible(false);
				
				//�ֹ��Ϸ��� Ȩ��ư�������� ����ư �ʱ�ȭ
				CoffCnt = 0;
				CakeCnt = 0;
				coffcntbutton.setText("  " + 0 + "��" );
				cakecntbutton.setText("  " + 0 + "��" );
			
			}
		});
	
		add(resultbutton);
		
	///////////////upbutton����///////////////////////////////	
		upbutton.setVisible(false);
		upbutton.setBounds(40,210,160,260);//��ġ ��ġ ���� ����
		upbutton.setBorderPainted(false);
		upbutton.setContentAreaFilled(false);
		upbutton.setFocusPainted(false);
		upbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				upbutton.setIcon(upbuttonenteredImage);//���콺�� �ö����� ��ư ����
				upbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				upbutton.setIcon(upbuttonbasicImage);
				upbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//���ʹ�ư �̺�Ʈ
				if(obj_sec == 0) {
					if(CoffCnt < 99)	CoffCnt++;
					System.out.println("Coff : " + CoffCnt);
					coffcntbutton.setText("  " + CoffCnt + "��" );
				}
				else if(obj_sec == 1) {
					if(CakeCnt < 99)	CakeCnt++;
					System.out.println("Cake " + CakeCnt);
					cakecntbutton.setText(" " + CakeCnt + "��" );
				}
			}
		});
	
		add(upbutton);
		
	//////////////downbutton����////////////////////////////////////////	
		downbutton.setVisible(false);
		downbutton.setBounds(1080,210,160,260);//��ġ ��ġ ���� ����
		downbutton.setBorderPainted(false);
		downbutton.setContentAreaFilled(false);
		downbutton.setFocusPainted(false);
		downbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				downbutton.setIcon(downbuttonenteredImage);//���콺�� �ö����� ��ư ����
				downbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				downbutton.setIcon(downbuttonbasicImage);
				downbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʹ�ư �̺�Ʈ
				
				if(obj_sec == 0) {
					if(CoffCnt > 0)	CoffCnt--;
					System.out.println("Coff " + CoffCnt);
					coffcntbutton.setText(" " + CoffCnt + "��" );
				}
				else if(obj_sec == 1) {
					if(CakeCnt > 0)	CakeCnt--;
					System.out.println("Cake " + CakeCnt);
					cakecntbutton.setText(" " + CakeCnt + "��" );
				}
			}
		});
	
		add(downbutton);
		
	
	//////////////�޴��� ����///////////////////////////////////////////////////////////
		menubar.setBounds(0,0,1280,30);
		menubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e ) {
				
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y- mouseY);
				
			}
		});
		add(menubar);
		
	
	}//������
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGH);//��ũ�� �ʺ�,���� ����
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(background,0,0,null);//�̹��� �׷���
		
		paintComponents(g);//���������ӿ� �׷��ֱ�-���̶󺧰����� �׸��� -add�Ȱ� ���
		this.repaint();//jframe�� ��ӹ��������� ������� â�� �׷��ش�
		//System.out.println("a");
		
		
	 
	}
}
