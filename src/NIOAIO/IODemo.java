package NIOAIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xiongxiaoyu on 2018/1/23.
 */
public class IODemo {

	public static void nioCopyFile(String resource,String destination) throws IOException {
		FileInputStream fis=new FileInputStream(resource);
		FileOutputStream fos=new FileOutputStream(destination);
		FileChannel readChannel=fis.getChannel();
		FileChannel writeChannel=fos.getChannel();
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		while (true){
			buffer.clear();
			int len=readChannel.read(buffer);
			if (len==-1){
				break;
				//读取完毕
			}
			buffer.flip();
			writeChannel.write(buffer);
			//写入文件
		}
		readChannel.close();
		writeChannel.close();
	}


	public static void main(String[] args) {

	}
}
