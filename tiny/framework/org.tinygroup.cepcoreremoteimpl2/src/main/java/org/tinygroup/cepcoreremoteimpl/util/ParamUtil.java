package org.tinygroup.cepcoreremoteimpl.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.xmlparser.node.XmlNode;

public class ParamUtil {
	private static final String PARAM_TAG = "param";
	private static Map<String, String> paramsMap = new HashMap<String, String>();
	// 请求超时的参数key
	private static final String REQUEST_TIME_OUT_KEY = "request-time-out";
	// 请求超时时间
	private static long requestTimeOut = 0;
	// 默认请求超时时间
	private static long DEFAUT_REQUEST_TIME_OUT = 5000;
	// 单服务端最大连接数Key
	private static final String MAX_CLIENT_KEY = "max-client";
	// 单服务端最大连接数
	private static int maxClient = 0;
	// 单服务端最大连接数
	private static int DEFAULT_MAX_CLIENT = 5;

	private static final String SC_TAG = "sc";
	private static final String SC_HOST_KEY = "host";
	private static final String SC_PORT_KEY = "port";

	public static long getMaxClient() {
		if (maxClient == 0) {
			if (paramsMap.containsKey(MAX_CLIENT_KEY)) {
				String value = paramsMap.get(MAX_CLIENT_KEY);
				maxClient = Integer.parseInt(value);
			} else {
				maxClient = DEFAULT_MAX_CLIENT;
			}
		}

		return maxClient;
	}

	public static long getRequestTimeOut() {
		if (requestTimeOut == 0) {
			if (paramsMap.containsKey(REQUEST_TIME_OUT_KEY)) {
				String time = paramsMap.get(REQUEST_TIME_OUT_KEY);
				requestTimeOut = Long.parseLong(time);
			} else {
				requestTimeOut = DEFAUT_REQUEST_TIME_OUT;
			}
		}

		return requestTimeOut;
	}

	public static void parseParam(XmlNode paramNode) {
		List<XmlNode> params = paramNode.getSubNodesRecursively(PARAM_TAG);
		if(params==null||params.size()==0){
			return;
		}
		for (XmlNode param : params) {
			paramsMap.put(param.getAttribute("name"),
					param.getAttribute("value"));
		}
	}

	public static void parseScs(XmlNode node) {
		List<XmlNode> scs = node.getSubNodesRecursively(SC_TAG);
		for (XmlNode sc : scs) {
			String host = sc.getAttribute(SC_HOST_KEY);
			String port = sc.getAttribute(SC_PORT_KEY);
			RemoteCepCoreUtil.regScAddress(Integer.parseInt(port), host);
		}
	}
}
