package alexandre_davi_miguel.busca_oferta_api.dto.encarte;

public record ProdutoExtraidoDTO(
		String nome,
		String marca,
		String medida,
		String precoUnitario,
		Long supermercadoId
) {}
