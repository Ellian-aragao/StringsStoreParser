package aragao.ellian.com.github.infra.adapters.database;

import aragao.ellian.com.github.core.models.Cliente;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.models.Vendedor;
import aragao.ellian.com.github.core.repository.ModelsRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DatabaseInMemory implements ModelsRepository {
	private final Set<Cliente> clienteSet = new HashSet<>();

	private final Set<Vendas> vendasSet = new HashSet<>();

	private final Set<Vendedor> vendedorSet = new HashSet<>();

	@Override
	public Cliente saveCliente(Cliente cliente) {
		synchronized (clienteSet) {
			clienteSet.add(cliente);
			return cliente;
		}
	}

	@Override
	public Set<Cliente> findAllClientes() {
		synchronized (clienteSet) {
			return clienteSet;
		}
	}

	@Override
	public Long countClientes() {
		synchronized (clienteSet) {
			return (long) clienteSet.size();
		}
	}

	@Override
	public Vendedor saveVendedor(Vendedor vendedor) {
		synchronized (vendedorSet) {
			vendedorSet.add(vendedor);
			return vendedor;
		}
	}

	@Override
	public Set<Vendedor> findAllVendedor() {
		synchronized (vendedorSet) {
			return vendedorSet;
		}
	}

	@Override
	public Long countVendedores() {
		synchronized (vendedorSet) {
			return (long) vendedorSet.size();
		}
	}

	@Override
	public Vendas saveVendas(Vendas vendas) {
		synchronized (vendasSet) {
			vendasSet.add(vendas);
			return vendas;
		}
	}

	@Override
	public Set<Vendas> findAllVendas() {
		synchronized (vendasSet) {
			return vendasSet;
		}
	}

	@Override
	public Optional<Vendas> findVendaMaisCara() {
		synchronized (vendasSet) {
			return vendasSet
					.stream()
					.reduce((venda1, venda2) -> venda1.precoDaVenda().compareTo(venda2.precoDaVenda()) > 0 ? venda1 : venda2);
		}
	}

	@Override
	public synchronized Optional<Vendedor> findPiorVendedor() {
		final var nomeDosVendedores = vendedorSet.stream()
				.collect(Collectors.toMap(Vendedor::name, Function.identity()));
		return vendasSet
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
		return "DatabaseInMemory [CLIENTE_LIST=" + clienteSet + ", VENDAS_LIST=" + vendasSet + ", VENDEDOR_LIST="
				+ vendedorSet + "]";
	}
}
