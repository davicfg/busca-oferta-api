package alexandre_davi_miguel.catalogo_pecas_api.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulação (mock) de um cliente de IA generativa (ex.: Google Gemini).
 *
 * O projeto já depende do Spring AI (spring-ai-starter-model-google-genai) no
 * pom.xml, para quando alguém tiver uma API key própria configurada. Até lá,
 * esta classe simula o comportamento do modelo: ela "finge" enviar o prompt +
 * documento para o Gemini, aguarda um pequeno tempo (simulando latência de
 * rede) e devolve um JSON no mesmo formato que o modelo real devolveria.
 *
 * Para plugar a IA de verdade no futuro: crie um @Service que injete um
 * ChatClient/ChatModel do Spring AI, implemente o mesmo método
 * (extrairCatalogoEmJson) chamando o modelo de verdade, e troque a injeção
 * em ConfiguracaoExtracaoCatalogo do MockGeminiClient para essa nova classe.
 */
@Component
public class MockGeminiClient {

    public String extrairCatalogoEmJson(String prompt, String conteudoDocumento) throws InterruptedException {
        System.out.println("==================================================");
        System.out.println("[IA MOCK - Gemini simulado] Enviando requisição...");
        System.out.println("[IA MOCK] Prompt enviado: " + prompt);
        System.out.println("[IA MOCK] Tamanho do documento recebido: " + conteudoDocumento.length() + " caracteres");

        // Simula a latência de uma chamada real a um modelo de IA generativa
        Thread.sleep(1200);

        List<String> itensJson = new ArrayList<>();
        String[] linhas = conteudoDocumento.split("\\r?\\n");

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty() || linha.startsWith("#")) {
                continue; // ignora linhas vazias e comentários
            }

            String[] campos = linha.split(";");
            if (campos.length < 4) {
                continue; // linha fora do formato esperado, ignorada (a IA real também descartaria lixo)
            }

            String codigo = escapar(campos[0].trim());
            String nome = escapar(campos[1].trim());
            String[] veiculosArr = campos[2].split(",");
            String preco = campos[3].trim().replace(",", ".");

            StringBuilder veiculosJson = new StringBuilder("[");
            for (int i = 0; i < veiculosArr.length; i++) {
                veiculosJson.append("\"").append(escapar(veiculosArr[i].trim())).append("\"");
                if (i < veiculosArr.length - 1) {
                    veiculosJson.append(",");
                }
            }
            veiculosJson.append("]");

            String item = String.format(
                    "{\"codigoPeca\":\"%s\",\"nome\":\"%s\",\"veiculosCompativeis\":%s,\"precoCusto\":%s}",
                    codigo, nome, veiculosJson, preco
            );
            itensJson.add(item);
        }

        String json = "[" + String.join(",", itensJson) + "]";

        System.out.println("[IA MOCK] Resposta simulada recebida com " + itensJson.size() + " item(ns) extraído(s).");
        System.out.println("==================================================");

        return json;
    }

    private String escapar(String valor) {
        return valor.replace("\"", "\\\"");
    }
}
