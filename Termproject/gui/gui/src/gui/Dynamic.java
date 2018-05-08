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



public class Dynamic extends JFrame//그래픽 기반 프로그램 상속
{
	private Image screenImage;//더블버퍼링
	private Graphics screenGraphic;//더블버퍼링
	
	
	// 창 종료버튼 생성
	private ImageIcon exitbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/exitbuttonentered.png"));
	private ImageIcon exitbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/exitbuttonbasic.png"));
	// 홈 복귀버튼 생성
	private ImageIcon homebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/homebuttonentered.png"));
	private ImageIcon homebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/homebuttonbasic.png"));
	// 커피 주문버튼 생성
	private ImageIcon coffeebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/coffeebuttonentered.png"));
	private ImageIcon coffeebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/coffeebuttonbasic.jpg"));
	// 케이크 주문버튼 생성
	private ImageIcon cakebuttonenteredImage = new ImageIcon(Main.class.getResource("../images/cakebuttonentered.png"));
	private ImageIcon cakebuttonbasicImage = new ImageIcon(Main.class.getResource("../images/cakebuttonbasic.jpg"));
	// 주문 결제버튼 생성
	private ImageIcon resultbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/resultbuttonentered.jpg"));
	private ImageIcon resultbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/resultbuttonbasic.jpg"));
	
	// 수량 추가버튼 생성
	private ImageIcon upbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/upbuttonentered.png"));
	private ImageIcon upbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/upbuttonbasic.png"));
	// 수량 감소버튼 생성
	private ImageIcon downbuttonenteredImage = new ImageIcon(Main.class.getResource("../images/downbuttonentered.png"));
	private ImageIcon downbuttonbasicImage = new ImageIcon(Main.class.getResource("../images/downbuttonbasic.png"));
	
	private Image selectedImage = new ImageIcon(Main.class.getResource("../images/bighero.jpg")).getImage();
	private Image background = new ImageIcon(Main.class.getResource("../images/introbackground.png")).getImage();//이미지파일을 얻어와서 이미지 인스턴스를 이미지 변수에 초기화
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
	// 커피, 케이크 카운트 초기화, obj_sec-> Coff or Cake 선택
	private JButton coffcntbutton = new JButton(" " + CoffCnt + "개" );//커피 수량
	private JButton cakecntbutton = new JButton(" " + CakeCnt + "개");//케이크 수량
	
	
	private Font font3 = new Font("Selif", Font.PLAIN, 50);
	
    ///////////////////인풋 스캐너 ///////////////////////////////////////////////////////////
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
	
	public Dynamic() //생성자
	{
    ////////////////////클라이언트 소켓 실행/////////////////////////////////////////////////////
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
		setResizable(false);//임의로 창설정 불가능
		setLocationRelativeTo(null);//정중앙
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//게임창 종료시 프로그램 전체종료
		setVisible(true);//게임 출력 
		setBackground(new Color(0,0,0,0));//배경 흰색
		setLayout(null);
	
	/////////////coffcntbutton생성//////////////////////
		coffcntbutton.setVisible(false);
	    coffcntbutton.setBounds(400,120,500,500);//위치 위치 가로 세로  좌표및 크기 설정
	    coffcntbutton.setBorderPainted(false);
	    coffcntbutton.setContentAreaFilled(false);
	    coffcntbutton.setFocusPainted(false);
	    coffcntbutton.setFont(font3);// 폰트 설정
	    coffcntbutton.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseEntered(MouseEvent e) {
	            //cofcntbutton.setIcon(upbuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
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
	   
	      add(coffcntbutton); //커피 카운트버튼 생성->커피수량 상승및 감소
	      
	      
	/////////////cakecntbutton생성///////////////////////	
	      	cakecntbutton.setVisible(false);
	      	cakecntbutton.setBounds(400,120,500,500);//위치 위치 가로 세로
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
		   
		      add(cakecntbutton);//케이크 카운트버튼 생성->케이크수량 상승및 감소
		      
	//////////////exitbutton생성//////////////////////////	
		exitbutton.setBounds(1245,0,30,30);
		exitbutton.setBorderPainted(false);
		exitbutton.setContentAreaFilled(false);
		exitbutton.setFocusPainted(false);
		exitbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitbutton.setIcon(exitbuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
				exitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitbutton.setIcon(exitbuttonbasicImage);//마우스가 버튼을 벗어났을때 초기버튼으로 복귀
				exitbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
				//마우스 클릭시 종료
			
				
			}
		});
	
		add(exitbutton);//종료버튼 추가
	
	//////////////homebutton생성////////////////////////////
		homebutton.setBounds(1175,30,100,100);
		homebutton.setBorderPainted(false);
		homebutton.setContentAreaFilled(false);
		homebutton.setFocusPainted(false);
		homebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				homebutton.setIcon(homebuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
				homebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				homebutton.setIcon(homebuttonbasicImage);//마우스가 버튼을 벗어났을때 초기버튼으로 복귀
				homebutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				coffeebutton.setVisible(true);//커피주문버튼 생성
				cakebutton.setVisible(true);//케이크주문버튼 생성
				upbutton.setVisible(false);//수량증가버튼 삭제
				downbutton.setVisible(false);//수량감소버튼 삭제
				background = new ImageIcon(Main.class.getResource("../images/introbackground.png")).getImage();//기본 바탕화면 로드
				coffcntbutton.setVisible(false);//커피수량버튼 삭제
				cakecntbutton.setVisible(false);//케이크수량버튼 삭제
				
			}
		});
	
		add(homebutton);//홈버튼 생성
		
	/////////////coffeebutton생성////////////////////////////

		coffeebutton.setBounds(80,250,220,176);
		coffeebutton.setBorderPainted(false);
		coffeebutton.setContentAreaFilled(false);
		coffeebutton.setFocusPainted(false);
		coffeebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				coffeebutton.setIcon(coffeebuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
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
		
	/////////////cakebutton생성////////////////////////////	
		cakebutton.setBounds(80,480,220,176);//위치 위치 가로 세로
		cakebutton.setBorderPainted(false);
		cakebutton.setContentAreaFilled(false);
		cakebutton.setFocusPainted(false);
		cakebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cakebutton.setIcon(cakebuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
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
		
	/////////////resultbutton생성////////////////////////////	
		resultbutton.setBounds(1000,480,220,176);
		resultbutton.setBorderPainted(false);
		resultbutton.setContentAreaFilled(false);
		resultbutton.setFocusPainted(false);
		resultbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				resultbutton.setIcon(resultbuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
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
			            	
			            	System.out.println("주문이 접수 되었습니다[" + money + "]");
			            	JOptionPane.showMessageDialog(null, "주문이 접수 되었습니다[" + money + "]");
			            }
			            //재고부족시 서버로부터 받아온 n을 수신받아 재고부족메시지 출력
			            else if(bt[0] == 'N') {
			            	System.out.println("재고가 부족합니다.");
			            	JOptionPane.showMessageDialog(null, "재고가 부족합니다.");
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
				
				//주문완료후 홈버튼눌렀을때 전버튼 초기화
				CoffCnt = 0;
				CakeCnt = 0;
				coffcntbutton.setText("  " + 0 + "개" );
				cakecntbutton.setText("  " + 0 + "개" );
			
			}
		});
	
		add(resultbutton);
		
	///////////////upbutton생성///////////////////////////////	
		upbutton.setVisible(false);
		upbutton.setBounds(40,210,160,260);//위치 위치 가로 세로
		upbutton.setBorderPainted(false);
		upbutton.setContentAreaFilled(false);
		upbutton.setFocusPainted(false);
		upbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				upbutton.setIcon(upbuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
				upbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				upbutton.setIcon(upbuttonbasicImage);
				upbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//왼쪽버튼 이벤트
				if(obj_sec == 0) {
					if(CoffCnt < 99)	CoffCnt++;
					System.out.println("Coff : " + CoffCnt);
					coffcntbutton.setText("  " + CoffCnt + "개" );
				}
				else if(obj_sec == 1) {
					if(CakeCnt < 99)	CakeCnt++;
					System.out.println("Cake " + CakeCnt);
					cakecntbutton.setText(" " + CakeCnt + "개" );
				}
			}
		});
	
		add(upbutton);
		
	//////////////downbutton생성////////////////////////////////////////	
		downbutton.setVisible(false);
		downbutton.setBounds(1080,210,160,260);//위치 위치 가로 세로
		downbutton.setBorderPainted(false);
		downbutton.setContentAreaFilled(false);
		downbutton.setFocusPainted(false);
		downbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				downbutton.setIcon(downbuttonenteredImage);//마우스가 올라갔을떄 버튼 변경
				downbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				downbutton.setIcon(downbuttonbasicImage);
				downbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//오른쪽버튼 이벤트
				
				if(obj_sec == 0) {
					if(CoffCnt > 0)	CoffCnt--;
					System.out.println("Coff " + CoffCnt);
					coffcntbutton.setText(" " + CoffCnt + "개" );
				}
				else if(obj_sec == 1) {
					if(CakeCnt > 0)	CakeCnt--;
					System.out.println("Cake " + CakeCnt);
					cakecntbutton.setText(" " + CakeCnt + "개" );
				}
			}
		});
	
		add(downbutton);
		
	
	//////////////메뉴바 생성///////////////////////////////////////////////////////////
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
		
	
	}//생성자
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGH);//스크린 너비,높이 생성
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(background,0,0,null);//이미지 그려줌
		
		paintComponents(g);//제이프레임에 그려주기-제이라벨같은거 그리기 -add된거 출력
		this.repaint();//jframe을 상속받은곳에서 가장먼저 창을 그려준다
		//System.out.println("a");
		
		
	 
	}
}
