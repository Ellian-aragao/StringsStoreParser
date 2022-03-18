package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Vendas;

import java.util.List;

public interface VendasRepository {
	Vendas saveVendas(Vendas vendas);
	List<Vendas> findAllVendas();

}
