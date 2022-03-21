package aragao.ellian.com.github.services.impl;

import aragao.ellian.com.github.database.ModelsRepository;
import aragao.ellian.com.github.models.Report;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;
import aragao.ellian.com.github.services.GenerateReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GenerateReportServiceImpl implements GenerateReportService {

	private final ModelsRepository modelsRepository;

	@Override
	public Report generateReport() {
		final var idVendaMaisCara = modelsRepository.findVendaMaisCara()
				.map(Vendas::saleId)
				.orElseGet(() -> {
					log.warn("Não foi possível encontrar uma venda mais cara");
					return "";
				});
		final var piorVendedor = modelsRepository.findPiorVendedor()
				.orElseGet(() -> {
					log.warn("Não foi possível encontrar o pior vendedor");
					return Vendedor.builder().build();
				});

		return Report.builder()
				.idVendaMaisCara(idVendaMaisCara)
				.piorVendedor(piorVendedor)
				.quantidadeDeVendedores(modelsRepository.countVendedores())
				.quantidadeDeClientes(modelsRepository.countClientes())
				.build();
	}
}
