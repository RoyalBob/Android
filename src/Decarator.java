public class Decarator extends MobilePhone{

	private MobilePhone _mobilePhone;
	
	public Decarator(MobilePhone mobilePhone){
		_mobilePhone=mobilePhone;
		phoneName=mobilePhone.phoneName;
	}
	@Override
	public void SendMessage() {
		// TODO Auto-generated method stub
		_mobilePhone.SendMessage();
	}

	@Override
	public void Call() {
		// TODO Auto-generated method stub
		_mobilePhone.Call();
	}

}
