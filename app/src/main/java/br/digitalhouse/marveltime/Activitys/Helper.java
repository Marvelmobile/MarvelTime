package br.digitalhouse.marveltime.Activitys;

public class Helper {

    public static boolean usuarioValido (String usuario){
        return (usuario.contains("@") && usuario.contains(".com") );
    }

    public static boolean isEmptyString (String valor){
        return valor == null || valor.trim().equals("");
    }

    public static boolean senhaValida(String senha) {
        if (senha.length() < 6)
            return false;

        boolean achouNumero = false;
        boolean achouMaiuscula = false;
        boolean achouMinuscula = false;
        boolean achouSimbolo = false;

        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9')
                achouNumero = true;
            else if (c >= 'A' && c <= 'Z')
                achouMaiuscula = true;
            else if (c >= 'a' && c <= 'z')
                achouMinuscula = true;
            else
                achouSimbolo = true;
        }
        return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
    }

    public static boolean nomeValido(String nomeCompleto) {
        return (nomeCompleto.contains(" "));
    }

    public static boolean senhaIguais(String senha, String confirmacaoSenha)
    {
        return (confirmacaoSenha.equals(senha));
    }
}
