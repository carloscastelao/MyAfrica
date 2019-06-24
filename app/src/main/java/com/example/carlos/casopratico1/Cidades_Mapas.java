package com.example.carlos.casopratico1;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Cidades_Mapas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Cidades_Mapas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cidades_Mapas extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleMap map;
    MapView mapView;
    private Button button;
    private EditText editText;
    private Geocoder geocoder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Cidades_Mapas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cidades_Mapas.
     */
    // TODO: Rename and change types and number of parameters
    public static Cidades_Mapas newInstance(String param1, String param2) {
        Cidades_Mapas fragment = new Cidades_Mapas();
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
        View view=inflater.inflate(R.layout.fragment_cidades__mapas, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView2);
        if(mapView!=null){
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    editText=view.findViewById(R.id.id_editText_nome);
        button=view.findViewById(R.id.id_irpara_local);
       geocoder =new Geocoder(getContext());

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 localmapa();
             }
         });

        // Updates the location and zoom of the MapView

        return view;
    }

    private void localmapa() {

        LatLng coordernadas;
        String textMorada=editText.getText().toString();
        editText.setText("");
        List<Address>ads=null;
        try{
        ads=geocoder.getFromLocationName(textMorada,3);
         }catch (IOException ex){
            ex.printStackTrace();
        }

        android.location.Address Adress=ads.get(0);
        coordernadas =new LatLng(Adress.getLatitude(),Adress.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordernadas,14));
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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map=googleMap;
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);
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
