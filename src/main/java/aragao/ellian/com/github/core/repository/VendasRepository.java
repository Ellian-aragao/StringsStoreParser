package aragao.ellian.com.github.core.repository;

import aragao.ellian.com.github.core.models.Vendas;

import java.util.Optional;
import java.util.Set;

public interface VendasRepository {
	Vendas saveVendas(Vendas vendas);
	Set<Vendas> findAllVendas();

	Optional<Vendas> findVendaMaisCara();
}
