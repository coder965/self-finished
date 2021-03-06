package com.transilink.znet;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.transilink.znet.Message;
import com.transilink.znet.MessageHandler;
import com.transilink.znet.nio.Session;

public class MyRemotingServer2 extends Rs {

	// 个性化定义Message中的那部分解释为命令，这里为了支持浏览器直接访问，把Message中的path理解为command
	@Override
	public String findHandlerKey(Message msg) {
		String cmd = msg.getCommand();
		if (cmd == null) {
			cmd = msg.getPath();
		}
		return cmd;
	}

	public MyRemotingServer2(int serverPort) throws IOException {
		super(serverPort);

	}

	@PostConstruct
	public void testxxx() {
		// 注册命令处理Callback
		System.err.println("URL:" + this.getRoute().toString());
		this.registerHandler(this.getRoute().toString(), new MessageHandler() {
			public void handleMessage(Message msg, Session sess)
					throws IOException {
				System.out.println("msg:\n\r" + msg);
				System.out.println("msg.name:" + msg.getParam("name"));
				sess.attr("sex", "男");
				System.err.println("sess:\n\r" + sess);
				msg.setStatus("200");
				msg.setBody("hello world");
				sess.write(msg);
			}
		});
	}
	//
	// public static void main(String[] args) throws Exception {
	// @SuppressWarnings("resource")
	// MyRemotingServer2 server = new MyRemotingServer2(80);
	// server.start();
	// }
}
