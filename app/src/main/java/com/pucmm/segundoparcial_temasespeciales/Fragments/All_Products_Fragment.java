package com.pucmm.segundoparcial_temasespeciales.Fragments;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.pucmm.segundoparcial_temasespeciales.DB.DBMain;
import com.pucmm.segundoparcial_temasespeciales.R;

public class All_Products_Fragment extends Fragment {

    //Animation translate_anim;

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DBMain DBMainManager;

    private final String[] columns = new String[]
            {
                    "_id",
                    "nombre",
                    "precio",
                    "categoria"
            };
    private final int[] txts = new int[]
            {
                    R.id.prod_Id,
                    R.id.Prod_Name,
                    R.id.prod_Pricing,
                    R.id.Prod_Cate
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View v = inflater.inflate(R.layout.fragment_all_products, container, false);
        listView = v.findViewById(R.id.all_products_view);

        DBMainManager = new DBMain(getContext());
        DBMainManager.open();
        adapter = new SimpleCursorAdapter(getContext(),
                R.layout.fragment_list_view, DBMainManager.getProducts(), columns, txts, 0);
        adapter.notifyDataSetChanged();

        //Animate Recyclerview
        //translate_anim = AnimationUtils.loadAnimation(getContext(), R.anim.translate_anim);
       // ConstraintLayout.setAnimation(translate_anim);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView productId = view.findViewById(R.id.prod_Id);
            TextView name = view.findViewById(R.id.Prod_Name);
            TextView price = view.findViewById(R.id.prod_Pricing);
            TextView category = view.findViewById(R.id.Prod_Cate);

            Products_Fragment fragment = new Products_Fragment();

            Bundle bundle = new Bundle();
            bundle.putString("id", productId.getText().toString());
            bundle.putString("nombre", name.getText().toString());
            bundle.putString("precio", price.getText().toString());
            bundle.putString("categoria", category.getText().toString());

            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, fragment, "PRODUCTS_ACT")
                    .addToBackStack("PRODUCTS_ACT").commit();
        });

        return v;
    }

    @Override
    public void onDestroy() {
        DBMainManager.close();
        super.onDestroy();
    }
}