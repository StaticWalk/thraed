package NIOAIO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
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


	//文件映射到内存
	public void fileTomemory(String resource) throws IOException {
		RandomAccessFile raf=new RandomAccessFile(resource,"rw");
		FileChannel fc=raf.getChannel();
		MappedByteBuffer mbb=
				fc.map(FileChannel.MapMode.READ_WRITE,0,raf.length()-1);
		while (mbb.hasRemaining()){
			System.out.println((char)mbb.get());
		}
		mbb.put(0,(byte)98);
		raf.close();
	}


	public static void main(String[] args) {

	}
}
