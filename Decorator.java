interface Workable {
    void work();
}

abstract class Tool implements Workable {
}

// нож
class Knife extends Tool {
    private final String voice = "вжик-вжик";

    @Override
    public void work() {
        System.out.println(voice);
    }
}

// отвертка
class Screwdriver extends Tool {
    private final String voice = "тррр-тррр";

    @Override
    public void work() {
        System.out.println(voice);
    }
}

// гвоздодер
class NailPuller extends Tool {
    private final String voice = "кряк-кряк";

    @Override
    public void work() {
        System.out.println(voice);
    }
}

abstract class BaseDecorator implements Workable {
    private Workable wrapper;

    public BaseDecorator(Workable wrapper) {
        this.wrapper = wrapper;
    }

    public void work() {
        this.wrapper.work();
    }
}

class KnifeDecorator extends BaseDecorator {
    public KnifeDecorator(Workable wrapper) {
        super(wrapper);
    }

    @Override
    public void work() {
        super.work();
        new Knife().work();
    }
}

class ScrewdriverDecorator extends BaseDecorator {
    public ScrewdriverDecorator(Workable wrapper) {
        super(wrapper);
    }

    @Override
    public void work() {
        super.work();
        new Screwdriver().work();
    }
}

class NailPullerDecorator extends BaseDecorator {
    public NailPullerDecorator(Workable wrapper) {
        super(wrapper);
    }

    @Override
    public void work() {
        super.work();
        new NailPuller().work();
    }
}

public class Decorator {
    public static void main(String[] args) {
        Tool knife = new Knife();
        Tool screwdriver = new Screwdriver();
        Tool nailPuller = new NailPuller();

        BaseDecorator multiNailPuller1 = new KnifeDecorator(new ScrewdriverDecorator(nailPuller));
        BaseDecorator multiScrewdriver1 = new KnifeDecorator(new NailPullerDecorator(screwdriver));
        BaseDecorator multiKnife1 = new ScrewdriverDecorator(new NailPullerDecorator(knife));
        BaseDecorator multiNailPuller2 = new ScrewdriverDecorator(new KnifeDecorator(nailPuller));
        BaseDecorator multiScrewdriver2 = new NailPullerDecorator(new KnifeDecorator(screwdriver));
        BaseDecorator multiKnife2 = new NailPullerDecorator(new ScrewdriverDecorator(knife));

        BaseDecorator[] baseDecorators = {multiNailPuller1, multiScrewdriver1, multiKnife1, multiNailPuller2, multiScrewdriver2, multiKnife2};

        for (BaseDecorator bd : baseDecorators) {
            bd.work();
            System.out.println();
        }
    }
}