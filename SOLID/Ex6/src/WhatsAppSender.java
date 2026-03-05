public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    public void doSend(Notification n) {
        // LSP violation: tightens precondition
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}

