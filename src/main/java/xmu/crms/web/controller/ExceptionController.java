package xmu.crms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.TopicNotFoundException;
import xmu.crms.web.VO.ExceptionResponseVO;

@RestControllerAdvice
public class ExceptionController {
	/**
	 * 401 -----------------401----------------
	 * 
	 * @param
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ExceptionResponseVO handle401(UnauthorizedException e) {
		return new ExceptionResponseVO(401, e.getMessage());
	}

	/**
	 * -----------------------400--------------------
	 * 
	 * @param e
	 * @return
	 */

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ShiroException.class)
	public ExceptionResponseVO handle403(ShiroException e) {
		return new ExceptionResponseVO(403, e.getMessage());
	}

	/**
	 * -----------------404---------------------------
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ClassNotFoundException.class)
	public ExceptionResponseVO handle404(ClassNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CourseNotFoundException.class)
	public ExceptionResponseVO handle404(CourseNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(SeminarNotFoundException.class)
	public ExceptionResponseVO handle404(SeminarNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(TopicNotFoundException.class)
	public ExceptionResponseVO handle404(TopicNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GroupNotFoundException.class)
	public ExceptionResponseVO handle404(GroupNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(FixGroupNotFoundException.class)
	public ExceptionResponseVO handle404(FixGroupNotFoundException e) {
		return new ExceptionResponseVO(404, e.getMessage());
	}

	/**
	 * -----------------405----------------------
	 * 
	 * @param e
	 * @return
	 */

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(InvalidOperationException.class)
	public ExceptionResponseVO handle405(InvalidOperationException e) {
		return new ExceptionResponseVO(405, e.getMessage());
	}

	/**
	 * --------------------500----------------------
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NullPointerException.class)
	public ExceptionResponseVO handle500(NullPointerException e) {
		return new ExceptionResponseVO(500, e.getMessage());

	}

	
	// 捕捉其他所有异常
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponseVO globalException(HttpServletRequest request, Throwable ex) {
		return new ExceptionResponseVO(getStatus(request).value(), ex.getMessage());
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

}
