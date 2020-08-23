import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



class ChatFrame extends JFrame implements ActionListener, KeyListener, Runnable
{
	Container cont;
	JPanel top,bot;
	JButton jb1;
	JTextArea ta;
	JTextField tf;
	JLabel jl;
	Font f;
	Socket client;
	DataInputStream dis=null;
	DataOutputStream dos=null;
	JScrollPane jsp;
	Thread th;
	
	ChatFrame(String s,Socket client)
	{
		super(s);
		th=new Thread(this);
		cont=getContentPane();
		cont.setLayout(null);
		this.client=client;
		try
		{
		dis=new DataInputStream(client.getInputStream());
		dos=new DataOutputStream(client.getOutputStream());
		}
		catch(Exception e)
		{
			System.out.println("Server const : "+e.getMessage());
		}
		f=new Font("Arial",Font.BOLD,100);
		setFont(f);
		
		top=new JPanel();
		bot=new JPanel();
		
		
		
		top.setBounds(0,0,400,500);
		bot.setBounds(0,500,400,100);
		
		top.setLayout(null);
		ta=new JTextArea();
		jsp=new JScrollPane(ta);
		jsp.setBounds(10,30,365,460);
		jl=new JLabel("CHAT");
		jl.setBounds(10,10,100,15);
		ta.addKeyListener(this);
		

		bot.setLayout(null);
		tf=new JTextField();
		tf.setBounds(10,10,310,40);
		tf.addKeyListener(this);
		
		jb1=new JButton(new ImageIcon("src//Images//send.png"));
		jb1.setActionCommand("SEND");
		jb1.addActionListener(this);

		jb1.setBounds(330,10,40,40);
		
		
		
		top.setBackground(Color.cyan);
		bot.setBackground(Color.yellow);
		
		top.add(jsp); top.add(jl);
		bot.add(jb1);		
		bot.add(tf);
		cont.add(top); cont.add(bot);
		th.start();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		

	}
	public void run()
	{
		String msg="";
		while(!msg.equals("QUIT"))
		{
			try
			{
				msg=dis.readUTF();
				ta.append("CLIENT :"+msg+"\n");
			}
			catch(Exception e){
				System.out.println("");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) 
	{
		
	}
		
	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent ke) 
	{
		char ch=ke.getKeyChar();
		if(ch=='\n')
		{
			String txt=tf.getText();
			ta.append("ME: "+txt+"\n");
			try
			{
				dos.writeUTF(txt);
			}
			catch(Exception e)
			{
				System.out.println(" SERVER SEND : "+e.getMessage());
			}
			tf.setText("");
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String s=ae.getActionCommand();
		if(s.equals("SEND"))
		{
			String txt=tf.getText();
			ta.append(txt+"\n");
			try
			{
				dos.writeUTF(txt);
			}
			catch(Exception e)
			{
				System.out.println(" SERVER SEND : "+e.getMessage());
			}
			tf.setText("");
		}
		
	}
	
}
public class ChatServer 
{

	public static void main(String[] args) 
	{
		try
		{
			ServerSocket ser=new ServerSocket(6789);
			Socket client=ser.accept();
			
		ChatFrame cf = new ChatFrame("SERVER ZONE",client);
		cf.setVisible(true);
		cf.setSize(400,600);
		cf.setLocation(50,50);
		}
		
		catch(Exception e)
		{
			System.out.println("Server Error : "+e.getMessage());
		}

	}

}
