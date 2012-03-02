package br.com.perfin.util;

public class Constantes {

	public static final String BANCO = "perfin";
	
	public static final int GASTO = 10;
	public static final int EXTRATO = 20;
	
	public static final int G_CARTAO = 0;
	public static final int G_CHEQUE = 1;
	public static final int G_DINHEIRO = 2;
	public static final int RENDA = 3;
	
	public static final String STATUS_PAID = "Pago/Compensado";
	public static final String STATUS_NOTPAID = "Não Pago/Não Compensado";
	
	public static final CharSequence[] G_TYPE = {"Cartão", "Cheque", "Dinheiro"};
	public static final CharSequence[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	public static final CharSequence[] OPTIONS = {"Excluir", "Faturar"};
	
	public static final int LIQUIDACAO_PARCELA_PENDENTE = 100;
	public static final int LIQUIDACAO_DATA_INVALIDA = 150;
	public static final int LIQUIDACAO_DATA_INVALIDA_2 = 200;
	public static final int LIQUIDACAO_DATA_INVALIDA_3 = 250;
}
