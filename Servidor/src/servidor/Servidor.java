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
import java.awt.Dimension;
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
    private JEditorPane texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JButton btnAtencao;
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
    private String nomeServidor;
    ManagerMessages mm;
    
    
    
   public void iniciar() throws IOException {
		                          
                String clientSentence;
      
	  	try {
     //			criando um socket que fica escutando a porta 9090		
                        ServerSocket s = new ServerSocket(12345);
                        System.out.println("Porta 12345 aberta!");
                        			
//			Loop principal.
			while (true) {
                            
//                          aguarda algum cliente se conectar. A execução do
//                          servidor fica bloqueada na chamada do método accept da
//                          classe ServerSocket. Quando algum cliente se conectar
//                          ao servidor, o método desbloqueia e retorna com um
//                          objeto da classe Socket, que é porta da comunicação.
                            System.out.print("Esperando alguem se conectar...\n");
                            conexao = s.accept();
                                
                            this.escutar();

                                   
                        }
                               
		}
	
	  	catch (IOException e) {
//                  caso ocorra alguma excessão de E/S, mostre qual foi.
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
         //imprime mensagem do servidor no cliente
         //formata a mensagem a ser enviada (nome;horário;mensagem)
         String text = mm.formatMessage(this.txtNome.getText(),msg);
        // outToClient.writeBytes(txtNome.getText());
        // outToClient.writeBytes(" -> ");
         outToClient.writeBytes(text);
         texto.setText(mm.insert(text));
         //outToClient.writeBytes("\n");
        // outToClient.writeBytes(txtNome.getText()+" -> " + msg + "\n");
         
         //texto.append(txtNome.getText()+" -> " + msg +"\r\n");
         //texto.append(txtNome.getText());
         //texto.append(" -> " + msg + "\r\n");
         //limpa o campo de digitar mensagem, apos mensagem ser enviada
         txtMsg.setText("");
         System.out.println(txtNome.getText());
      // }
    
   }

   
   ////07-04-2020/////////
   public Servidor(){
        this.mm = new ManagerMessages();

       JLabel lblMessage = new JLabel("Verificado");
            txtIP = new JTextField("127.0.0.1");
            txtPorta = new JTextField("12345");
            txtNome = new JTextField("Servidor");
            Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
            JOptionPane.showMessageDialog(null, texts);
     
        pnlContent = new JPanel();

        texto = new JEditorPane();
        texto.setContentType("text/html");
        
        texto.setPreferredSize(new Dimension(330, 250));
       // texto = new JTextArea(15,30);
        texto.setEditable(false);
        texto.setBackground(new Color(240,240,240));
        
        

        txtMsg = new JTextField(20);
        lblHistorico = new JLabel("Histórico");
        lblMsg = new JLabel("Mensagem");
        btnSend = new JButton("Enviar");
        btnSend.setToolTipText("Enviar mensagem");
        btnSair = new JButton("Sair");
        btnSair.setToolTipText("Sair do Chat");
        btnAtencao = new JButton("Psiu!");
        btnAtencao.setToolTipText("Chamar atenção");
        btnSend.addActionListener(this);
        btnSair.addActionListener(this);
        btnAtencao.addActionListener(this);
        btnSend.addKeyListener(this);
        txtMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(texto);
        //texto.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.add(btnAtencao);
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
                    texto.setText(mm.insert(scan.nextLine()));
                    //texto.append(scan.nextLine()+"\n");
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

        public void atencao() throws IOException{
        
         JOptionPane.showMessageDialog(null, "Ei " +  txtNome.getText() + " você me deixou no vacuo!\n ME RESPONDE INFELIZ!");
            
        }
   
   
       
   
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
          if(e.getActionCommand().equals(btnSend.getActionCommand())){
              enviarMsg(txtMsg.getText());
            
          }
                
            
          else
               if(e.getActionCommand().equals(btnSair.getActionCommand())){
              
                    sair();
          }
          
          else if(e.getActionCommand().equals(btnAtencao.getActionCommand())){
                   atencao();
          }
          
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
