package aragao.ellian.com.github;

public record Cliente(String cnpj, String name, String BusinessArea) {
	public static final String id = ModelsIds.CLIENTE.getId();
}
