import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class MyClient 
{

	public static void main(String[] args) 
	{
		try
		{							//127.0.0.1
			Socket server=new Socket("1ocalhost",1234);  //object is of server //vandana ip address
			
			
			InetAddress ip=server.getInetAddress();
			
			System.out.print("\n SERVER :"+ip.getHostAddress());
			System.out.print("\n SERVER :"+ip.getHostName());
			
			DataInputStream dis=new DataInputStream(server.getInputStream());
			DataOutputStream dos=new DataOutputStream(server.getOutputStream());
			
			Scanner scan=new Scanner(System.in);
			String msg=" ";

			while(!msg.equals("QUIT"))
			{
				msg=dis.readUTF();
				System.out.print("\n MSG FROM SERVER : "+msg);
				
				System.out.print("\n SEND MESSAGE TO SERVER : ");
				msg=scan.nextLine();
				dos.writeUTF(msg);
				
				
				
			}
			
			//String msg=dis.readUTF();
			//System.out.print("\n FROM SERVER : "+msg);
			
			server.close();
			
		}
		
		catch(Exception e)
		{
			System.out.print("\n CLIENT ERROR: "+e.getMessage());
		}

	}

}
