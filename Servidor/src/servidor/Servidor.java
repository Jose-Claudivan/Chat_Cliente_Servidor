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
public class Servidor extends JFrame implements ActionListener, KeyListener{
//public class Servidor {
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
    private Socket conexao;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;
    private JLabel icon;
    private BufferedReader bfr;
    private DataOutputStream outToClient;
    private BufferedReader inFromUsuario;
    
    
    
   public void inicio() throws IOException {
		                          
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
				conexao = s.accept();
                                this.escutar();
                                
             			//System.out.println(conexao.getInetAddress().getHostAddress()+" Se conectou!");
                                    
                                   // this.enviarMsg();
                                  //  app.conectar();
                                   //  app.escutar();
                                
                                 //EMOJI/////        if (clientSentence.equals("<3")){
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

   public void enviarMsg(String msg) throws IOException{
      
       //Scanner scan = new Scanner(conexao.getInputStream());
       // while(scan.hasNextLine()){
             // System.out.println(scan.nextLine());
       //Ler a mensagem do cliente para o servidor
       //    inFromUsuario = new BufferedReader(
           //  new InputStreamReader(System.in));
     
      //define as mensagem para um odjeto de saida
          outToClient = new DataOutputStream(
             conexao.getOutputStream());
         
       //  clientSentence = inFromUsuario.readLine();
         //imprime mensagem do cliente no servidor
         outToClient.writeBytes(txtNome.getText()+" -> " + msg + "\n");
         
         texto.append(txtNome.getText()+" -> " + msg +"\r\n");
         
         txtMsg.setText("");
      
      // }
    
   }

   
   ////07-04-2020/////////
   public Servidor(){
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
            Scanner scan = new Scanner(conexao.getInputStream());
                while(scan.hasNextLine()){
                    texto.append(scan.nextLine()+"\n");
                    }
        }
        
        public void sair() throws IOException{
             /*if(outToClient != null){
                 outToClient.close();
             }
            if(inFromUsuario != null){
                inFromUsuario.close();
            }*/
            enviarMsg("Desconectado");
            conexao.close();
            
            System.out.println("Servidor desconectado");
        }

   
   
   
       
   
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
          if(e.getActionCommand().equals(btnSend.getActionCommand())){
              enviarMsg(txtMsg.getText());
            
          }
                
            
            else
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
                enviarMsg(txtMsg.getText());
                
                  } catch(Exception e1){
                //Todo auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    
    @Override
    public void keyReleased(KeyEvent e) {
        //todo auto-generated method block
    }

}
