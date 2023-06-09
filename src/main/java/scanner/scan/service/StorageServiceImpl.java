package scanner.scan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import scanner.common.utils.FileDigest;
import scanner.common.exception.ApiException;
import scanner.common.enums.ResponseCode;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	private static final String ROOT_PATH = "./uploads/";

	@Override
	public void init() {
		try {
			Files.createDirectories(Paths.get(uploadPath));
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_CREATE_DIR_ERROR);
		}
	}

	/**
	 * 	Save Files under UUID directory
	 */
	@Override
	public String[] store(MultipartFile file) {
		try {
			String[] result = new String[3];
			String saved = UUID.randomUUID().toString();
			Path root = Paths.get(uploadPath + saved);
			File dir = new File(uploadPath + saved);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (file.isEmpty()) {
				throw new ApiException(ResponseCode.DATA_MISSING);
			}

			if (!Files.exists(root)) {
				init();
			}

			try (InputStream inputStream = file.getInputStream()) {
				if (isNotValidExt(file.getOriginalFilename()))
					throw new ApiException(ResponseCode.SERVER_STORE_ERROR);

				Files.copy(inputStream, root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

				String fileHash = FileDigest.getChecksum(ROOT_PATH + saved + "/" + file.getOriginalFilename());

				if (isCompressed(file.getOriginalFilename()))
					decompress(ROOT_PATH + saved + "/" + file.getOriginalFilename(), ROOT_PATH + saved);

				result[0] = fileHash;
				result[1] = saved;
				result[2] = file.getOriginalFilename();

				return result;
			}
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		} catch (Throwable e) {
			throw new ApiException(ResponseCode.SERVER_DECOMPRESS_ERROR);
		}
	}

	public static void decompress(String zipFileName, String directory) throws Throwable {
		File zipFile = new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;

		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);

			while ((zipentry = zis.getNextEntry()) != null) {
				String filename = zipentry.getName();
				File file = new File(directory, filename);

				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					createFile(file, zis);
				}
			}
		} catch (Throwable e) {
			throw new ApiException(ResponseCode.SERVER_DECOMPRESS_ERROR);
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
	}

	private static void createFile(File file, ZipInputStream zis) {
		File parentDir = new File(file.getParent());
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}

		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			while ((size = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, size);
			}
		} catch (Throwable e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	public boolean isNotValidExt(String fullName) {
		return fullName.matches("..|\\/|\\|;");
	}

	public boolean isCompressed(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		String pt = "zip";

		return ext.matches(pt);
	}

	@Override
	public Stream<Path> loadAll() {
		return null;
	}

	@Override
	public Path load(String filename) {
		return Paths.get(this.uploadPath).resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename).normalize();
			Resource rsc = new UrlResource(file.toUri());

			if (rsc.exists() || rsc.isReadable())
				return rsc;
			else
				throw new ApiException(ResponseCode.SERVER_ERROR);
		} catch (MalformedURLException e) {
			throw new ApiException(ResponseCode.SERVER_ERROR);
		}
	}

	@Override
	public void deleteAll() throws UnsupportedOperationException {
		//
	}

	public String getContentType(HttpServletRequest request, Resource resource) {
		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			throw new ApiException(ResponseCode.SERVER_ERROR);
		}

		if (contentType == null) {
			return "application/octet-stream";
		}

		return contentType;
	}
}