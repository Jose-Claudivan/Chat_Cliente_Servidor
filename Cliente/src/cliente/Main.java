package cliente;

public class Main {

    public static void main(String args[]){
        ManagerMessages mm = new ManagerMessages();
        mm.insert("pires;10:38;olá");
        mm.insert("pires2;10:38;olá2");
        mm.insert("pires3;10:38;olá3");
        System.out.println(mm.insert("pires4;10:38;olá4: sempre"));
    }
}