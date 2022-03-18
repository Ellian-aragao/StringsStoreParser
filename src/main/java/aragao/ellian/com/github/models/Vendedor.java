package aragao.ellian.com.github.models;

import java.math.BigDecimal;
import java.util.Objects;

public record Vendedor(String cpf, String name, BigDecimal salary) {
	public static VendedorBuilder builder() {
		return new VendedorBuilder();
	}

	public static class VendedorBuilder {
		private String cpf;
		private String name;
		private BigDecimal salary;

		public VendedorBuilder withCpf(String cpf) {
			this.cpf = cpf;
			return this;
		}

		public VendedorBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public VendedorBuilder withSalary(BigDecimal salary) {
			this.salary = salary;
			return this;
		}

		public VendedorBuilder withSalary(String salary) {
			this.salary = new BigDecimal(salary);
			return this;
		}

		public Vendedor build() {
			if (Objects.isNull(cpf) || cpf.isBlank()) {
				throw new IllegalArgumentException("Cpf is required with valid value");
			}
			if (Objects.isNull(name) || name.isBlank()) {
				throw new IllegalArgumentException("Name is required with valid value");
			}
			if (Objects.isNull(salary)) {
				throw new IllegalArgumentException("Salary is required");
			}
			return new Vendedor(cpf, name, salary);
		}
	}
}
