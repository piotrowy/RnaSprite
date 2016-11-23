package pl.poznan.put.mail;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class MailContent {

    private String to;
    private String dear;
    private String subject;
    private String text;
    private List<File> attachments;

    public final String formatText(final String from) {
        return String.format("Dear {},\n\t{}\n{}", this.dear, this.text, from);
    }
}
