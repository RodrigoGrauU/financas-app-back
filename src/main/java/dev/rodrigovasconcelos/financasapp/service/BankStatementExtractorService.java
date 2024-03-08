package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.entity.TipoTransacao;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BankStatementExtractorService {

    private boolean fimLeituraArquivo;
    private Random random = new Random();

    public List<TransacaoDto> transactionsFromPdf(MultipartFile bankStatement) throws IOException {
        this.fimLeituraArquivo = false;
        String pathName = "src/main/resources/" + random.nextInt(1000) + ".tmp";
        File tempFile = new File(pathName);
        OutputStream os = new FileOutputStream(tempFile);
        os.write(bankStatement.getBytes());
        os.close();

        PDDocument document = Loader.loadPDF(tempFile);
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );
        Rectangle rect = new Rectangle( 0, 115, 1000, 800 );
        stripper.addRegion( "class1", rect );

        List<TransacaoDto> transacoes = new ArrayList<>();
        for (int indexPage = 0; indexPage < document.getNumberOfPages(); indexPage++) {
            if(!this.fimLeituraArquivo) {
                PDPage thePage = document.getPage(indexPage);
                stripper.extractRegions(thePage);
                StringBuilder stringPageContent = new StringBuilder(stripper.getTextForRegion("class1"));
                transacoes.addAll(getInformationFromPageBbLayout(stringPageContent));
            }
        }
        tempFile.delete();
        return transacoes.stream().sorted(Comparator.comparing(TransacaoDto::getDataTransacao)).collect(Collectors.toList());
    }

    private List<TransacaoDto> getInformationFromPageBbLayout(StringBuilder stringPageContent) {
        List<TransacaoDto> transacoes = new ArrayList<>();

        String[] linhasTransacoes = stringPageContent.toString().split("\n");
        for (int i = 0; i < linhasTransacoes.length; i++) {
            //identifica linha do saldo
            if(linhasTransacoes[i].contains("Saldo Anterior")) {
                continue;
            }

            // pega as 3 próximas linhas
            String[] linhas = new String[3];
            linhas[0] = linhasTransacoes[i];
            linhas[1] = linhasTransacoes[(++i)];
            linhas[2] = linhasTransacoes[(++i)];

            //VERIFICA FIM DO FLUXO
            if(Arrays.asList(linhas).stream().anyMatch(linha -> linha.contains("Informações Adicionais"))) {
                this.fimLeituraArquivo = true;
                break;
            }

            //trataLinhas
            String[] linhasTransacao = new String[3];
            for (String linha : linhas) {
                int tipoLinha = identificaPadraoLinha(linha);
                if( tipoLinha == 0) {
                    linhasTransacao[0] = linha;
                } else if(tipoLinha == 1) {
                    linhasTransacao[1] = linha;
                } else {
                    linhasTransacao[2] = linha;
                }
            }

            if(Arrays.asList(linhasTransacao).stream().filter(linha -> linha == null).collect(Collectors.toList()).size() > 1) {
                continue;
            }

            String data = linhasTransacao[0].substring(0, 10);
            String descricaoPagamento = "";
            if(linhasTransacao[1] != null) {
                descricaoPagamento = linhasTransacao[1].substring(12);
            } else {
                descricaoPagamento = linhasTransacao[2];
            }
            String tipoPagamento = linhasTransacao[0].contains("(-)") ? "DEBITO" : "CREDITO";
            String valorString = linhasTransacao[0].substring(11);
            String valorStringTratado = valorString.split("\\s\\(")[0];
            descricaoPagamento = descricaoPagamento.replace("\r", "");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataTransacao = LocalDate.parse(data, formatter);
            valorStringTratado = valorStringTratado.replace(".", "");
            valorStringTratado = valorStringTratado.replace(",",".");

            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setDescricao(descricaoPagamento);
            transacaoDto.setValor(new BigDecimal(valorStringTratado));
            transacaoDto.setTipoTransacao(tipoPagamento.equalsIgnoreCase("DEBITO") ? TipoTransacao.DEBITO : TipoTransacao.CREDITO);
            transacaoDto.setDataTransacao(dataTransacao);
            transacoes.add(transacaoDto);
        }
        return transacoes;
    }

    private static int identificaPadraoLinha(String linha) {
        String regexDataValorTipo = "^\\d{2}/\\d{2}/\\d{4} (.+) \\([-+]\\)\r$";
        String regexDataHoraDescricao = "^\\d{2}/\\d{2} \\d{2}:\\d{2} (.+)\r$";
        Pattern patternDataValorTipo = Pattern.compile(regexDataValorTipo);
        Matcher matcherDataValorTipo = patternDataValorTipo.matcher(linha);

        Pattern patternDataHoraDescricao = Pattern.compile(regexDataHoraDescricao);
        Matcher matcherDataHoraDescricao = patternDataHoraDescricao.matcher(linha);

        if(matcherDataValorTipo.matches()) {
            return 0;
        } else if(matcherDataHoraDescricao.matches()) {
            return 1;
        } else {
            return 2;
        }
    }
}
