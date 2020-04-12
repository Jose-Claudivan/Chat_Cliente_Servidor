////////////////////////////////////////////////////////////////////////////////
///                                                                          ///
/// UNIFAVIP WYDEN                                                           ///
/// Professor: Jadson                                                        ///
/// Disciplina: Programação de Serviços de Rede                              ///
/// Curso: Ciência da Computação                                             ///
/// Aluno: José Claudivan da Silva                                           ///
/// Matricula: 181096479                                                     ///
/// Projeto: Chat                                                            ///
///                                                                          ///
////////////////////////////////////////////////////////////////////////////////
package servidor;


import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.lang.Thread;



/**
 *
 * @author Claudivan
 */
//public class Servidor extends Thread implements Runnable  {
//public class Servidor extends JFrame implements ActionListener, KeyListener{
public class Servidor {
        ///////////////07/04/2020//////////////
    private static boolean done = false;
    
    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JLabel lblHistorico;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;
    private static JLabel icon;
    private BufferedReader bfr;
    
    
   public static void main(String args[]) throws IOException {
		                          
                String clientSentence;
      
	  	try {
     //			criando um socket que fica escutando a porta 9090		
                        ServerSocket s = new ServerSocket(12345);
                        System.out.println("Porta 12345 aberta!");

			
//			Loop principal.
			while (true) {
//				aguarda algum cliente se conectar. A execução do
//				servidor fica bloqueada na chamada do método accept da
//				classe ServerSocket. Quando algum cliente se conectar
//				ao servidor, o método desbloqueia e retorna com um
//				objeto da classe Socket, que é porta da comunicação.
				System.out.print("Esperando alguem se conectar...\n");
				Socket conexao = s.accept();
                                
             			//System.out.println(conexao.getInetAddress().getHostAddress()+" Se conectou!");
                                    Servidor app = new Servidor();
                                    app.enviarMsg(conexao);
                                  //  app.conectar();
                                   //  app.escutar();
                                
                                 //EMOJI/////                                                                                              if (clientSentence.equals("<3")){
                                   /* ImageIcon imagem = new ImageIcon(Servidor.class.getResource("heart_icon.png"));
                                    JOptionPane.showMessageDialog(
                                    null,
                                   "Love",
                                   "Love", JOptionPane.INFORMATION_MESSAGE,
                                    imagem);
                                        
                                    }*/
                                   
                                }
                               
		  	}
		//}
	  	catch (IOException e) {
//			caso ocorra alguma excessão de E/S, mostre qual foi.
		  	System.out.println("IOException: " + e);
		  }

	  }
   
 //  public Socket conexao;
   public String clientSentence;

   public void enviarMsg(Socket conexao) throws IOException{
      
       Scanner scan = new Scanner(conexao.getInputStream());
        while(scan.hasNextLine()){
              System.out.println(scan.nextLine());
       //Ler a mensagem do cliente para o servidor
          BufferedReader inFromUsuario = new BufferedReader(
             new InputStreamReader(System.in));
     
      //define as mensagem para um odjeto de saida
         DataOutputStream outToClient = new DataOutputStream(
             conexao.getOutputStream());
         clientSentence = inFromUsuario.readLine();
         //imprime mensagem do cliente no servidor
         outToClient.writeBytes(clientSentence + "\n");
              System.out.print("Cliente -> ");
             
       }
    
   }

        //22/03
     /*   public void conectar() throws IOException{
            //System.out.println(conexao.getInetAddress().getHostAddress()+" Se conectou!");
            
            socket = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);
            bfw.write(txtNome.getText()+"\r\n");
            bfw.flush();
        }*/
   
   ////07-04-2020/////////
  /* public Servidor(){
       JLabel lblMessage = new JLabel("Verificado");
            txtIP = new JTextField("127.0.0.1");
            txtPorta = new JTextField("12345");
            txtNome = new JTextField("Servidor");
            Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
            JOptionPane.showMessageDialog(null, texts);
   
        pnlContent = new JPanel();
        texto = new JTextArea(15,30);
        texto.setEditable(false);
        texto.setBackground(new Color(240,240,240));
        txtMsg = new JTextField(20);
        lblHistorico = new JLabel("Histórico");
        lblMsg = new JLabel("Mensagem");
        btnSend = new JButton("Enviar");
        btnSend.setToolTipText("Enviar mensagem");
        btnSair = new JButton("Sair");
        btnSair.setToolTipText("Sair do Chat");
        btnSend.addActionListener(this);
        btnSair.addActionListener(this);
        btnSend.addKeyListener(this);
        txtMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(texto);
        texto.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(350, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
                
            
        }
         
        //22/03
        public void escutar() throws IOException{
            
            InputStream in = socket.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader bfr = new BufferedReader(inr);
            String msg = "";
            
            while(!"Sair".equalsIgnoreCase(msg))
                
                if(bfr.ready()){
                    msg = bfr.readLine();
                    if(msg.equals("Sair"))
                        texto.append("Servidor caiu! \r\n");
                    else
                        texto.append(msg+"\r\n");
                }
        }
        
        public void sair() throws IOException{
        //    enviarMensagem("Sair");
            bfw.close();
            ouw.close();
            ou.close();
            socket.close();
        }

   
   
   
       
   
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getActionCommand().equals(btnSend.getActionCommand()))
            //   enviarMensagem(txtMsg.getText());
           // else
                if(e.getActionCommand().equals(btnSair.getActionCommand()))
                    sair();
        } catch (Exception e1) {
            //Todo auto-generated catch block
            e1.printStackTrace();
        }  
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //todo auto-generated method block
    }

    @Override
    public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
            try{
           //     enviarMensagem(txtMsg.getText());
            } catch(Exception e1){
                //Todo auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    
    @Override
    public void keyReleased(KeyEvent e) {
        //todo auto-generated method block
    }*/

}
