package com.forbitbd.storeapp.ui.store.comsumed.consumeDetail;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumeDetail extends DialogFragment {
    
    private Consume consume;


    public ConsumeDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.consume = (Consume) getArguments().getSerializable(Constant.CONSUME);
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consume_detail, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_consume_detail, null);
        initView(view);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.MyDialog).create();
        alertDialog.setView(view);
        return alertDialog;

    }

    private void initView(View view) {
        TextView tvName = view.findViewById(R.id.name);
        TextView tvIssueTo = view.findViewById(R.id.issue_to);
        TextView tvWhereUsed = view.findViewById(R.id.where_used);
        TextView tvDate = view.findViewById(R.id.date);
        TextView tvQuantity = view.findViewById(R.id.quantity);

        MaterialButton btnOk = view.findViewById(R.id.ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(consume!=null){
            tvDate.setText(MyUtil.getStringDate(consume.getDate()));
            tvIssueTo.setText(consume.getIssue_to());
            tvWhereUsed.setText(consume.getWhere_used().getName());
            tvName.setText(consume.getName());
            tvQuantity.setText(String.valueOf(consume.getQuantity()).concat("  ").concat(consume.getUnit()));
        }
    }

}
