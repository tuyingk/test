package com.tz.javasms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.javasms.utils.GetMessageCode;

/**
 * ���ŷ���������
 * @author ���־���
 *
 */

@WebServlet("/sendSMS")
public class SendSms extends HttpServlet {
	private static final long serialVersionUID = -8940196742313994740L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String phone = req.getParameter("phone");
		//����������ֻ����뷢����֤��
		String code = GetMessageCode.getCode(phone);
		resp.getWriter().print(code);
		
		
		
	}
	//��ȡ�����ҳ�洫�ݹ����Ĳ���(�ֻ���)
	/**
	 * 1.�̳�HttpServlet��
	 * 2.��Web.xml����ע��Servlet����(web3.0������ ע��ķ���ʵ��)
	 * 3.ʵ�ֶ��ڷ���
	 */
	

}
