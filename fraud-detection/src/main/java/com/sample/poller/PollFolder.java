package com.sample.poller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sample.process.ProcessTransactions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PollFolder {

	@Value("${read.folder}")
	private String inputFolderPath;
	
	private final ProcessTransactions processTransactions;
	
	@Autowired
	public PollFolder(ProcessTransactions processTransactions) {
		this.processTransactions = processTransactions;
	}

	@Scheduled(cron = "${cron.expression}")
	public void pollFolder() {
		File inputFolder = new File(this.inputFolderPath);
		log.info("Processing files from folder = {}", this.inputFolderPath);

		Set<Path> filesToProcess = Stream.of(inputFolder.listFiles()).filter(f -> !f.isDirectory())
				.map(f -> Path.of(f.getAbsolutePath())).collect(Collectors.toSet());

		List<InputContent> allTransactionDetails = readTransactionDetails(filesToProcess);
		processTransactions.processAllTransactions(allTransactionDetails);
		
		deleteAlreadyProcessedFiles(filesToProcess);
	}

	private List<InputContent> readTransactionDetails(Set<Path> filesToProcess) {
		List<InputContent> transactionsList = new ArrayList<>();
		for (Path inputFilePath : filesToProcess) {
			try {
				String fileContent = Files.readString(inputFilePath, StandardCharsets.UTF_8);
				List<InputContent> transactionsInOneFile = fileContent.lines().filter(l -> l.contains(";"))
						.map(l -> parseInputContent(l)).collect(Collectors.toList());
				transactionsList.addAll(transactionsInOneFile);
			} catch (IOException ex) {
				log.info("Unable to read file with path: {}", this.inputFolderPath);
				log.error("Error occurred while processing is ", ex);
			}
		}
		return transactionsList;
	}

	private InputContent parseInputContent(String inputString) {
		InputContent content = new InputContent(inputString);
		return content;
	}
	
	private void deleteAlreadyProcessedFiles(Set<Path> filesToProcess) {
		for (Path path : filesToProcess) {
			try {
				Files.deleteIfExists(path);
			} catch (IOException ex) {
				log.error("Error occurred while deleting file {} is: {}", path.toFile().getAbsolutePath(), ex.getMessage());
			}
		}
	}

}
