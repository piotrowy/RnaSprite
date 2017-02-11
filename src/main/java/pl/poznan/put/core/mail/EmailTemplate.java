package pl.poznan.put.core.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTemplate {

    public static final String EMAIL_TEXT = "<p>Dear Sir or Madam:</p>\n" + "<p>your computational request was managed."
            + "Now you can open you requested files and dive into biological data world.</p>\n"
            + "<p>Best regards\n RNAsprite team.</p>";
}
