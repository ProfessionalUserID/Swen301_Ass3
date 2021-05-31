package nz.ac.wgtn.swen301.a3.client;

public class Client {

    static String type;
    static String fileName;

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("ERROR!!");
            return;
        }
        type = args[0];
        fileName = args[1];
    }

}
