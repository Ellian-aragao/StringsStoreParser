package aragao.ellian.com.github.models;

public record Report(Long quantidadeDeClientes, Long quantidadeDeVendedores, String idVendaMaisCara,
                     Vendedor piorVendedor) {
	public static ReportBuilder builder() {
		return new ReportBuilder();
	}

	public static class ReportBuilder {
		private Long quantidadeDeClientes;
		private Long quantidadeDeVendedores;
		private String idVendaMaisCara;
		private Vendedor piorVendedor;

		public ReportBuilder quantidadeDeClientes(Long quantidadeDeClientes) {
			this.quantidadeDeClientes = quantidadeDeClientes;
			return this;
		}

		public ReportBuilder quantidadeDeVendedores(Long quantidadeDeVendedores) {
			this.quantidadeDeVendedores = quantidadeDeVendedores;
			return this;
		}

		public ReportBuilder idVendaMaisCara(String idVendaMaisCara) {
			this.idVendaMaisCara = idVendaMaisCara;
			return this;
		}

		public ReportBuilder piorVendedor(Vendedor piorVendedor) {
			this.piorVendedor = piorVendedor;
			return this;
		}

		public Report build() {
			return new Report(quantidadeDeClientes, quantidadeDeVendedores, idVendaMaisCara, piorVendedor);
		}
	}
}
