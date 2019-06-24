package com.example.carlos.casopratico1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chamar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chamar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chamar extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  final  int RequestPermissionCode=1;
    ListView listView;
    Cursor cursor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Chamar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chamar.
     */
    // TODO: Rename and change types and number of parameters
    public static Chamar newInstance(String param1, String param2) {
        Chamar fragment = new Chamar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chamar, container, false);
        listView=view.findViewById(android.R.id.list);
        if(CheckPermission()) {
            String[] campos=new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            String condicao= ContactsContract.Contacts.HAS_PHONE_NUMBER+"='1'";
            int [] v=new int[]{android.R.id.text1};
            cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, condicao, null,null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, campos, v);
            listView.setAdapter(adapter);


        }else{
            requestPermission();
        }
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public boolean CheckPermission(){

        int Result=ContextCompat.checkSelfPermission(getActivity(),READ_CONTACTS);
        int Result1=ContextCompat.checkSelfPermission(getActivity(),CALL_PHONE);

        return Result ==PackageManager.PERMISSION_GRANTED && Result1 ==PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        switch (requestCode){
            case RequestPermissionCode :{

                if (grantResults.length>0){
                    boolean StoragePermission= grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission =grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(StoragePermission&& RecordPermission){
                        Toast.makeText(getActivity(),"Permisao Aceite",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Permisao nao Aceite",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }


    public void requestPermission(){

        ActivityCompat.requestPermissions(getActivity(),new String[] { READ_CONTACTS,CALL_PHONE},RequestPermissionCode);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        Toast.makeText(getActivity(),"sol",Toast.LENGTH_SHORT).show();
        Cursor tlfcursor=getActivity().getApplicationContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ? ",
                new String[]{ "" +id },null);

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
                if(CheckPermission()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + telefone[which]));

                    startActivity(callIntent);
                    //startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefone[which], null)));
                }else{
                    requestPermission();
                }
            }

        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
}
}
