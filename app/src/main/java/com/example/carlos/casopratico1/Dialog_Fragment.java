package com.example.carlos.casopratico1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Dialog_Fragment extends DialogFragment {

    Button bOk,bCancelar;
    EditText cidadesinput;

private  static final String Tag="Dialog_Fragment";


    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;
    @Nullable
    @Override
    public View onCreateView( final LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_frament,container,false);
        bCancelar =view.findViewById(R.id.cancelar_id);
        bOk=view.findViewById(R.id.addcidades_id);
        cidadesinput=view.findViewById(R.id.campocidade_id);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = cidadesinput.getText().toString();
                if(!input.isEmpty()) {
                    mOnInputSelected.sendInput(input);
                }
                getDialog().dismiss();
            }

        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(Tag, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }
}
