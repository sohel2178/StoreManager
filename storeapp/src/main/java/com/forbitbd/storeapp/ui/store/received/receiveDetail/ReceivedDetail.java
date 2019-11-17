package com.forbitbd.storeapp.ui.store.received.receiveDetail;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.MyUtil;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedDetail extends DialogFragment {

    private Receive receive;


    public ReceivedDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.receive = (Receive) getArguments().getSerializable(Constant.RECEIVED);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_received_detail, null);
        initView(view);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.MyDialog).create();
        alertDialog.setView(view);
        return alertDialog;

    }

    private void initView(View view) {

        TextView tvName = view.findViewById(R.id.name);
        TextView tvInvoice = view.findViewById(R.id.invoice_no);
        TextView tvSupplierName = view.findViewById(R.id.supplier_name);
        TextView tvDate = view.findViewById(R.id.date);
        TextView tvQuantity = view.findViewById(R.id.quantity);

        MaterialButton btnOk = view.findViewById(R.id.ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(receive!=null){
            tvDate.setText(MyUtil.getStringDate(receive.getDate()));
            tvSupplierName.setText(receive.getReceived_from().getName());
            tvInvoice.setText(receive.getInvoice_no());
            tvName.setText(receive.getName());
            tvQuantity.setText(String.valueOf(receive.getQuantity()).concat("  ").concat(receive.getUnit()));
        }


    }

}
