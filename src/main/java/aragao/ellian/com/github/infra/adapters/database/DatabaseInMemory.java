package aragao.ellian.com.github.infra.adapters.database;

import aragao.ellian.com.github.core.repository.ModelsRepository;
import aragao.ellian.com.github.core.models.Cliente;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.models.Vendedor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DatabaseInMemory implements ModelsRepository {
	private static final Set<Cliente> CLIENTE_SET = new HashSet<>();

	private static final Set<Vendas> VENDAS_SET = new HashSet<>();

	private static final Set<Vendedor> VENDEDOR_SET = new HashSet<>();

	@Override
	public Cliente saveCliente(Cliente cliente) {
		synchronized (CLIENTE_SET) {
			CLIENTE_SET.add(cliente);
			return cliente;
		}
	}

	@Override
	public Set<Cliente> findAllClientes() {
		synchronized (CLIENTE_SET) {
			return CLIENTE_SET;
		}
	}

	@Override
	public Long countClientes() {
		synchronized (CLIENTE_SET) {
			return (long) CLIENTE_SET.size();
		}
	}

	@Override
	public Vendedor saveVendedor(Vendedor vendedor) {
		synchronized (VENDEDOR_SET) {
			VENDEDOR_SET.add(vendedor);
			return vendedor;
		}
	}

	@Override
	public Set<Vendedor> findAllVendedor() {
		synchronized (VENDEDOR_SET) {
			return VENDEDOR_SET;
		}
	}

	@Override
	public Long countVendedores() {
		synchronized (VENDEDOR_SET) {
			return (long) VENDEDOR_SET.size();
		}
	}

	@Override
	public Vendas saveVendas(Vendas vendas) {
		synchronized (VENDAS_SET) {
			VENDAS_SET.add(vendas);
			return vendas;
		}
	}

	@Override
	public Set<Vendas> findAllVendas() {
		synchronized (VENDAS_SET) {
			return VENDAS_SET;
		}
	}

	@Override
	public Optional<Vendas> findVendaMaisCara() {
		synchronized (VENDAS_SET) {
			return VENDAS_SET
					.stream()
					.reduce((venda1, venda2) -> venda1.precoDaVenda().compareTo(venda2.precoDaVenda()) > 0 ? venda1 : venda2);
		}
	}

	@Override
	public synchronized Optional<Vendedor> findPiorVendedor() {
		final var nomeDosVendedores = VENDEDOR_SET.stream()
				.collect(Collectors.toMap(Vendedor::name, Function.identity()));
		return VENDAS_SET
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
	public synchronized String toString() {
		return "DatabaseInMemory [CLIENTE_LIST=" + CLIENTE_SET + ", VENDAS_LIST=" + VENDAS_SET + ", VENDEDOR_LIST="
				+ VENDEDOR_SET + "]";
	}
}
