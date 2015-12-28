public class MiPhone extends MobilePhone{

	public MiPhone() {
		// TODO Auto-generated constructor stub
		phoneName="MiPhone";
	}
	@Override
	public void SendMessage() {
		// TODO Auto-generated method stub
		System.out.println( phoneName+"'s SendMessage." );
	}

	@Override
	public void Call() {
		// TODO Auto-generated method stub
		System.out.println( phoneName+"'s Call.");
	}

}
