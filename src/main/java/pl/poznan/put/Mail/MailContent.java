package pl.poznan.put.Mail;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class MailContent {

    private String to;
    private String dear;
    private String content;
    private String subject;
    private String text;
    private List<File> attachments;
}
