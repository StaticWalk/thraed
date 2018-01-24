package NIOAIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiongxiaoyu on 2018/1/23.
 */
public class MultiThreadNIOEchoServer {
	public static Map<Socket,Long> geym_time_stat=new HashMap<Socket,Long>(10240);
	class EchoClient{
		private LinkedList<ByteBuffer> outq;
		public EchoClient() {
		}
		public LinkedList<ByteBuffer> getOutq() {
			return outq;
		}
		public void enqueue(ByteBuffer bb) {
			outq.addFirst(bb);
		}
	}
	class HandleMsg implements Runnable {
		SelectionKey sk;
		ByteBuffer bb;

		public HandleMsg(SelectionKey sk, ByteBuffer bb) {
			this.sk = sk;
			this.bb = bb;
		}
		@Override
		public void run() {
			EchoClient echoClient = (EchoClient) sk.attachment();
			echoClient.enqueue(bb);
			sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			selector.wakeup();
		}
	}

		private Selector selector;
		private ExecutorService tp= Executors.newCachedThreadPool();

		private void doAccept(SelectionKey sk){
			ServerSocketChannel server= (ServerSocketChannel) sk.channel();
			SocketChannel clientChannel;
			try {
				clientChannel=server.accept();
				clientChannel.configureBlocking(false);
				SelectionKey clientKey=clientChannel.register(selector,SelectionKey.OP_READ);
				EchoClient echoClient=new EchoClient();
				clientKey.attach(echoClient);

				InetAddress clientAddress=clientChannel.socket().getInetAddress();
				System.out.println("Accepted connection from"+clientAddress.getHostAddress());
			} catch (IOException e) {
				System.out.println("Failed to accept new client.");
				e.printStackTrace();
			}
		}
		private void doRead(SelectionKey sk){
			SocketChannel channel= (SocketChannel) sk.channel();
			ByteBuffer bb=ByteBuffer.allocate(8192);
			int len;
			try {
				len=channel.read(bb);
				if (len<0){
					disconnect(sk);
					return;
				}
			} catch (IOException e) {
				System.out.println("Failed to read from client.");
				e.printStackTrace();
				disconnect(sk);
				return;
			}
			bb.flip();
			tp.execute(new HandleMsg(sk,bb));
		}
		public void doWrite(SelectionKey sk){
			SocketChannel channel= (SocketChannel) sk.channel();
			EchoClient echoClient= (EchoClient) sk.attachment();
			LinkedList<ByteBuffer> outq=echoClient.getOutq();
			ByteBuffer bb=outq.getLast();
			try {
				int len=channel.write(bb);
				if (len==-1){
					disconnect(sk);
					return;
				}
				if (bb.remaining()==0){
					//the buffer was completely,written,remove it
					outq.removeLast();
				}
			} catch (IOException e) {
				System.out.println("Failed to write to client.");
				e.printStackTrace();
				disconnect(sk);
			}
			//if there is no more data to be written ,remove interest in
			//OP_WRITE
			if (outq.size()==0){
				sk.interestOps(SelectionKey.OP_READ);
			}
		}
		private void disconnect(SelectionKey sk){

		}


		public  void startServer() throws IOException {
			selector= SelectorProvider.provider().openSelector();

			//create non-blocking server socket
			ServerSocketChannel ssc=ServerSocketChannel.open();
			ssc.configureBlocking(false);

			//Bind the server socket to localhost
			InetSocketAddress isa=new InetSocketAddress(8000);
			ssc.socket().bind(isa);
			//register the socket for select events
			SelectionKey acceptKey=ssc.register(selector,SelectionKey.OP_ACCEPT);
			for (;;){
				selector.select();
			Set readyKeys=selector.selectedKeys();
			Iterator i=readyKeys.iterator();
			long e=0;
			while (i.hasNext()){
				SelectionKey sk= (SelectionKey) i.next();
				i.remove();
				if (sk.isAcceptable()){
					doAccept(sk);
				}
				else if (sk.isValid()&&sk.isReadable()){
					if (!geym_time_stat.containsKey(((SocketChannel)sk.channel()).socket()))
						geym_time_stat.put(((SocketChannel)sk.channel()).socket()
									,System.currentTimeMillis());
					doRead(sk);
				}
				else if (sk.isValid()&&sk.isWritable()){
					doWrite(sk);
					e=System.currentTimeMillis();
					long b=geym_time_stat.remove(((SocketChannel)sk.channel()).socket());
					System.out.println("spend:"+(e-b)+"ms");
				}
			}
		}
	}
	public static void main(String[] args) {
		MultiThreadNIOEchoServer echoServer=new MultiThreadNIOEchoServer();
		try {
			echoServer.startServer();
		} catch (IOException e) {
			System.out.println("Exception caught,peogram exiting..");
			e.printStackTrace();
		}

	}
}
