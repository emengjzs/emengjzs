package service;

import javax.ejb.Local;

import logic.AccountIdentifyResult;
import message.Message;
import entity.Student;
import entity.User;

@Local
public interface AccountServiceRemote {
	public AccountIdentifyResult login(User input);
	public String SayHello(User input);
	public Message registerStudent(Student input);
}
