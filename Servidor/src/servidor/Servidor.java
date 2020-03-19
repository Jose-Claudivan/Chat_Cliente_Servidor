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


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



/**
 *
 * @author Claudivan
 */
public class Servidor extends Thread implements Runnable  {
//public class Servidor {

   public static void main(String args[]) {
		
                /***********************************/
                String clientSentence;
                String capitalized;
//		instancia o vetor de clientes conectados
		clientes = new Vector();
		try {
                    
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
                                    outToClient.writeBytes(clientSentence + "\n");
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                   // capitalized = inFromUsuario.readLine();
                                    /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/
                                   // System.out.println("From Client: " + capitalized);
                                    
                                    
                                    
                                }

//				cria uma nova thread para tratar essa conexão
                                Thread t = new Servidor(conexao);
				t.start();
//				voltando ao loop, esperando mais alguém se conectar.
                               
			}
		}
		catch (IOException e) {
//			caso ocorra alguma excessão de E/S, mostre qual foi.
			System.out.println("IOException: " + e);
		}

	}
//	Parte que controla as conexões por meio de threads.
//	Note que a instanciação está no main.
	private static Vector clientes;
//	socket deste cliente
	private Socket conexao;
//	nome deste cliente
	private String meuNome;
//	construtor que recebe o socket deste cliente
	public Servidor(Socket s) {
            conexao = s;
	}
//	execução da thread
   @Override
	public void run() {
		try {
//			objetos que permitem controlar fluxo de comunicação
			BufferedReader entrada = new BufferedReader(new
					InputStreamReader(conexao.getInputStream()));
			PrintStream saida = new
			PrintStream(conexao.getOutputStream());
			
//			primeiramente, espera-se pelo nome do cliente
			meuNome = entrada.readLine();
			
//			agora, verifica se string recebida é valida, pois
//			sem a conexão foi interrompida, a string é null.
//			Se isso ocorrer, deve-se terminar a execução.
			if (meuNome == null) {return;}
			
//			Uma vez que se tem um cliente conectado e conhecido,
//			coloca-se fluxo de saída para esse cliente no vetor de
//			clientes conectados.
			clientes.add(saida);
			
//			clientes é objeto compartilhado por várias threads!
//			De acordo com o manual da API, os métodos são
//			sincronizados. Portanto, não há problemas de acessos
//			simultâneos.
//			Loop principal: esperando por alguma string do cliente.
//			Quando recebe, envia a todos os conectados até que o
//			cliente envie linha em branco.
//			Verificar se linha é null (conexão interrompida)
//			Se não for nula, pode-se compará-la com métodos string
			String linha = entrada.readLine();
			
			while (linha != null && !(linha.trim().equals(""))) {
//				reenvia a linha para todos os clientes conectados
				sendToAll(saida, " disse: ", linha);

//				espera por uma nova linha.
				linha = entrada.readLine();
			}
//			Uma vez que o cliente enviou linha em branco, retira-se
//			fluxo de saída do vetor de clientes e fecha-se conexão.
			sendToAll(saida, " saiu ", "do chat!");
			clientes.remove(saida);
			conexao.close();
		}
		catch (IOException e) {
//			Caso ocorra alguma excessão de E/S, mostre qual foi.
			System.out.println("IOException: " + e);
		}
	}
//	enviar uma mensagem para todos, menos para o próprio
	public void sendToAll(PrintStream saida, String acao,
			String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
//			obtém o fluxo de saída de um dos clientes
			PrintStream chat = (PrintStream) e.nextElement();
//			envia para todos, menos para o próprio usuário
			if (chat != saida) {chat.println(meuNome + acao + linha);}
		}
	}
}