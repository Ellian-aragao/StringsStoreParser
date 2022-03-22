package aragao.ellian.com.github.core.repository;

import aragao.ellian.com.github.core.models.Cliente;

import java.util.Set;

public interface ClienteRepository {
	Cliente saveCliente(Cliente cliente);

	Set<Cliente> findAllClientes();

	Long countClientes();
}
