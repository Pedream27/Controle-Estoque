package br.com.pholiveira.Controle.de.Estoque.services;

import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.model.enuns.Localizacao;
import br.com.pholiveira.Controle.de.Estoque.repository.EquipamentosRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class ProcessamentoExcel {

    @Autowired // Injeta uma instância de ClienteRepository
    private EquipamentosRepository repository;

    private static Logger logger = LoggerFactory.getLogger(ProcessamentoExcel.class);

    public void processarUploadPlanilha(MultipartFile file) throws IOException {
        logger.info("Iniciando o processamento do arquivo: {}", file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        if (filename == null) {
            logger.error("Nome do arquivo é nulo.");
            throw new IllegalArgumentException("Nome do arquivo não pode ser nulo.");
        }

        if (filename.endsWith(".csv")) {
            logger.info("Processando arquivo CSV: {}", filename);
            processarCsv(file);
        } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
            logger.info("Processando arquivo Excel: {}", filename);
            processarExcel(file);
        } else {
            logger.error("Formato de arquivo não suportado: {}", filename);
            throw new IllegalArgumentException("Formato de arquivo não suportado. Use .csv, .xlsx ou .xls.");
        }
    }

    private void processarCsv(MultipartFile file) throws IOException {
    logger.info("Processando arquivo CSV: {}", file.getOriginalFilename());
    List<Equipamentos> equipamentosList = new ArrayList<>();

    try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
        String[] data;
        boolean firstLine = true;

        while ((data = reader.readNext()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            if (data.length >= 9) { // Agora espera 9 colunas
                Equipamentos equipamentos = new Equipamentos();
                equipamentos.setNomeCadastradoTasy(data[0].trim().replace("\"", ""));
                equipamentos.setTipoEquipamento(data[1].trim().replace("\"", ""));
                equipamentos.setMarca(data[2].trim().replace("\"", ""));
                equipamentos.setModelo(data[3].trim().replace("\"", ""));
                equipamentos.setLocalizacao(setLoca(data[4]));
                try {
                    equipamentos.setQntEstoque(Integer.parseInt(data[5].trim()));
                } catch (NumberFormatException e) {
                    equipamentos.setQntEstoque(0);
                }
                try {
                    equipamentos.setQntFuncionando(Integer.parseInt(data[6].trim()));
                } catch (NumberFormatException e) {
                    equipamentos.setQntFuncionando(0);
                }
                try {
                    equipamentos.setQntInoperante(Integer.parseInt(data[7].trim()));
                } catch (NumberFormatException e) {
                    equipamentos.setQntInoperante(0);
                }
                equipamentos.setURLImagem(data[8].trim().replace("\"", "")); // Novo campo

                equipamentosList.add(equipamentos);
            }
        }
    } catch (CsvValidationException e) {
        throw new RuntimeException(e);
    }

    repository.saveAll(equipamentosList);
}
    private Localizacao setLoca(String data) {
        
        if(data.equals("DATA_CENTER")) {
            return Localizacao.DATA_CENTER;

        }else if(data.equals("ARMARIO_SUPORTE")) {
            return  Localizacao.ARMARIO_SUPORTE;
        }
        new RuntimeException("Erro localização não existente");
        return null;
    }

 private void processarExcel(MultipartFile file) throws IOException {
    logger.info("Processando arquivo Excel: {}", file.getOriginalFilename());
    List<Equipamentos> equipamentosList = new ArrayList<>();
    try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next(); // Pula cabeçalho

        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (isRowEmpty(currentRow)) {
                continue;
            }

            Equipamentos equipamento = new Equipamentos();
            Cell nomeTasyCell = currentRow.getCell(0);
            Cell tipoEquipamentoCell = currentRow.getCell(1);
            Cell marcaCell = currentRow.getCell(2);
            Cell modeloCell = currentRow.getCell(3);
            Cell localizacaoCell = currentRow.getCell(4);
            Cell qntEstoqueCell = currentRow.getCell(5);
            Cell qntOperanteCell = currentRow.getCell(6);
            Cell qntInoperanteCell = currentRow.getCell(7);
            Cell urlImagemCell = currentRow.getCell(8); // Novo campo

            if (nomeTasyCell != null) {
                equipamento.setNomeCadastradoTasy(nomeTasyCell.getStringCellValue());
            }
            if (tipoEquipamentoCell != null) {
                equipamento.setTipoEquipamento(tipoEquipamentoCell.getStringCellValue());
            }
            if (marcaCell != null) {
                equipamento.setMarca(marcaCell.getStringCellValue());
            }
            if (modeloCell != null) {
                equipamento.setModelo(modeloCell.getStringCellValue());
            }
            if (localizacaoCell != null) {
                equipamento.setLocalizacao(setLoca(localizacaoCell.getStringCellValue()));
            }
            if (qntEstoqueCell != null) {
                if (qntEstoqueCell.getCellType() == CellType.NUMERIC) {
                    equipamento.setQntEstoque((int) qntEstoqueCell.getNumericCellValue());
                } else if (qntEstoqueCell.getCellType() == CellType.STRING) {
                    try {
                        equipamento.setQntEstoque(Integer.parseInt(qntEstoqueCell.getStringCellValue()));
                    } catch (NumberFormatException e) {
                        System.err.println("Aviso: Estoque inválido na célula: " + qntEstoqueCell.getStringCellValue());
                    }
                }
            }
            if (qntOperanteCell != null) {
                if (qntOperanteCell.getCellType() == CellType.NUMERIC) {
                    equipamento.setQntFuncionando((int) qntOperanteCell.getNumericCellValue());
                } else if (qntOperanteCell.getCellType() == CellType.STRING) {
                    try {
                        equipamento.setQntFuncionando(Integer.parseInt(qntOperanteCell.getStringCellValue()));
                    } catch (NumberFormatException e) {
                        System.err.println("Aviso: Funcionando inválido na célula: " + qntOperanteCell.getStringCellValue());
                    }
                }
            }
            if (qntInoperanteCell != null) {
                if (qntInoperanteCell.getCellType() == CellType.NUMERIC) {
                    equipamento.setQntInoperante((int) qntInoperanteCell.getNumericCellValue());
                } else if (qntInoperanteCell.getCellType() == CellType.STRING) {
                    try {
                        equipamento.setQntInoperante(Integer.parseInt(qntInoperanteCell.getStringCellValue()));
                    } catch (NumberFormatException e) {
                        System.err.println("Aviso: Inoperante inválido na célula: " + qntInoperanteCell.getStringCellValue());
                    }
                }
            }
            if (urlImagemCell != null) {
                if (urlImagemCell.getCellType() == CellType.STRING) {
                    equipamento.setURLImagem(urlImagemCell.getStringCellValue());
                } else if (urlImagemCell.getCellType() == CellType.NUMERIC) {
                    equipamento.setURLImagem(String.valueOf(urlImagemCell.getNumericCellValue()));
                }
            }

            equipamentosList.add(equipamento);
        }
    }
    repository.saveAll(equipamentosList);
}

    // Helper para verificar se uma linha é vazia
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK && !cell.getStringCellValue().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<InputStreamResource> exportToCSV() throws IOException {
    logger.info("Exportando dados para CSV");
    List<Equipamentos> equipamentos = repository.findAll();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {
        // Cabeçalho atualizado
        writer.writeNext(new String[]{
                "Nome", "Tipo", "Marca", "Modelo", "Localização", "Funcionando", "Inoperante", "Estoque", "URLImagem"
        });

        for (Equipamentos eq : equipamentos) {
            writer.writeNext(new String[]{
                    eq.getNomeCadastradoTasy(),
                    eq.getTipoEquipamento(),
                    eq.getMarca(),
                    eq.getModelo(),
                    eq.getLocalizacao() != null ? eq.getLocalizacao().name() : "INDEFINIDA",
                    String.valueOf(eq.getQntFuncionando()),
                    String.valueOf(eq.getQntInoperante()),
                    String.valueOf(eq.getQntEstoque()),
                    eq.getURLImagem() != null ? eq.getURLImagem() : ""
            });
        }
    }

    String fileName = "Estoque_de_Equipamento_" + LocalDate.now() + ".csv";

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
    headers.setContentType(MediaType.parseMediaType("text/csv"));
    headers.add("Access-Control-Expose-Headers", "Content-Disposition");

    return ResponseEntity.ok()
            .headers(headers)
            .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
}

}
