package model.enums;

public enum Status {
    ATIVO("Ativo", true),
    INATIVO("Inativo", false);

    private final String descricao;
    private final boolean ativo;

    Status(String descricao, boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public static Status fromBoolean(boolean ativo) {
        return ativo ? ATIVO : INATIVO;
    }
}