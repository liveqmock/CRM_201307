/**
 * 
 */
package com.hundsun.jres.bizframe.core.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * @author zhengbin
 *
 */
public class CharsetConversion  implements Filter {
	private FilterConfig filterConfig = null;  
	private boolean flag = true;  
	private String charcode = "utf-8";  
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			if (flag) {  
				String c = request.getCharacterEncoding();  
				if (c == null) {  
					request.setCharacterEncoding(charcode);  
				} else if (!c.equals(this.charcode)) {  
					request.setCharacterEncoding(charcode);  
				}  
			}  
			chain.doFilter(request, response);  
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = config;  
		String temp = this.filterConfig.getInitParameter("enable");  
		String str = this.filterConfig.getInitParameter("encoding");  
		if(temp != null){  
			if(temp.equals("true")){  
				this.flag = true;  
			}else if(temp.equals("false")){  
				this.flag = false;  
			}else{  
				this.flag = true;  
			}  
		}  
		if(str != null){  
		  this.charcode = str;  
		}  
	}
	

}
