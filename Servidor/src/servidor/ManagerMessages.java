package servidor;

import java.io.File;

public class ManagerMessages {

    private String text="";

    public String insert(String msg){

       String body="";
        
       String[] str =  msg.split(";",3);

       String title = "<p>"+str[0]+" ("+str[1]+"):"+"</p>";

       //condição para exibir o emoji do coraçao
       if(str[2].equals("<3")){

        String pathSeparator = File.separator;
        String path = "file:images"+pathSeparator+"heart_icon.png";
        
        body = "<p><img src="+path+"/><\\p>";   
    }else{
        body = "<p>"+str[2]+"</p>";
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