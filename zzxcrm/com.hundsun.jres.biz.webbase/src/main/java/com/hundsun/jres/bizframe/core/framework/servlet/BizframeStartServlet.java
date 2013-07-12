package com.hundsun.jres.bizframe.core.framework.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizframeStartServlet.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BizframeStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private transient BizLog log = LoggerSupport.getBizLogger(BizframeStartServlet.class);
	
	public void init(ServletConfig config) throws ServletException {
		try {
			this.initFramework(config);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			throw new ServletException(" the bizframe'framework  init failure !(基础业务框架初始化失败)");
		}
	}
	
	@Override
	public void destroy() {
		BizframeContext.destroy();
	}
	
	//---------------------初始化基础业务框架------------------
	/**
	 * 第一：加载bizframe-config.xml,生成Iconfig对象放入BizframeContext中(注：只加载一次，放入系统参数配置缓存中)
	 * 第二：从IConfig对象中把基础业务框架的运行的基础配置参数加载到对象中
	 * 第三：初始化登陆服务链和退出服务链
	 * 第四：初始化配置中API服务的实现类，放入BizframeContext中
	 * 第五：初始化基础业务框架的7大缓存（交易、菜单、岗位、部门、数据字典、机构、参数）
	 * 
	 * @throws Exception 
	 * 
	 */
	
	private void initFramework(ServletConfig config) throws Exception{
		FrameworkLoader loader=new FrameworkLoader();
		loader.initContext(config);
		loader.initLoginChain(config);
		loader.initApiService(config);
		loader.initFrameworkCache(config);
		loader.initSystemData(config);
	}

}
