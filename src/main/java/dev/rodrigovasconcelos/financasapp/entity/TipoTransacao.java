package dev.rodrigovasconcelos.financasapp.entity;

public enum TipoTransacao {
    DEBITO("D"),
    CREDITO("C");

    private String siglaTransacao;

    TipoTransacao(String siglaTransacao) {
        this.siglaTransacao = siglaTransacao;
    }

    public String getSiglaTransacao() {
        return siglaTransacao;
    }
}
