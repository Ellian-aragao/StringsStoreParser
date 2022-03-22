package aragao.ellian.com.github.core.repository;

import aragao.ellian.com.github.core.models.Vendedor;

import java.util.Optional;
import java.util.Set;

public interface VendedorRepository {
	Vendedor saveVendedor(Vendedor vendedor);

	Set<Vendedor> findAllVendedor();

	Long countVendedores();

	Optional<Vendedor> findPiorVendedor();
}
