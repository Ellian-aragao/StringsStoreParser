package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Cliente;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;

import java.util.LinkedList;
import java.util.List;

public class Database implements ClienteRepository, VendedorRepository, VendasRepository {

	private static final List<Cliente> CLIENTE_LIST = new LinkedList<>();

	private static final List<Vendas> VENDAS_LIST = new LinkedList<>();

	private static final List<Vendedor> VENDEDOR_LIST = new LinkedList<>();

	@Override
	public Cliente saveCliente(Cliente cliente) {
		CLIENTE_LIST.add(cliente);
		return cliente;
	}

	@Override
	public List<Cliente> findAllClientes() {
		return CLIENTE_LIST;
	}

	@Override
	public Vendedor saveVendedor(Vendedor vendedor) {
		VENDEDOR_LIST.add(vendedor);
		return vendedor;
	}

	@Override
	public List<Vendedor> findAllVendedor() {
		return VENDEDOR_LIST;
	}

	@Override
	public Vendas saveVendas(Vendas vendas) {
		VENDAS_LIST.add(vendas);
		return vendas;
	}

	@Override
	public List<Vendas> findAllVendas() {
		return VENDAS_LIST;
	}
	@Override
	public String toString() {
		return "Database [CLIENTE_LIST=" + CLIENTE_LIST + ", VENDAS_LIST=" + VENDAS_LIST + ", VENDEDOR_LIST="
				+ VENDEDOR_LIST + "]";
	}
}
