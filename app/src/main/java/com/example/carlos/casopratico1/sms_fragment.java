package com.example.carlos.casopratico1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sms_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sms_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sms_fragment extends ListFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  final  int RequestPermissionCode=1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    Cursor cursor;
    ListView listView;
    private OnFragmentInteractionListener mListener;

    public sms_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sms_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sms_fragment newInstance(String param1, String param2) {
        sms_fragment fragment = new sms_fragment();
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
      context=getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sms_fragment, container, false);
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
                Intent intent = new Intent(getContext(),Enviar_sms.class);
                intent.putExtra("numero",telefone[which]);
                startActivity(intent);
            }

        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();



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


        return Result ==PackageManager.PERMISSION_GRANTED ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void requestPermission(){

        ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.READ_CONTACTS},RequestPermissionCode);
    }




}
