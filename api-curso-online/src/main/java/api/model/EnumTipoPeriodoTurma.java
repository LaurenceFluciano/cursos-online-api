package api.model;


public enum EnumTipoPeriodoTurma {
    BIMESTRE(2),
    TRIMESTRE(3),
    QUADRIMESTRE(4),
    SEMESTRE(6);

    private final int meses;

    EnumTipoPeriodoTurma(int meses) { this.meses = meses; }

    public int getMeses() { return meses; }
}