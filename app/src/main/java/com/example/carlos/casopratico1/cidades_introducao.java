package com.example.carlos.casopratico1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cidades_introducao.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cidades_introducao#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cidades_introducao extends Fragment implements Dialog_Fragment.OnInputSelected {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button button;
    static List<String> cidades=new ArrayList<>();
    ListView listView1;
    private Cursor cursor;
    private OnFragmentInteractionListener mListener;

    public cidades_introducao() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cidades_introducao.
     */
    // TODO: Rename and change types and number of parameters
    public static cidades_introducao newInstance(String param1, String param2) {
        cidades_introducao fragment = new cidades_introducao();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cidades_introducao, container, false);
        button=view.findViewById(R.id.addcidadesButton_id);
     ListView  listView=view.findViewById(R.id.list_view);
     listView1=listView;
        final  CriarDatabase criarDatabase =new CriarDatabase(getActivity());
        final SQLiteDatabase db=criarDatabase.getWritableDatabase();
        final String[] campodb={"cidade",BaseColumns._ID};
        cursor=db.query("cidades",campodb,null,null,null,null,"cidade ASC");
        int [] campoView= new int[]{android.R.id.text1};

       final SimpleCursorAdapter adapter= new SimpleCursorAdapter(getActivity(),android.R.layout.activity_list_item,cursor,campodb,campoView);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Fragment dialog= new Dialog_Fragment();
                dialog.setTargetFragment(cidades_introducao.this, 1);
                dialog.show(getFragmentManager(), "Dialog_Fragment");
            }
        });
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
    public void sendInput(String input) {

        final  CriarDatabase criarDatabase =new CriarDatabase(getActivity());
        final SQLiteDatabase db=criarDatabase.getWritableDatabase();
        final String[] campodb={"cidade",BaseColumns._ID};
        criarDatabase.adicionar(db,input);
        cursor=db.query("cidades",campodb,null,null,null,null,"cidade ASC");
        int [] campoView= new int[]{android.R.id.text1};

        final SimpleCursorAdapter adapter= new SimpleCursorAdapter(getActivity(),android.R.layout.activity_list_item,cursor,campodb,campoView);
        listView1.setAdapter(adapter);
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


}
