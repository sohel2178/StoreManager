package com.forbitbd.storeapp.ui.store.comsumed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;

public class ConsumeHolder extends BaseHolder<Consume,ConsumeListener> implements View.OnClickListener {

    TextView tvName,tvSupplier;

    ImageView ivEdit,ivDelete,ivAttach;

    private SharedProject.Permission storePermission;

    public ConsumeHolder(View itemView, ConsumeListener listener,SharedProject.Permission storePermission) {
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
    }

    @Override
    public void bind(Consume consume) {

        tvName.setText(consume.getName()
                .concat(" ")
                .concat(String.valueOf(consume.getQuantity()))
                .concat(" ").concat(consume.getUnit())
        );

        tvSupplier.setText("Used In : ".concat(consume.getWhere_used().getName()));



    }

    @Override
    public void onClick(View view) {
        if(view==ivAttach){
            getListener().onImageClick(getAdapterPosition());
        }else if(view==ivEdit){
            getListener().onItemUpdate(getAdapterPosition());
        }else if(view==ivDelete){
            getListener().onItemRemove(getAdapterPosition());
        }else if(view==itemView){
            getListener().onItemClick(getAdapterPosition());
        }
    }
}
