package com.jp.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Takes a given PDF file, prevents any modification to it, and applies relevant passwords
 */
public class PdfEncrypt {

    private static final Logger LOGGER = Logger.getLogger(PdfEncrypt.class.getName());

    private static final String FILENAME = "";
    private static final String OWNER_PASSWORD = "";
    private static final String USER_PASSWORD = "";
    private static final int KEY_LENGTH = 256;

    public static void main(String[] args) {

        // Create permission configuration
        AccessPermission ap = new AccessPermission();
        ap.setReadOnly();
        ap.setCanModify(false);

        // Create protection policy
        StandardProtectionPolicy spp = new StandardProtectionPolicy(OWNER_PASSWORD, USER_PASSWORD, ap);
        spp.setEncryptionKeyLength(KEY_LENGTH);

        LOGGER.info("opening '" + FILENAME + "'");
        try (PDDocument doc = PDDocument.load(new File(FILENAME))) {

            // Apply policy
            doc.protect(spp);
            doc.save(FILENAME + "_encrypted.pdf");

        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }

        LOGGER.info("Finished");
    }
}
