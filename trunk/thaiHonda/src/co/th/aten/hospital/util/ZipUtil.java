/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Aten
 */
public class ZipUtil {

    

    public static boolean zipFile(String pathFrom, String[] fileFrom, String fileZip) throws FileNotFoundException, IOException {
        boolean isSuccess = false;
        System.out.println("ZIP file=" + fileZip);
        byte[] buffer = new byte[18024];
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fileZip));

        // Set the compression ratio
        out.setLevel(Deflater.DEFAULT_COMPRESSION);

        // iterate through the array of files, adding each to the zip file
        for (int i = 0; i < fileFrom.length; i++) {
            String file = pathFrom+"/"+fileFrom[i];
            System.out.println(file);
            // Associate a file input stream for the current file
            FileInputStream in = new FileInputStream(file);

            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(file));
            // Transfer bytes from the current file to the ZIP file
            //out.write(buffer, 0, in.read(buffer));

            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            // Close the current entry
            out.closeEntry();
            // Close the current file input stream
            in.close();
        }
        // Close the ZipOutPutStream
        out.close();
        isSuccess = true;
        System.out.println("zip success ");


        return isSuccess;
    }

    public static boolean upzipFile(String fileZip, String fileDst) {
        boolean isSuccess = false;
        int BUFFER = 2048;
        try {
            System.out.println("Example of ZIP file decompression.");

            // Specify file to decompress
            String inFileName = fileZip;
            // Specify destination where file will be unzipped
            String destinationDirectory = fileDst;

            File sourceZipFile = new File(inFileName);
            File unzipDestinationDirectory = new File(destinationDirectory);

            // Open Zip file for reading
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

            // Create an enumeration of the entries in the zip file
            Enumeration zipFileEntries = zipFile.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

                String currentEntry = entry.getName();
                System.out.println("Extracting: " + entry);

                File destFile = new File(unzipDestinationDirectory, currentEntry);

                // grab file's parent directory structure
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                // extract file if not a directory
                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
            zipFile.close();
            isSuccess = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return isSuccess;
    }
}
