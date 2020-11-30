package com.example.jan455app;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    Button btnAdd;
    CheckBox isFoil, isSigned;
    SwitchCompat isReguired;
    EditText txtCardName, txtCardCount;


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
        isFoil = (CheckBox) v.findViewById((R.id.chkIsFoil));
        isSigned = (CheckBox) v.findViewById((R.id.chkIsSigned));
        isReguired = (SwitchCompat) v.findViewById((R.id.swIsReguired));
        txtCardName = (EditText) v.findViewById((R.id.txtCardName));
        txtCardCount = (EditText) v.findViewById((R.id.txtCardCount));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityCard addedCard = new EntityCard();
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

                try{
                    addedCard = new EntityCard(-1, String.valueOf(txtCardName.getText()),
                            Integer.parseInt(String.valueOf(txtCardCount.getText())), isFoil.isChecked(),
                            isSigned.isChecked(), isReguired.isChecked(),-1 );

                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error card data!",Toast.LENGTH_LONG).show();
                }

                if(addedCard!=null)
                    if(databaseHelper.addCard(addedCard))
                        Toast.makeText(getActivity(),"Card was added!",Toast.LENGTH_LONG).show();

            }
        });
        return v;
    }
}