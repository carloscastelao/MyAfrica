package com.example.carlos.casopratico1;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Fragment_Adapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public Fragment_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                inicio_fragment tab1 = new inicio_fragment();
                return tab1;
            case 1:
                cidades_introducao tab2 = new cidades_introducao();
                return tab2;
            case 2:

                return new Cidades_Mapas();
            case 3:
                return new Data();
            case 4: return new Chamar();

            case 5:return new sms_fragment();

            case 6:return new Criador();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}