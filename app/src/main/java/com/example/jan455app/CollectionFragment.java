package com.example.jan455app;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    Button btnAdd, btnShowAll, btnShowRequired;
    CheckBox isFoil, isSigned;
    SwitchCompat isReguired;
    EditText txtCardName, txtCardCount;
    ListView cardList;

    DatabaseHelper dbHelp;
    ArrayAdapter adCards;

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_collection, container, false);

        btnAdd = (Button) v.findViewById(R.id.btnAddCard);
        btnShowAll = (Button) v.findViewById(R.id.btnShowAll);
        btnShowRequired = (Button) v.findViewById(R.id.btnShowRequired);
        isFoil = (CheckBox) v.findViewById((R.id.chkIsFoil));
        isSigned = (CheckBox) v.findViewById((R.id.chkIsSigned));
        isReguired = (SwitchCompat) v.findViewById((R.id.swIsRequired));
        txtCardName = (EditText) v.findViewById((R.id.txtCardName));
        txtCardCount = (EditText) v.findViewById((R.id.txtCardCount));
        cardList = (ListView) v.findViewById(R.id.cardsList);

        dbHelp = new DatabaseHelper(getActivity());

        showAllCards();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityCard addedCard = new EntityCard();

                try{
                    addedCard = new EntityCard(-1, String.valueOf(txtCardName.getText()),
                            Integer.parseInt(String.valueOf(txtCardCount.getText())), isFoil.isChecked(),
                            isSigned.isChecked(), isReguired.isChecked(),-1 );

                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error card data!",Toast.LENGTH_LONG).show();
                }

                if(addedCard!=null)
                    if(dbHelp.addCard(addedCard)) {
                        Toast.makeText(getActivity(), "Card was added!", Toast.LENGTH_LONG).show();
                        resetAllFields();
                    }

                showAllCards();
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllCards();
            }
        });

        btnShowRequired.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adCards = new ArrayAdapter<EntityCard>(getActivity(), android.R.layout.simple_dropdown_item_1line, dbHelp.getOnlyRequired());
                cardList.setAdapter(adCards);
            }
        }));

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntityCard deletedCard = (EntityCard) parent.getItemAtPosition(position);
                if(dbHelp.delete(deletedCard))
                    Toast.makeText(getActivity(), "Card " + deletedCard.getCardName() + "was deleted!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "Error when delete " + deletedCard.getCardName() + " !", Toast.LENGTH_LONG).show();

                showAllCards();
            }
        });

        return v;
    }

    private void showAllCards() {
        adCards = new ArrayAdapter<EntityCard>(getActivity(), android.R.layout.simple_dropdown_item_1line, dbHelp.getAll());
        cardList.setAdapter(adCards);
    }

    private void resetAllFields(){
        isFoil.setChecked(false);
        isReguired.setChecked(false);
        isSigned.setChecked(false);
        txtCardCount.setText("0");
        txtCardName.setText("");

    }
}