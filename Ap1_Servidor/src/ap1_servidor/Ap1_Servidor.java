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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ap1_Servidor {
//  metodo principal que faz a chamada do metodo iniciar()
//  atraves do objeto servidor
    public static void main(String[] args) {
                Servidor servidor = new Servidor();
        try {
            servidor.iniciar();
        } catch (IOException ex) {
            Logger.getLogger(Ap1_Servidor.class.getName()).log(Level.SEVERE, null, ex);
          }
       
    
    }    
}

