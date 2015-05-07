package org.zstacks.znet;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.zstacks.znet.nio.Codec;
import org.zstacks.znet.nio.IoBuffer;

public class MessageCodec implements Codec{ 
	
	public IoBuffer encode(Object obj) { 
		if(!(obj instanceof Message)){ 
			throw new RuntimeException("Message unknown"); 
		} 
		
		Message msg = (Message)obj;   
		IoBuffer buf = IoBuffer.allocate(msg.estimatedSize()+256);
		buf.put(msg.getMetaString()+"\r\n");
		Map<String, String> headers = msg.getHead();
		
		int contentLength = 0;
		if(msg.getBody() != null){
			contentLength = msg.getBody().length;
		}
		String lenKey = Message.HEADER_CONTENT_LENGTH; 
		
		Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, String> e = iter.next();
			buf.put(e.getKey() + ": " + e.getValue() + "\r\n");
		} 
		if(!headers.containsKey(lenKey)){
			buf.put(lenKey + ": " + contentLength + "\r\n");
		}
		
		buf.put("\r\n");
		if(msg.getBody() != null){
			buf.put(msg.getBody());
		} 
		buf.flip();
		return buf; 
	}
        
	public Object decode(IoBuffer buf) {  
		int headerIdx = findHeaderEnd(buf);
		if(headerIdx == -1) return null; 
		
		int headerLen = headerIdx+1-buf.position();
		
		buf.mark();
		Message msg = new Message();  
		msg.decodeHeaders(buf.array(), buf.position(), headerLen);
		buf.position(buf.position()+headerLen);
		
		String contentLength = msg.getHeadOrParam(Message.HEADER_CONTENT_LENGTH);
		if(contentLength == null){ //just head 
			return msg;
		}
		
		int bodyLen = Integer.valueOf(contentLength); 
		if(buf.remaining()<bodyLen) {
			buf.reset();
			return null;
		}
		 
		byte[] body = new byte[bodyLen];
		buf.get(body);
		msg.setBody(body); 
		
		return msg;
	} 
	
	private static int findHeaderEnd(IoBuffer buf){
		byte[] data = buf.array();
		int i = buf.position();
		while(i+3<buf.limit()){
			if(data[i]=='\r' && data[i+1]=='\n' && data[i+2]=='\r' && data[i+3]=='\n'){
				return i+3;
			}
			i++;
		}
		return -1;
	}
}