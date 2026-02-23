public class AddonPrice implements PriceComponent{
    private AddOn addOn;

    public AddonPrice(AddOn addOn){
        this.addOn = addOn;
    }

    @Override
    public Money cost(){
            return switch (addOn) {
            case MESS -> new Money(1000.0);
            case LAUNDRY -> new Money(500.0);
            case GYM -> new Money(300.0);
        };
    }

}

// if we want to add more add-on just add in addon enum class 
// and add here in the cost method