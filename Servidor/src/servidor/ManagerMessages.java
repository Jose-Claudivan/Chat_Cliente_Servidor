package servidor;

import java.io.File;

import javax.swing.JOptionPane;

public class ManagerMessages {

    private String text="";

    public String insert(String msg){

       String body="";
        
       String[] str =  msg.split(";",3);

       String title = "<span>"+str[0]+" ("+str[1]+"):"+"</span><br>";

       //condição para exibir o emoji do coraçao
       if(str[2].equals("<3")){

        String pathSeparator = File.separator;
        String path = "file:images"+pathSeparator+"heart_icon.png";
        
        body = "<img src="+path+"/><br>";   
    }else{
        body = "<span>"+str[2]+"</span><br>";
    }


       String message = title+body;
       this.text = this.text+message;

       return this.text;
    }

    public String getMessages(){
        return this.text;
    }

    public String formatMessage(String name, String msg) {

        String text = name+ ";" + TimeUtil.getTime() + ";" + msg;

        return text;

    }

}