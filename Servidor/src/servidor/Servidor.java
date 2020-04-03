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
public class Servidor {
        ///////////////02/04/2020//////////////
   /* private static boolean done = false;
    
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
    private JTextField txtNome;*/
    private static JLabel icon;
    
    
   public static void main(String args[]) throws IOException {
		
                /***********************************/
                
                //Servidor app = new Servidor();
               // app.conectar();
               // app.escutar();
                String clientSentence;
                

               // String capitalized;
//		instancia o vetor de clientes conectados
//		clientes = new Vector();
		try {
                    //02-04-2020 -> teste icone
                    ImageIcon imagem = new ImageIcon(Servidor.class.getResource("heart_icon.png"));
                    JOptionPane.showMessageDialog(
                    null,
                    "Olá",
                    "Olá", JOptionPane.INFORMATION_MESSAGE,
                    imagem);
                                      
                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                    JLabel lblMessage = new JLabel("PORTA DO SERVIDOR: ");
                    JTextField txtPorta = new JTextField("12345");
                    Object[] texts = {lblMessage, txtPorta};
                    JOptionPane.showMessageDialog(null, texts);
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
                                    //conexao.getInetAddress().getHostAddress();
				System.out.println(conexao.getInetAddress().getHostAddress()+" Se conectou!");
                                 ////////////
                                Scanner scan = new Scanner(conexao.getInputStream());
                                while(scan.hasNextLine()){
                                    System.out.println(scan.nextLine());
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                   BufferedReader inFromClient = new BufferedReader(
                                        new InputStreamReader(conexao.getInputStream()));
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                    BufferedReader inFromUsuario = new BufferedReader(
                                        new InputStreamReader(System.in));
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                    DataOutputStream outToClient = new DataOutputStream(
                                        conexao.getOutputStream());
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                    BufferedReader inFromCliente = new BufferedReader(
                                        new InputStreamReader(conexao.getInputStream()));
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                    clientSentence = inFromUsuario.readLine();
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                   //imprime mensagem do servidor no cliente
                                    outToClient.writeBytes(clientSentence + "\n");
                                    //outToClient.writeBytes(clientSentence);
                                    System.out.print("Cliente -> ");
                                    
                                                                     
                                    
                                }

//				cria uma nova thread para tratar essa conexão
//                                Thread t = new Servidor(conexao);
				//t.start();
//				voltando ao loop, esperando mais alguém se conectar.
                               
			}
		}
		catch (IOException e) {
//			caso ocorra alguma excessão de E/S, mostre qual foi.
			System.out.println("IOException: " + e);
		}

	}

   
}