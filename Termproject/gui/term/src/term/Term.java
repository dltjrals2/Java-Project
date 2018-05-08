package term;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.awt.List;


class posThread extends Thread {
	 
    Socket ServerSocket;
    int ID;
    
    static int Obj_Size[] = new int[10];
    static int Obj_Money[] = new int[10];
    
    posThread(Socket SS, int ID) {
        this.ServerSocket = SS;
        this.ID = ID;
        
        //재고 초기화
        Obj_Size[0] = 50;
        Obj_Size[1] = 50;
        
        //가격 초기화
        Obj_Money[0] = 4100;
        Obj_Money[1] = 5000;
    }
 
    @Override
    public void run() {
    	
    	//변수 초기화
    	int state = 0, mode = 0;
    	int buf_size = 0;
    	int packet_size = 0;
    	int arg_size = 0, arg_mode = 0, arg_cnt = 0;
    	byte buf[] = new byte[10];
    	int obj_name[] = new int[10];
    	int obj_size[] = new int[10];
    	
        try {
            while (true) {
            	
            	//소켓 인풋스트림 생성
                InputStream IS = ServerSocket.getInputStream();
                byte[] bt = new byte[256];
                
                //소켓에서 데이터를 읽어온다.
                int size = IS.read(bt);
 
                String output = new String(bt, 0, size, "UTF-8");
                //System.out.println(ID + "> " + output);
                
                byte buffer[] = output.getBytes();
                
                /////받은 데이터 처리-파싱시작/////
                for(int i=0; i<size; i++) {
                	//소켓데이터의 끊겨들어옴 방지
                	//State 0
                	//패킷 시작신호 확인
                	if(state == 0) {
                		//시작신호이면 패킷 크기를 가저옴
                		if(buffer[i] == 'S'){
                			//System.out.println("S");
                			state = 1;
                		}
                	}
                	
                	//State 1
                	//패킷 길이 저장
                	else if(state == 1) {
                		//,이면 패킷 길이 저장
                		if(buffer[i] == ',') {
                			packet_size = ((buf[0] - 48) * 10) + (buf[1] - 48);
                			buf_size = 0;
                			state = 2;
                			//System.out.println(packet_size + "," + state);
                		}
                		//버퍼에 문자 저장
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 2
                	//패킷 종류 저장
                	else if(state == 2) {
                		//,이면 패킷 종류 저장
                		if(buffer[i] == ',') {
                			mode = buf[0] - 48;
                			buf_size = 0;
                			state = 3;
                			//System.out.println(mode + "," + state);
                		}
                		//버퍼에 문자 저장
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 3
                	//arg크기 크기 저장
                	else if(state == 3) {
                		//,이면 arg크기 저장
                		if(buffer[i] == ',') {
                			arg_size = ((buf[0] - 48) * 10) + (buf[1] - 48);
                			buf_size = 0;
                			state = 4;
                			//System.out.println(arg_size + "," + state);
                		}
                		//버퍼에 문자 저장
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 4-물건 개수정보
                	else if(state == 4) {
                		
                		//물건시작
                		if(arg_mode == 0) {
                			
                			if(buffer[i] == '!') {
                				arg_mode = 1;
                				//System.out.println("!" + "," + arg_mode);
                			}
                			else {
                				//Error
                			}
                		}
                		//물건 이름
                		else if(arg_mode == 1) {
                			
                			if(buffer[i] == '@') {
                				//@-물건이름 정보종료
                				buf[buf_size] = 0;
                				obj_name[arg_cnt] = ((buf[0] - 48) * 10) + (buf[1] - 48);
                				arg_mode = 2;
                				buf_size = 0;
                				//System.out.println(obj_name + "," + arg_mode);
                			}
                			else {
                				//물건 이름 저장
                				buf[buf_size] = buffer[i];
                    			buf_size++;
                			}
                		}
                		//물건 갯수
						else if(arg_mode == 2) {
							
							if(buffer[i] == '#') {
								//#-물건개수 정보종료
                				arg_mode = 0;
                				obj_size[arg_cnt] = ((buf[0] - 48) * 10) + (buf[1] - 48);
                				arg_cnt++;
                				buf_size = 0;
                				//System.out.println(obj_size + "," + arg_mode);
                				
                				if(arg_size == arg_cnt) {
                					arg_cnt = 0;
                					state = 5;
                				}
                			}
							else {
								buf[buf_size] = buffer[i];
                    			buf_size++;
                			}
						}
                	}
                	
                	//State 5
                	else if(state == 5) {
                		
                		if(buffer[i] == 'E') {
                			state = 0;
                			//System.out.println("E" + "," + state);
                			
                			//재고 확인
                			String message = "O";
                			int money = 0;
                			
                			for(int j=0; j<arg_size; j++) {
                				
	                			boolean check = isEnough(obj_name[j], obj_size[j]);
	                			
	                			
	                			//재고가 충분하면 Ok 송신
	                			if(check) {
	                				//System.out.println(obj_name[j] + " " + obj_size[j] + " 충분데스");
	                				
	                			}
	                			
	                			//재고가 부족하면 Error 송신
	                			else {
	                				//System.out.println(obj_name[j] + " " + obj_size[j] + " 불충분데스");

	                				message = "N";//재고가 부족할시 데이터변경
	                			}
                			}
                			
                			//O이면 재고부족이 없음
                			if(message == "O") {
                				
                				System.out.print("ID[" + ID + "]에서 주문을 받았습니다.     " + "재고 : ");
                				
                				
                				//재고 업데이트 / 가격 계산
                				for(int j=0; j<arg_size; j++) {
                					int _size = getObjSize(j) - obj_size[j];
                					SetObjSize(j, _size);
                					System.out.print(j + "[" + _size + "]  ");
                					money += Obj_Money[j] * obj_size[j];
                				}
                				
                				System.out.println("총액 : " + money);
                			}
                			else {
                				System.out.println("ID[" + ID + "] " + "재고가 부족하여 주문이 접수되지 않았습니다.");
                			}
                			
                			//성공 여부를 클라이언트에게 전송
                			OutputStream OS = ServerSocket.getOutputStream();
                			message += Integer.toString(money);
            				byte[] as = message.getBytes("UTF-8");
            				OS.write(as);
                		}
                		else {
                			//Error
                		}
                	}
                }//패킷 처리 종료
            }
            
        } catch (IOException e) {
            System.out.println("--" + ID + " user OUT");
        }
    }
 
    //물건 재고 수정
    void SetObjSize(int index, int size) {
    	
    	Obj_Size[index] = size;
    }
    
    //물건 가격 수정
    void SetObjMoney(int index, int size) {
    	
    	Obj_Money[index] = size;
    }
    
    //물건 재고 리턴
    int getObjSize(int index) {
    	
    	return Obj_Size[index];
    }
    
    //물건 가격 리턴
    int getObjMoney(int index) {
    	
    	return Obj_Money[index];
    }
    
    //물건 재고가 충분한지 확인
    boolean isEnough(int index, int size) {
    	
    	if(getObjSize(index) >= size)	
    		return true;
    	
    	else
    		return false;
    }
}

 
class connectThread extends Thread {
 
	//키보드 입력 스캐너
	Scanner input = new Scanner(System.in);
	
	//소캣 서버 객체
    ServerSocket MSS;
    
    //접속 클라이언트 갯수 계산
    int Count = 0;
    
    //물건 이름 저장
    String ObjName[] = new String[10];
 
    //클라이언트 스레드
    posThread ust;
    
    //서버 초기화
    connectThread(ServerSocket MSS) {
        this.MSS = MSS;
        ObjName[0] = "Coffee";
        ObjName[1] = "Cake";
    }
 
    @Override
    public void run() {
        try {
            while (true) {
            	//서버 소켓 생성
            	//클라이언트가 접속할때까지 대기
                Socket ServerSocket = MSS.accept();//blocking소켓 -클라이언트의 접속까지 대기
                System.out.println("--" + Count + " user login");
 
                //클라이언트 스레드 생성
                ust = new posThread(ServerSocket, Count);

                //클라이언트 스레드 실행
                ust.start();
                
                //접속중인 클라이언트 갯수 업데이트
                Count++;
                
                //클라이언트가 2개이면 서버 실행
                if(Count > 1) {
                	boolean flag = true;
                	while(flag) {
                		//사용자 입력 
                		System.out.println("1:재고확인   2:재고수정");
                		int mode = input.nextInt();
                		
                		//1이면 재고 확인
                		if(mode == 1) {
                			for(int i=0; i<10; i++) {
                				System.out.println(ObjName[i] + " : " + ust.getObjSize(i) );
                			}
                		}
                		//2이면 재고 수정
                		else if(mode == 2) {
                			
                			System.out.println("물건 index");
                			
                			for(int i=0; i<10; i++) {
                				System.out.println(ObjName[i] + " : " + i);
                			}

                    		int obj_index = input.nextInt();
                    		System.out.println("물건 size : ");
                    		int obj_size = input.nextInt();
                    		ust.SetObjSize(obj_index, obj_size);
                		}

                	}
                }
            }
 
        } catch (IOException e) {
            System.out.println("--SERVER CLOSE");
        }
    }
    
}//


class Term {

	//main//
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		//서버소캣 생성
        ServerSocket MSS = null;
 
        try {
        	//서버 소캣 생성
            MSS = new ServerSocket();
            MSS.bind(new InetSocketAddress("127.0.0.1", 9999));//서버 open - 주소및 포트번호 설정
 
            System.out.println("--SERVER Close : input num");
            System.out.println("--SERVER Waiting...");
            
            //서버 소캣 실행
            connectThread cnt = new connectThread(MSS);
            cnt.start();//스레드 실행
 
            int temp = input.nextInt();//프로그램 종료방지
 
        } catch (Exception e) {
            System.out.println(e);
        }
 
        try {
            MSS.close();
        } catch (Exception e) {
            System.out.println(e);
        }

		
	} // main
	

}
