package aragao.ellian.com.github.database;

import aragao.ellian.com.github.models.Cliente;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Database implements ModelsRepository {

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
	public Long countClientes() {
		return (long) CLIENTE_LIST.size();
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
	public Long countVendedores() {
		return (long) VENDEDOR_LIST.size();
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
	public Optional<Vendas> findVendaMaisCara() {
		return VENDAS_LIST
				.stream()
				.reduce((venda1, venda2) -> venda1.precoDaVenda().compareTo(venda2.precoDaVenda()) > 0 ? venda1 : venda2);
	}

	@Override
	public Optional<Vendedor> findPiorVendedor() {
		final var nomeDosVendedores = VENDEDOR_LIST.stream()
				.collect(Collectors.toMap(Vendedor::name, Function.identity()));
		return VENDAS_LIST
				.stream()
				.collect(Collectors.groupingBy(Vendas::salesmanName, Collectors
						.reducing(BigDecimal.ZERO, Vendas::precoDaVenda, BigDecimal::add)))
				.entrySet()
				.stream()
				.min(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.map(nomeDosVendedores::get);
	}

	@Override
	public String toString() {
		return "Database [CLIENTE_LIST=" + CLIENTE_LIST + ", VENDAS_LIST=" + VENDAS_LIST + ", VENDEDOR_LIST="
				+ VENDEDOR_LIST + "]";
	}
}
