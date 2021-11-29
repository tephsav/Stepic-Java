public static class UntrustworthyMailWorker implements MailService {
    private MailService[] mailSerives;
    private RealMailService realMailService;

    public UntrustworthyMailWorker(MailService[] mailSerives) {
        this.mailSerives = mailSerives;
        realMailService = new RealMailService();
    }

    public RealMailService getRealMailService() {
        return realMailService;
    }

    public Sendable processMail(Sendable mail) {
        for (int i = 0; i < mailSerives.length; i++) {
            mail = mailSerives[i].processMail(mail);
        }

        return realMailService.processMail(mail);
    }
}

public static class Spy implements MailService {
    private final Logger logger;

    public Spy(Logger logger) {
        this.logger = logger;
    }

    public Sendable processMail(Sendable mail) {
        String from = mail.getFrom();
        String to = mail.getTo();
        boolean isAustinPowers = from.equals(AUSTIN_POWERS) || to.equals(AUSTIN_POWERS);

        if (isAustinPowers && mail instanceof MailMessage) {
            logger.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1} \"{2}\"",
                    new Object[]{from, to, ((MailMessage) mail).getMessage()});
        } else if (!isAustinPowers && mail instanceof MailMessage){
            logger.log(Level.INFO, "Usual correspondence: from {0} to {1}",
                    new Object[]{from, to});
        }

        return mail;
    }
}

public static class Thief implements MailService {
    private int minValue;
    private int stolenValue;

    public Thief(int minValue) {
        this.minValue = minValue;
        stolenValue = 0;
    }

    public int getStolenValue(){
        return stolenValue;
    }

    public Sendable processMail(Sendable mail) {
        if (mail instanceof MailPackage) {
            MailPackage mailPackage = (MailPackage) mail;
            Package pack = mailPackage.getContent();

            if (pack.getPrice() >= minValue) {
                stolenValue += pack.getPrice();
                return new MailPackage(mailPackage.getFrom(), mailPackage.getTo(),
                        new Package("stones instead of " + pack.getContent(), 0));
            }
        }

        return mail;
    }
}

public static class Inspector implements MailService {

    public Sendable processMail(Sendable mail) {
        if (mail instanceof MailPackage) {
            MailPackage mailPackage = (MailPackage) mail;
            String content = mailPackage.getContent().getContent();

            if (content.equals(WEAPONS) || content.equals(BANNED_SUBSTANCE)) {
                throw new IllegalPackageException();
            } else if (content.contains("stones")) {
                throw new StolenPackageException();
            }
        }

        return mail;
    }
}

public static class IllegalPackageException extends RuntimeException {

    public IllegalPackageException() {
        super("WEAPONS or BANNED_SUBSTANCE here");
    }
}

public static class StolenPackageException extends RuntimeException {

    public StolenPackageException() {
        super("stones");
    }
}