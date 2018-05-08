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
        
        //��� �ʱ�ȭ
        Obj_Size[0] = 50;
        Obj_Size[1] = 50;
        
        //���� �ʱ�ȭ
        Obj_Money[0] = 4100;
        Obj_Money[1] = 5000;
    }
 
    @Override
    public void run() {
    	
    	//���� �ʱ�ȭ
    	int state = 0, mode = 0;
    	int buf_size = 0;
    	int packet_size = 0;
    	int arg_size = 0, arg_mode = 0, arg_cnt = 0;
    	byte buf[] = new byte[10];
    	int obj_name[] = new int[10];
    	int obj_size[] = new int[10];
    	
        try {
            while (true) {
            	
            	//���� ��ǲ��Ʈ�� ����
                InputStream IS = ServerSocket.getInputStream();
                byte[] bt = new byte[256];
                
                //���Ͽ��� �����͸� �о�´�.
                int size = IS.read(bt);
 
                String output = new String(bt, 0, size, "UTF-8");
                //System.out.println(ID + "> " + output);
                
                byte buffer[] = output.getBytes();
                
                /////���� ������ ó��-�Ľ̽���/////
                for(int i=0; i<size; i++) {
                	//���ϵ������� ���ܵ��� ����
                	//State 0
                	//��Ŷ ���۽�ȣ Ȯ��
                	if(state == 0) {
                		//���۽�ȣ�̸� ��Ŷ ũ�⸦ ������
                		if(buffer[i] == 'S'){
                			//System.out.println("S");
                			state = 1;
                		}
                	}
                	
                	//State 1
                	//��Ŷ ���� ����
                	else if(state == 1) {
                		//,�̸� ��Ŷ ���� ����
                		if(buffer[i] == ',') {
                			packet_size = ((buf[0] - 48) * 10) + (buf[1] - 48);
                			buf_size = 0;
                			state = 2;
                			//System.out.println(packet_size + "," + state);
                		}
                		//���ۿ� ���� ����
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 2
                	//��Ŷ ���� ����
                	else if(state == 2) {
                		//,�̸� ��Ŷ ���� ����
                		if(buffer[i] == ',') {
                			mode = buf[0] - 48;
                			buf_size = 0;
                			state = 3;
                			//System.out.println(mode + "," + state);
                		}
                		//���ۿ� ���� ����
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 3
                	//argũ�� ũ�� ����
                	else if(state == 3) {
                		//,�̸� argũ�� ����
                		if(buffer[i] == ',') {
                			arg_size = ((buf[0] - 48) * 10) + (buf[1] - 48);
                			buf_size = 0;
                			state = 4;
                			//System.out.println(arg_size + "," + state);
                		}
                		//���ۿ� ���� ����
                		else {
                			buf[buf_size] = buffer[i];
                			buf_size++;
                		}
                	}
                	
                	//State 4-���� ��������
                	else if(state == 4) {
                		
                		//���ǽ���
                		if(arg_mode == 0) {
                			
                			if(buffer[i] == '!') {
                				arg_mode = 1;
                				//System.out.println("!" + "," + arg_mode);
                			}
                			else {
                				//Error
                			}
                		}
                		//���� �̸�
                		else if(arg_mode == 1) {
                			
                			if(buffer[i] == '@') {
                				//@-�����̸� ��������
                				buf[buf_size] = 0;
                				obj_name[arg_cnt] = ((buf[0] - 48) * 10) + (buf[1] - 48);
                				arg_mode = 2;
                				buf_size = 0;
                				//System.out.println(obj_name + "," + arg_mode);
                			}
                			else {
                				//���� �̸� ����
                				buf[buf_size] = buffer[i];
                    			buf_size++;
                			}
                		}
                		//���� ����
						else if(arg_mode == 2) {
							
							if(buffer[i] == '#') {
								//#-���ǰ��� ��������
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
                			
                			//��� Ȯ��
                			String message = "O";
                			int money = 0;
                			
                			for(int j=0; j<arg_size; j++) {
                				
	                			boolean check = isEnough(obj_name[j], obj_size[j]);
	                			
	                			
	                			//��� ����ϸ� Ok �۽�
	                			if(check) {
	                				//System.out.println(obj_name[j] + " " + obj_size[j] + " ��е���");
	                				
	                			}
	                			
	                			//��� �����ϸ� Error �۽�
	                			else {
	                				//System.out.println(obj_name[j] + " " + obj_size[j] + " ����е���");

	                				message = "N";//��� �����ҽ� �����ͺ���
	                			}
                			}
                			
                			//O�̸� �������� ����
                			if(message == "O") {
                				
                				System.out.print("ID[" + ID + "]���� �ֹ��� �޾ҽ��ϴ�.     " + "��� : ");
                				
                				
                				//��� ������Ʈ / ���� ���
                				for(int j=0; j<arg_size; j++) {
                					int _size = getObjSize(j) - obj_size[j];
                					SetObjSize(j, _size);
                					System.out.print(j + "[" + _size + "]  ");
                					money += Obj_Money[j] * obj_size[j];
                				}
                				
                				System.out.println("�Ѿ� : " + money);
                			}
                			else {
                				System.out.println("ID[" + ID + "] " + "��� �����Ͽ� �ֹ��� �������� �ʾҽ��ϴ�.");
                			}
                			
                			//���� ���θ� Ŭ���̾�Ʈ���� ����
                			OutputStream OS = ServerSocket.getOutputStream();
                			message += Integer.toString(money);
            				byte[] as = message.getBytes("UTF-8");
            				OS.write(as);
                		}
                		else {
                			//Error
                		}
                	}
                }//��Ŷ ó�� ����
            }
            
        } catch (IOException e) {
            System.out.println("--" + ID + " user OUT");
        }
    }
 
    //���� ��� ����
    void SetObjSize(int index, int size) {
    	
    	Obj_Size[index] = size;
    }
    
    //���� ���� ����
    void SetObjMoney(int index, int size) {
    	
    	Obj_Money[index] = size;
    }
    
    //���� ��� ����
    int getObjSize(int index) {
    	
    	return Obj_Size[index];
    }
    
    //���� ���� ����
    int getObjMoney(int index) {
    	
    	return Obj_Money[index];
    }
    
    //���� ��� ������� Ȯ��
    boolean isEnough(int index, int size) {
    	
    	if(getObjSize(index) >= size)	
    		return true;
    	
    	else
    		return false;
    }
}

 
class connectThread extends Thread {
 
	//Ű���� �Է� ��ĳ��
	Scanner input = new Scanner(System.in);
	
	//��Ĺ ���� ��ü
    ServerSocket MSS;
    
    //���� Ŭ���̾�Ʈ ���� ���
    int Count = 0;
    
    //���� �̸� ����
    String ObjName[] = new String[10];
 
    //Ŭ���̾�Ʈ ������
    posThread ust;
    
    //���� �ʱ�ȭ
    connectThread(ServerSocket MSS) {
        this.MSS = MSS;
        ObjName[0] = "Coffee";
        ObjName[1] = "Cake";
    }
 
    @Override
    public void run() {
        try {
            while (true) {
            	//���� ���� ����
            	//Ŭ���̾�Ʈ�� �����Ҷ����� ���
                Socket ServerSocket = MSS.accept();//blocking���� -Ŭ���̾�Ʈ�� ���ӱ��� ���
                System.out.println("--" + Count + " user login");
 
                //Ŭ���̾�Ʈ ������ ����
                ust = new posThread(ServerSocket, Count);

                //Ŭ���̾�Ʈ ������ ����
                ust.start();
                
                //�������� Ŭ���̾�Ʈ ���� ������Ʈ
                Count++;
                
                //Ŭ���̾�Ʈ�� 2���̸� ���� ����
                if(Count > 1) {
                	boolean flag = true;
                	while(flag) {
                		//����� �Է� 
                		System.out.println("1:���Ȯ��   2:������");
                		int mode = input.nextInt();
                		
                		//1�̸� ��� Ȯ��
                		if(mode == 1) {
                			for(int i=0; i<10; i++) {
                				System.out.println(ObjName[i] + " : " + ust.getObjSize(i) );
                			}
                		}
                		//2�̸� ��� ����
                		else if(mode == 2) {
                			
                			System.out.println("���� index");
                			
                			for(int i=0; i<10; i++) {
                				System.out.println(ObjName[i] + " : " + i);
                			}

                    		int obj_index = input.nextInt();
                    		System.out.println("���� size : ");
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

		//������Ĺ ����
        ServerSocket MSS = null;
 
        try {
        	//���� ��Ĺ ����
            MSS = new ServerSocket();
            MSS.bind(new InetSocketAddress("127.0.0.1", 9999));//���� open - �ּҹ� ��Ʈ��ȣ ����
 
            System.out.println("--SERVER Close : input num");
            System.out.println("--SERVER Waiting...");
            
            //���� ��Ĺ ����
            connectThread cnt = new connectThread(MSS);
            cnt.start();//������ ����
 
            int temp = input.nextInt();//���α׷� �������
 
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
