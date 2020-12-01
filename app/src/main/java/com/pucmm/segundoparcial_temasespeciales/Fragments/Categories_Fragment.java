package com.pucmm.segundoparcial_temasespeciales.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pucmm.segundoparcial_temasespeciales.DB.DBMain;
import com.pucmm.segundoparcial_temasespeciales.R;

public class Categories_Fragment extends Fragment {

    private EditText ETextCate;
    private Button mButtonSave;
    private DBMain DBMainManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_categories, container, false);



        ETextCate = view.findViewById(R.id.category_name_im);
        mButtonSave = view.findViewById(R.id.btn_Save_Cate);

        mButtonSave.setOnClickListener(v -> {
            String category = ETextCate.getText().toString();

            DBMainManager = new DBMain(getContext());
            DBMainManager.createCategory(category);

            ETextCate.setText("");
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, new Products_Fragment(), "PRODUCTS_ACT")
                    .commit();
        });
        return view;
    }

    @Override
    public void onDestroy() {
        DBMainManager.close();
        super.onDestroy();
    }
}