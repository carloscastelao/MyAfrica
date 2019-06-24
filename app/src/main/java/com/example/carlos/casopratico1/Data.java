package com.example.carlos.casopratico1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Data.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Data extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CalendarView calendarView;
    int diad,mesm,anaa;
    final Calendar DataProxAniversario=Calendar.getInstance();
    Button button;
    long millis1;
    long millis2;
    long difiis;
    long countniver;
    long diffisday;
    long milidDataniver;
    boolean estado=true;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Data.
     */
    // TODO: Rename and change types and number of parameters
    public static Data newInstance(String param1, String param2) {
        Data fragment = new Data();
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

        View view=inflater.inflate(R.layout.fragment_data, container, false);
        calendarView=view.findViewById(R.id.id_calendarView);
        button =view.findViewById(R.id.id_botao_guardar_data);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView calendarView, int i, int i1, int i2) {

                final Calendar DataActual=Calendar.getInstance();
                int ano =DataActual.get(Calendar.YEAR);
                int mes=DataActual.get(Calendar.MONTH);
                int dia=DataActual.get(Calendar.DAY_OF_MONTH);
              final   Calendar dataaniversario= Calendar.getInstance();

                dataaniversario.set(i,i1,i2);
                if(i1>mes||i1==mes&&i2<dia) {
                    DataProxAniversario.set(ano+1, i1, i2);
                }else {
                    estado=false;
                    DataProxAniversario.set(ano, i1, i2);
                }
                milidDataniver=dataaniversario.getTimeInMillis();
                millis1=DataActual.getTimeInMillis();
                millis2=DataProxAniversario.getTimeInMillis();
                countniver=ano-i;
                button.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                                /*  if (estado)
                                      difiis=millis2-millis1;
                                  else{
                                      difiis=millis1-millis2;
                                  }*/
                               difiis=millis2-millis1;
                                   diffisday=difiis/(24*60*60*1000);

                                   SharedPreferences sharedPreferences = getActivity().getPreferences(0);
                                   SharedPreferences.Editor editor = sharedPreferences.edit();
                                   editor.putLong("valordia", diffisday);
                                   editor.putLong("countniver", countniver);
                                   editor.commit();

                           }
                       });





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
