package cn.edu.nju.software.jzs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Created by emengjzs on 2016/4/28.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Msg handleUnexpectedServerError(ConstraintViolationException ex) {
        return Msg.error(getString(ex.getConstraintViolations()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Msg handleUnexpectedServerError(RuntimeException ex) {
        ex.printStackTrace();
        return Msg.error(ex.getMessage());
    }

    private String getString(Set<ConstraintViolation<?>> vs) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation v : vs) {
            sb.append(v.getMessage());
            sb.append(" ");
        };
        return sb.toString();
    }

}
