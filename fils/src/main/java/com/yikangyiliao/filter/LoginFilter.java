package com.yikangyiliao.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.yikangyiliao.base.utils.AccessTiketCheckout;
import com.yikangyiliao.base.utils.NetworkUtil;

public class LoginFilter implements Filter {
	private Logger log = Logger.getLogger(LoginFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) arg0;
		String requestURI = hsr.getRequestURI();
		String contextPath = hsr.getContextPath();

		if ((!contextPath.equals("/")) && (requestURI.indexOf(contextPath) >= 0)) {
			requestURI = requestURI.substring(contextPath.length());
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		Map param = new HashMap();
		try {
			List<FileItem> items = upload.parseRequest((HttpServletRequest) arg0);

			for (FileItem fileItem:items) {
				if (fileItem.isFormField())
					param.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}

		String appId = param.get("appId").toString();
		String accessTicket = param.get("accessTicket").toString();
		String hostIp = NetworkUtil.getIpAddress(hsr);
		this.log.debug("登陆ip-->" + hostIp);

		if (((null != appId) && (appId.length() > 1) && (null != accessTicket) && (accessTicket.length() >= 0))
				|| (requestURI.equals("/service/login")) || (requestURI.equals("/service/registAndSaveServiceInfo"))
				|| (requestURI.equals("/fileUpload/imageFileUpload"))) {
			try {
				if ((requestURI.equals("/service/login")) || (requestURI.equals("/fileUpload/imageFileUpload")))
					arg2.doFilter(arg0, arg1);
				else if ((null == accessTicket)
						|| (!AccessTiketCheckout.checkAccessTiketLayout(accessTicket, hsr).booleanValue())) {
					arg2.doFilter(arg0, arg1);
				}
			} catch (Exception e) {
				this.log.error("登陆ip-->" + hostIp);
				e.printStackTrace();
			}
		} else if(requestURI.equals("/fileUpload/doFileUpload")){
			arg2.doFilter(arg0, arg1);
		}else{
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=UTF-8");
			arg1.getWriter().println("{'status':'999999','message':'数据校验失败!'}");
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 *                                         --陈苗辉
*/

//  ┏┓　   ┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//   ┃　　　┃ 神兽保佑　　　　　　　　
//   ┃　　　┃ 代码无BUG！
//   ┃　　　┗━━━┓
//   ┃　　　　　　　┣┓
//   ┃　　　　　　　┏┛
//   ┗┓┓┏━┳┓┏┛
//     ┃┫┫　┃┫┫
//     ┗┻┛　┗┻┛
}