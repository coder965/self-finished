package org.zstacks.znet.usecase;


import java.io.IOException;

import org.zstacks.znet.ClientDispatcherManager;
import org.zstacks.znet.Message;
import org.zstacks.znet.RemotingClient;
import org.zstacks.znet.ticket.ResultCallback;

public class AsyncClient {

	public static void main(String[] args) throws Exception { 
		final ClientDispatcherManager manager = new ClientDispatcherManager();

		final RemotingClient client = new RemotingClient("127.0.0.1:8080", manager);
	
		Message msg = new Message();
		msg.setCommand("hello");
		msg.setBody("hello");
		//异步请求
		client.invokeAsync(msg, new ResultCallback() {
			
			@Override
			public void onCompleted(Message result) {
				System.out.println(result);
				client.close();
				try {
					manager.close();
				} catch (IOException e) {
					//ignore
				} 
			}
		});
	}

}
