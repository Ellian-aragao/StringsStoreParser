package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Vendedor;

import java.util.List;

public interface VendedorRepository {
	Vendedor saveVendedor(Vendedor vendedor);

	List<Vendedor> findAllVendedor();

	Long countVendedores();
}
