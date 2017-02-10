package pl.poznan.put.core.mail;

public enum EmailTemplate {

    ERROR_TEMPLATE("./src/main/resources/mail/error.emailTemplate.vm"),
    WITH_ATTACHMENTS("./src/main/resources/mail/withAttachments.emailTemplate.vm"),
    WITH_ATTACHMENTS_ERRORS("./src/main/resources/mail/withAttachmentsErrors.emailTemplate.vm");

    private final String text;

    EmailTemplate(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
