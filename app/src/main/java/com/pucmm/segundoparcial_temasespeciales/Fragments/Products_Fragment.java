package com.pucmm.segundoparcial_temasespeciales.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.pucmm.segundoparcial_temasespeciales.DB.DBMain;
import com.pucmm.segundoparcial_temasespeciales.R;

import java.util.List;

public class Products_Fragment extends Fragment {

    private EditText mEditTextName;
    private EditText mEditTextPrice;
    private Button mBtnAdd;
    private Button mBtnSave;
    private Button mBtnUpdate;
    private Button mBtnRemove;
    private Spinner mSpinnerCategory;
    private ArrayAdapter<String> mAdapter;
    private List<String> allCategories;

    private DBMain dbMain;

    private long _id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        mBtnAdd = view.findViewById(R.id.btn_add_Product);
        mBtnSave = view.findViewById(R.id.Btn_Save_Product);
        mEditTextName = view.findViewById(R.id.enter_name);
        mEditTextPrice = view.findViewById(R.id.enter_pricing);
        mBtnUpdate = view.findViewById(R.id.btn_Update_Product);
        mBtnRemove = view.findViewById(R.id.btn_Remove_Product);

        getActivity().setTitle("Add product");
        mSpinnerCategory = view.findViewById(R.id.spinner_category);
        loadSpinnerData();

        dbMain = new DBMain(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            getActivity().setTitle("Modify product");

            mEditTextName.setText(bundle.getString("nombre"));
            mEditTextPrice.setText(bundle.getString("precio"));
            _id = Long.parseLong(bundle.getString("id"));
            mSpinnerCategory.post(() ->
                    mSpinnerCategory.setSelection(mAdapter.getPosition((String)bundle.get("categoria"))));

            mBtnSave.setVisibility(View.GONE);
            mBtnUpdate.setVisibility(View.VISIBLE);
            mBtnRemove.setVisibility(View.VISIBLE);
        }

        mBtnAdd.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, new com.pucmm.segundoparcial_temasespeciales.Fragments.Categories_Fragment())
                    .addToBackStack("CATEGORIES_ACT").commit();
        });

        mBtnSave.setOnClickListener(v ->  manageProduct(bundle));
        mBtnUpdate.setOnClickListener(v -> manageProduct(bundle));
        mBtnRemove.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext()).setTitle("Delete product")
            .setMessage("¿Are you sure you want to delete the product?")
            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                dbMain.removeProduct(_id);
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, new com.pucmm.segundoparcial_temasespeciales.Fragments.All_Products_Fragment(), "ALL_PRODUCTS")
                        .addToBackStack("ALL_PRODUCTS").commit();
            })
            .setNegativeButton(android.R.string.cancel, null)
            .show();
        });
        return view;
    }

    private void manageProduct(Bundle bundle) {
        String nombre    = mEditTextName.getText().toString();
        String precio = mEditTextPrice.getText().toString();
        String categoria = allCategories.size() > 0 ? mSpinnerCategory.getSelectedItem().toString() : "";
        int count = mSpinnerCategory.getAdapter() != null ? mSpinnerCategory.getAdapter().getCount() : 0;

        String ACTION = "created";
        if (bundle != null) {
            dbMain.updateProducts(_id, nombre, precio, categoria);
            ACTION = "updated";
        } else {
            dbMain.createProduct(nombre, precio, categoria);
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, new com.pucmm.segundoparcial_temasespeciales.Fragments.All_Products_Fragment(), "ALL_PRODUCTS")
                .addToBackStack("ALL_PRODUCTS").commit();
    }

    public void loadSpinnerData() {
        DBMain dbManager = new DBMain(getContext());

        allCategories = dbManager.getCategories();

        mAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, allCategories);

        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mSpinnerCategory.setAdapter(mAdapter);
    }
}
