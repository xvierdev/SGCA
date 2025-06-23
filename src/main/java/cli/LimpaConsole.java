package cli;

public class LimpaConsole {
    public static void Limpar() {
        try {
            final String ANSI_CLS = "\033[2J";
            final String ANSI_HOME = "\033[H";
            System.out.print(ANSI_CLS + ANSI_HOME);
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Erro ao tentar limpar o console via ANSI: " + e.getMessage());
        }
    }
}
