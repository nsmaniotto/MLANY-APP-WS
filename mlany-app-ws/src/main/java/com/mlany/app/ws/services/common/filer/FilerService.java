package com.mlany.app.ws.services.common.filer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mlany.app.persistence.entity.common.FileInfo;
import com.mlany.app.persistence.repository.common.FileInfoRepository;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

@Service
public class FilerService {
	private final Path uploadPath = Paths.get("C:\\dev\\projects\\uploads");

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private FileInfoRepository fileInfoRepository;

	@Transactional
	public FileInfo upload(MultipartFile file) throws IOException {
		FileInfo fileInfo = generateFileInfo("", file.getOriginalFilename());

		String filePath = new StringBuilder().append(this.uploadPath.toString()).append("\\")
				.append(this.getStoredFileName(fileInfo)).toString();
		this.logger.debug("filePath = {}", filePath);
		File dest = new File(filePath);
		file.transferTo(dest);

		return fileInfo;
	}

	public FileInfo generateFileInfo(String directory, String name) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(name);
		String filePath = StringUtils.isEmpty(directory) ? this.uploadPath.toString()
				: new StringBuilder().append(this.uploadPath.toString()).append("\\").append(directory).toString();
		fileInfo.setPath(filePath);

		// Create directory if not exists
		if (!new File(filePath).exists()) {
			new File(filePath).mkdir();
		}

		return fileInfoRepository.save(fileInfo);
	}

	private String getStoredFileName(FileInfo fileInfo) {
		return new StringBuilder().append(fileInfo.getId()).append("-").append(fileInfo.getName()).toString();
	}

	private String getStoredFileFullPath(FileInfo fileInfo) {
		return new StringBuilder().append(fileInfo.getPath()).append(File.separator).append(getStoredFileName(fileInfo))
				.toString();
	}

	public String getFileName(Long id) {
		String fileName = "unknown";
		Optional<FileInfo> optionalFile = fileInfoRepository.findById(id);
		if (optionalFile.isPresent()) {
			fileName = optionalFile.get().getName();
		}
		return fileName;
	}

	public byte[] getFileAsBytes(Long fileInfoId) throws IOException {
		logger.info("Access to fileInfo#{}", fileInfoId);
		File file = getFile(fileInfoId);

		byte[] byteArray = new byte[0];

		if (file != null) {
			byteArray = Files.readAllBytes(file.toPath());
		}

		return byteArray;
	}

	private File getFile(Long fileInfoId) {
		Optional<FileInfo> optionalFileInfo = fileInfoRepository.findById(fileInfoId);

		return optionalFileInfo.isPresent() ? getFile(optionalFileInfo.get()) : null;
	}

	public File getFile(FileInfo fileInfo) {
		Path filePath = Paths.get(getStoredFileFullPath(fileInfo));

		return filePath.toFile();
	}

	public FileReader getFileReader(FileInfo fileInfo) throws FileNotFoundException {
		return new FileReader(getStoredFileFullPath(fileInfo));
	}

	public Long countLines(FileInfo fileInfo) throws IOException {
		Path filePath = Paths.get(getStoredFileFullPath(fileInfo));

		long lines = 0;
		lines = Files.lines(filePath).parallel().count();

		return lines;
	}

	public Boolean updateFileContent(Long fileInfoId, byte[] content) throws IOException {
		logger.info("Updating fileInfo#{}", fileInfoId);
		File outputFile = getFile(fileInfoId);

		if (outputFile != null) {
			try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
				outputStream.write(content);
			}
		}

		return Boolean.valueOf(outputFile != null);
	}
}
