package logic;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
//import javax.validation.constraints.AssertTrue.List;





import message.Message;
import message.MyMessage;
import data.NotifyDao;
import debug.Debug;
import entity.Assignment;
import entity.User;

@Stateless
@LocalBean
public class NotifyService {
	@EJB
	NotifyDao notify;
	
	
	
	public Message getNotifyMessage(User user) {
		Debug.print("Noyify: " + user.getClass().getName());
		Message m = new MyMessage();
		StringBuffer info = new StringBuffer();
		if(user instanceof entity.Student) {
			List<Assignment> list = this.notify.getUnSubmitAssignment(((entity.Student) user).getSid());
			
			if(list.isEmpty())
				info.append("δ��������ҵ��ȫ���ύ");
			else{
				info.append("����" + list.size() + "����ҵδ�ύ");
			}
			
			if(((entity.Student) user).isTuitor()) {
				list = this.notify.getUnReviewAssignment(((entity.Student) user).getSid());
				if(list.isEmpty())
					info.append(" / ���̵Ŀγ���δ��������ҵ��ȫ������");
				else{
					info.append(" / ���̵Ŀγ��л���" + list.size() + "����ҵδ�������");
				}
			}
			
		}
		else if(user instanceof entity.Teacher) {
			List list = this.notify.getUncheckAssignment(((entity.Teacher) user).getTid());
			if(list.isEmpty()) {
				info.append("��û����Ҫ�����ɼ�����ҵ");
			}
			else {
				info.append("��" + list.size() + "����ҵ�ĳɼ���Ҫ����");
			}
		}
		else {
			info.append("��ӭʹ�ã�");
		}
		m.setInfo(info.toString());
		return m;
	}
	
}
