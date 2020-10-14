package com.forbitbd.storeapp.ui.store.supplier;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.models.Supplier;
import com.squareup.picasso.Picasso;

public class SupplierHolder extends BaseHolder<Supplier,SupplierListener> implements View.OnClickListener {

    //ImageView ivImage;
    TextView tvName,tvContact,tvEmail;

    ImageView ivEdit,ivDelete,ivAttach;



    public SupplierHolder(View itemView, SupplierListener listener) {
        super(itemView, listener);

        tvName = itemView.findViewById(R.id.name);
        tvContact = itemView.findViewById(R.id.contact);
        tvEmail = itemView.findViewById(R.id.email);

        ivAttach = itemView.findViewById(R.id.attach);
        ivEdit = itemView.findViewById(R.id.edit);
        ivDelete = itemView.findViewById(R.id.delete);

        ivEdit.setOnClickListener(this);
        ivAttach.setOnClickListener(this);
        ivDelete.setOnClickListener(this);

        itemView.setOnClickListener(this);
    }

    @Override
    public void bind(Supplier supplier) {

        tvName.setText(supplier.getName());
        tvContact.setText(supplier.getContact());
        tvEmail.setText(supplier.getEmail());

    }

    @Override
    public void onClick(View v) {
        if(v==itemView){
            getListener().onItemClick(getAdapterPosition());
        }else if(v==ivEdit){
            getListener().onItemUpdate(getAdapterPosition());
        }else if(v==ivAttach){
            getListener().onImageClick(getAdapterPosition());
        }else if(v==ivDelete){
            getListener().onItemRemove(getAdapterPosition());
        }
    }
}
