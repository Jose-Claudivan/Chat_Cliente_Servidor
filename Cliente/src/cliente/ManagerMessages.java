package cliente;

public class ManagerMessages {

    private String text="";

    public String insert(String msg){
        
       String[] str =  msg.split(";",3);

       String title = "<p>"+str[0]+" ("+str[1]+"):"+"</p>";
       String body = "<p>"+str[2]+"</p>";
       String message = title+body;

       this.text = this.text+message;

       return this.text;
    }

    public String getMessages(){
        return this.text;
    }

}