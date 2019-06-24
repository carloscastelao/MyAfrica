package com.example.carlos.casopratico1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

abstract class sms_fragment_interface extends ListFragment {
    public abstract void onItemClick(ListView l, View view, int position, long id);
    /*Cursor tlfcursor=getActivity().getApplicationContext().getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID, new String[]{""+id},null);
    int ntelefones= tlfcursor.getCount();
    final String[] telefone=new String[ntelefones];
    int x=0;
        while(tlfcursor.moveToNext()){
        int col =tlfcursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        telefone[x++]=tlfcursor.getString(col);
        Toast.makeText(getActivity(),"sola",Toast.LENGTH_SHORT).show();
    }
        tlfcursor.close();
    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_dialog_numero);
        builder.setItems(telefone, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

    });
    AlertDialog alertDialog=builder.create();
        alertDialog.show();*/
}
