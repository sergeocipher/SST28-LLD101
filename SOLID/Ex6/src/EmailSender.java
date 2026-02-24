public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    public void doSend(Notification n) {
        // LSP smell: truncates silently, changing meaning
        String body = n.body;
        if (body.length() > 40) throw new IllegalArgumentException("Email body too long");
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + body);
        audit.add("email sent");
    }
}
