package com.hundsun.jres.bizframe.core.framework.servlet;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageServlet extends HttpServlet {

	// 图片的宽度。
	private int width = 60;
	// 图片的高度。
	private int height = 20;
	// 验证码字符个数
	private int codeCount = 4;
	// 验证码干扰线数
	private int lineCount = 10;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		ValidateCode validateCode=new ValidateCode(width,height,codeCount,lineCount);
		HttpSession session=request.getSession();
		session.setAttribute("randCode", validateCode.getCode().toLowerCase());
		ImageIO.write(validateCode.getBuffImg(), "JPEG", response.getOutputStream());
		return;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		
		String imageWidth=this.getInitParameter("imageWidth");
		this.width=Integer.parseInt((null==imageWidth||"".equals(imageWidth))?"60":imageWidth);
		
		String imageHeight=this.getInitParameter("imageHeight");
		this.height=Integer.parseInt((null==imageHeight||"".equals(imageHeight))?"20":imageHeight);
		
		String imageCodeCount=this.getInitParameter("codeCount");
		this.codeCount=Integer.parseInt((null==imageCodeCount||"".equals(imageCodeCount))?"4":imageCodeCount);
		
		String imageLineCount=this.getInitParameter("lineCount");
		this.lineCount=Integer.parseInt((null==imageLineCount||"".equals(imageLineCount))?"10":imageLineCount);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

}
