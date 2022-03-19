package aragao.ellian.com.github.services.impl;

import aragao.ellian.com.github.database.Database;
import aragao.ellian.com.github.database.ModelsRepository;
import aragao.ellian.com.github.models.Report;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.services.GenerateReportService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateReportServiceImpl implements GenerateReportService {

	private final ModelsRepository modelsRepository;

	@Override
	public Report generateReport() {
		return Report.builder()
				.idVendaMaisCara(modelsRepository.findVendaMaisCara().map(Vendas::saleId).orElse(null))
				.piorVendedor(modelsRepository.findPiorVendedor().map(Vendas::saleId).orElse(null))
				.quantidadeDeVendedores(modelsRepository.countVendedores())
				.quantidadeDeClientes(modelsRepository.countClientes())
				.build();
	}
}
