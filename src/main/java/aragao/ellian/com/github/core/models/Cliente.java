package aragao.ellian.com.github.core.models;

public record Cliente(String cnpj, String name, String BusinessArea) {
	public static ClienteBuilder builder() {
		return new ClienteBuilder();
	}

	public static class ClienteBuilder {
		private String cnpj;
		private String name;
		private String businessArea;

		public ClienteBuilder withCnpj(String cnpj) {
			this.cnpj = cnpj;
			return this;
		}

		public ClienteBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ClienteBuilder withBusinessArea(String businessArea) {
			this.businessArea = businessArea;
			return this;
		}

		public Cliente build() {
			return new Cliente(cnpj, name, businessArea);
		}
	}
}
