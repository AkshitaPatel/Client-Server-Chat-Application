import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class MyServer 
{

	public static void main(String[] args) 
	{
		try
		{
			ServerSocket ser=new ServerSocket(1234);
			
			System.out.print("\n SERVER WAITITNG....");
			Socket client=ser.accept();
			System.out.print("\n CLIENT CONNECTED....");
			
			InetAddress ip=client.getInetAddress();
			
			
			System.out.print("\n CLIENT :"+ip.getHostAddress());
			System.out.print("\n CLIENT :"+ip.getHostName());
			
			DataInputStream dis=new DataInputStream(client.getInputStream());
			DataOutputStream dos=new DataOutputStream(client.getOutputStream());
			
			Scanner scan=new Scanner(System.in);
			String msg=" ";

			//dos.writeUTF("WELCOME CLIENT");   //unicode transfer format
			while(!msg.equals("QUIT"))
			{
				System.out.print("\n SEND MESSAGE TO CLIENT : ");
				msg=scan.nextLine();
				dos.writeUTF(msg);
				
				msg=dis.readUTF();
				System.out.print("\n MSG FROM CLIENT : "+msg);
				
			}
			
			
			ser.close();
		}
		
		catch(Exception e)
		{
			System.out.print("\n SERVER ERROR: "+e.getMessage());
		}
	}

}
