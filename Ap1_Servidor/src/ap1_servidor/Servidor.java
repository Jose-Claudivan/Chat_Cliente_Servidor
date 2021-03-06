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

package ap1_servidor;

import java.awt.Color;                  //biblioteca responsavel pela atribuiçao de cores
import java.awt.Dimension;              //biblioteca responsavel pela definiçao do tamanho das telas
import java.io.*;                       //biblioteca responsavel pelos metodos de entrad/saida de dados
import java.net.*;                      //biblioteca responsavel pelos metodos do Socket
import java.util.Scanner;               //biblioteca responsavel pela esntrada via teclado
import java.awt.event.ActionEvent;      //biblioteca responsavel pelas açoes dos botoes
import java.awt.event.ActionListener;   //biblioteca responsavel pelas açoes dos botoes
import java.awt.event.KeyEvent;         //biblioteca responsavel pelas açoes das teclas
import java.awt.event.KeyListener;      //biblioteca responsavel pelas açoes dos botoes
import javax.swing.*;                   //biblioteca responsavel pelor toda a parte grafica


public class Servidor extends JFrame implements ActionListener, KeyListener{
//  declaraçao de todas as variaveis utilizadas
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
    private JTextField txtPorta;
    private JTextField txtNome;
    private JLabel icon;
    private BufferedReader bfr;
    private DataOutputStream outToClient;
    private BufferedReader inFromUsuario;
    private String nomeServidor;
    ManagerMessages mm;
    
// metodo responsavel pela parte de conexao do socket
   public void iniciar() throws IOException {
       try {
//          criando um socket que fica escutando a porta 12345		
            ServerSocket ss = new ServerSocket(12345);
            System.out.println("Porta 12345 aberta!");      
//          loop principal da conexao
            while (true) {
//                aguarda algum cliente se conectar. A execução do
                  System.out.print("Esperando alguem se conectar...\n");
//                assim q uma solicitaçao e recebida o obejo faz o aceite 
//                da mesma atraves do metodo accept()
                  conexao = ss.accept();
                  System.out.println(conexao.getInetAddress().getHostAddress() +
                    " esta conectado ao socket.");
//                assim q a conexao é aceita o servidor ja esta pronto para
//                receber dados do cliente conectado
                  this.escutar();                  
            }
	}catch (IOException e) {
//          caso ocorra algum erro na conexao
            System.out.println("IOException: " + e);
         
	}
    }

// construtor com toda a parte visual do programa
   public Servidor(){
//     objeto responsavel por passas a mensagens formatada   
       this.mm = new ManagerMessages();
//     tela com informaçoes sobre o servidor
       JLabel lblMessage = new JLabel("Verificado");
       txtPorta = new JTextField("12345");
       txtNome = new JTextField("Servidor");
       Object[] texts = {lblMessage, txtPorta, txtNome};
       JOptionPane.showMessageDialog(null, texts);
//     todos os componentes da tela do chat     
       pnlContent = new JPanel();
       texto = new JEditorPane();
       texto.setContentType("text/html");
       texto.setPreferredSize(new Dimension(330, 300));
       texto.setEditable(false);
       texto.setBackground(new Color(238, 233, 233));
       txtMsg = new JTextField(23);
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
       setSize(350, 440);
       setVisible(true);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       
   }
  
// metodo responsavel por toda a parte de envio de mensagem
   public void enviarMsg(String msg) throws IOException{
//  define as mensagem para um odjeto de saida
    outToClient = new DataOutputStream(
        conexao.getOutputStream());
//  formata a mensagem a ser enviada (nome;horário;mensagem)
    String text = mm.formatMessage(this.txtNome.getText(),msg);
    outToClient.writeBytes(text+"\r\n");
    texto.setText(mm.insert(text));
//  limpa o campo de digitar mensagem, apos mensagem ser enviada
    txtMsg.setText("");

   }

//  metodo responsavel por receber as mensagens enviadas
    public void escutar() throws IOException{
//      scanner responsavel por ler oq foi enviado 
//      loop q fica escutando enquanto tiver algo a 
//      ser lido
        Scanner scan = new Scanner(conexao.getInputStream());
        while(scan.hasNextLine()){    
            texto.setText(mm.insert(scan.nextLine()));    
                    
        }
    }
    
//  metodo responsavel por encerrar a conexao        
    public void sair() throws IOException{
//      chama o metodo enviarMsg() e passa DESCONECTADO
//      para ser enviado para o cliente, logo em seguida
//      fechar a conexao atraves do close()
        enviarMsg("Desconectado");
        conexao.close();
        System.out.println("Servidor desconectado");
        
    }
    
//  metodo chamar atençao() abre uma tela e exibe uma mensagem pré definida
    public void atencao() throws IOException{
        
        JOptionPane.showMessageDialog(null, "Ei você me deixou no vacuo!\n"
                + " ME RESPONDE INFELIZ!");
            
    }
    
//  metodos responsaveis pelas açoes dos botoes
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
//        se o botao enviar for pressionado
//        envia a mensagem
          if(e.getActionCommand().equals(btnSend.getActionCommand())){
              enviarMsg(txtMsg.getText());
            
          }
//        se o botao sair for pressionado
//        chama o metodo sair()
          else if(e.getActionCommand().equals(btnSair.getActionCommand())){
              sair();
       
          }
//        se o botao atencao for pressionado
//        chama o metodo atencao()          
          else if(e.getActionCommand().equals(btnAtencao.getActionCommand())){
                   atencao();
          }
          
        } catch (Exception e1) {
//          caso ocorra algum erro na chamada das açoes
            e1.printStackTrace();
        }  
    }

    @Override
    public void keyTyped(KeyEvent e) {
//      metodo implementado devio a interface
//      porem nao utilizado no codigo
    }
    
//  metodo responsavel pelas açoes das teclas
    @Override
    public void keyPressed(KeyEvent e) {
//      se a tecla enter for pressionada
//      chama o metodo enviarMsg()
        if(e.getKeyCode()==KeyEvent.VK_ENTER){    
            try{
                enviarMsg(txtMsg.getText());
                
            } catch(Exception e1){
//              caso ocorra algum erro na chamada das açoes
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//      metodo implementado devio a interface
//      porem nao utilizado no codigo
    }
}

