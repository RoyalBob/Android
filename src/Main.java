public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiPhone miPhone=new MiPhone();
		iPhone iphone=new iPhone();
		
		Bluetooth miBluetooth=new Bluetooth(miPhone);
		miBluetooth.Connect();
		GPS miGPS=new GPS(miPhone);
		miGPS.Location="MiPhone�Ķ�λ�ɹ�";
		System.out.println(miGPS.Location);
		Camera miCamera=new Camera(miPhone);
		miCamera.VideoCall();
		
		Bluetooth iBluetooth=new Bluetooth(iphone);
		iBluetooth.Connect();
		GPS iGPS=new GPS(iphone);
		miGPS.Location="iPhone�Ķ�λ�ɹ�";
		System.out.println(miGPS.Location);
		Camera iCamera=new Camera(iphone);
		iCamera.VideoCall();
	}

}
