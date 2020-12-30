package org.triplea.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ZipFileUtil {

  /**
   * Finds all game XMLs in a zip file. More specifically, given a zip file, finds all '*.xml' files
   */
  public List<URI> findXmlFilesInZip(final File zip) {
    final List<URI> zipFiles = new ArrayList<>();

    try (ZipFile zipFile = new ZipFile(zip);
        URLClassLoader loader = new URLClassLoader(new URL[] {zip.toURI().toURL()})) {

      final Enumeration<? extends ZipEntry> entries = zipFile.entries();
      while (entries.hasMoreElements()) {
        final ZipEntry entry = entries.nextElement();

        if (entry.getName().toLowerCase().endsWith(".xml")) {
          Optional.ofNullable(loader.getResource(entry.getName()))
              .map(url -> URI.create(url.toString().replace(" ", "%20")))
              .ifPresent(zipFiles::add);
        }
      }
    } catch (final IOException e) {
      log.error("Error reading zip file in: " + zip.getAbsolutePath(), e);
    }
    return zipFiles;
  }
}