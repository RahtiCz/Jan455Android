package com.example.jan455app;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import androidx.annotation.Nullable;

public class EntityCard {
    private int id;
    private String cardName;
    private int cardCount;
    private Boolean isFoil;
    private Boolean isSigned;
    private Boolean required;

    //foreign key
    private @Nullable int idDeck;

    public EntityCard() {
    }

    public EntityCard(int id, String cardName, int cardCount, Boolean isFoil, Boolean isSigned, Boolean required, @Nullable int idDeck) {
        this.id = id;
        this.cardName = cardName;
        this.cardCount = cardCount;
        this.isFoil = isFoil;
        this.isSigned = isSigned;
        this.required = required;
        this.idDeck = idDeck;
    }

    @Override
    public String toString() {
        return "EntityCard{" +
                "id=" + id +
                ", cardName='" + cardName + '\'' +
                ", cardCount=" + cardCount +
                ", isFoil=" + isFoil +
                ", isSigned=" + isSigned +
                ", required=" + required +
                ", idDeck=" + idDeck +
                '}';
    }


    public int getId() {
        return id;
    }

    public int getIdDeck() {
        return idDeck;
    }

    public void setIdDeck(int idDeck) {
        this.idDeck = idDeck;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public Boolean getFoil() {
        return isFoil;
    }

    public void setFoil(Boolean foil) {
        isFoil = foil;
    }

    public Boolean getSigned() {
        return isSigned;
    }

    public void setSigned(Boolean signed) {
        isSigned = signed;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}

