package authority;


public class ServiceAuthorityList {
	private ServiceAuthority[] list;
	
	public ServiceAuthorityList() {
		list = new ServiceAuthority[ServiceAuthority.values().length];
	}
	
	
	public void enable(ServiceAuthority a[]) {
		for(ServiceAuthority i : a) {
			enable(i);
		}
	}
	
	public void enable(ServiceAuthority a) {
		a.setEnable(true);
		addAuthority(a);
	}
	
	public void disable(ServiceAuthority a) {
		a.setEnable(false);
		addAuthority(a);
	}
	
	private void addAuthority(ServiceAuthority a) {
		if(a != null)
			this.list[a.ordinal()] = a;
	}
	
	public boolean isAble(ServiceAuthority e) {
		if( e == null)
			return false;
		ServiceAuthority t = list[e.ordinal()];
		if(t == null)
			return false;
		return t.isAble();
	}
	
}
