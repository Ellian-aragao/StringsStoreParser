package aragao.ellian.com.github.core.usecases.impl;

import aragao.ellian.com.github.core.exceptions.NotFoundMostExpensiveSale;
import aragao.ellian.com.github.core.exceptions.NotFoundWorstSaler;
import aragao.ellian.com.github.core.models.Report;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.repository.ClienteRepository;
import aragao.ellian.com.github.core.repository.VendasRepository;
import aragao.ellian.com.github.core.repository.VendedorRepository;
import aragao.ellian.com.github.core.usecases.GenerateReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GenerateReportImpl implements GenerateReport {

	private final VendasRepository vendasRepository;

	private final VendedorRepository vendedorRepository;

	private final ClienteRepository clienteRepository;

	@Override
	public Report generateReport() {
		final var idVendaMaisCara = vendasRepository.findVendaMaisCara()
				.map(Vendas::saleId)
				.orElseThrow(() -> {
					log.warn("Não foi possível encontrar uma venda mais cara");
					return new NotFoundMostExpensiveSale("Não foi possível encontrar a venda mais cara");
				});
		final var piorVendedor = vendedorRepository.findPiorVendedor()
				.orElseThrow(() -> {
					log.warn("Não foi possível encontrar o pior vendedor");
					return new NotFoundWorstSaler("Não foi possível encontrar o pior vendedor");
				});

		return Report.builder()
				.idVendaMaisCara(idVendaMaisCara)
				.piorVendedor(piorVendedor)
				.quantidadeDeVendedores(vendedorRepository.countVendedores())
				.quantidadeDeClientes(clienteRepository.countClientes())
				.build();
	}
}
