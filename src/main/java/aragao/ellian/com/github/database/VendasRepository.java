package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;

import java.util.List;
import java.util.Optional;

public interface VendasRepository {
	Vendas saveVendas(Vendas vendas);
	List<Vendas> findAllVendas();

	Optional<Vendas> findVendaMaisCara();

	Optional<Vendedor> findPiorVendedor();
}
