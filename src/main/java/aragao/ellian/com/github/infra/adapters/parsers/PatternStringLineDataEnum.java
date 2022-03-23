package aragao.ellian.com.github.infra.adapters.parsers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum PatternStringLineDataEnum {
	GENERIC_ENTRY_PATTERN("\\d{3}([ç](.*)){3}"),
	VENDEDOR("001ç\\d{13}ç\\w*ç[\\d.]*"),
	CLIENTE("002ç\\d{16}ç[\\w\\s]*ç\\w*"),
	VENDA("003ç\\d*ç\\[((\\d+|-){2}(\\d+|.)*[,|\\]])ç\\w*"),
	LISTA_ITEMS("[\\[\\]]"),
	ITEM("((\\d+)-){2}(\\d+)(.(\\d){2})?");

	private static final String DELIMITER = "ç";
	private final String regex;

	public static String getDELIMITER() {
		return DELIMITER;
	}

	public Pattern getPattern() {
		return Pattern.compile(regex);
	}
}
