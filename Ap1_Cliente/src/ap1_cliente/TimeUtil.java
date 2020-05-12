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

package ap1_cliente;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//classe responsavel por pegar o horario do sistema
public class TimeUtil {
    public static String getTime(){
        
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        
        return strResult;
    }
}
