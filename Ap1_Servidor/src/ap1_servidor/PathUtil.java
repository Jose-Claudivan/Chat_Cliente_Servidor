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

import java.nio.file.Paths;

public class PathUtil {

    public static String getImagePath(String fileName) {
        
        String path ="";

        path = Paths.get("images/"+fileName).toAbsolutePath().toString();
        path="file:"+path;
        System.out.print(path);
       
        return path;
    }
}
