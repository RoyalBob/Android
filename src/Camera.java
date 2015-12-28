public class Camera extends Decorator{

	public Camera(MobilePhone mobilePhone) {
		super(mobilePhone);
		// TODO Auto-generated constructor stub
	}

	public void VideoCall(){
		System.out.println(phoneName+"增加视频电话功能。");
	}
}
