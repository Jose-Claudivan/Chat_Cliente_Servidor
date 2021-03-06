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
package cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.sql.Time;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.*;
import java.lang.Thread;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class Cliente extends JFrame implements ActionListener, KeyListener {
    // public class Cliente{
    // Flag que indica quando se deve terminar a execução.
    private static boolean done = false;
    private static final long serialVersionUID = 1L;
    // private static JTextArea texto;
    private JEditorPane texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JButton btnAtencao;
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
    ManagerMessages mm;

    public void iniciar() {
        try {
            this.conectar();
            this.escutar();

            // Para se conectar a algum servidor, basta se criar um
            // objeto da classe Socket. O primeiro parâmetro é o IP ou
            // o endereço da máquina a qual se quer conectar e o
            // segundo parâmetro é a porta da aplicação. Neste caso,
            // utiliza-se o IP da máquina local (127.0.0.1) e a porta
            // da aplicação ServidorDeChat. Nada impede a mudança
            // desses valores, tentando estabelecer uma conexão com
            // outras portas em outras máquinas.
            Socket conexao = new Socket("localhost", 12345);
            System.out.println("Usuario Conectado ao Servidor");

            // Basuma vez estabelecida a comunicação, deve-se obter os
            // objetos que permitem controlar o fluxo de comunicação
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            // enviar antes de tudo o nome do usuário
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entre com o seu nome: ");
            String meuNome = teclado.readLine();
            // saida.println(meuNome);

            // Uma vez que tudo está pronto, antes de iniciar o loop
            // principal, executar a thread de recepção de mensagens.
            Thread t1 = new Thread(new Cliente(conexao).new RunnableImpl());
            t1.start();
            // Thread t = new Cliente(conexao);
            // t.start();

            // loop principal: obtendo uma linha digitada no teclado e
            // enviando-a para o servidor.
            String linha;
            /*
             * while (true) { ///////////////////////////////////// //System.out.print("> "
             * + meuNome +": "); System.out.print(meuNome + "-> "); // ler a linha digitada
             * no teclado linha = teclado.readLine(); // antes de enviar, verifica se a
             * conexão não foi fechada if (done) {break;} // envia para o servidor
             * saida.println(linha); }
             */
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    // parte que controla a recepção de mensagens deste cliente
    private Socket conexao;
    // construtor que recebe o socket deste cliente

    public Cliente(Socket s) {
        conexao = s;

    }

    // 21/03
    public Cliente(){
        
        this.mm = new ManagerMessages();

        JLabel lblMessage = new JLabel("Verificar");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Cliente");
        Object[] texts = { lblMessage, txtIP, txtPorta, txtNome };
        JOptionPane.showMessageDialog(null, texts);

        pnlContent = new JPanel();
        // texto = new JTextArea(15,30);
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
        // texto.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.add(btnAtencao);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        texto.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.GREEN));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.GREEN));
        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        // setSize(350, 400);
        setSize(350, 440);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    // 22/03////Trata a conexao do cliente com o servidor
    public void conectar() throws IOException {
        // passa as informações necessarias para socket para realixao a conexao
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        // bfw.write(txtNome.getText()+"\r\n");
        bfw.flush();

    }

        private void send(String msg) {
            try {
    
                //formata a mensagem a ser enviada (nome;horário;mensagem)
                String text = mm.formatMessage(this.txtNome.getText(),msg);
                
                //envia a mensagem para o servidor
                bfw.write(text+"\r\n");
    
                //insere a mensagem na tela atual
                texto.setText(mm.insert(text));
    
            } catch (IOException e) {
                e.printStackTrace();
            }
    
    
            }
        //22/03
        public void enviarMensagem(String msg) throws IOException{
             //txtAuxiliar = texto.getText();


            if(msg.equals("Sair")){
                /*bfw.write(txtNome.getText() + " -> Desconectado \r\n");
             //   appendToPane(texto, "Desconectado \r\n", Color.red);
                texto.setText(txtAuxiliar + "Desconectado \r\n");
             // texto.append("Desconectado \r\n");
             txtAuxiliar = texto.getText();
                System.out.println(txtAuxiliar + "2");*/

                this.send("Desconectado");
            }
            else{
                /*bfw.write(txtNome.getText()+" -> " + msg +"\r\n");
             //   appendToPane(texto, txtNome.getText() + " diz -> " + txtMsg.getText() + "\r\n", Color.red);
                texto.setText(txtAuxiliar+txtNome.getText()+" -> " + txtMsg.getText()+"\r\n");
                //texto.append(txtNome.getText()+" diz -> " + txtMsg.getText()+"\r\n");
                txtAuxiliar = texto.getText();
                System.out.println(txtAuxiliar + "3");*/

                this.send(msg);
            }
            bfw.flush();
            txtMsg.setText("");
        }
        
        //22/03
  public void escutar() throws IOException{
           
            InputStream in = socket.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader bfr = new BufferedReader(inr);
            String msg = "";
            
            while(!"Sair".equalsIgnoreCase(msg)){
              
                if(bfr.ready()){
                    msg = bfr.readLine();
                  
                    if(msg.equals("Sair")){
                        this.sair();
                    //     appendToPane(texto, "Servidor caiu! \r\n", Color.red);
                    
                       //texto.setText(txtAuxiliar + "Servidor caiu! \r\n");
                      // System.out.println(txtAuxiliar + "5");
                      // txtAuxiliar = texto.getText();
                     // texto.append("Servidor caiu! \r\n");
                    }
                    //condição para exibir o emoji do sorriso
                  /*  else if (msg.equals(":)")){
                        texto.setContentType("text/html");
                        texto.setText("<html><img src=\"file:C:\\\\Users\\\\Juciana\\\\Documents\\\\NetBeansProjects\\\\Cliente\\\\src\\\\cliente\\\\smile_icon.png\"/></html>");
                    }
                    //condição para exibir o emoji do choro
                    else if (msg.equals(":(")){
                        texto.setContentType("text/html");
                        texto.setText("<html><img src=\"file:C:\\\\Users\\\\Juciana\\\\Documents\\\\NetBeansProjects\\\\Cliente\\\\src\\\\cliente\\\\crying_icon.png\"/></html>");
                    }
                    //condiçao para exibir o emoji do blz
                    else if (msg.equals("(y)")){
                        texto.setContentType("text/html");
                        texto.setText("<html><img src=\"file:C:\\\\Users\\\\Juciana\\\\Documents\\\\NetBeansProjects\\\\Cliente\\\\src\\\\cliente\\\\like_icon.png\"/></html>");
                    }*/
                  
                    
                    else {
                       texto.setText(mm.insert(msg));
                    } 
                }
            }
        }
        
        public void sair() throws IOException{
            enviarMensagem("Sair");
            bfw.close();
            ouw.close();
            ou.close();
            socket.close();
        }
        
        public void atencao() throws IOException{
           // System.out.println("ATENCAO");
            JOptionPane.showMessageDialog(null, "Ei " +  txtNome.getText() + " você me deixou no vacuo!\n ME RESPONDE INFELIZ!");
            
        }


    private class RunnableImpl implements Runnable { 
  
        @Override
        public void run() 
        { 
            try {
                BufferedReader entrada = new BufferedReader
                (new InputStreamReader(conexao.getInputStream()));
                String linha;
                while (true) {
        //          pega o que o servidor enviou
                    linha = entrada.readLine();
        //			verifica se é uma linha válida. Pode ser que a conexão
        //			foi interrompida. Neste caso, a linha é null. Se isso
        //			ocorrer, termina-se a execução saindo com break
                    if (linha == null) {
                    
                      System.out.println("Conexão encerrada!");
                      break;
                    }
                    
                else {
                    System.out.println();
                    System.out.print("Server -> ");
                    System.out.println(linha);
                                        
                
                }
            }
            }
            catch (IOException e) {
        //		caso ocorra alguma exceção de E/S, mostre qual foi.
                 System.out.println("IOException: " + e);
            }
        //	sinaliza para o main que a conexão encerrou.
            done = true;
        } 
    } 
       
//	execução da thread Entrada das mensagens do servidor
        
         
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getActionCommand().equals(btnSend.getActionCommand())){
                enviarMensagem(txtMsg.getText());
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
                enviarMensagem(txtMsg.getText());
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