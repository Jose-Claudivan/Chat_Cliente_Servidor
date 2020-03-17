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
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.lang.Thread;

	
//public class Cliente extends JFrame implements ActionListener, KeyListener {

public class Cliente extends JFrame implements Runnable, ActionListener, KeyListener {
//public class Cliente extends Thread implements ActionListener, KeyListener {
//	Flag que indica quando se deve terminar a execução.
	private static boolean done = false;
        /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
        private JTextArea texto;
        private JTextField txtMsg;
        private JButton btnSend;
        private JButton btnSair;
        private JLabel lblHistorico;
        private JLabel lblMsg;
        private JPanel pnlContent;
        //private Socket socket;
        //private OutputStream ou;
        //private Write ouw;
        //private BufferWriter bfw;
        private JTextField txtIP;
        private JTextField txtPorta;
        private JTextField txtNome;
               
                
       	public static void main(String args[]) {
            
       		try {
//			Para se conectar a algum servidor, basta se criar um
//			objeto da classe Socket. O primeiro parâmetro é o IP ou
//			o endereço da máquina a qual se quer conectar e o
//			segundo parâmetro é a porta da aplicação. Neste caso,
//			utiliza-se o IP da máquina local (127.0.0.1) e a porta
//			da aplicação ServidorDeChat. Nada impede a mudança
//			desses valores, tentando estabelecer uma conexão com
//			outras portas em outras máquinas.

			Socket conexao = new Socket("localhost", 12345);
                        System.out.println("Usuario Conectado ao Servidor");
                        
                        ///////////////////////
                        Scanner tecla = new Scanner(System.in);
                        
//		Basuma vez estabelecida a comunicação, deve-se obter os
//			objetos que permitem controlar o fluxo de comunicação
			PrintStream saida = new PrintStream(conexao.getOutputStream());
                        ///////
                       // System.out.println("Digite alguma mensagem");
			 ///////////////
                         //while(tecla.hasNextLine()){
                           //  saida.println(tecla.nextLine());
                         //}
//			enviar antes de tudo o nome do usuário
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Entre com o seu nome: ");
			String meuNome = teclado.readLine();
			saida.println(meuNome);
                     
                                    
//			Uma vez que tudo está pronto, antes de iniciar o loop
//			principal, executar a thread de recepção de mensagens.
			//Thread t = new Cliente(conexao);
                        Thread t = new Thread();
                        //t = new Cliente(conexao);
			t.start();
                        
//			loop principal: obtendo uma linha digitada no teclado e
//			enviando-a para o servidor.
			String linha;
			while (true) {
                            /////////////////////////////////////
				System.out.print("> " + meuNome +": ");
//                              ler a linha digitada no teclado
				linha = teclado.readLine();
//				antes de enviar, verifica se a conexão não foi fechada
				if (done) {break;}
//				envia para o servidor
				saida.println(linha);
			}
		}
		catch (IOException e) {
//			Caso ocorra alguma excessão de E/S, mostre qual foi.
			System.out.println("IOException: " + e);
		}
	}
//	parte que controla a recepção de mensagens deste cliente
	private Socket conexao;
//	construtor que recebe o socket deste cliente

	public Cliente(Socket s) {
		conexao = s;
                /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
        JLabel lblMessage = new JLabel("Verificar");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        texto = new JTextArea(10,20);
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
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
                
	}
//	execução da thread
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader
			(new InputStreamReader(conexao.getInputStream()));
			String linha;
			while (true) {
//				pega o que o servidor enviou
				linha = entrada.readLine();
//				verifica se é uma linha válida. Pode ser que a conexão
//				foi interrompida. Neste caso, a linha é null. Se isso
//				ocorrer, termina-se a execução saindo com break
				if (linha == null) {
					System.out.println("Conexão encerrada!");
					break;
				}
//				caso a linha não seja nula, deve-se imprimi-la
				System.out.println();
				System.out.println(linha);
				System.out.print("...> ");
			}
			

		}
		catch (IOException e) {
//			caso ocorra alguma exceção de E/S, mostre qual foi.
			System.out.println("IOException: " + e);
		}
//		sinaliza para o main que a conexão encerrou.
		done = true;
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getActionCommand().equals(btnSend.getActionCommand()))
                enviarMensagem(txtMsg.getText());
            else
                if(e.getActionCommand().equals(btnSair.getActionCommand()))
                    sair();
        } catch (IOException e1) {
            //
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}