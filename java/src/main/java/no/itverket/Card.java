package no.itverket;

class Card {
    Suit suit;
    int rank;
    String pictureCard;

    Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        this.pictureCard = checkPictureCard(rank);
    }

    private String checkPictureCard(int rank){
        switch (rank){
            case 11:
                return pictureCard = "J";
            case 12:
                return pictureCard = "Q";
            case 13:
                return pictureCard = "K";
            case 14:
                return pictureCard = "A";
            default:
                return pictureCard = "";
        }
    }
}
