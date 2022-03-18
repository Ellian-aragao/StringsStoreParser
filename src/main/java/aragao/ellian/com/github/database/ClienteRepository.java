package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Cliente;

import java.util.List;

public interface ClienteRepository {
	Cliente saveCliente(Cliente cliente);

	List<Cliente> findAllClientes();
}
