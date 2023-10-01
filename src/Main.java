import easyaccept.EasyAccept;

public class Main {
    public static void main(String[] args) {
        String[] args1 = { "br.ufal.ic.p2.jackut.Facade", "tests/us5_1.txt" };
        String[] args2 = { "br.ufal.ic.p2.jackut.Facade", "tests/us5_2.txt" };
        EasyAccept.main(args1);
        EasyAccept.main(args2);
        
    }
}