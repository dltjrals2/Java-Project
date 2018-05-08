package gui;

public class Main {	

	public static final int SCREEN_WIDTH = 1280;//public static 모든 프로젝트에서 공유
	public static final int SCREEN_HEIGH = 720;//final 한번바꾸면 안바뀜
	public static void main(String[] args) {
		

		new Dynamic();//인스턴스 개체 생성
	} 
}
//더블버퍼링	->매순간 원하는 컨퍼런트 출력-버퍼에 이미지를 담아서 갱신