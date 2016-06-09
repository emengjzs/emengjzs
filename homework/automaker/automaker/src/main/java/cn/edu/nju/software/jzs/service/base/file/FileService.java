package cn.edu.nju.software.jzs.service.base.file;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/11.
 */
@Service
public class FileService {
    Logger log = LoggerFactory.getLogger(getClass());

    public void copy(File from, File to) throws IOException {
        if (to.isDirectory()) {
            to = new File(to, from.getName());
        }
        log.debug("Copy file [{}] to [{}]", from.getAbsolutePath(), to.getAbsolutePath());
        FileCopyUtils.copy(from, to);
    }


    public void deleteDictionary(File f) throws IOException {
        FileUtils.deleteDirectory(f);
    }

    public void unzip(File zipFile, String to) throws IOException {
        if (!to.endsWith("/")) {
            to += "/";
        }
        File file = zipFile;

        if (!file.exists()) {
            throw new IOException("File " + zipFile.getAbsolutePath() + " Not Found!");
        }
        ZipArchiveInputStream zais = null;
        try {
            zais = new ZipArchiveInputStream(new FileInputStream(file));
            ArchiveEntry archiveEntry = null;
            while ((archiveEntry = zais.getNextEntry()) != null) {
                String entryFileName = archiveEntry.getName();
                String entryFilePath = to + entryFileName;
                File entryFile = new File(entryFilePath);
                if (archiveEntry.isDirectory()) {
                    log.debug("Unzip dict [{}] to [{}]", archiveEntry.getName(), entryFile.getAbsolutePath());
                    if (!entryFile.exists()) {
                        entryFile.mkdirs();
                    }
                } else {
                    log.debug("Unzip file [{}] to [{}]", archiveEntry.getName(), entryFile.getAbsolutePath());
                    byte[] content = new byte[4096];
                    FileOutputStream os = new FileOutputStream(entryFile);
                    int actualRead = 0;
                    while ((actualRead = zais.read(content)) != -1) {
                        os.write(content, 0, actualRead);
                    }
                    os.flush();
                    os.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            if (zais != null) {
                zais.close();
            }
        }

    }

}
