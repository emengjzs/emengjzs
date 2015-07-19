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
				info.append("未结束的作业已全部提交");
			else{
				info.append("还有" + list.size() + "次作业未提交");
			}
			
			if(((entity.Student) user).isTuitor()) {
				list = this.notify.getUnReviewAssignment(((entity.Student) user).getSid());
				if(list.isEmpty())
					info.append(" / 助教的课程中未结束的作业已全部批改");
				else{
					info.append(" / 助教的课程中还有" + list.size() + "次作业未批改完毕");
				}
			}
			
		}
		else if(user instanceof entity.Teacher) {
			List list = this.notify.getUncheckAssignment(((entity.Teacher) user).getTid());
			if(list.isEmpty()) {
				info.append("暂没有需要审批成绩的作业");
			}
			else {
				info.append("有" + list.size() + "次作业的成绩需要审批");
			}
		}
		else {
			info.append("欢迎使用！");
		}
		m.setInfo(info.toString());
		return m;
	}
	
}
