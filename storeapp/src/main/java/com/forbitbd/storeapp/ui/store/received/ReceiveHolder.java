package com.forbitbd.storeapp.ui.store.received;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.models.Receive;

public class ReceiveHolder extends BaseHolder<Receive,ReceiveListener> implements View.OnClickListener {

    TextView tvName,tvSupplier;

    ImageView ivEdit,ivDelete,ivAttach;

    private SharedProject.Permission storePermission;

    public ReceiveHolder(View itemView, ReceiveListener listener, SharedProject.Permission storePermission) {
        super(itemView, listener);
        this.storePermission = storePermission;
        ivAttach = itemView.findViewById(R.id.attach);
        ivEdit = itemView.findViewById(R.id.edit);
        ivDelete = itemView.findViewById(R.id.delete);

        tvName = itemView.findViewById(R.id.name);
        tvSupplier = itemView.findViewById(R.id.supplier);


        ivAttach.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        itemView.setOnClickListener(this);

        if(storePermission.isUpdate()){
            ivEdit.setVisibility(View.VISIBLE);
        }else{
            ivEdit.setVisibility(View.GONE);
        }

        if(storePermission.isDelete()){
            ivDelete.setVisibility(View.VISIBLE);
        }else{
            ivDelete.setVisibility(View.GONE);
        }

       /* if(storePermission!=null){

        }else{
            ivAttach.setEnabled(false);
            ivDelete.setEnabled(false);
            ivEdit.setEnabled(false);
        }*/



    }

    @Override
    public void bind(Receive receive) {

        tvName.setText(receive.getName()
                .concat(" ")
                .concat(String.valueOf(receive.getQuantity()))
                .concat(" ").concat(receive.getUnit())
        );

        tvSupplier.setText("Supplier: ".concat(receive.getReceived_from().getName()));

    }

    @Override
    public void onClick(View view) {
        if(view==itemView){
            getListener().onItemClick(getAdapterPosition());
        }else if(view==ivEdit){
            AppPreference.getInstance(view.getContext()).increaseCounter();
            getListener().onItemUpdate(getAdapterPosition());
        }else if(view==ivDelete){
            AppPreference.getInstance(view.getContext()).increaseCounter();
            getListener().onItemRemove(getAdapterPosition());
        }else if(view==ivAttach){
            getListener().onImageClick(getAdapterPosition());
        }
    }
}
