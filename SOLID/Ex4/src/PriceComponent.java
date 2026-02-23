
public interface PriceComponent {
    Money cost();
}

// so here we update anything that contributes to price 
// calculator just follow the interface 
// also Addonprice , RoomPrice follow this interface