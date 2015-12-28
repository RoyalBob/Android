public class iPhone extends MobilePhone{

	public iPhone() {
		// TODO Auto-generated constructor stub
		phoneName="iPhone";
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
